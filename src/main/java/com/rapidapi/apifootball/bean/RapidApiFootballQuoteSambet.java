package com.rapidapi.apifootball.bean;

import java.util.List;

/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
public class RapidApiFootballQuoteSambet {
	

	private List<Risultati> risultati;
	
	
	public List<Risultati> getRisultati() {
		return risultati;
	}
	public void setRisultati(List<Risultati> risultati) {
		this.risultati = risultati;
	}


	static public class Risultati {
		
		private int squadraHome;
		private int squadraAway;
		private double val;
		
		public Risultati(int squadraHome, int squadraAway) {
			super();
			this.squadraHome = squadraHome;
			this.squadraAway = squadraAway;
		}
		
		public Risultati() { }

		public int getSquadraHome() {
			return squadraHome;
		}
		public void setSquadraHome(int squadraHome) {
			this.squadraHome = squadraHome;
		}
		public int getSquadraAway() {
			return squadraAway;
		}
		public void setSquadraAway(int squadraAway) {
			this.squadraAway = squadraAway;
		}
		public double getVal() {
			return val;
		}
		public void setVal(double val) {
			this.val = val;
		}

	}
	
	
	static public class Statistics {
		private double squadra_Home_GolFatti;
		private double squadra_Home_GolSubiti;
		private double squadra_Home_NumeroPartGiocate;
		private double squadra_Away_GolFatti; 
		private double squadra_Away_GolSubiti;
		private double squadra_Away_NumeroPartGiocate;
		
		
		public Statistics(double squadra_Home_GolFatti, double squadra_Home_GolSubiti,
				double squadra_Home_NumeroPartGiocate, double squadra_Away_GolFatti, double squadra_Away_GolSubiti,
				double squadra_Away_NumeroPartGiocate) {
			super();
			this.squadra_Home_GolFatti = squadra_Home_GolFatti;
			this.squadra_Home_GolSubiti = squadra_Home_GolSubiti;
			this.squadra_Home_NumeroPartGiocate = squadra_Home_NumeroPartGiocate;
			this.squadra_Away_GolFatti = squadra_Away_GolFatti;
			this.squadra_Away_GolSubiti = squadra_Away_GolSubiti;
			this.squadra_Away_NumeroPartGiocate = squadra_Away_NumeroPartGiocate;
		}
		
		
		public double getSquadra_Home_GolFatti() {
			return squadra_Home_GolFatti;
		}
		public void setSquadra_Home_GolFatti(double squadra_Home_GolFatti) {
			this.squadra_Home_GolFatti = squadra_Home_GolFatti;
		}
		public double getSquadra_Home_GolSubiti() {
			return squadra_Home_GolSubiti;
		}
		public void setSquadra_Home_GolSubiti(double squadra_Home_GolSubiti) {
			this.squadra_Home_GolSubiti = squadra_Home_GolSubiti;
		}
		public double getSquadra_Home_NumeroPartGiocate() {
			return squadra_Home_NumeroPartGiocate;
		}
		public void setSquadra_Home_NumeroPartGiocate(double squadra_Home_NumeroPartGiocate) {
			this.squadra_Home_NumeroPartGiocate = squadra_Home_NumeroPartGiocate;
		}
		public double getSquadra_Away_GolFatti() {
			return squadra_Away_GolFatti;
		}
		public void setSquadra_Away_GolFatti(double squadra_Away_GolFatti) {
			this.squadra_Away_GolFatti = squadra_Away_GolFatti;
		}
		public double getSquadra_Away_GolSubiti() {
			return squadra_Away_GolSubiti;
		}
		public void setSquadra_Away_GolSubiti(double squadra_Away_GolSubiti) {
			this.squadra_Away_GolSubiti = squadra_Away_GolSubiti;
		}
		public double getSquadra_Away_NumeroPartGiocate() {
			return squadra_Away_NumeroPartGiocate;
		}
		public void setSquadra_Away_NumeroPartGiocate(double squadra_Away_NumeroPartGiocate) {
			this.squadra_Away_NumeroPartGiocate = squadra_Away_NumeroPartGiocate;
		}
		
		
		
		
		
		
		
	}
	
	
	
	
	
	
	
}
