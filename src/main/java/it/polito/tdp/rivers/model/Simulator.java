package it.polito.tdp.rivers.model;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import it.polito.tdp.rivers.db.RiversDAO;


public class Simulator {
	private int secondiGiorno=24*3600;
	//Modello
	private List<Flow> flow;
	
	//Parametri (specificati nel testo)
	private double Q;
	private double k;
	private double C;
	private double fmed;
	private double foutMin;
	
	//Statistiche
	private double cMed;
	private double cTot;
	private int nGiorniAnomali;
	private int nGiorniTot;
	
	public Simulator() {
	}
	
	public void init(River r, double k) {		
		fmed=r.getFlowAvg(); //flusso medio
		flow=new ArrayList<>(r.getFlows()); //carico tutti i flussi del 
		Q=k*fmed*30; //capienza bacino
		C=Q/2; //capienza inizialmente a questo valore
		foutMin=0.8*fmed; //Flusso in uscita minimo
		cMed=0;
		cTot=0;
		nGiorniAnomali=0;
		nGiorniTot=0;
	}
	
	public void run() {
		for(Flow f: flow) {
			double fin=f.getFlow();
			double fout=0;
			C+=fin*this.secondiGiorno; //Riempio
			if(C>Q) {
				//tracimazione
				fout=C-Q;
				this.nGiorniAnomali++;
			}else {
			
				double caso=Math.random();
				if(caso<0.05) {
					fout=10*this.foutMin;
					this.nGiorniAnomali++;
				}else {
					fout=this.foutMin;
				}
			}
			C-=fout; //Svuoto
			this.cTot+=C;
			this.nGiorniTot++;
		}
		this.cMed=this.cTot/this.nGiorniTot;
	}

	public double getcMed() {
		return cMed;
	}

	public int getnGiorniAnomali() {
		return nGiorniAnomali;
	}
	
	
}
