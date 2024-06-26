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
import com.sambet.model.ListaNazioni;
import com.sambet.model.Nazioni;
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
public class ApiNGJsonRpcSearchInternatCompet extends ApplicationUtils {
	
	private static final Log log = LogFactory.getLog(ApiNGJsonRpcSearchInternatCompet.class);

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

	private static int ContaGenerale;

	public static void start(String appKey, String ssoid) throws APINGException {
		applicationKey = appKey;
		sessionToken = ssoid;
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


			final int maxResultsLong = (int)(long) gestioneApplicazioneDao.getName("API_MAX_RESULT_MARKET_BETFAIR").getValueNumber(); //MAX 1000 (MERCATI)
			final int MaxResultListaNazioni = (int)(long) gestioneApplicazioneDao.getName("MAX_RESULT_API_LISTA_NAZIONI").getValueNumber(); 

			List<Integer> list_Giorni_From_Data_Evento = new ArrayList<Integer>(Arrays.asList(0, 1, 2, 3, 4));
			//List<Integer> list_Giorni_From_Data_Evento = new ArrayList<Integer>(Arrays.asList(0));

			for(Integer ite_list_Giorni_From_Data_Evento: list_Giorni_From_Data_Evento) {
				
				Set<String> countries = new HashSet<String>();
				//countries.add("GB");
				//countries.add("IT");
				countries.add( "" );
//				log.info("maxResultsLong: "+maxResultsLong +" MaxResultListaNazioni: "+MaxResultListaNazioni );
				
				TimeRange time = new TimeRange();
				if( ite_list_Giorni_From_Data_Evento.intValue() == 0 ) {
					time.setFrom( new Date() );
				}else {
					Date dt = new Date();
					Calendar c = Calendar.getInstance(); 
					c.setTime(dt); 
					c.add(Calendar.DATE, ite_list_Giorni_From_Data_Evento);
					dt = c.getTime();
					time.setFrom( c.getTime() );
				}
				
				Set<String> typesCode = new HashSet<String>();
				typesCode.add( MATCH_ODDS );
				typesCode.add( OVER_UNDER_25 );
				typesCode.add( BOTH_TEAMS_TO_SCORE );
	
				marketFilter = new MarketFilter();
				marketFilter.setEventTypeIds(eventTypeIds);
				marketFilter.setMarketStartTime(time);
				marketFilter.setMarketCountries(countries);
				marketFilter.setMarketTypeCodes(typesCode);
				//marketFilter.setTextQuery("Preliminari UEFA Europa League");
				/*
				Set<String> competitionIds = new HashSet<String>();
				competitionIds.add("12243231");
				marketFilter.setCompetitionIds(competitionIds);
				*/
				// 12243231 UEFA
	
				Set<MarketProjection> marketProjection = new HashSet<MarketProjection>();
				marketProjection.add(MarketProjection.COMPETITION);
				marketProjection.add(MarketProjection.EVENT);
				//marketProjection.add(MarketProjection.MARKET_DESCRIPTION);
				//marketProjection.add(MarketProjection.EVENT_TYPE);
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
//						log.info(apiExc.toString());
//						log.info("maxResultsInt: "+maxResultsInt);
						if( !apiExc.getErrorCode().equals("TOO_MUCH_DATA") ) {
							break;
						}
					}
				}
				if( marketCatalogueResult.size() > 0) {
					MemorizzaMarket(marketCatalogueResult);
				} else {
					String sorry = "marketCatalogueResult.size: "+marketCatalogueResult.size();
//					log.info(sorry);
				}
//				log.info("ContaGenerale: "+ContaGenerale);
				
			}
		} catch (Exception exc) {
			log.info(exc.toString());
		}
	}
	

	private static List<MarketCatalogue> ListMarketCatalogue(MarketFilter marketFilter, Set<MarketProjection> marketProjection, String maxResults) throws APINGException {
		List<MarketCatalogue> marketCatalogueResult = jsonOperations.listMarketCatalogue(marketFilter, marketProjection, MarketSort.FIRST_TO_START, maxResults, 
				applicationKey, sessionToken);
		return marketCatalogueResult;
	}


	private static void MemorizzaMarket(List<MarketCatalogue> marketCatalogueResult) throws APINGException {
		for( MarketCatalogue ite: marketCatalogueResult ) {
			if( ite.getEvent().getCountryCode() == null ) {
				// TODO togliere
				/*
				log.info( "NAZIONE: "+ite.getEvent().getCountryCode()
						+" | COMPETIZIONE: "+ite.getCompetition().getId()
						+" | "+ite.getCompetition().getName()
						+" | EVENTO: "+ite.getEvent().getId()
						+" | "+ite.getEvent().getOpenDate()
						+" | "+ite.getEvent().getName()
						+" | MATKET "+ite.getMarketId()
						//+" | "+ite.getDescription().getMarketType()
						+" | "+ite.getMarketName());
				*/
				ContaGenerale++;

				ListaNazioni listaNazioni = listaNazioniDao.getListaNazioni_from_siglaNazione( Constants.SILGLA_CAMPIONATO_INTERNAZIONALE );
				if(listaNazioni == null) {
					listaNazioni = new ListaNazioni();
					listaNazioni.setSiglaNazione( Constants.SILGLA_CAMPIONATO_INTERNAZIONALE );
					listaNazioni.setAttivo(true);
					listaNazioni.setStatus(0);
					listaNazioniDao.saveListaNazioni(listaNazioni);
				}
				
				Nazioni nazione = nazioniDao.getNazione_from_SiglaNazione( Constants.SILGLA_CAMPIONATO_INTERNAZIONALE );
				if(nazione == null) {
					nazione = new Nazioni();
					nazione.setSiglaNazione( Constants.SILGLA_CAMPIONATO_INTERNAZIONALE );
					nazione.setAttivo( true );
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
			}
		}
	}



}
