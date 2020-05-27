package it.polito.tdp.rivers.model;

import java.time.LocalDate;

public class Event implements Comparable<Event>{
	
	private LocalDate date;
	private double flowIn;
	private double flowOut;
	
	public Event(LocalDate date, double flowIn, double flowOut) {
		this.date = date;
		this.flowIn = flowIn;
		this.flowOut = flowOut;
	}

	public LocalDate getDate() {
		return date;
	}

	public double getFlowIn() {
		return flowIn;
	}

	public double getFlowOut() {
		return flowOut;
	}

	@Override
	public int compareTo(Event other) {
		return this.date.compareTo(other.date);
	}
	
}
