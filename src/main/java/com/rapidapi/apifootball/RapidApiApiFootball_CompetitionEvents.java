package com.rapidapi.apifootball;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.dao.DataIntegrityViolationException;
import com.rapidapi.apifootball.bean.RapidApiFootballCompetizioni.Leagues;
import com.rapidapi.apifootball.bean.RapidApiFootballEventi.Fixtures;
import com.rapidapi.apifootball.util.RapidApiApiFootball_Util;
import com.sambet.Constants;
import com.sambet.dao.EventiDao;
import com.sambet.dao.ListaNazioniDao;
import com.sambet.dao.NazioniDao;
import com.sambet.dao.RafCompetizioniDao;
import com.sambet.dao.RafEventiDao;
import com.sambet.dao.RafTeamsDao;
import com.sambet.model.Eventi;
import com.sambet.model.ListaNazioni;
import com.sambet.model.Nazioni;
import com.sambet.model.RafCompetizioni;
import com.sambet.model.RafEventi;
import com.sambet.model.RafTeams;
import com.sambet.util.DateUtil;
import java.util.ArrayList;
import java.util.List;


public class RapidApiApiFootball_CompetitionEvents extends RapidApiApiFootball_Engine {
//	private static final Log log = LogFactory.getLog(RapidApiApiFootball_CompetitionEvents.class);
	
	public static ListaNazioniDao listaNazioniDao = (ListaNazioniDao) contextDao.getBean("ListaNazioniDao");
	public static NazioniDao nazioniDao = (NazioniDao) contextDao.getBean("NazioniDao");
	
	public static RafCompetizioniDao rafCompetizioniDao = (RafCompetizioniDao) contextDao.getBean("RafCompetizioniDao");
	public static RafEventiDao rafEventiDao = (RafEventiDao) contextDao.getBean("RafEventiDao");
	public static RafTeamsDao rafTeamsDao = (RafTeamsDao) contextDao.getBean("RafTeamsDao");
	
	public static EventiDao eventiDao = (EventiDao) contextDao.getBean("EventiDao");

