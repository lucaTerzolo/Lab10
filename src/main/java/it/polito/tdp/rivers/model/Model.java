package it.polito.tdp.rivers.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import it.polito.tdp.rivers.db.RiversDAO;

public class Model {
	private RiversDAO dao;
	private Map<Integer,River> idMap;
	private List<Flow> flow;
	private Simulator sim;
	
	public Model() {
		super();	
		dao=new RiversDAO();
		idMap=new HashMap<>();
		flow=new LinkedList<>();
		sim=new Simulator();
	}

	public List<River> getAllRiver() {
		return dao.getAllRivers(idMap);
	}
	
	public double avgFlow(River r) {
		if(flow.isEmpty())
			flow.addAll(dao.getFlow(idMap));
		double avg=0;
		int n=0;
		
		for(Flow f: flow) {
			if(f.getRiver().equals(r)) {
				avg+=f.getFlow();
				n++;
			}
		}
		avg=avg/n;
		this.getFlowRiver(r);
		r.setFlowAvg(avg*3600*24);
		System.out.println("media fiume "+r.getName()+" è "+avg);
		return avg*3600*24;
	}
	
	public int getNMisure (River r) {
		int n=0;
		for(Flow f:flow)
			if(f.getRiver().equals(r))
				n++;
		
		return n;
	}
	
	public void getFlowRiver(River r) {
		r.setFlows(this.dao.getFlowRiver(r));
	}
	
	public LocalDate getDataInizio(River r) {
		for(Flow f:flow)
			if(f.getRiver().equals(r)) {
				return f.getDay();
			}
		return null;
	}
	
	public LocalDate getDataFine(River r) {
		
		LocalDate data = null;
		
		for(Flow f:flow) {
			if(f.getRiver().equals(r)) {
				data=f.getDay();
			}	
		}
		return data;
	}

	public Map<Integer, River> getIdMap() {
		return idMap;
	}
	
	public String simula(River r, double k) {
		sim.init(r,k);
		sim.run();
		return "Giorni in cui non si è potuta garantire l’erogazione minima: "+sim.getnGiorniAnomali()+
				"\nl’occupazione media del bacino: "+sim.getcMed();
	}

}
