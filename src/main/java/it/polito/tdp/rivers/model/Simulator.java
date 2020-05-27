package it.polito.tdp.rivers.model;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

public class Simulator {

	private double Q = -1.0;
	private double C = -1.0;
	private double probabilita = 0.05; // Probabilità richiesta acqua straordinaria
	private int entita = 10; // quante volte l'evento straordinazio è > dell'ordinario
	private double proporzione = 0.8; // proporzione flusso minimo rapportato con flusso medio

	private PriorityQueue<Event> coda;

	private List<Double> occupazioneGiornaliera;
	private int insoddisfatti; // giorni senza erogazione minima

	public void init(double k, River fiume) {
		coda = new PriorityQueue<Event>();
		occupazioneGiornaliera = new LinkedList<Double>();

		// Capacità giornaliera
		Q = k * fiume.getFlowAvg() * 30 * 60 * 60 * 24;
		// Occupazione iniziale
		C = Q / 2;
		
		occupazioneGiornaliera.add(C);

		insoddisfatti = 0;

		// Riempio la coda
		for (Flow f : fiume.getFlows()) {
			double flowIn = f.getFlow() * 60 * 60 * 24;
			double flowOut = generaFlussoUscita(fiume.getFlowAvg());
			Event e = new Event(f.getDay(), flowIn, flowOut);
			coda.add(e);
		}

		// SIMULAZIONE VERA E PROPRIA
		while (!coda.isEmpty()) {
			Event oggi = coda.poll();
			double quantitàOdierna = occupazioneGiornaliera.get(occupazioneGiornaliera.size() - 1);

			// flusso in entrata
			quantitàOdierna += oggi.getFlowIn();
			if (quantitàOdierna > Q) { // tracimazione
				quantitàOdierna = Q;
				System.out.println("TRACIMAZIONE " + oggi.getDate().toString());
			}
			// flusso in uscita
			quantitàOdierna -= oggi.getFlowOut();
			if (quantitàOdierna < 0) { // non ho acqua
				quantitàOdierna = 0.0;
				insoddisfatti++;
				System.out.println("CARENZA D'ACQUA " + oggi.getDate().toString());
			}

			occupazioneGiornaliera.add(quantitàOdierna);
		}

	}

	private double generaFlussoUscita(double flowAvg) {
		Double p = Math.random();
		if (p > probabilita) {
			// caso portata ordinaria
			return flowAvg * proporzione * 60 * 60 * 24;
		} else {
			// evento straordinario
			return flowAvg * proporzione * entita * 60 * 60 * 24;
		}
	}

	public double getOccupazioneMedia() {
		double somma = 0.0;
		for (double d : occupazioneGiornaliera) {
			somma += d;
		}
		return somma / occupazioneGiornaliera.size();
	}
	
	public int getInsoddisfatti() {
		return insoddisfatti;
	}
	
	public int getGiorniTotali() {
		return occupazioneGiornaliera.size();
	}

	public double getQ() {
		return Q;
	}
	
	
}
