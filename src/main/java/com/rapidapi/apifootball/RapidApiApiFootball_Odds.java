package com.rapidapi.apifootball;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.dao.DataIntegrityViolationException;
import com.sambet.Constants;
import com.sambet.dao.RafBookmakersDao;
import com.sambet.dao.RafCompetizioniDao;
import com.sambet.dao.RafEventiDao;
import com.sambet.dao.RafMarket_EsitoFinaleDao;
import com.sambet.dao.RafMarket_GolDao;
import com.sambet.dao.RafMarket_OverUnder_25Dao;
import com.sambet.model.RafBookmakers;
import com.sambet.model.RafCompetizioni;
import com.sambet.model.RafEventi;
import com.sambet.model.RafMarket_EsitoFinale;
import com.sambet.model.RafMarket_Gol;
import com.sambet.model.RafMarket_OverUnder_25;
import com.sambet.util.DateUtil;

public class RapidApiApiFootball_Odds extends RapidApiApiFootball_Engine {
	private static final Log log = LogFactory.getLog(RapidApiApiFootball_Bookmakers.class);
		
	public static RafCompetizioniDao rafCompetizioniDao = (RafCompetizioniDao) contextDao.getBean("RafCompetizioniDao");
	public static RafEventiDao rafEventiDao = (RafEventiDao) contextDao.getBean("RafEventiDao");
	public static RafBookmakersDao rafBookmakersDao = (RafBookmakersDao) contextDao.getBean("RafBookmakersDao");
	
	public static RafMarket_EsitoFinaleDao rafMarket_EsitoFinaleDao = (RafMarket_EsitoFinaleDao) contextDao.getBean("RafMarket_EsitoFinaleDao");
	public static RafMarket_OverUnder_25Dao rafMarket_OverUnder_25Dao = (RafMarket_OverUnder_25Dao) contextDao.getBean("RafMarket_OverUnder_25Dao");
	public static RafMarket_GolDao rafMarket_GolDao = (RafMarket_GolDao) contextDao.getBean("RafMarket_GolDao");

	private static final int Lab_MatchWinner = 1; 		//"label_id": 1, "label_name": "Match Winner",
	private static final String Lab_Val_MatchWinner_Home = "Home"; 
	private static final String Lab_Val_MatchWinner_Draw = "Draw"; 
	private static final String Lab_Val_MatchWinner_Away = "Away"; 	
	
	private static final int Lab_GoalsOverUnder = 5; 	//"label_id": 5, "label_name": "Goals Over/Under",
	private static final String Lab_Val_GoalsOverUnder_Over25 = "Over 2.5"; 	
	private static final String Lab_Val_GoalsOverUnder_Under25 = "Under 2.5"; 	
	
