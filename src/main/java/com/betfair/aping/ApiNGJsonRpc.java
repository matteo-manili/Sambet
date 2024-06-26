package com.betfair.aping;

import com.betfair.aping.api.ApiNgJsonRpcOperations;
import com.betfair.aping.api.ApiNgOperations;
import com.betfair.aping.entities.*;
import com.betfair.aping.enums.*;
import com.betfair.aping.exceptions.APINGException;
import com.sambet.Constants;
import com.sambet.dao.CompetizioniDao;
import com.sambet.dao.EventiDao;
import com.sambet.dao.ListaNazioniDao;
import com.sambet.dao.Market_EsitoFinaleDao;
import com.sambet.dao.Market_GolDao;
import com.sambet.dao.Market_OverUnder_25Dao;
import com.sambet.dao.NazioniDao;
import com.sambet.model.Competizioni;
import com.sambet.model.Eventi;
import com.sambet.model.ListaNazioni;
import com.sambet.model.Market_EsitoFinale;
import com.sambet.model.Market_Gol;
import com.sambet.model.Market_OverUnder_25;
import com.sambet.model.Nazioni;
import com.sambet.util.CheckQuote;
import com.sambet.webapp.util.ApplicationUtils;
import java.util.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This is a demonstration class to show a quick demo of the new Betfair API-NG.
 * When you execute the class will: <li>find a market (next horse race in the
 * UK)</li> <li>get prices and runners on this market</li> <li>place a bet on 1
 * runner</li> <li>handle the error</li>
 *
 */
public class ApiNGJsonRpc extends ApplicationUtils {
	
	private static final Log log = LogFactory.getLog(ApiNGJsonRpc.class);

	public static ListaNazioniDao listaNazioniDao = (ListaNazioniDao) contextDao.getBean("ListaNazioniDao");
	public static NazioniDao nazioniDao = (NazioniDao) contextDao.getBean("NazioniDao");
	public static CompetizioniDao competizioniDao = (CompetizioniDao) contextDao.getBean("CompetizioniDao");
	public static EventiDao eventiDao = (EventiDao) contextDao.getBean("EventiDao");
	public static Market_EsitoFinaleDao market_EsitoFinaleDao = (Market_EsitoFinaleDao) contextDao.getBean("Market_EsitoFinaleDao");
	public static Market_OverUnder_25Dao market_OverUnder_25Dao = (Market_OverUnder_25Dao) contextDao.getBean("Market_OverUnder_25Dao");
	public static Market_GolDao market_GolDao = (Market_GolDao) contextDao.getBean("Market_GolDao");

	private static ApiNgOperations jsonOperations = ApiNgJsonRpcOperations.getInstance();
	private static String applicationKey; 
	private static String sessionToken;

	public static final String MATCH_ODDS = "MATCH_ODDS";
	public static final String OVER_UNDER_25 = "OVER_UNDER_25";
	public static final String BOTH_TEAMS_TO_SCORE = "BOTH_TEAMS_TO_SCORE";
	public static final String EVENT_TYPE_ID = "1"; //Calcio



