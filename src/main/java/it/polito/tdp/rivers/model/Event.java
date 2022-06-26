package it.polito.tdp.rivers.model;

import java.time.LocalDate;

public class Event {

	public enum EventType{
		RIEMPIMENTO_ACQUA,
		SVUOTAMENTO_ACQUA,
		TRACIMAZIONE
	}
	
	private EventType type;
	private LocalDate date;
	private double qta;
	
	public Event(EventType type, LocalDate date, double qta) {
		super();
		this.type = type;
		this.date = date;
		this.qta = qta;
	}

	public EventType getType() {
		return type;
	}

	public LocalDate getDate() {
		return date;
	}

	public double getQta() {
		return qta;
	}

	public void setType(EventType type) {
		this.type = type;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public void setQta(double qta) {
		this.qta = qta;
	}
	
	
	
	
}
