package it.polito.tdp.rivers.model;

import java.time.LocalDate;
import java.util.List;

import it.polito.tdp.rivers.db.RiversDAO;

public class Model {
	
	private RiversDAO dao;
	private Simulator sim;

	public Model() {
		dao = new RiversDAO();
		sim = new Simulator();
	}

	public List<River> getAllRivers() {
		return dao.getAllRivers();
	}

	public LocalDate getPrimaData(River fiume) {
		return dao.getPrimaData(fiume);
	}

	public LocalDate getUltimaData(River fiume) {
		return dao.getUltimaData(fiume);
	}

	public int getNumeroMisurazioni(River fiume) {
		return dao.getNumeroMisurazioni(fiume);
	}

	public Double getMedia(River fiume) {
		return dao.getMedia(fiume);
	}
	
	private List<Flow> getFlows(River fiume) {
		return dao.getFlows(fiume);
	}
	
	public void simula(double k, River fiume) {
		fiume.setFlowAvg(getMedia(fiume));
		fiume.setFlows(getFlows(fiume));
		sim.init(k, fiume);
	}
	
	public double getOccupazioneMedia() {
		return sim.getOccupazioneMedia();
	}
	
	public int getInsoddisfatti() {
		return sim.getInsoddisfatti();
	}
	
	public int getGiorniTotali() {
		return sim.getGiorniTotali();
	}
	
	public double getQ() {
		return sim.getQ();
	}

	
	
	
	
	

}
