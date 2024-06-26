package com.rapidapi.apifootball.bean;

import java.util.List;

/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
public class RapidApiFootballCompetizioni {
	
	Integer results;
	List<Leagues> leaguesList;

	public Integer getResults() {
		return results;
	}
	public void setResults(Integer results) {
		this.results = results;
	}

	public List<Leagues> getLeaguesList() {
		return leaguesList;
	}

	public void setLeaguesList(List<Leagues> leaguesList) {
		this.leaguesList = leaguesList;
	}


	static public class Leagues {
		private int league_id;
		private String name;
		private String type;
		private String country;
		private String country_code;
		private int season;
		private String season_start;
		private String season_end;
		
		public Leagues(int league_id, String name, String type, String country, String country_code, int season, String season_start, String season_end) {
			super();
			this.league_id = league_id;
			this.name = name;
			this.type = type;
			this.country = country;
			this.country_code = country_code;
			this.season = season;
			this.season_start = season_start;
			this.season_end = season_end;
		}
		
		public int getLeague_id() {
			return league_id;
		}
		public void setLeague_id(int league_id) {
			this.league_id = league_id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getCountry() {
			return country;
		}
		public void setCountry(String country) {
			this.country = country;
		}
		public String getCountry_code() {
			return country_code;
		}
		public void setCountry_code(String country_code) {
			this.country_code = country_code;
		}
		public int getSeason() {
			return season;
		}
		public void setSeason(int season) {
			this.season = season;
		}
		public String getSeason_start() {
			return season_start;
		}
		public void setSeason_start(String season_start) {
			this.season_start = season_start;
		}
		public String getSeason_end() {
			return season_end;
		}
		public void setSeason_end(String season_end) {
			this.season_end = season_end;
		}
		

	}
	
	
	
	
	
	
	
}