	public static void start(String appKey, String ssoid) throws APINGException {
//		log.info("com.betfair.aping ApiNGJsonRpc start");
		applicationKey = appKey; sessionToken = ssoid;
		try {
			MarketFilter marketFilter;
			marketFilter = new MarketFilter();

			List<EventTypeResult> eventTypeResult = jsonOperations.listEventTypes(marketFilter, applicationKey, sessionToken);
			Set<String> eventTypeIds = new HashSet<String>();
			for (EventTypeResult ite : eventTypeResult) {
				//log.info( ite.getEventType().getId()+" | "+ite.getEventType().getName() );
				if( ite.getEventType().getId().equals( EVENT_TYPE_ID ) /* && ite.getEventType().getName().equals("Calcio")*/ ) {
					eventTypeIds.add(ite.getEventType().getId().toString());
					break;
				}
			}

			/**
			 * ListMarketCatalogue: Get next available horse races, parameters:
			 * eventTypeIds : 7 - get all available horse races for event id 7 (horse racing)
			 * maxResults: 1 - specify number of results returned (narrowed to 1 to get first race)
			 * marketStartTime: specify date (must be in this format: yyyy-mm-ddTHH:MM:SSZ)
			 * sort: FIRST_TO_START - specify sort order to first to start race
			 */

			// 5 | 1000 | COMMENTO: FINE_LAB-  SECONDI: 207.940792

			// 2 | 1000 | COMMENTO: FINE_LAB-  SECONDI: 216.0413471
			// 2 | 500 | COMMENTO: FINE_LAB-  SECONDI: 208.6810968
			// 2 | 500 | 550 | COMMENTO: FINE_LAB-  SECONDI: 215.5009907

			// 1 | 1000 | 562 | COMMENTO: FINE_LAB-  SECONDI: 233.3513348
			// 1 | 500 | 562 | COMMENTO: FINE_LAB-  SECONDI: 229.2243954
			// 1 | 1000 | COMMENTO: FINE_LAB-  SECONDI: 224.1490873

			// COMBINAZIONE MIGLIORE: maxResultsLong = 500 | MaxResultListaNazioni = 1

			final int maxResultsLong = (int)(long) gestioneApplicazioneDao.getName("API_MAX_RESULT_MARKET_BETFAIR").getValueNumber(); //MAX 1000 (MERCATI)
			//final int MaxResultListaNazioni = (int)(long) gestioneApplicazioneDao.getName("MAX_RESULT_API_LISTA_NAZIONI").getValueNumber(); 

			List<ListaNazioni> listaNazioni = listaNazioniDao.getListaNazioni_Attivo(true);

			for( ListaNazioni ite:  listaNazioni ) {
				TimeRange time = new TimeRange();
				time.setFrom(new Date());
				Set<String> typesCode = new HashSet<String>();
				typesCode.add( MATCH_ODDS );
				typesCode.add( OVER_UNDER_25 );
				typesCode.add( BOTH_TEAMS_TO_SCORE );
				
//				log.info("maxResultsLong: "+maxResultsLong +" MaxResultListaNazioni: "+MaxResultListaNazioni +" ite.getSiglaNazione: "+ite.getSiglaNazione() );
				
				if( !ite.getSiglaNazione().equals(Constants.SILGLA_CAMPIONATO_INTERNAZIONALE) ) {
					Set<String> countries = new HashSet<String>();
					countries.add( ite.getSiglaNazione() );
					//countries.add("GB");
					//countries.add("IT");
	
					marketFilter = new MarketFilter();
					marketFilter.setEventTypeIds(eventTypeIds);
					marketFilter.setMarketStartTime(time);
					marketFilter.setMarketCountries(countries);
					marketFilter.setMarketTypeCodes(typesCode);
					
					SetMarketFilter(maxResultsLong, marketFilter);
					
				}else if( ite.getSiglaNazione().equals(Constants.SILGLA_CAMPIONATO_INTERNAZIONALE) ) {
					
					
					List<Competizioni> listCompetiz =  competizioniDao.getCompetizioni_from_siglaNazione( ite.getSiglaNazione() );
					for(Competizioni ite_competiz: listCompetiz) {
						
						Set<String> competitionIds = new HashSet<String>();
						//competitionIds.add("12243231"); // 12243231 UEFA
						competitionIds.add( ite_competiz.getIdCompetitionBetFair() );
						
						marketFilter = new MarketFilter();
						marketFilter.setEventTypeIds(eventTypeIds);
						marketFilter.setMarketStartTime(time);
						marketFilter.setMarketTypeCodes(typesCode);
						
						marketFilter.setCompetitionIds(competitionIds);
						
						SetMarketFilter(maxResultsLong, marketFilter);
					}
				}
			}
		} catch (Exception exc) {
			log.info(exc.toString());
		}
	}
	
	
	private static void SetMarketFilter(int maxResultsLong, MarketFilter marketFilter) throws APINGException {
		Set<MarketProjection> marketProjection = new HashSet<MarketProjection>();
		marketProjection.add(MarketProjection.COMPETITION);
		marketProjection.add(MarketProjection.EVENT);
		marketProjection.add(MarketProjection.MARKET_DESCRIPTION);
		marketProjection.add(MarketProjection.EVENT_TYPE);
		//marketProjection.add(MarketProjection.MARKET_START_TIME);
		//marketProjection.add(MarketProjection.RUNNER_DESCRIPTION);
		//marketProjection.add(MarketProjection.RUNNER_METADATA);
		
		List<MarketCatalogue> marketCatalogueResult = null;
		for(int maxResultsInt = maxResultsLong; maxResultsInt > 0; maxResultsInt = maxResultsInt -25  ) {
			try {
				String maxResults = String.valueOf(maxResultsInt);
				marketCatalogueResult = ListMarketCatalogue(marketFilter, marketProjection, maxResults);
				break;

			} catch (APINGException apiExc) {
//				log.info(apiExc.toString());
//				log.info("maxResultsInt: "+maxResultsInt);
				if( !apiExc.getErrorCode().equals("TOO_MUCH_DATA") ) {
					break;
				}
			}
		}
		if( marketCatalogueResult.size() > 0) {
			MemorizzaMarket(marketCatalogueResult);
		} else {
//			log.info( "marketCatalogueResult.size: "+marketCatalogueResult.size() );
		}
	}
	

