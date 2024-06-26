package com.rapidapi.apifootball.bean;

import java.util.List;

/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
public class RapidApiFootballEventi {
	
	Integer results;
	List<Fixtures> fixturesList;

	public Integer getResults() {
		return results;
	}
	public void setResults(Integer results) {
		this.results = results;
	}
	public List<Fixtures> getFixturesList() {
		return fixturesList;
	}
	public void setFixturesList(List<Fixtures> fixturesList) {
		this.fixturesList = fixturesList;
	}

	static public class Fixtures {
		private int fixture_id;
		private int league_id;
		private String event_date;
		private Long event_timestamp;
		private String statusShort;
		private String homeTeam;
		private String awayTeam;
		private int homeTeam_id;
		private int awayTeam_id;		
		
		
		public Fixtures(int fixture_id, int league_id, String event_date, Long event_timestamp, String statusShort, String homeTeam, String awayTeam, 
				int homeTeam_id, int awayTeam_id) {
			super();
			this.fixture_id = fixture_id;
			this.league_id = league_id;
			this.event_date = event_date;
			this.event_timestamp = event_timestamp;
			this.statusShort = statusShort;
			this.homeTeam = homeTeam;
			this.awayTeam = awayTeam;
			this.homeTeam_id = homeTeam_id;
			this.awayTeam_id = awayTeam_id;
		}
		
		public int getFixture_id() {
			return fixture_id;
		}
		public void setFixture_id(int fixture_id) {
			this.fixture_id = fixture_id;
		}
		public int getLeague_id() {
			return league_id;
		}
		public void setLeague_id(int league_id) {
			this.league_id = league_id;
		}
		public String getEvent_date() {
			return event_date;
		}
		public void setEvent_date(String event_date) {
			this.event_date = event_date;
		}
		public Long getEvent_timestamp() {
			return event_timestamp;
		}
		public void setEvent_timestamp(Long event_timestamp) {
			this.event_timestamp = event_timestamp;
		}
		public String getStatusShort() {
			return statusShort;
		}
		public void setStatusShort(String statusShort) {
			this.statusShort = statusShort;
		}
		public String getHomeTeam() {
			return homeTeam;
		}
		public void setHomeTeam(String homeTeam) {
			this.homeTeam = homeTeam;
		}
		public String getAwayTeam() {
			return awayTeam;
		}
		public void setAwayTeam(String awayTeam) {
			this.awayTeam = awayTeam;
		}
		public int getHomeTeam_id() {
			return homeTeam_id;
		}
		public void setHomeTeam_id(int homeTeam_id) {
			this.homeTeam_id = homeTeam_id;
		}
		public int getAwayTeam_id() {
			return awayTeam_id;
		}
		public void setAwayTeam_id(int awayTeam_id) {
			this.awayTeam_id = awayTeam_id;
		}
		
	}
	
	
	
	
	
	
	
}