	private static final int Lab_BothTeamsScore = 8;	//"label_id": 8,"label_name": "Both Teams Score",
	private static final String Lab_Val_BothTeamsScore_Yes = "Yes"; 	
	private static final String Lab_Val_BothTeamsScore_No = "No"; 	
	
	
    
    
    /**
     * INFO [main] RapidApiApiFootball_GetBookmakers.Odds_from_idFixture(239) | totRequestChiamate: 383 totEventiLavorati: 378 rafEventiAssegnatiList.size: 383
     * INFO [main] RapidApiApiFootball_GetBookmakers.Odds_from_idFixture(414) | totRequestChiamate: 254 totEventiLavorati: 243 rafEventiAssegnatiList.size: 254
     * è quello che prende più dati ma consuma di più
     */
    public static void GetOddsfrom_idFixture() {
	    try {
	    	
//	    	log.info("----- Inzio GetOddsfrom_idFixture");
	    	
	    	// Aggiornare sempre la lista bookmakers perché sembra che spesso cambiano
	    	RapidApiApiFootball_Bookmakers.GetBookmakers();
	    	
	    	List<RafBookmakers> rafBookmakersList = rafBookmakersDao.getRafBookmakers();
	    	HashMap<Integer, RafBookmakers> rafBookmakersHashMap = new HashMap<Integer, RafBookmakers>();
	    	for(RafBookmakers ite: rafBookmakersList) {
	    		rafBookmakersHashMap.put(ite.getId_bookmaker(), ite);
	    	}
	    	
	    	List<RafEventi> rafEventiAssegnatiEventiList = rafEventiDao.getRafEventi_AssegnatiEventi();
	    	
	    	JsonInfo jsonInfo = Data_idFixture(rafEventiAssegnatiEventiList, rafBookmakersHashMap);
	    	
//	    	log.info("totRequestChiamate: "+jsonInfo.getTotRequestChiamate()+" totEventiLavorati: "+jsonInfo.getTotEventiLavorati()
//	    		+" rafEventiAssegnatiList.size: "+rafEventiAssegnatiEventiList.size());
	
	    }catch( Exception exc ) {
	    	exc.printStackTrace();
	    }
    }
    
    
    private static JsonInfo Data_idFixture(List<RafEventi> rafEventiAssegnatiEventiList, HashMap<Integer, RafBookmakers> rafBookmakersHashMap) {
    	HashMap<Integer, RafEventi> rafEventiHashMap = new HashMap<Integer, RafEventi>();
    	for(RafEventi ite: rafEventiAssegnatiEventiList) {
    		rafEventiHashMap.put(ite.getFixture_id(), ite);
    	}
    	
    	int totEventiLavorati = 0; int totRequestChiamate = 0;
    	for(RafEventi ite_evento: rafEventiAssegnatiEventiList) {
        	for(int currentPage = 1, totalPage = 1; currentPage <= totalPage && totEventiLavorati <= rafEventiAssegnatiEventiList.size(); currentPage++) {
		    	String result = GetRequest(null, Get_Url_Odds_fixture_Timezone(ite_evento.getFixture_id()));
		    	totRequestChiamate++;
		    	JsonInfo jsonInfo = Elabora_ODDS(result, currentPage, totalPage, rafEventiAssegnatiEventiList, rafEventiHashMap, rafBookmakersHashMap);
	        	if( jsonInfo != null ) {
	        		currentPage = jsonInfo.getCurrentPage();
		        	totalPage = jsonInfo.getTotalPage();
		        	totEventiLavorati = totEventiLavorati + jsonInfo.getTotEventiLavorati();
	        	}
        	}
    	}
    	
        JsonInfo jsonInfo = new JsonInfo(totEventiLavorati, totRequestChiamate);
    	return jsonInfo;
    }
    
    
    private static JsonInfo Elabora_ODDS(String result, int currentPage, int totalPage, List<RafEventi> rafEventiAssegnatiEventiList, 
    		HashMap<Integer, RafEventi> rafEventiHashMap, HashMap<Integer, RafBookmakers> rafBookmakersHashMap) {
    	try {
    		int totEventiLavorati = 0;
        	JSONObject jsonObject = new JSONObject( result );
        	JSONObject api = jsonObject.getJSONObject("api");
        	//Integer results = api.getInt("results");
        	JSONObject paging = api.getJSONObject("paging");
        	currentPage = paging.getInt("current");
        	totalPage = paging.getInt("total");

        	JSONArray oddsJsonList = api.getJSONArray("odds");
        	for(int i = 0; i < oddsJsonList.length(); i++) {
        		JSONObject oddsObject = oddsJsonList.getJSONObject(i);
        		JSONObject fixture = oddsObject.getJSONObject("fixture");
        		//Integer legue_id = fixture.getInt("league_id");
            	Integer fixture_id = fixture.getInt("fixture_id");
            	Integer updateAt = fixture.getInt("updateAt");
            	
            	if( rafEventiAssegnatiEventiList.stream().anyMatch(o -> o.getFixture_id().equals(fixture_id)) ) {
            		List<RafMarket_EsitoFinale> rafM_EsitoFinaleList = rafMarket_EsitoFinaleDao.getRafMarket_EsitoFinale_from_idRafEvento(fixture_id);
            		List<RafMarket_OverUnder_25> RafM_OverUnder_25List = rafMarket_OverUnder_25Dao.getRafMarket_OverUnder_25_from_idRafEvento(fixture_id);
            		List<RafMarket_Gol> rafM_GolList = rafMarket_GolDao.getRafMarket_Gol_from_idRafEvento(fixture_id);
            		//log.info("---------updateAt: "+updateAt+" fixture_id: "+fixture_id);
            		
	            	JSONArray bookmakersJsonList = oddsObject.getJSONArray("bookmakers");
	            	for(int i_book = 0; i_book < bookmakersJsonList.length(); i_book++) {
	            		JSONObject bookmakersObject = bookmakersJsonList.getJSONObject(i_book);
	            		Integer bookmaker_id = bookmakersObject.getInt("bookmaker_id");
	            		//String bookmaker_name = bookmakersObject.getString("bookmaker_name");
	            		//log.info("--------- bookmaker_name: "+bookmaker_name+" updateAt: "+updateAt+" fixture_id: "+fixture_id);
	            		
	            		JSONArray betsJsonList = bookmakersObject.getJSONArray("bets");
	                	for(int i_bets = 0; i_bets < betsJsonList.length(); i_bets++) {
	                		JSONObject betsObject = betsJsonList.getJSONObject(i_bets);
	                		Integer label_id = betsObject.getInt("label_id");
	                		//String label_name = betsObject.getString("label_name");
	                		
	                		if( label_id.intValue() == Lab_MatchWinner || label_id.intValue() == Lab_GoalsOverUnder || label_id.intValue() == Lab_BothTeamsScore) {
	                			HashMap<Object, String> valuesHashMap = new HashMap<Object, String>();
	                			JSONArray valuesJsonList = betsObject.getJSONArray("values");
	                    		for(int i_values = 0; i_values < valuesJsonList.length(); i_values++) {
	                    			JSONObject valuesObject = valuesJsonList.getJSONObject(i_values);
	                    			//String value = valuesObject.getString("value");
	                    			valuesHashMap.put(valuesObject.get("value"), valuesObject.getString("odd"));
	                    		}
	                    		try {
	                    			SalvaMarket(label_id, bookmaker_id, fixture_id, updateAt, valuesHashMap, rafBookmakersHashMap, rafEventiHashMap, rafM_EsitoFinaleList, 
	                    					RafM_OverUnder_25List, rafM_GolList);
	                    		}catch(final DataIntegrityViolationException dataIntegrViolException_1) {
//                					log.info(dataIntegrViolException_1.getMessage());
	                    		}
	                		}
	                	}
	            	}
	            	totEventiLavorati++;
            	}
        	}
        	JsonInfo jsonInfo = new JsonInfo(totEventiLavorati, currentPage, totalPage);
        	return jsonInfo;
        	
    	}catch( JSONException jsonExc ) {
//    		log.info("JSONException: "+jsonExc.getMessage());
		}
    	return null;
    }
    
    
    private static void SalvaMarket(Integer label_id, Integer bookmaker_id, Integer fixture_id, Integer updateAt, 
    		HashMap<Object, String> valuesHashMap, HashMap<Integer, RafBookmakers> rafBookmakersHashMap, HashMap<Integer, RafEventi> rafEventiHashMap,
    		List<RafMarket_EsitoFinale> rafM_EsitoFinaleList, List<RafMarket_OverUnder_25> RafM_OverUnder_25List, List<RafMarket_Gol> rafM_GolList) {
    	// ----- MATCH_WINNER
		if( label_id.intValue() == Lab_MatchWinner ) {
			//log.info(valuesHashMap.get(Lab_Val_MatchWinner_Home)+" | "+valuesHashMap.get(Lab_Val_MatchWinner_Draw)+" | "+valuesHashMap.get(Lab_Val_MatchWinner_Away) );
			
			Double home = valuesHashMap.get(Lab_Val_MatchWinner_Home) != null ? Double.parseDouble(valuesHashMap.get(Lab_Val_MatchWinner_Home)) : null;
			Double draw = valuesHashMap.get(Lab_Val_MatchWinner_Draw) != null ? Double.parseDouble(valuesHashMap.get(Lab_Val_MatchWinner_Draw)) : null;
			Double away = valuesHashMap.get(Lab_Val_MatchWinner_Away) != null ? Double.parseDouble(valuesHashMap.get(Lab_Val_MatchWinner_Away)) : null;
			boolean quotaAperturaPresente = false; RafMarket_EsitoFinale rafMarket_EsitoFinale_QuotaAttuale = null;
			for(RafMarket_EsitoFinale ite: rafM_EsitoFinaleList) {
				if(ite.getRafBookmakers().getId_bookmaker() == bookmaker_id && ite.getTipoQuota() == Constants.QTA_APERTURA) {
					quotaAperturaPresente = true;
				}
				if(ite.getRafBookmakers().getId_bookmaker() == bookmaker_id && ite.getTipoQuota() == Constants.QTA_ATTUALE) {
					rafMarket_EsitoFinale_QuotaAttuale = ite;
				}
			}
			if( quotaAperturaPresente == false ) {
				RafMarket_EsitoFinale rafMarket_EsitoFinale = new RafMarket_EsitoFinale(updateAt, new Date(), home, draw, away, 
    					Constants.QTA_APERTURA, rafBookmakersHashMap.get(bookmaker_id), rafEventiHashMap.get(fixture_id));
    			rafMarket_EsitoFinaleDao.saveRafMarket_EsitoFinale(rafMarket_EsitoFinale);
    			//log.info("save rafMarket_EsitoFinale: "+Constants.QTA_APERTURA);
			}
			if(rafMarket_EsitoFinale_QuotaAttuale == null) {
				RafMarket_EsitoFinale rafMarket_EsitoFinale = new RafMarket_EsitoFinale(updateAt, new Date(), home, draw, away, 
    					Constants.QTA_ATTUALE, rafBookmakersHashMap.get(bookmaker_id), rafEventiHashMap.get(fixture_id));
    			rafMarket_EsitoFinaleDao.saveRafMarket_EsitoFinale(rafMarket_EsitoFinale);
    			//log.info("save rafMarket_EsitoFinale: "+Constants.QTA_ATTUALE);
				
			}else  {
				if( rafMarket_EsitoFinale_QuotaAttuale.getUpdateAt().intValue() < updateAt ) {
					rafMarket_EsitoFinale_QuotaAttuale.setUpdateAt(updateAt);
					rafMarket_EsitoFinale_QuotaAttuale.setDataRichiesta(new Date());
					rafMarket_EsitoFinale_QuotaAttuale.setQuota_1(home);
					rafMarket_EsitoFinale_QuotaAttuale.setQuota_X(draw);
					rafMarket_EsitoFinale_QuotaAttuale.setQuota_2(away);
					rafMarket_EsitoFinaleDao.saveRafMarket_EsitoFinale(rafMarket_EsitoFinale_QuotaAttuale);
//					log.info("NUOVO AGGIORNAMENTO update rafMarket_EsitoFinale: "+Constants.QTA_ATTUALE);
				}
			}

    	// ----- OVER_UNDER
		}else if( label_id.intValue() == Lab_GoalsOverUnder ) {
    		//log.info(valuesHashMap.get(Lab_Val_GoalsOverUnder_Over25)+" | "+valuesHashMap.get(Lab_Val_GoalsOverUnder_Under25));
			
			Double over = valuesHashMap.get(Lab_Val_GoalsOverUnder_Over25) != null ? Double.parseDouble(valuesHashMap.get(Lab_Val_GoalsOverUnder_Over25)) : null;
			Double under = valuesHashMap.get(Lab_Val_GoalsOverUnder_Under25) != null ? Double.parseDouble(valuesHashMap.get(Lab_Val_GoalsOverUnder_Under25)) : null;
			boolean quotaAperturaPresente = false; RafMarket_OverUnder_25 rafMarket_OverUnder_25_QuotaAttuale = null;
			for(RafMarket_OverUnder_25 ite: RafM_OverUnder_25List) {
				if(ite.getRafBookmakers().getId_bookmaker() == bookmaker_id && ite.getTipoQuota() == Constants.QTA_APERTURA) {
					quotaAperturaPresente = true;
				}
				if(ite.getRafBookmakers().getId_bookmaker() == bookmaker_id && ite.getTipoQuota() == Constants.QTA_ATTUALE) {
					rafMarket_OverUnder_25_QuotaAttuale = ite;
				}
			}
			if( quotaAperturaPresente == false ) {
				RafMarket_OverUnder_25 rafMarket_OverUnder_25 = new RafMarket_OverUnder_25(updateAt, new Date(), over, under, 
    					Constants.QTA_APERTURA, rafBookmakersHashMap.get(bookmaker_id), rafEventiHashMap.get(fixture_id));
    			rafMarket_OverUnder_25Dao.saveRafMarket_OverUnder_25(rafMarket_OverUnder_25);
    			//log.info("save rafMarket_OverUnder_25: "+Constants.QTA_APERTURA);
			}
			if(rafMarket_OverUnder_25_QuotaAttuale == null) {
				RafMarket_OverUnder_25 rafMarket_OverUnder_25 = new RafMarket_OverUnder_25(updateAt, new Date(), over, under, 
        					Constants.QTA_ATTUALE, rafBookmakersHashMap.get(bookmaker_id), rafEventiHashMap.get(fixture_id));
				rafMarket_OverUnder_25Dao.saveRafMarket_OverUnder_25(rafMarket_OverUnder_25);
				//log.info("save rafMarket_OverUnder_25: "+Constants.QTA_ATTUALE);
				
			}else  {
				if( rafMarket_OverUnder_25_QuotaAttuale.getUpdateAt().intValue() < updateAt ) {
					rafMarket_OverUnder_25_QuotaAttuale.setUpdateAt(updateAt);
					rafMarket_OverUnder_25_QuotaAttuale.setDataRichiesta(new Date());
					rafMarket_OverUnder_25_QuotaAttuale.setQuota_over(over);
					rafMarket_OverUnder_25_QuotaAttuale.setQuota_under(under);
					rafMarket_OverUnder_25Dao.saveRafMarket_OverUnder_25(rafMarket_OverUnder_25_QuotaAttuale);
//					log.info("NUOVO AGGIORNAMENTO update rafMarket_OverUnder_25: "+Constants.QTA_ATTUALE);
				}
			}
			
		// ----- GOL
		}else if( label_id.intValue() == Lab_BothTeamsScore ) {
    		//log.info(valuesHashMap.get(Lab_Val_BothTeamsScore_Yes)+" | "+valuesHashMap.get(Lab_Val_BothTeamsScore_No) );
			
			Double yes = valuesHashMap.get(Lab_Val_BothTeamsScore_Yes) != null ? Double.parseDouble(valuesHashMap.get(Lab_Val_BothTeamsScore_Yes)) : null;
			Double no = valuesHashMap.get(Lab_Val_BothTeamsScore_No) != null ? Double.parseDouble(valuesHashMap.get(Lab_Val_BothTeamsScore_No)) : null;
			boolean quotaAperturaPresente = false; RafMarket_Gol rafMarket_Gol_QuotaAttuale = null;
			for(RafMarket_Gol ite: rafM_GolList) {
				if(ite.getRafBookmakers().getId_bookmaker() == bookmaker_id && ite.getTipoQuota() == Constants.QTA_APERTURA) {
					quotaAperturaPresente = true;
				}
				if(ite.getRafBookmakers().getId_bookmaker() == bookmaker_id && ite.getTipoQuota() == Constants.QTA_ATTUALE) {
					rafMarket_Gol_QuotaAttuale = ite;
				}
			}
			if( quotaAperturaPresente == false ) {
				RafMarket_Gol rafMarket_Gol = new RafMarket_Gol(updateAt, new Date(), yes, no, 
    					Constants.QTA_APERTURA, rafBookmakersHashMap.get(bookmaker_id), rafEventiHashMap.get(fixture_id));
    			rafMarket_GolDao.saveRafMarket_Gol(rafMarket_Gol);
    			//log.info("save rafMarket_Gol: "+Constants.QTA_APERTURA);
			}
			if(rafMarket_Gol_QuotaAttuale == null) {
				RafMarket_Gol rafMarket_Gol = new RafMarket_Gol(updateAt, new Date(), yes, no, 
    					Constants.QTA_ATTUALE, rafBookmakersHashMap.get(bookmaker_id), rafEventiHashMap.get(fixture_id));
    			rafMarket_GolDao.saveRafMarket_Gol(rafMarket_Gol);
    			//log.info("save rafMarket_Gol: "+Constants.QTA_ATTUALE);
				
			}else  {
				if( rafMarket_Gol_QuotaAttuale.getUpdateAt().intValue() < updateAt ) {
					rafMarket_Gol_QuotaAttuale.setUpdateAt(updateAt);
					rafMarket_Gol_QuotaAttuale.setDataRichiesta(new Date());
					rafMarket_Gol_QuotaAttuale.setQuota_gol(yes);
					rafMarket_Gol_QuotaAttuale.setQuota_noGol(no);
					rafMarket_GolDao.saveRafMarket_Gol(rafMarket_Gol_QuotaAttuale);
//					log.info("NUOVO AGGIORNAMENTO update rafMarket_Gol: "+Constants.QTA_ATTUALE);
				}
			}
		}
    }

    
    /**
	 * INFO [main] RapidApiApiFootball_GetBookmakers.GetOdds_from_Date(91) | totRequestChiamate: 74 totEventiLavorati: 86 rafEventiAssegnatiList.size: 263
	 * 2924
	 * Non usare, ne prende limitati:
	 */
	@Deprecated
    public static void GetOddsfrom_Date() {
	    try {
		// Aggiornare sempre la lista bookmakers perché sembra che spesso cambiano
		RapidApiApiFootball_Bookmakers.GetBookmakers();
		
		
		List<RafBookmakers> rafBookmakersList = rafBookmakersDao.getRafBookmakers();
		HashMap<Integer, RafBookmakers> rafBookmakersHashMap = new HashMap<Integer, RafBookmakers>();
		for(RafBookmakers ite: rafBookmakersList) {
			rafBookmakersHashMap.put(ite.getId_bookmaker(), ite);
		}
		
		List<RafEventi> rafEventiAssegnatiEventiList = rafEventiDao.getRafEventi_AssegnatiEventi();
		HashMap<Integer, RafEventi> rafEventiHashMap = new HashMap<Integer, RafEventi>();
		for(RafEventi ite: rafEventiAssegnatiEventiList) {
			rafEventiHashMap.put(ite.getFixture_id(), ite);
		}
		
		
		int totEventiLavorati = 0; int totRequestChiamate = 0;
		for(int countDay = 0; countDay <= 20; countDay++ ) {
			Date dt = new Date(); Calendar cal = Calendar.getInstance(); cal.setTime(dt); cal.add(Calendar.DATE, countDay);
	    	String dateCount = countDay == 0 ? DateUtil.FormatoData_3.format(new Date()) : DateUtil.FormatoData_3.format(cal.getTime());
	    	for(int currentPage = 1, totalPage = 1; currentPage <= totalPage && totEventiLavorati <= rafEventiAssegnatiEventiList.size(); currentPage++) {
	        	String result = GetRequest(null, Get_Url_Odds_Date_Timezone(dateCount, currentPage));
	        	totRequestChiamate++;
	        	JsonInfo jsonInfo = Elabora_ODDS(result, currentPage, totalPage, rafEventiAssegnatiEventiList, rafEventiHashMap, rafBookmakersHashMap);
	        	if( jsonInfo != null ) {
	        		currentPage = jsonInfo.getCurrentPage();
		        	totalPage = jsonInfo.getTotalPage();
		        	totEventiLavorati = totEventiLavorati + jsonInfo.getTotEventiLavorati();
	        	}
	    	}
		}
//		log.info("totRequestChiamate: "+totRequestChiamate+" totEventiLavorati: "+totEventiLavorati+" rafEventiAssegnatiList.size: "+rafEventiAssegnatiEventiList.size());
	    }catch( Exception exc ) {
	    	exc.printStackTrace();
	    }
    }
    

    
    /**
     * 
     * INFO [main] RapidApiApiFootball_GetBookmakers.from_idLeague(138) | totRequestChiamate: 438 totEventiLavorati: 265 rafEventiAssegnatiList.size: 263
     * 1786
     * Non usare, ne prende limitati:
     */
    @Deprecated
    public static void GetOddsfrom_idLeague() {
		try {
	    	// Aggiornare sempre la lista bookmakers perché sembra che spesso cambiano
	    	RapidApiApiFootball_Bookmakers.GetBookmakers();
	    	
	    	List<RafBookmakers> rafBookmakersList = rafBookmakersDao.getRafBookmakers();
	    	HashMap<Integer, RafBookmakers> rafBookmakersHashMap = new HashMap<Integer, RafBookmakers>();
	    	for(RafBookmakers ite: rafBookmakersList) {
	    		rafBookmakersHashMap.put(ite.getId_bookmaker(), ite);
	    	}
	    	
	    	List<RafCompetizioni> rafCompetizioniAssegnatiEventiList = rafCompetizioniDao.getRafCompetizioni_AssegnatiEventi();
	    	
	    	List<RafEventi> rafEventiAssegnatiEventiList = rafEventiDao.getRafEventi_AssegnatiEventi();
	    	HashMap<Integer, RafEventi> rafEventiHashMap = new HashMap<Integer, RafEventi>();
	    	for(RafEventi ite: rafEventiAssegnatiEventiList) {
	    		rafEventiHashMap.put(ite.getFixture_id(), ite);
	    	}
	
	    	int totEventiLavorati = 0; int totRequestChiamate = 0;
	    	for(RafCompetizioni ite_competiz: rafCompetizioniAssegnatiEventiList) {
	        	for(int currentPage = 1, totalPage = 1; currentPage <= totalPage && totEventiLavorati <= rafEventiAssegnatiEventiList.size(); currentPage++) {
		        	String result = GetRequest(null, Get_Url_Odds_legue_Timezone(ite_competiz.getLeague_id(), currentPage));
		        	totRequestChiamate++;
		        	JsonInfo jsonInfo = Elabora_ODDS(result, currentPage, totalPage, rafEventiAssegnatiEventiList, rafEventiHashMap, rafBookmakersHashMap);
		        	if( jsonInfo != null ) {
		        		currentPage = jsonInfo.getCurrentPage();
			        	totalPage = jsonInfo.getTotalPage();
			        	totEventiLavorati = totEventiLavorati + jsonInfo.getTotEventiLavorati();
		        	}
	        	}
	    	}
//	    	log.info("totRequestChiamate: "+totRequestChiamate+" totEventiLavorati: "+totEventiLavorati+" rafEventiAssegnatiList.size: "+rafEventiAssegnatiEventiList.size());
	    }catch( Exception exc ) {
	    	exc.printStackTrace();
	    }
    }
    
    
    /**
     * Da sperimentare
     * le api rispondono lentamente, almeno 15 secondi, dovrebbe servire a fornire gli ultimi aggiornamenti delle quote, 
     * da usare in combinazione con from_idFixture. Facendo paritre from_idFixture una volta ogni ora e GetOdds_Mapping ogni 5 miunti
     */
    @Deprecated
    public static void GetOddsfrom_Mapping() {
		try {
	
	    	List<RafBookmakers> rafBookmakersList = rafBookmakersDao.getRafBookmakers();
	    	HashMap<Integer, RafBookmakers> rafBookmakersHashMap = new HashMap<Integer, RafBookmakers>();
	    	for(RafBookmakers ite: rafBookmakersList) {
	    		rafBookmakersHashMap.put(ite.getId_bookmaker(), ite);
	    	}
	
	    	int totRequestChiamate = 0; int totEventiLavorati = 0;
	
	    	for(int currentPageMapping = 1, totalPageMapping = 1; currentPageMapping <= totalPageMapping; currentPageMapping++) {
	        	String result = GetRequest(null, Get_Url_Odds_mapping(currentPageMapping));
	        	totRequestChiamate++;
	        	try {
		        	JSONObject jsonObject = new JSONObject( result );
		        	JSONObject api = jsonObject.getJSONObject("api");
		        	//Integer results = api.getInt("results");
		        	JSONObject paging = api.getJSONObject("paging");
		        	currentPageMapping = paging.getInt("current");
		        	totalPageMapping = paging.getInt("total");
		        	
		        	JSONArray oddsJsonList = api.getJSONArray("mapping");
		        	for(int i = 0; i < oddsJsonList.length(); i++) {
		        		JSONObject oddsObject = oddsJsonList.getJSONObject(i);
		            	Integer fixture_id = oddsObject.getInt("fixture_id");
		            	Integer updateAt = oddsObject.getInt("updateAt");
		            	
		            	//System.out.println(fixture_id+" | "+updateAt);
		            	
		            	List<RafEventi> aaa = rafEventiDao.getRafEventi_ApiMapping(fixture_id, updateAt);
		            	if( aaa.size() > 0 ) {
		            		//System.out.println("aaa.size: "+aaa.size()+" fixture_id: "+fixture_id+" updateAt:"+updateAt);
		            		
		            		JsonInfo jsonInfo = Data_idFixture(aaa, rafBookmakersHashMap);
		            		if( jsonInfo != null ) {
					        	totEventiLavorati = totEventiLavorati + jsonInfo.getTotEventiLavorati();
					        	totRequestChiamate = totRequestChiamate + jsonInfo.getTotRequestChiamate();
				        	}
				        	
		            		
		            	}
		        	}
	        	}catch( JSONException jsonExc ) {
//	        		log.info("JSONException: "+jsonExc.getMessage());
	    		}
	    	}

//	    	log.info("totRequestChiamate: "+totRequestChiamate+" totEventiLavorati: "+totEventiLavorati);
	    	
	    	
	    }catch( Exception exc ) {
	    	exc.printStackTrace();
	    }
    }
    
    
    private static class JsonInfo {
    	private int totEventiLavorati;
    	private int currentPage;
    	private int totalPage;
    	private int totRequestChiamate;
    	
		public JsonInfo(int totEventiLavorati, int currentPage, int totalPage) {
			super();
			this.totEventiLavorati = totEventiLavorati;
			this.currentPage = currentPage;
			this.totalPage = totalPage;
		}
		
		public JsonInfo(int totEventiLavorati, int totRequestChiamate) {
			super();
			this.totEventiLavorati = totEventiLavorati;
			this.totRequestChiamate = totRequestChiamate;
		}
		
		public int getTotEventiLavorati() {
			return totEventiLavorati;
		}
		public int getCurrentPage() {
			return currentPage;
		}
		public int getTotalPage() {
			return totalPage;
		}
		public int getTotRequestChiamate() {
			return totRequestChiamate;
		}

    }

    
}