	private static List<MarketCatalogue> ListMarketCatalogue(MarketFilter marketFilter, Set<MarketProjection> marketProjection, String maxResults) throws APINGException {
		List<MarketCatalogue> marketCatalogueResult = jsonOperations.listMarketCatalogue(marketFilter, marketProjection, MarketSort.FIRST_TO_START, maxResults, 
				applicationKey, sessionToken);
		return marketCatalogueResult;
	}


	private static void MemorizzaMarket(List<MarketCatalogue> marketCatalogueResult) throws APINGException {
		for( MarketCatalogue ite: marketCatalogueResult ) {
			if( ite.getEventType().getId().equals(EVENT_TYPE_ID) && ite.getEvent().getOpenDate().getTime() > new Date().getTime() ) {
				
				/*
				// TODO da togliere in produzione
				log.info(
						"NAZIONE: "+ite.getEvent().getCountryCode()+" | "+ite.toString()
						+" | COMPETIZIONE: "+ite.getCompetition().getId()
						+" | "+ite.getCompetition().getName()
						+" | EVENTO: "+ite.getEvent().getId()
						+" | "+ite.getEvent().getOpenDate()
						+" | "+ite.getEvent().getName()
						+" | MATKET "+ite.getMarketId()
						+" | "+ite.getDescription().getMarketType()
						+" | "+ite.getMarketName());
				*/
				
				String SiglaNazione = ite.getEvent().getCountryCode() != null ? ite.getEvent().getCountryCode() : Constants.SILGLA_CAMPIONATO_INTERNAZIONALE;
				Nazioni nazione = nazioniDao.getNazione_from_SiglaNazione( SiglaNazione );
				if(nazione == null) {
					nazione = new Nazioni(SiglaNazione, true);
					nazione = nazioniDao.saveNazioni(nazione);
				}
				Competizioni competizione = competizioniDao.GetCompetizione_from_idCompetitionBetFair( ite.getCompetition().getId() );
				if(competizione == null) {
					competizione = new Competizioni();
					competizione.setIdCompetitionBetFair( ite.getCompetition().getId() );
					competizione.setNome( ite.getCompetition().getName() );
					competizione.setAttivo( true );
					competizione.setNazione( nazione );
					competizione = competizioniDao.saveCompetizioni( competizione );
				}
				Eventi evento = eventiDao.GetEvento_from_idEventBetFair( ite.getEvent().getId() );
				if(evento == null) {
					evento = new Eventi();
					evento.setIdEventBetFair( ite.getEvent().getId() );
					evento.setDataEvento( ite.getEvent().getOpenDate() );
					evento.setNome( ite.getEvent().getName() );
					evento.setCompetizione( competizione );
					evento = eventiDao.saveEventi( evento );
				}else {
					evento.setDataEvento( ite.getEvent().getOpenDate() ); // aggiorno la data evento, perché sembra che betfair la modifica
					evento = eventiDao.saveEventi( evento );
				}

				/*
				List<RunnerCatalog> runners = ite.getRunners();
			    if(runners!=null) {
			        for(RunnerCatalog rCat : runners){
			            //log.info("Runner Name: "+rCat.getRunnerName()+" | Selection Id: "+rCat.getSelectionId());
			        }
			    }
				 */

				///-------- DETTAGLIO QUOTAZIONI-----------------

				PriceProjection priceProjection = new PriceProjection();
				Set<PriceData> priceData = new HashSet<PriceData>();
				priceData.add(PriceData.EX_BEST_OFFERS); // Questa è quella che fa vedere le quotazioni
				//priceData.add(PriceData.EX_ALL_OFFERS);
				//priceData.add(PriceData.EX_BEST_OFFERS);
				//priceData.add(PriceData.EX_TRADED);
				//priceData.add(PriceData.SP_AVAILABLE);
				//priceData.add(PriceData.SP_TRADED);

				priceProjection.setPriceData(priceData);

				//In this case we don't need these objects so they are declared null
				OrderProjection orderProjection = null;
				MatchProjection matchProjection = null;
				String currencyCode = "EUR";

				List<String> marketIds = new ArrayList<String>();
				marketIds.add(ite.getMarketId());

				List<MarketBook> marketBookReturn = jsonOperations.listMarketBook(marketIds, priceProjection, orderProjection, matchProjection, currencyCode, 
						applicationKey, sessionToken);

				for(MarketBook marketBookReturn_ite: marketBookReturn) {
					//log.info("MarketId: "+marketBookReturn_ite.getMarketId()+" Status: "+marketBookReturn_ite.getStatus());
					int conta = 1;
					Double Quota_1 = null;
					Double Quota_2 = null;
					Double Quota_3 = null;
					for(Runner runner_ite: marketBookReturn_ite.getRunners() ) {
						//log.info("------------ "+ conta + "size: "+marketBookReturn_ite.getRunners().size()+" --------------");
						Double firstQuota = null;
						for(PriceSize priceSize_ite: runner_ite.getEx().getAvailableToLay() ) {
							firstQuota = priceSize_ite.getPrice() ;
							//log.info( "Quota: "+priceSize_ite.getPrice() ); // TODO da togliere in produzione
							break;
						}
						if( conta == 1 ) {
							Quota_1 = firstQuota;
						}else if( conta == 2 ) {
							Quota_2 = firstQuota;
						}else if( conta == 3 ) {
							Quota_3 = firstQuota;
						}
						conta++;
					}

					if( Controllo_Aggio(ite.getDescription().getMarketType(), Quota_1, Quota_2, Quota_3) ) {
						if( ite.getDescription().getMarketType().equals(MATCH_ODDS) ) {
							List<Market_EsitoFinale> listMarketEsitoFinale = market_EsitoFinaleDao.getMarket_EsitoFinale_Evento(evento.getId());
							CheckQuote checkQuote = new CheckQuote();
							checkQuote.CheckQuote_Market_EsitoFinale(listMarketEsitoFinale, Quota_1, Quota_2, Quota_3);
							//QUOTA APERTURA
							if( checkQuote.isQuotaAperturaPresente() == false ) {
								Market_EsitoFinale mark_EsitoFinale = new Market_EsitoFinale(marketBookReturn_ite.getMarketId(), new Date(), Quota_1, Quota_2, Quota_3, Constants.QTA_APERTURA, evento);
								market_EsitoFinaleDao.saveMarket_EsitoFinale(mark_EsitoFinale);
							}else {
								for(Market_EsitoFinale ite_listMarketEsitoFinale: listMarketEsitoFinale) {
									if( ite_listMarketEsitoFinale.getTipoQuota().intValue() == Constants.QTA_APERTURA ) {
										if( ite_listMarketEsitoFinale.getQuota_1() == null ) {
											ite_listMarketEsitoFinale.setQuota_1(Quota_1);
											market_EsitoFinaleDao.saveMarket_EsitoFinale(ite_listMarketEsitoFinale);
										}
										if( ite_listMarketEsitoFinale.getQuota_2() == null ) {
											ite_listMarketEsitoFinale.setQuota_2(Quota_2);
											market_EsitoFinaleDao.saveMarket_EsitoFinale(ite_listMarketEsitoFinale);
										}
										if( ite_listMarketEsitoFinale.getQuota_X() == null ) {
											ite_listMarketEsitoFinale.setQuota_X(Quota_3);
											market_EsitoFinaleDao.saveMarket_EsitoFinale(ite_listMarketEsitoFinale);
										}
									}
								}
							}
							//QUOTA MEDIA
							if( checkQuote.isQuotaMediaPresente() == false ) {
								Market_EsitoFinale mark_EsitoFinale = new Market_EsitoFinale(marketBookReturn_ite.getMarketId(), new Date(), Quota_1, Quota_2, Quota_3, Constants.QTA_MEDIA, evento);
								market_EsitoFinaleDao.saveMarket_EsitoFinale(mark_EsitoFinale);
							}
							//QUOTA ATTUALE
							if( checkQuote.isQuotaAttualePresente() == false ) {
								market_EsitoFinaleDao.saveMarket_EsitoFinale(new Market_EsitoFinale(marketBookReturn_ite.getMarketId(), new Date(), Quota_1, Quota_2, Quota_3, Constants.QTA_ATTUALE, evento));
							}else {
								for(Market_EsitoFinale ite_listMarketEsitoFinale: listMarketEsitoFinale) {
									if(ite_listMarketEsitoFinale.getTipoQuota().intValue() == Constants.QTA_ATTUALE) {
										ite_listMarketEsitoFinale.setIdMarketBetFair( marketBookReturn_ite.getMarketId() );
										ite_listMarketEsitoFinale.setDataRichiesta( new Date() );
										ite_listMarketEsitoFinale.setQuota_1(Quota_1);
										ite_listMarketEsitoFinale.setQuota_2(Quota_2);
										ite_listMarketEsitoFinale.setQuota_X(Quota_3);
										market_EsitoFinaleDao.saveMarket_EsitoFinale(ite_listMarketEsitoFinale);
										break;
									}
								}
							}

						}else if( ite.getDescription().getMarketType().equals(OVER_UNDER_25) ) {
							List<Market_OverUnder_25> listMarket_OverUnder_25 = market_OverUnder_25Dao.getMarket_OverUnder_25_Evento(evento.getId());
							CheckQuote checkQuote = new CheckQuote();
							checkQuote.CheckQuote_Market_OverUnder_25(listMarket_OverUnder_25, Quota_1, Quota_2);
							//QUOTA APERTURA
							if( checkQuote.isQuotaAperturaPresente() == false ) {
								Market_OverUnder_25 overUnder25 = new Market_OverUnder_25(marketBookReturn_ite.getMarketId(), new Date(), Quota_1, Quota_2, Constants.QTA_APERTURA, evento);
								market_OverUnder_25Dao.saveMarket_OverUnder_25(overUnder25);
							}else {
								for(Market_OverUnder_25 ite_listMarket_OverUnder_25: listMarket_OverUnder_25) {
									if( ite_listMarket_OverUnder_25.getTipoQuota().intValue() == Constants.QTA_APERTURA ) {
										if( ite_listMarket_OverUnder_25.getQuota_under() == null ) {
											ite_listMarket_OverUnder_25.setQuota_under(Quota_1);
											market_OverUnder_25Dao.saveMarket_OverUnder_25(ite_listMarket_OverUnder_25);
										}
										if( ite_listMarket_OverUnder_25.getQuota_over() == null ) {
											ite_listMarket_OverUnder_25.setQuota_over(Quota_2);
											market_OverUnder_25Dao.saveMarket_OverUnder_25(ite_listMarket_OverUnder_25);
										}
									}
								}
							}
							//QUOTA MEDIA
							if( checkQuote.isQuotaMediaPresente() == false ) {
								Market_OverUnder_25 overUnder25 = new Market_OverUnder_25(marketBookReturn_ite.getMarketId(), new Date(), Quota_1, Quota_2, Constants.QTA_MEDIA, evento);
								market_OverUnder_25Dao.saveMarket_OverUnder_25(overUnder25);
							}
							//QUOTA ATTUALE
							if( checkQuote.isQuotaAttualePresente() == false ) {
								market_OverUnder_25Dao.saveMarket_OverUnder_25(new Market_OverUnder_25(marketBookReturn_ite.getMarketId(), new Date(), Quota_1, Quota_2, Constants.QTA_ATTUALE, evento));
							}else {
								for(Market_OverUnder_25 ite_listMarket_OverUnder_25: listMarket_OverUnder_25) {
									if(ite_listMarket_OverUnder_25.getTipoQuota().intValue() == Constants.QTA_ATTUALE) {
										ite_listMarket_OverUnder_25.setIdMarketBetFair( marketBookReturn_ite.getMarketId() );
										ite_listMarket_OverUnder_25.setDataRichiesta( new Date() );
										ite_listMarket_OverUnder_25.setQuota_under(Quota_1);
										ite_listMarket_OverUnder_25.setQuota_over(Quota_2);
										market_OverUnder_25Dao.saveMarket_OverUnder_25(ite_listMarket_OverUnder_25);
										break;
									}
								}
							}
							
						}else if( ite.getDescription().getMarketType().equals(BOTH_TEAMS_TO_SCORE) ) {
							List<Market_Gol> listMarket_Gol = market_GolDao.getMarket_Gol_Evento(evento.getId());
							CheckQuote checkQuote = new CheckQuote();
							checkQuote.CheckQuote_Market_Gol(listMarket_Gol, Quota_1, Quota_2);
							//QUOTA APERTURA
							if( checkQuote.isQuotaAperturaPresente() == false ) {
								Market_Gol mark_Gol = new Market_Gol(marketBookReturn_ite.getMarketId(), new Date(), Quota_1, Quota_2, Constants.QTA_APERTURA, evento);
								market_GolDao.saveMarket_Gol(mark_Gol);
							}else {
								for(Market_Gol ite_listMarket_Gol: listMarket_Gol) {
									if( ite_listMarket_Gol.getTipoQuota().intValue() == Constants.QTA_APERTURA ) {
										if( ite_listMarket_Gol.getQuota_gol() == null ) {
											ite_listMarket_Gol.setQuota_gol(Quota_1);
											market_GolDao.saveMarket_Gol(ite_listMarket_Gol);
										}
										if( ite_listMarket_Gol.getQuota_noGol() == null ) {
											ite_listMarket_Gol.setQuota_noGol(Quota_2);
											market_GolDao.saveMarket_Gol(ite_listMarket_Gol);
										}
									}
								}
							}
							//QUOTA MEDIA
							if( checkQuote.isQuotaMediaPresente() == false ) {
								Market_Gol mark_Gol = new Market_Gol(marketBookReturn_ite.getMarketId(), new Date(), Quota_1, Quota_2, Constants.QTA_MEDIA, evento);
								market_GolDao.saveMarket_Gol(mark_Gol);
							}
							//QUOTA ATTUALE
							if( checkQuote.isQuotaAttualePresente() == false ) {
								market_GolDao.saveMarket_Gol(new Market_Gol(marketBookReturn_ite.getMarketId(), new Date(), Quota_1, Quota_2, Constants.QTA_ATTUALE, evento));
							}else {
								for(Market_Gol ite_listMarket_Gol: listMarket_Gol) {
									if(ite_listMarket_Gol.getTipoQuota().intValue() == Constants.QTA_ATTUALE) {
										ite_listMarket_Gol.setIdMarketBetFair( marketBookReturn_ite.getMarketId() );
										ite_listMarket_Gol.setDataRichiesta( new Date() );
										ite_listMarket_Gol.setQuota_gol(Quota_1);
										ite_listMarket_Gol.setQuota_noGol(Quota_2);
										market_GolDao.saveMarket_Gol(ite_listMarket_Gol);
										break;
									}
								}
							}
						}
					}
				}
			}
		}
	}

	
	
	