    public static void GetCompetizioniEventi_Associa_BfEventi() {
        try {
//        	log.info("----- Inzio GetCompetizioniEventi_Associa_BfEventi");
        	/**
        	 * RAFFINAZIONE 1
        	 */
        	List<ListaNazioni> listaNazioni = listaNazioniDao.getListaNazioni_Attivo(true);
//        	int Salva_1 = 0; int Salva_2 = 0; int Salva_3 = 0;  
        	String result = GetRequest(null, Url_leagues_current);
        	JSONObject jsonObject = new JSONObject( result );
        	JSONObject api = jsonObject.getJSONObject("api");    	
        	JSONArray leaguesJsonList = api.getJSONArray("leagues");
        	for(int i = 0; i < leaguesJsonList.length(); i++) {
        		JSONObject object = leaguesJsonList.getJSONObject(i);
        		Leagues leagues = new Leagues(object.getInt("league_id"), object.getString("name"), object.getString("type"), object.getString("country"), 
        				object.isNull("country_code") ? Constants.SILGLA_CAMPIONATO_INTERNAZIONALE : object.getString("country_code"), object.getInt("season"), object.getString("season_start"), object.getString("season_end"));
        		
        		for(ListaNazioni ite: listaNazioni) {
        			if( leagues.getCountry_code().equals(ite.getSiglaNazione()) 
        					|| (leagues.getCountry_code() == null && ite.getSiglaNazione().equals(Constants.SILGLA_CAMPIONATO_INTERNAZIONALE)) ) {
        				//log.info( leagues.getCountry_code() );
        				Nazioni nazione = nazioniDao.getNazione_from_SiglaNazione( leagues.getCountry_code() );
        				if(nazione == null) {
        					nazione = new Nazioni(leagues.getCountry_code(), true);
        					nazione = nazioniDao.saveNazioni(nazione);
        				}
        				RafCompetizioni rafCompetizione = rafCompetizioniDao.GetCompetizione_from_legueId( leagues.getLeague_id() );
        				if(rafCompetizione == null) {
        					rafCompetizione = new RafCompetizioni(nazione, leagues.getLeague_id(), leagues.getName(), leagues.getType(), leagues.getCountry(), leagues.getCountry_code(), 
        							leagues.getSeason(), leagues.getSeason_start(), leagues.getSeason_end());
        					rafCompetizione = rafCompetizioniDao.saveRafCompetizioni( rafCompetizione );
        				}
        				
        				List<Eventi> listaEventiBetFair = eventiDao.GetEventi_from_siglaNazione_rafEventiIsNULL( ite.getSiglaNazione() );
        				if( listaEventiBetFair.size() > 0 ) {
        					try {
	        					String resultEventi = GetRequest(null, Get_Url_Fixtures_Legue_Next_Timezone( rafCompetizione.getLeague_id() ));
		                    	JSONObject jsonObjectEventi = new JSONObject( resultEventi );
		                    	JSONObject apiEventi = jsonObjectEventi.getJSONObject("api");
		                    	JSONArray fixturesJsonList = apiEventi.getJSONArray("fixtures");
		                    	for(int i_eventi = 0; i_eventi < fixturesJsonList.length(); i_eventi++) {
		                    		JSONObject objectFixture = fixturesJsonList.getJSONObject(i_eventi);
		                    		Fixtures fixtures = new Fixtures(objectFixture.getInt("fixture_id"), objectFixture.getInt("league_id"), objectFixture.getString("event_date"), 
		                    				objectFixture.getLong("event_timestamp"), objectFixture.getString("statusShort"), 
		                    				objectFixture.getJSONObject("homeTeam").getString("team_name"), 
		                    				objectFixture.getJSONObject("awayTeam").getString("team_name"),
		                    				objectFixture.getJSONObject("homeTeam").getInt("team_id"), 
		                    				objectFixture.getJSONObject("awayTeam").getInt("team_id"));
		                    		
		                    		
		                    		// HOME TEAM
		                    		RafTeams rafTeams_Home = rafTeamsDao.getTeams_from_idTeam(fixtures.getHomeTeam_id());
		                    		if( rafTeams_Home == null ) {
		                    			rafTeams_Home = new RafTeams(fixtures.getHomeTeam_id(), fixtures.getHomeTeam());
		                    			rafTeams_Home = rafTeamsDao.saveRafTeams(rafTeams_Home);
			                    	// nel caso in cui il team_name viene modificato nel tempo
		                    		}else if( rafTeams_Home != null && !rafTeams_Home.getTeam_name().equals(fixtures.getHomeTeam()) ) {
		                    			rafTeams_Home.setTeam_name(fixtures.getHomeTeam());
		                    			rafTeams_Home = rafTeamsDao.saveRafTeams(rafTeams_Home);
		                    		}
		                    		
		                    		// AWAY TEAM
		                    		RafTeams rafTeams_Away = rafTeamsDao.getTeams_from_idTeam(fixtures.getAwayTeam_id());
		                    		if( rafTeams_Away == null ) {
		                    			rafTeams_Away = new RafTeams(fixtures.getAwayTeam_id(), fixtures.getAwayTeam());
		                    		// nel caso in cui il team_name viene modificato nel tempo
		                    		}else if( rafTeams_Away != null && !rafTeams_Away.getTeam_name().equals(fixtures.getAwayTeam()) ) {
		                    			rafTeams_Away.setTeam_name(fixtures.getAwayTeam());
		                    			rafTeams_Away = rafTeamsDao.saveRafTeams(rafTeams_Away);
		                    		}
		                    		
			            			RafEventi rafEvento = rafEventiDao.GetEvento_from_idFixture( fixtures.getFixture_id() );
			            			try {
					    				if(rafEvento == null) {
					    					rafEvento = new RafEventi(rafCompetizione, fixtures.getFixture_id(), fixtures.getLeague_id(), fixtures.getEvent_date(), fixtures.getEvent_timestamp(), 
					    							fixtures.getStatusShort(), fixtures.getHomeTeam(), fixtures.getAwayTeam(), rafTeams_Home, rafTeams_Away);
					    					rafEventiDao.saveRafEventi(rafEvento);
					    					
					    				}else {
					    					rafEvento.setEvent_date(fixtures.getEvent_date()); 				// aggiorno la data evento, perché sembra che betfair la modifica
					    					rafEvento.setEvent_timestamp(fixtures.getEvent_timestamp()); 	// aggiorno la data evento, perché sembra che betfair la modifica
					    					rafEventiDao.saveRafEventi(rafEvento);
					    				}
			            			
		                        		for(Eventi ite_eventi_bf: listaEventiBetFair) {
		                        			if( ite_eventi_bf.getRafEventi() == null && RapidApiApiFootball_Util.ConfrontoEventi(rafEvento, ite_eventi_bf, 
		                        					RapidApiApiFootball_Util.punteggioSimilarityAlto) ) {
	            	            				ite_eventi_bf.setRafEventi(rafEvento);
	            	            				ite_eventi_bf.setRafCompetizioni(rafCompetizione);
	            	            				eventiDao.saveEventi(ite_eventi_bf);
//	            	            				Salva_1++;
//	            	            				log.info( "BF evento: "+ite_eventi_bf.getDataEvento()+" "+ite_eventi_bf.getNome() +" RAF evento: "+rafEvento.getEvent_date()+" "+rafEvento.getHomeTeam()+" - "+rafEvento.getAwayTeam());
	            	            				break;
		                        			}
		                            	}
			            			}catch(final DataIntegrityViolationException dataIntegrViolException) {
//	                					log.info(dataIntegrViolException.getMessage());
			            			}catch(final Exception exc) {
//	                					log.info(exc.getMessage());
			            			}
		                    	}
		                    	
        					}catch(final JSONException exc) {
	        					exc.printStackTrace();
	        				}catch(final Exception exc) {
	        					exc.printStackTrace();
	            			}
        				}
        			}
        		}
        	}
        	
        	
        	/**
        	 * RAFFINAZIONE 2
        	 */
        	List<Eventi> listaEventi = eventiDao.getEventi();
        	
        	for(Eventi ite_eventi: listaEventi) {
        		if(ite_eventi.getRafEventi() == null ) {
        			
        			String siglaNazione = ite_eventi.getCompetizione().getNazione().getSiglaNazione();
    				String eventDate = DateUtil.FormatoData_3.format(ite_eventi.getDataEvento());
    				//log.info("siglaNazione: "+siglaNazione +" eventDate:"+eventDate);
    				List<RafEventi> listRafEventiDaAssegnare = rafEventiDao.getRafEventi_from_siglaNazione_dataRafEvento_nonPresenteInEventi(siglaNazione, eventDate);
        			
    				RafEventi refEventoMigliore = null; double punteggioMigliore = 0d;
    				
    				for(RafEventi ite_rafEventi_daAssegnare: listRafEventiDaAssegnare) {
						if( RapidApiApiFootball_Util.EventiSameDay(ite_rafEventi_daAssegnare, ite_eventi ) ) {
							double punteggio = RapidApiApiFootball_Util.Punteggio_Somiglianza_Stringhe(ite_rafEventi_daAssegnare.getHomeTeam()+" - "+ite_rafEventi_daAssegnare.getAwayTeam(), ite_eventi.getNome());
	        				if( punteggio >= RapidApiApiFootball_Util.punteggioSimilarityMedio && punteggio > punteggioMigliore ) {
								punteggioMigliore = punteggio;
								refEventoMigliore = ite_rafEventi_daAssegnare;
							}
						}
					}
    				
    				if( refEventoMigliore != null ) {
						try {
							ite_eventi.setRafCompetizioni( refEventoMigliore.getRafCompetizioni() != null 
    								? refEventoMigliore.getRafCompetizioni() : rafEventiDao.get(refEventoMigliore.getId()).getRafCompetizioni() );
							ite_eventi.setRafEventi(refEventoMigliore);
        					eventiDao.saveEventi(ite_eventi);
//        					Salva_2++;
//        					log.info( "BF evento: "+ite_eventi.getDataEvento()+" "+ite_eventi.getNome() +" RAF evento: "+refEventoMigliore.getEvent_date()+" "+refEventoMigliore.getHomeTeam()+" - "+refEventoMigliore.getAwayTeam());
    					}catch(final DataIntegrityViolationException dataIntegrViolException) {
//        					log.info(dataIntegrViolException.getMessage());
        				}
					}
    				
        		}
        	}
        	
        	
        	
        	/**
        	 * RAFFINAZIONE 3
        	 */
        	listaEventi = eventiDao.getEventi_orderBy_idCompetizione();
        	Long idCompetizione = null; List<Eventi> listaEventiCompetizioneCheck = new ArrayList<Eventi>();
        	for(int conta = 0; conta < listaEventi.size(); conta++ ) {
        		idCompetizione = listaEventi.get(conta).getCompetizione().getId();
        		listaEventiCompetizioneCheck.add(listaEventi.get(conta));
        		if( conta + 1 == listaEventi.size() 
        				|| idCompetizione.longValue() != listaEventi.get(conta + 1).getCompetizione().getId().longValue() ) {
        			boolean eventiNonAssegnati = false;
        			for(Eventi ite_eventi_check: listaEventiCompetizioneCheck) {
        				if(ite_eventi_check.getRafEventi() == null ) {
        					eventiNonAssegnati = true;
        					break;
        				}
        			}
        			if( eventiNonAssegnati ) {
	        			List<Long> listLongIdRafCompetizioni = new ArrayList<Long>(); List<Long> listLongIdRafEventi = new ArrayList<Long>();  
	        			List<RafEventi> listRafEventiDaAssegnare = new ArrayList<RafEventi>();  
	        			for(Eventi ite_eventi_check: listaEventiCompetizioneCheck) {
	        				if( ite_eventi_check.getRafCompetizioni() != null && ite_eventi_check.getRafEventi() != null ) {
		        				if( !listLongIdRafCompetizioni.contains(ite_eventi_check.getRafCompetizioni().getId()) ) {
		        					listLongIdRafCompetizioni.add( ite_eventi_check.getRafCompetizioni().getId() );
		        				}
		        				if( !listLongIdRafEventi.contains(ite_eventi_check.getRafEventi().getId()) ) {
		        					listLongIdRafEventi.add( ite_eventi_check.getRafEventi().getId() );
		        				}
	        				}
	        			}
	        			
	        			if( listLongIdRafCompetizioni.size() > 0 && listLongIdRafEventi.size() > 0 ) {
	        				listRafEventiDaAssegnare = rafEventiDao.getRafEventi_EventiCampionato_NonAssegnati(listLongIdRafCompetizioni, listLongIdRafEventi);
	        			}
	        			
	        			for(Eventi ite_eventi_check: listaEventiCompetizioneCheck) {
	        				RafEventi refEventoMigliore = null; double punteggioMigliore = 0d;
	        				if(ite_eventi_check.getRafEventi() == null ) {
	        					for(RafEventi ite_rafEventi_daAssegnare: listRafEventiDaAssegnare) {
	        						if( RapidApiApiFootball_Util.EventiSameDay(ite_rafEventi_daAssegnare, ite_eventi_check ) ) {
	        							
	        							double punteggio = RapidApiApiFootball_Util.Punteggio_Somiglianza_Stringhe(ite_rafEventi_daAssegnare.getHomeTeam()
	        									+" - "+ite_rafEventi_daAssegnare.getAwayTeam(), ite_eventi_check.getNome());
	        							
	        							if( punteggio >= RapidApiApiFootball_Util.punteggioSimilarityBasso && punteggio > punteggioMigliore ) {
	        								punteggioMigliore = punteggio;
	        								refEventoMigliore = ite_rafEventi_daAssegnare;
	        							}
	        						}
	        					}
	        					if( refEventoMigliore != null ) {
	        						try {
		        						ite_eventi_check.setRafCompetizioni( refEventoMigliore.getRafCompetizioni() != null 
		        								? refEventoMigliore.getRafCompetizioni() : rafEventiDao.get(refEventoMigliore.getId()).getRafCompetizioni() );
			        					ite_eventi_check.setRafEventi(refEventoMigliore);
			        					eventiDao.saveEventi(ite_eventi_check);
//			        					Salva_3++;
//			        					log.info( "BF evento: "+ite_eventi_check.getDataEvento()+" "+ite_eventi_check.getNome() +" RAF evento: "+refEventoMigliore.getEvent_date()+" "+refEventoMigliore.getHomeTeam()+" - "+refEventoMigliore.getAwayTeam());
			        					
		        					}catch(final DataIntegrityViolationException dataIntegrViolException) {
//	                					log.info(dataIntegrViolException.getMessage());
	                				}
	        					}
	        					
	        				}
	        			}
        			}
        			listaEventiCompetizioneCheck = new ArrayList<Eventi>();
        		}
        	}
        	
//        	log.info("Salva_1: "+Salva_1+" Salva_2: "+Salva_2+" Salva_3: "+Salva_3);

        }catch( Exception exc ) {
        	exc.printStackTrace();
        }
    }
    
 

}
