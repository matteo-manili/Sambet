package com.rapidapi.apifootball;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;
import org.springframework.orm.hibernate4.HibernateJdbcException;

import com.ibm.icu.util.Calendar;
import com.rapidapi.apifootball.bean.RapidApiFootballQuoteSambet.Risultati;
import com.rapidapi.apifootball.bean.RapidApiFootballQuoteSambet.Statistics;
import com.sambet.dao.EventiDao;
import com.sambet.dao.ListaNazioniDao;
import com.sambet.dao.NazioniDao;
import com.sambet.dao.RafCompetizioniDao;
import com.sambet.dao.RafEventiDao;
import com.sambet.dao.RafQuoteSambetDao;
import com.sambet.dao.RafStatisticsDao;
import com.sambet.dao.RafTeamsDao;
import com.sambet.model.RafCompetizioni;
import com.sambet.model.RafQuoteSambet;
import com.sambet.model.RafStatistics;
import com.sambet.model.RafTeams;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class RapidApiApiFootball_Statistics extends RapidApiApiFootball_Engine {
	private static final Log log = LogFactory.getLog(RapidApiApiFootball_Statistics.class);
	
	public static ListaNazioniDao listaNazioniDao = (ListaNazioniDao) contextDao.getBean("ListaNazioniDao");
	public static NazioniDao nazioniDao = (NazioniDao) contextDao.getBean("NazioniDao");
	public static RafCompetizioniDao rafCompetizioniDao = (RafCompetizioniDao) contextDao.getBean("RafCompetizioniDao");
	public static RafEventiDao rafEventiDao = (RafEventiDao) contextDao.getBean("RafEventiDao");
	public static RafTeamsDao rafTeamsDao = (RafTeamsDao) contextDao.getBean("RafTeamsDao");
	public static RafStatisticsDao rafStatisticsDao = (RafStatisticsDao) contextDao.getBean("RafStatisticsDao");
	public static EventiDao eventiDao = (EventiDao) contextDao.getBean("EventiDao");
	public static RafQuoteSambetDao rafQuoteSambetDao = (RafQuoteSambetDao) contextDao.getBean("RafQuoteSambetDao");
	
	
    public static void GetCompetizioniEventi_Statistics() {
//    	log.info("----- Inzio GetCompetizioniEventi_Statistics");
        try {
			List<Object[]> resultList = eventiDao.GetEventi_from_siglaNazione_rafEventiIsNotNULL();
			for(Object[] ite_object: resultList) {
				Long 			var_0 = ((BigInteger)ite_object[0]).longValue();
				Integer 		var_1 = (Integer)ite_object[1];
				Integer 		var_2 = (Integer)ite_object[2];
				Integer 		var_3 = (Integer)ite_object[3];
				
				//System.out.println(var_0+" | " +var_1+" | " +var_2+" | " +var_3); 
				
				RafStatistics statisticsHome = rafStatisticsDao.getStatistics_from_idStatistic(var_1, var_2);
				statisticsHome = Save_Statistics(statisticsHome, var_1, var_2);
				
				RafStatistics statisticsAway = rafStatisticsDao.getStatistics_from_idStatistic(var_1, var_3);
				statisticsAway = Save_Statistics(statisticsAway, var_1, var_3);
	

				final double COSTANTE_1 = 2.718;
				
				/*
				double squadra_Home_GolFatti = 12;
				double squadra_Home_GolSubiti = 5;
				double squadra_Home_NumeroPartGiocate = 5;
				double squadra_Away_GolFatti = 3; 
				double squadra_Away_GolSubiti = 13;
				double squadra_Away_NumeroPartGiocate = 5;
				*/
				
				double squadra_Home_GolFatti = statisticsHome.getHome_GolFatti();
				double squadra_Home_GolSubiti = statisticsHome.getHome_GolSubiti();
				double squadra_Home_NumeroPartGiocate = statisticsHome.getHome_NumeroPartGiocate();
				double squadra_Away_GolFatti = statisticsAway.getAway_GolFatti(); 
				double squadra_Away_GolSubiti = statisticsAway.getAway_GolSubiti();
				double squadra_Away_NumeroPartGiocate = statisticsAway.getAway_NumeroPartGiocate();
				
//				log.info(squadra_Home_GolFatti+" | "+squadra_Home_GolSubiti+" | "+squadra_Home_NumeroPartGiocate+" | "+squadra_Away_GolFatti
//						+" | "+squadra_Away_GolSubiti+" | "+squadra_Away_NumeroPartGiocate);
			
				// TODO ripetere tutte le operazione da 0 a 10 gli esponenti "exponent" per ambedue le squaddre quindi totale 20 risultati
		        // TODO confrontare tutti valori di una squadra con l'altra (saranno un centinaio....) secondo la formula: =F1*F12*100
				
				double M_HOME = ((squadra_Home_GolFatti / squadra_Home_NumeroPartGiocate) + (squadra_Away_GolSubiti / squadra_Away_NumeroPartGiocate)) / 2;
				//System.out.println("M_HOME: "+M_HOME);
				List<Double> HOME_F_List = new ArrayList<Double>();
				for(int exponent = 0; exponent <= 10; exponent++) {
			        double D_HOME = Math.pow(M_HOME, exponent);
			        double VAL_HOME_D = Math.pow(COSTANTE_1, -M_HOME);
			        double VAL_HOME_E = VAL_HOME_D * D_HOME;
			        double VAL_HOME_F = VAL_HOME_E / CalcoloFattoriale(exponent) ;
			        HOME_F_List.add(VAL_HOME_F);
				}
				
				double M_AWAY = ((squadra_Home_GolSubiti / squadra_Home_NumeroPartGiocate) + (squadra_Away_GolFatti / squadra_Away_NumeroPartGiocate)) / 2;
				//System.out.println("M_AWAY: "+M_AWAY);
				List<Double> AWAY_F_List = new ArrayList<Double>();
				for(int exponent = 0; exponent <= 10; exponent++) {
			        double D_HOME = Math.pow(M_AWAY, exponent);
			        double VAL_HOME_D = Math.pow(COSTANTE_1, -M_AWAY);
			        double VAL_HOME_E = VAL_HOME_D * D_HOME;
			        double VAL_HOME_F = VAL_HOME_E / CalcoloFattoriale(exponent) ;
			        AWAY_F_List.add(VAL_HOME_F);
				}
	
				List<Risultati> risultati_List = new ArrayList<Risultati>();
				for(int ite_home = 0; ite_home < HOME_F_List.size(); ite_home++) {
					for(int ite_away = 0; ite_away < AWAY_F_List.size(); ite_away++) {
						Risultati ris = new Risultati();
						ris.setSquadraHome(ite_home);
						ris.setSquadraAway(ite_away);
						ris.setVal( HOME_F_List.get(ite_home) * AWAY_F_List.get(ite_away) * 100 );
						risultati_List.add(ris);
					}
				}
				
				// 1 - x - 2
				// --- sommo tutti i valori di X _ 1 _ 2 per determinare il valore finale da visualizzare (per determinare la X sono 20 per 1 e 2 sono di più). e fare "100 / la somma"
				// GOL E NO GOL
		        // --- per determinare il valor GOL bisogna considere i risultato dove c'è almeno un gol in  tutte e due le squadre. e fare "100 / la somma"
		        // --- per determinare il valorr NO GOL bisogna considere i risultato dove c'è almeno una squadra che ha fatto 0 incluso il 0 a 0. e fare "100 / la somma"
		        // OVER E UNDER, 2,5
		        // --- l'over è la somma dei gol di ambedue le squadre che supera i 2,5 gol. e fare "100 / la somma"
		        // --- l'under è la somma dei gol di ambedue le squadre che non superano i 2,5 gol. e fare "100 / la somma"
				
				double somma_1 = 0, somma_x = 0, somma_2 = 0, somma_over = 0, somma_under = 0, somma_gol = 0, somma_no_gol = 0;
				for(Risultati ite: risultati_List) {
					if( ite.getSquadraHome() > ite.getSquadraAway() ) {
						somma_1 = somma_1 + ite.getVal();
					}
					if( ite.getSquadraHome() == ite.getSquadraAway() ) {
						somma_x = somma_x + ite.getVal();
					}
					if( ite.getSquadraHome() < ite.getSquadraAway() ) {
						somma_2 = somma_2 + ite.getVal();
					}
					if( ite.getSquadraHome() + ite.getSquadraAway() > 2.5 ) {
						somma_over = somma_over + ite.getVal();
					}
					if( ite.getSquadraHome() + ite.getSquadraAway() < 2.5 ) {
						somma_under = somma_under + ite.getVal();
					}
					if( ite.getSquadraHome() > 0 && ite.getSquadraAway() > 0 ) {
						somma_gol = somma_gol + ite.getVal();
					}
					if( ite.getSquadraHome() == 0 || ite.getSquadraAway() == 0 ) {
						somma_no_gol = somma_no_gol + ite.getVal();
					}
				}
	
				// VISUALIZZAZIONE
		        // in visualizzazione fare vedere il risultato secondo la forumula "100/il risultato"
				
				//log.info(100 / somma_1+ " | "+100 / somma_x+ " | "+100 / somma_2+ " | "+100 / somma_over+ " | "+100 / somma_under+ " | "+100 / somma_gol + " | "+100 / somma_no_gol);
				RafQuoteSambet rafQuoteSambet = rafQuoteSambetDao.getQuoteSambet_from_idRafEvento(var_0);
				
				try {
					if( rafQuoteSambet != null ) {
						rafQuoteSambet.setDataRichiesta(new Date());
						rafQuoteSambet.setQuota_1( !Double.isNaN(somma_1) && somma_1 > 0 ? 100 / somma_1 : null );
						rafQuoteSambet.setQuota_X( !Double.isNaN(somma_x) && somma_x > 0 ? 100 / somma_x : null );
						rafQuoteSambet.setQuota_2( !Double.isNaN(somma_2) && somma_2 > 0 ? 100 / somma_2 : null );
						rafQuoteSambet.setQuota_over( !Double.isNaN(somma_over) && somma_over > 0 ? 100 / somma_over : null );
						rafQuoteSambet.setQuota_under( !Double.isNaN(somma_under) && somma_under > 0 ? 100 / somma_under : null );
						rafQuoteSambet.setQuota_gol( !Double.isNaN(somma_gol) && somma_gol > 0 ? 100 / somma_gol : null );
						rafQuoteSambet.setQuota_noGol( !Double.isNaN(somma_no_gol) && somma_no_gol > 0 ? 100 / somma_no_gol : null );
					}else {
						rafQuoteSambet = new RafQuoteSambet(rafEventiDao.get(var_0), 
								new Date(), !Double.isNaN(somma_1) && somma_1 > 0 ? 100 / somma_1 : null, 
										!Double.isNaN(somma_x) && somma_x > 0 ? 100 / somma_x : null, 
												!Double.isNaN(somma_2) && somma_2 > 0 ? 100 / somma_2 : null, 
														!Double.isNaN(somma_over) && somma_over > 0 ? 100 / somma_over : null, 
																!Double.isNaN(somma_under) && somma_under > 0 ? 100 / somma_under : null, 
																		!Double.isNaN(somma_gol) && somma_gol > 0 ? 100 / somma_gol : null, 
																				!Double.isNaN(somma_no_gol) && somma_no_gol > 0 ? 100 / somma_no_gol : null);
					}
					rafQuoteSambetDao.saveRafQuoteSambet(rafQuoteSambet);
					
				}catch(HibernateJdbcException exc) {
					log.info(100 / somma_1+ " | "+100 / somma_x+ " | "+100 / somma_2+ " | "+100 / somma_over+ " | "+100 / somma_under+ " | "+100 / somma_gol + " | "+100 / somma_no_gol);
					exc.printStackTrace();
					
				}catch(Exception exc) {
					exc.printStackTrace();
				}
			}
	       
	        
			// mettere i dati sulla prima linea chiamando la colonna Bookmakers: "Quote sambet" e sotto la colonna Quote: "niente"
			// -------------- FINE ISTRUZIONI PROGETTO --------------------

        }catch( Exception exc ) {
        	exc.printStackTrace();
        }
    }
    
 
    
	private static RafStatistics Save_Statistics(RafStatistics rafStatistics, Integer comp, Integer team) {
		Calendar calendarHourPrevius_6 = Calendar.getInstance();
		calendarHourPrevius_6.add(Calendar.HOUR_OF_DAY, -6);
		if( rafStatistics != null ) {
			Calendar calendar = Calendar.getInstance();
	        calendar.setTime(rafStatistics.getDataRichiesta());
	        if( rafStatistics.getDataRichiesta().before(calendarHourPrevius_6.getTime()) ) {
	        	String result = RapidApiApiFootball_Engine.GetRequest(null, RapidApiApiFootball_Engine.Get_Url_Statistics(comp, team));
	        	Statistics statistics = Json_to_Statistics(result);
	        	rafStatistics.setHome_GolFatti(statistics.getSquadra_Home_GolFatti());
	        	rafStatistics.setHome_GolSubiti(statistics.getSquadra_Home_GolSubiti());
	        	rafStatistics.setHome_NumeroPartGiocate(statistics.getSquadra_Home_NumeroPartGiocate());
	        	rafStatistics.setAway_GolFatti(statistics.getSquadra_Away_GolFatti());
	        	rafStatistics.setAway_GolSubiti(statistics.getSquadra_Away_GolSubiti());
	        	rafStatistics.setAway_NumeroPartGiocate(statistics.getSquadra_Away_NumeroPartGiocate());
	        	rafStatistics.setDataRichiesta(new Date());
	        	rafStatistics = rafStatisticsDao.saveRafStatistics(rafStatistics);
	        }
	        
		}else {
			String result = RapidApiApiFootball_Engine.GetRequest(null, RapidApiApiFootball_Engine.Get_Url_Statistics(comp, team));
        	Statistics statistics = Json_to_Statistics(result);
			RafCompetizioni rafCompetizione = rafCompetizioniDao.GetCompetizione_from_legueId(comp);
			RafTeams rafTeams = rafTeamsDao.getTeams_from_idTeam(team);
			rafStatistics = new RafStatistics(rafCompetizione, rafTeams, new Date(), statistics.getSquadra_Home_GolFatti(), statistics.getSquadra_Home_GolSubiti(), 
					statistics.getSquadra_Home_NumeroPartGiocate(), statistics.getSquadra_Away_GolFatti(), statistics.getSquadra_Away_GolSubiti(), 
					statistics.getSquadra_Away_NumeroPartGiocate());
			rafStatistics = rafStatisticsDao.saveRafStatistics(rafStatistics);
		}
		return rafStatistics;
	}

	
	private static Statistics Json_to_Statistics(String result) {
		JSONObject jsonObject = new JSONObject( result );
    	JSONObject api = jsonObject.getJSONObject("api");
    	JSONObject statistics = api.getJSONObject("statistics");
    	JSONObject matchs = statistics.getJSONObject("matchs");
    	JSONObject matchsPlayed = matchs.getJSONObject("matchsPlayed");
    	double squadra_Home_NumeroPartGiocate = matchsPlayed.getDouble("home");
    	double squadra_Away_NumeroPartGiocate = matchsPlayed.getDouble("away");
		
		JSONObject goals = statistics.getJSONObject("goals");
    	JSONObject goalsFor = goals.getJSONObject("goalsFor");
    	double squadra_Home_GolFatti = goalsFor.getDouble("home");
    	double squadra_Away_GolFatti = goalsFor.getDouble("away");
		
    	JSONObject goalsAgainst = goals.getJSONObject("goalsAgainst");
    	double squadra_Home_GolSubiti = goalsAgainst.getDouble("home");
    	double squadra_Away_GolSubiti = goalsAgainst.getDouble("away");
		
		return new Statistics(squadra_Home_GolFatti, squadra_Home_GolSubiti, squadra_Home_NumeroPartGiocate, 
				squadra_Away_GolFatti, squadra_Away_GolSubiti, squadra_Away_NumeroPartGiocate);
	}
	
	
    private static int CalcoloFattoriale(int x) {
	    int i; int f=1;
	    for(i=1; i<=x; i=i+1) {
	    	f=f*i;
	    }
	    return f;
	}
    
    
}