	private static boolean Controllo_Aggio(String MarketType, Double Quota_1, Double Quota_2, Double Quota_3) {
		final double ValoreQuotaNull = 10; 
		final double AggioMinResult_MATCH_ODDS = 0.85; 
		final double AggioMinResult_OVER_UNDER_25__BOTH_TEAMS_TO_SCORE = 0.85;
		if( MarketType.equals(MATCH_ODDS) ) {
			double var1 = Quota_1 != null ? 1 / Quota_1 : 1 / ValoreQuotaNull;
			double var2 = Quota_2 != null ? 1 / Quota_2 : 1 / ValoreQuotaNull;
			double var3 = Quota_3 != null ? 1 / Quota_3 : 1 / ValoreQuotaNull;
			if( (var1 + var2 + var3) >= AggioMinResult_MATCH_ODDS ) {
				return true;
			}

		}else if( MarketType.equals(OVER_UNDER_25) || MarketType.equals(BOTH_TEAMS_TO_SCORE) ) {
			double var1 = Quota_1 != null ? 1 / Quota_1 : 1 / ValoreQuotaNull;
			double var2 = Quota_2 != null ? 1 / Quota_2 : 1 / ValoreQuotaNull;

			if( (var1 + var2) >= AggioMinResult_OVER_UNDER_25__BOTH_TEAMS_TO_SCORE ) {
				return true;
			}
		}
		return false;
	}


	private static double getPrice() {
		try {
			return new Double((String) ApiNGDemo.getProp().get("BET_PRICE"));
		} catch (NumberFormatException e) {
			//returning the default value
			return new Double(1000);
		}
	}

	private static double getSize(){
		try{
			return new Double((String)ApiNGDemo.getProp().get("BET_SIZE"));
		} catch (NumberFormatException e){
			//returning the default value
			return new Double(0.01);
		}
	}



}
