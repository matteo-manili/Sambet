package com.sambet.webapp.controller.home;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.sambet.Constants;
import com.sambet.dao.CompetizioniDao;
import com.sambet.dao.EventiDao;
import com.sambet.util.DateUtil;
import com.sambet.webapp.util.bean.MainTable;
import com.sambet.webapp.util.bean.MainTable.RowTable;
import com.sambet.webapp.util.bean.MainTable.RowTable.Pronostico;
import com.sambet.webapp.util.bean.MainTable.RowTable.Quotazione;
import com.sambet.webapp.util.bean.MainTableFiltri.FiltroAscDesc;
import com.sambet.webapp.util.bean.MainTableFiltri.FiltroCampionati;
import com.sambet.webapp.util.bean.MainTableFiltri.FiltroNazioni;
import com.sambet.webapp.util.bean.MainTableFiltri;

/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
public class CaricaMainTable  {
	public static ApplicationContext contextDao = new ClassPathXmlApplicationContext("App-Database-Spring-Module-Web.xml");
	private static EventiDao eventiDao = (EventiDao) contextDao.getBean("EventiDao");
	private static CompetizioniDao competizioniDao = (CompetizioniDao) contextDao.getBean("CompetizioniDao");
	
	
	public static MainTableFiltri Carica_Table(String siglaNazione, List<Long> idArrayListCompetizione, String dataInizioPartita, String oraInizioPartita, String dataFinePartita, 
			String oraFinePartita, String nomePartita, HashMap<String, String> filtriAd) {
		MainTableFiltri mainTableFiltri = new MainTableFiltri();
		
		// DATABASE PARAMETRI
		mainTableFiltri = Set_MainTableFiltri(mainTableFiltri, siglaNazione, idArrayListCompetizione, filtriAd);
		
		mainTableFiltri.setNomePartita( nomePartita != null && !nomePartita.equals("") ? nomePartita : null );
		
		// HTML CALENDARI DATE E ORA INZIO E FINE PARTIE
		if( dataInizioPartita != null && !dataInizioPartita.equals("") ) {
			mainTableFiltri.setDataInizioPartita(dataInizioPartita);
			mainTableFiltri.setInizioPartita( new Date(Long.parseLong(dataInizioPartita)) );
			if( oraInizioPartita != null  && !oraInizioPartita.equals("")) {
				mainTableFiltri.setOraInizioPartita(oraInizioPartita);
				Calendar calendar = Calendar.getInstance(); calendar.setTime(new Date(Long.parseLong(dataInizioPartita)));
    		    calendar.add(Calendar.HOUR_OF_DAY, Integer.parseInt( oraInizioPartita.split(":")[0] ));
    		    calendar.add(Calendar.MINUTE, Integer.parseInt( oraInizioPartita.split(":")[1] ));
        		//System.out.println("oraPartita: "+oraInizioPartita);
    		    mainTableFiltri.setInizioPartita(calendar.getTime());
			}
		}
		if( dataFinePartita != null && !dataFinePartita.equals("") ) {
			mainTableFiltri.setDataFinePartita(dataFinePartita);
			Calendar calendar = Calendar.getInstance(); calendar.setTime(new Date(Long.parseLong(dataFinePartita)));
			calendar.add(Calendar.HOUR, 24);
			mainTableFiltri.setFinePartita( calendar.getTime() );
			if( oraFinePartita != null  && !oraFinePartita.equals("")) {
				mainTableFiltri.setOraFinePartita(oraFinePartita);
				calendar = Calendar.getInstance(); calendar.setTime( new Date(Long.parseLong(dataFinePartita)) );
    		    calendar.add(Calendar.HOUR_OF_DAY, Integer.parseInt( oraFinePartita.split(":")[0] ));
    		    calendar.add(Calendar.MINUTE, Integer.parseInt( oraFinePartita.split(":")[1] ));
        		//System.out.println("oraPartita: "+oraFinePartita);
    		    mainTableFiltri.setFinePartita(calendar.getTime());
			}
		}
		
		return mainTableFiltri;
	}
	
	public static MainTableFiltri Carica_Filtri(String siglaNazione, List<Long> idArrayListCompetizione, HashMap<String, String> filtriAd) {
		MainTableFiltri mainTableFiltri = new MainTableFiltri();

		// DATABASE PARAMETRI
		mainTableFiltri = Set_MainTableFiltri(mainTableFiltri, siglaNazione, idArrayListCompetizione, filtriAd);
		
		// HTML SELECT NAZIONI E CAMPIONATI
		List<FiltroNazioni> listNazioni = new ArrayList<FiltroNazioni>();
		listNazioni.add( new FiltroNazioni(Constants.OPTION_SELECT_ID_TUTTI, Constants.OPTION_SELECT_NOME_TUTTI, Constants.OPTION_SELECT_NOME_TUTTI, false) );
		
		List<FiltroCampionati> listCampionati = new ArrayList<FiltroCampionati>();
		
		if( siglaNazione != null ) {
			listCampionati.add(new FiltroCampionati(Constants.OPTION_SELECT_ID_TUTTI, Constants.OPTION_SELECT_NOME_TUTTI, 
					(siglaNazione.equals(Constants.OPTION_SELECT_NOME_TUTTI) || idArrayListCompetizione.size() == 0
							|| (idArrayListCompetizione.size() > 0 && idArrayListCompetizione.get(0) == Constants.OPTION_SELECT_ID_TUTTI) ) ? true : false));
		
		}else {
			listCampionati.add(new FiltroCampionati(Constants.OPTION_SELECT_ID_TUTTI, Constants.OPTION_SELECT_NOME_TUTTI, true));
		}
		
		List<Long> idElemnt = new ArrayList<Long>();
		for(Object[] ite_object: competizioniDao.Filtri_Nazione_Competizione()) {
			Long 		var_0 = ite_object[0] != null ? ((BigInteger)ite_object[0]).longValue() : null;
			String 		var_1 = (String)ite_object[1];
			String 		var_2 = (String)ite_object[2];
			Long 		var_3 = ite_object[3] != null ? ((BigInteger)ite_object[3]).longValue() : null;
			String 		var_4 = (String)ite_object[4];
			String 		var_5 = (String)ite_object[5];
			//System.out.println(var_0+" | " +var_1+" | "+var_2+" | "+var_3+" | "+var_4+" | "+var_5); 
			if( !idElemnt.contains(var_0) ) {
				FiltroNazioni filtroNazioni = new FiltroNazioni();
				filtroNazioni.setIdNazione(var_0);
				filtroNazioni.setNazioneSigla(var_1);
				idElemnt.add(var_0);
				if( (siglaNazione == null && Constants.FILTRO_SELECT_SILGA_NAZIONEALE_DEFAULT.equals(var_1)) || (siglaNazione != null && siglaNazione.equals(var_1)) ) {
					filtroNazioni.setSelected(true);
				}else {
					filtroNazioni.setSelected(false);
				}
				filtroNazioni.setNazioneNome( var_2 != null && !var_2.equals("") ? var_2 : var_1 );
				listNazioni.add(filtroNazioni);
			}
			
			if( siglaNazione == null || (Constants.FILTRO_SELECT_SILGA_NAZIONEALE_DEFAULT.equals(var_1)) 
					|| (siglaNazione != null && (siglaNazione.equals(var_1) || siglaNazione.equals(Constants.OPTION_SELECT_NOME_TUTTI))) ) {
				FiltroCampionati filtroCampionati = new FiltroCampionati();
				filtroCampionati.setIdCampionato(var_3);
				if( siglaNazione != null && !siglaNazione.equals(Constants.OPTION_SELECT_NOME_TUTTI) && idArrayListCompetizione.contains(var_3) ) {
					filtroCampionati.setSelected(true);
				}else {
					filtroCampionati.setSelected(false);
				}
				filtroCampionati.setCampionatoNome( var_5 != null && !var_5.equals("") ? var_5 : var_4 );
				listCampionati.add(filtroCampionati);
			}
		}
		mainTableFiltri.setListNazioni(listNazioni);
		mainTableFiltri.setListCampionati(listCampionati);
		return mainTableFiltri;
	}
	
	
	private static MainTableFiltri Set_MainTableFiltri(MainTableFiltri mainTableFiltri, String siglaNazione, List<Long> idArrayListCompetizione, HashMap<String, String> filtriAd) {
		siglaNazione = siglaNazione != null ? siglaNazione : Constants.FILTRO_SELECT_SILGA_NAZIONEALE_DEFAULT;
		if( (idArrayListCompetizione == null || idArrayListCompetizione.size() == 0) && !siglaNazione.equals(Constants.OPTION_SELECT_NOME_TUTTI) ){
			idArrayListCompetizione = new ArrayList<Long>();
			idArrayListCompetizione.add(Constants.OPTION_SELECT_ID_TUTTI);
		}
		mainTableFiltri.setSiglaNazione(siglaNazione);
		mainTableFiltri.setIdArrayListCompetizione(idArrayListCompetizione);
		
		// ASC DESC
		List<FiltroAscDesc> ListFiltriAscDesc = new ArrayList<FiltroAscDesc>();
		Iterator<Entry<String, String>> ite = filtriAd.entrySet().iterator();
		while (ite.hasNext()) {
			Map.Entry<String, String> pair = (Map.Entry<String, String>)ite.next();
			ListFiltriAscDesc.add( new FiltroAscDesc(pair.getKey(), pair.getValue() != null ? pair.getValue() : "" ));
		}
		mainTableFiltri.setListFiltriAscDesc(ListFiltriAscDesc);
		
		return mainTableFiltri;
	}
	
	
	public static MainTable CaricaBean(MainTableFiltri mainTableFiltri, JSONArray jsonArrayFiltriQuote) { 
		MainTable mainTable = new MainTable();
		List<Object[]> resultList = eventiDao.Report_1(mainTableFiltri);
		mainTable.setTotaleRow( resultList.size() );
		List<RowTable> listRowTable = new ArrayList<RowTable>();

		final double COST_LIMITE_QUOTA = 2; 
		final double COST_OVER_UNDER_GOL_NOGOL = 1.8; 
		
		for(Object[] ite_object: resultList) {
			String 		var_0 = (String)ite_object[0];
			String 		var_1 = (String)ite_object[1];
			String 		var_2 = (String)ite_object[2];
			Date		var_3_Date = (Date)ite_object[3];
			
			// APERTURA market_esito_finale -------------------------------------
			Double 		var_4 = ite_object[4] != null ? (Double)ite_object[4] : null;
			var_4 = var_4 != null ? var_4 : null;
			Double 		var_5 = ite_object[5] != null ? (Double)ite_object[5] : null;
			var_5 = var_5 != null ? var_5 : null; 
			Double 		var_6 = ite_object[6] != null ? (Double)ite_object[6] : null;
			var_6 = var_6 != null ? var_6 : null;
			// MEDIA market_esito_finale -------------------------------------		
			Integer 	var_7 = ite_object[7] != null ? ((BigInteger)ite_object[7]).intValue() : null;
			Double 		var_8 = ite_object[8] != null ? (Double)ite_object[8] : null;
			var_8 = var_8 != null ? var_8 : null;
			Integer 	var_9 = ite_object[9] != null ? ((BigInteger)ite_object[9]).intValue() : null;
			Double 		var_10 = ite_object[10] != null ? (Double)ite_object[10] : null;
			var_10 = var_10 != null ? var_10 : null; 
			Integer 	var_11 = ite_object[11] != null ? ((BigInteger)ite_object[11]).intValue() : null;
			Double 		var_12 = ite_object[12] != null ? (Double)ite_object[12] : null;
			var_12 = var_12 != null ? var_12 : null;
			// ATTUALE market_esito_finale -------------------------------------
			Double 		var_13 = ite_object[13] != null ? (Double)ite_object[13] : null;
			var_13 = var_13 != null ? var_13 : null;
			Double 		var_14 = ite_object[14] != null ? (Double)ite_object[14] : null;
			var_14 = var_14 != null ? var_14 : null; 
			Double 		var_15 = ite_object[15] != null ? (Double)ite_object[15] : null;
			var_15 = var_15 != null ? var_15 : null;
			//-------------------------------------
			//-------------------------------------
			// APERTURA market_over_under_25 -------------------------------------
			Double 		var_16 = ite_object[16] != null ? (Double)ite_object[16] : null;
			var_16 = var_16 != null ? var_16 : null;
			Double 		var_17 = ite_object[17] != null ? (Double)ite_object[17] : null;
			var_17 = var_17 != null ? var_17 : null; 
			// MEDIA market_over_under_25 -------------------------------------		
			Integer 	var_18 = ite_object[18] != null ? ((BigInteger)ite_object[18]).intValue() : null;
			Double 		var_19 = ite_object[19] != null ? (Double)ite_object[19] : null;
			var_19 = var_19 != null ? var_19 : null;
			Integer 	var_20 = ite_object[20] != null ? ((BigInteger)ite_object[20]).intValue() : null;
			Double 		var_21 = ite_object[21] != null ? (Double)ite_object[21] : null;
			var_21 = var_21 != null ? var_21 : null;
			// ATTUALE market_over_under_25 -------------------------------------
			Double 		var_22 = ite_object[22] != null ? (Double)ite_object[22] : null;
			var_22 = var_22 != null ? var_22 : null;
			Double 		var_23 = ite_object[23] != null ? (Double)ite_object[23] : null;
			var_23 = var_23 != null ? var_23 : null; 
			//-------------------------------------
			//-------------------------------------
			// APERTURA market_gol -------------------------------------
			Double 		var_24 = ite_object[24] != null ? (Double)ite_object[24] : null;
			var_24 = var_24 != null ? var_24 : null;
			Double 		var_25 = ite_object[25] != null ? (Double)ite_object[25] : null;
			var_25 = var_25 != null ? var_25 : null; 
			// MEDIA market_gol -------------------------------------		
			Integer 	var_26 = ite_object[26] != null ? ((BigInteger)ite_object[26]).intValue() : null;
			Double 		var_27 = ite_object[27] != null ? (Double)ite_object[27] : null;
			var_27 = var_27 != null ? var_27 : null;
			Integer 	var_28 = ite_object[28] != null ? ((BigInteger)ite_object[28]).intValue() : null;
			Double 		var_29 = ite_object[29] != null ? (Double)ite_object[29] : null;
			var_29 = var_29 != null ? var_29 : null;
			// ATTUALE market_gol -------------------------------------
			Double 		var_30 = ite_object[30] != null ? (Double)ite_object[30] : null;
			var_30 = var_30 != null ? var_30 : null;
			Double 		var_31 = ite_object[31] != null ? (Double)ite_object[31] : null;
			var_31 = var_31 != null ? var_31 : null; 
			
			String 		var_32 = (String)ite_object[32];
			var_0 = var_32 != null && !var_32.equals("") ? var_32 : var_0; // NAZ_DISPLAY
			
			String 		var_33 = (String)ite_object[33];
			var_1 = var_33 != null && !var_33.equals("") ? var_33 : var_1; // COMP_DISPLAY

			// dal 34 al 40 si usano per l'ordinamento (ANDAMENTO_*)
			
			// BOOKMAKER_1
			String 		var_34 = (String)ite_object[34];
			Double 		var_35 = ite_object[35] != null ? (Double)ite_object[35] : null;
			Double 		var_36 = ite_object[36] != null ? (Double)ite_object[36] : null;
			Double 		var_37 = ite_object[37] != null ? (Double)ite_object[37] : null;
			Double 		var_38 = ite_object[38] != null ? (Double)ite_object[38] : null;
			Double 		var_39 = ite_object[39] != null ? (Double)ite_object[39] : null;
			Double 		var_40 = ite_object[40] != null ? (Double)ite_object[40] : null;
			Double 		var_41 = ite_object[41] != null ? (Double)ite_object[41] : null;
			Double 		var_42 = ite_object[42] != null ? (Double)ite_object[42] : null;
			Double 		var_43 = ite_object[43] != null ? (Double)ite_object[43] : null;
			Double 		var_44 = ite_object[44] != null ? (Double)ite_object[44] : null;
			Double 		var_45 = ite_object[45] != null ? (Double)ite_object[45] : null;
			Double 		var_46 = ite_object[46] != null ? (Double)ite_object[46] : null;
			Double 		var_47 = ite_object[47] != null ? (Double)ite_object[47] : null;
			Double 		var_48 = ite_object[48] != null ? (Double)ite_object[48] : null;
			
			// BOOKMAKER_2
			String 		var_49 = (String)ite_object[49];
			Double 		var_50 = ite_object[50] != null ? (Double)ite_object[50] : null;
			Double 		var_51 = ite_object[51] != null ? (Double)ite_object[51] : null;
			Double 		var_52 = ite_object[52] != null ? (Double)ite_object[52] : null;
			Double 		var_53 = ite_object[53] != null ? (Double)ite_object[53] : null;
			Double 		var_54 = ite_object[54] != null ? (Double)ite_object[54] : null;
			Double 		var_55 = ite_object[55] != null ? (Double)ite_object[55] : null;
			Double 		var_56 = ite_object[56] != null ? (Double)ite_object[56] : null;
			Double 		var_57 = ite_object[57] != null ? (Double)ite_object[57] : null;
			Double 		var_58 = ite_object[58] != null ? (Double)ite_object[58] : null;
			Double 		var_59 = ite_object[59] != null ? (Double)ite_object[59] : null;
			Double 		var_60 = ite_object[60] != null ? (Double)ite_object[60] : null;
			Double 		var_61 = ite_object[61] != null ? (Double)ite_object[61] : null;
			Double 		var_62 = ite_object[62] != null ? (Double)ite_object[62] : null;
			Double 		var_63 = ite_object[63] != null ? (Double)ite_object[63] : null;
			
			// BOOKMAKERS MEDIA APERTURA E ATTUALE
			Double 		var_64 = ite_object[64] != null ? (Double)ite_object[64] : null;
			Double 		var_65 = ite_object[65] != null ? (Double)ite_object[65] : null;
			Double 		var_66 = ite_object[66] != null ? (Double)ite_object[66] : null;
			Double 		var_67 = ite_object[67] != null ? (Double)ite_object[67] : null;
			Double 		var_68 = ite_object[68] != null ? (Double)ite_object[68] : null;
			Double 		var_69 = ite_object[69] != null ? (Double)ite_object[69] : null;
			Double 		var_70 = ite_object[70] != null ? (Double)ite_object[70] : null;
			Double 		var_71 = ite_object[71] != null ? (Double)ite_object[71] : null;
			Double 		var_72 = ite_object[72] != null ? (Double)ite_object[72] : null;
			Double 		var_73 = ite_object[73] != null ? (Double)ite_object[73] : null;
			Double 		var_74 = ite_object[74] != null ? (Double)ite_object[74] : null;
			Double 		var_75 = ite_object[75] != null ? (Double)ite_object[75] : null;
			Double 		var_76 = ite_object[76] != null ? (Double)ite_object[76] : null;
			Double 		var_77 = ite_object[77] != null ? (Double)ite_object[77] : null;
			
			Double 		var_85 = ite_object[85] != null ? (Double)ite_object[85] : null;
			var_85 = var_85 != null ? Math.round(var_85 * 100.0) / 100.0 : null;
			Double 		var_86 = ite_object[86] != null ? (Double)ite_object[86] : null;
			var_86 = var_86 != null ? Math.round(var_86 * 100.0) / 100.0 : null;
			Double 		var_87 = ite_object[87] != null ? (Double)ite_object[87] : null;
			var_87 = var_87 != null ? Math.round(var_87 * 100.0) / 100.0 : null;
			Double 		var_88 = ite_object[88] != null ? (Double)ite_object[88] : null;
			var_88 = var_88 != null ? Math.round(var_88 * 100.0) / 100.0 : null;
			Double 		var_89 = ite_object[89] != null ? (Double)ite_object[89] : null;
			var_89 = var_89 != null ? Math.round(var_89 * 100.0) / 100.0 : null;
			Double 		var_90 = ite_object[90] != null ? (Double)ite_object[90] : null;
			var_90 = var_90 != null ? Math.round(var_90 * 100.0) / 100.0 : null;
			Double 		var_91 = ite_object[91] != null ? (Double)ite_object[91] : null;
			var_91 = var_91 != null ? Math.round(var_91 * 100.0) / 100.0 : null;
			
			
/*
System.out.println(var_0+" | " +var_1+" | "+var_2+" | "+var_3_Date+" | "+var_4+" | "+var_5+" | "+var_6+" | "+var_7+" | "+var_8+" | "+var_9
+" | "+ite_object[34]+" | "+ite_object[35]+" | "+ite_object[36]+" | "+ite_object[37]+" | "+ite_object[38]+" | "+ite_object[39]+" | "+ite_object[40]
+" | "+ite_object[41]+" | "+(Double)ite_object[42]+" | "+(Double)ite_object[43]+" | "+(Double)ite_object[44]+" | "+(Double)ite_object[45]+" | "+(Double)ite_object[46]
+" | "+(Double)ite_object[47]); */
	
			RowTable rowTable = new RowTable();
			
			rowTable.setNazioneNome(var_0);
			rowTable.setCampionatoNome(var_1);
			rowTable.setPartitaNome(var_2);
			rowTable.setPartitaData( DateUtil.FormatoData_1.format(var_3_Date) );
			rowTable.setPartitaOra( DateUtil.FORMATO_ORA.format(var_3_Date) );

			Quotazione quotApertura = new Quotazione();
			quotApertura.setMatchOdds_1(var_4);
			quotApertura.setMatchOdds_X(var_5);
			quotApertura.setMatchOdds_2(var_6);
			quotApertura.setOu25_Over(var_16);
			quotApertura.setOu25_Under(var_17);
			quotApertura.setBtts_Gol(var_24);
			quotApertura.setBtts_NoGol(var_25);
			rowTable.setQuotApertura(quotApertura);
			
			Quotazione quotMedia = new Quotazione();
			quotMedia.setMatchOdds_1(var_8);
			quotMedia.setMatchOdds_X(var_10);
			quotMedia.setMatchOdds_2(var_12);
			quotMedia.setOu25_Over(var_19);
			quotMedia.setOu25_Under(var_21);
			quotMedia.setBtts_Gol(var_27);
			quotMedia.setBtts_NoGol(var_29);
			rowTable.setQuotMedia(quotMedia);
			
			Quotazione quotAttuale = new Quotazione();
			quotAttuale.setMatchOdds_1(var_13);
			quotAttuale.setMatchOdds_X(var_14);
			quotAttuale.setMatchOdds_2(var_15);
			quotAttuale.setOu25_Over(var_22);
			quotAttuale.setOu25_Under(var_23);
			quotAttuale.setBtts_Gol(var_30);
			quotAttuale.setBtts_NoGol(var_31);
			rowTable.setQuotAttuale(quotAttuale);
			
			rowTable.setAndamento_1( CalcolaAndamento(var_8, var_13) );
			rowTable.setAndamento_2( CalcolaAndamento(var_12, var_15) );
			rowTable.setAndamento_X( CalcolaAndamento(var_10, var_14) );
			rowTable.setAndamento_Over25( CalcolaAndamento(var_19, var_22) );
			rowTable.setAndamento_Under25( CalcolaAndamento(var_21, var_23) );
			rowTable.setAndamento_Gol( CalcolaAndamento(var_27, var_30) );
			rowTable.setAndamento_noGol( CalcolaAndamento(var_29, var_31) );
			
			rowTable.setQuotMediaCount_1(var_7);
			rowTable.setQuotMediaCount_2(var_11);
			rowTable.setQuotMediaCount_X(var_9);
			rowTable.setQuotMediaCount_Over25(var_18);
			rowTable.setQuotMediaCount_Under25(var_20);
			rowTable.setQuotMediaCount_Gol(var_26);
			rowTable.setQuotMediaCount_noGol(var_28);
			
			// BOOKMAKER_1
			rowTable.setNomeBookmaker_1(var_34);
			Quotazione bookmaker_1QuotApertura = new Quotazione();
			bookmaker_1QuotApertura.setMatchOdds_1(var_35);
			bookmaker_1QuotApertura.setMatchOdds_X(var_36);
			bookmaker_1QuotApertura.setMatchOdds_2(var_37);
			bookmaker_1QuotApertura.setOu25_Over(var_38);
			bookmaker_1QuotApertura.setOu25_Under(var_39);
			bookmaker_1QuotApertura.setBtts_Gol(var_40);
			bookmaker_1QuotApertura.setBtts_NoGol(var_41);
			rowTable.setBookmaker_1QuotApertura(bookmaker_1QuotApertura);
			
			Quotazione bookmaker_1QuotAttuale = new Quotazione();
			bookmaker_1QuotAttuale.setMatchOdds_1(var_42);
			bookmaker_1QuotAttuale.setMatchOdds_X(var_43);
			bookmaker_1QuotAttuale.setMatchOdds_2(var_44);
			bookmaker_1QuotAttuale.setOu25_Over(var_45);
			bookmaker_1QuotAttuale.setOu25_Under(var_46);
			bookmaker_1QuotAttuale.setBtts_Gol(var_47);
			bookmaker_1QuotAttuale.setBtts_NoGol(var_48);
			rowTable.setBookmaker_1QuotAttuale(bookmaker_1QuotAttuale);
			
			// BOOKMAKER_1
			rowTable.setNomeBookmaker_2(var_49);
			Quotazione bookmaker_2QuotApertura = new Quotazione();
			bookmaker_2QuotApertura.setMatchOdds_1(var_50);
			bookmaker_2QuotApertura.setMatchOdds_X(var_51);
			bookmaker_2QuotApertura.setMatchOdds_2(var_52);
			bookmaker_2QuotApertura.setOu25_Over(var_53);
			bookmaker_2QuotApertura.setOu25_Under(var_54);
			bookmaker_2QuotApertura.setBtts_Gol(var_55);
			bookmaker_2QuotApertura.setBtts_NoGol(var_56);
			rowTable.setBookmaker_2QuotApertura(bookmaker_2QuotApertura);
			
			Quotazione bookmaker_2QuotAttuale = new Quotazione();
			bookmaker_2QuotAttuale.setMatchOdds_1(var_57);
			bookmaker_2QuotAttuale.setMatchOdds_X(var_58);
			bookmaker_2QuotAttuale.setMatchOdds_2(var_59);
			bookmaker_2QuotAttuale.setOu25_Over(var_60);
			bookmaker_2QuotAttuale.setOu25_Under(var_61);
			bookmaker_2QuotAttuale.setBtts_Gol(var_62);
			bookmaker_2QuotAttuale.setBtts_NoGol(var_63);
			rowTable.setBookmaker_2QuotAttuale(bookmaker_2QuotAttuale);
			
			// BOOKMAKERS MEDIA APERTURA
			Quotazione bookmakersMedia_QuotApertura = new Quotazione();
			bookmakersMedia_QuotApertura.setMatchOdds_1(var_64);
			bookmakersMedia_QuotApertura.setMatchOdds_X(var_65);
			bookmakersMedia_QuotApertura.setMatchOdds_2(var_66);
			bookmakersMedia_QuotApertura.setOu25_Over(var_67);
			bookmakersMedia_QuotApertura.setOu25_Under(var_68);
			bookmakersMedia_QuotApertura.setBtts_Gol(var_69);
			bookmakersMedia_QuotApertura.setBtts_NoGol(var_70);
			rowTable.setBookmakersMedia_QuotApertura(bookmakersMedia_QuotApertura); 
			
			Quotazione bookmakersMedia_QuotAttuale = new Quotazione();
			bookmakersMedia_QuotAttuale.setMatchOdds_1(var_71);
			bookmakersMedia_QuotAttuale.setMatchOdds_X(var_72);
			bookmakersMedia_QuotAttuale.setMatchOdds_2(var_73);
			bookmakersMedia_QuotAttuale.setOu25_Over(var_74);
			bookmakersMedia_QuotAttuale.setOu25_Under(var_75);
			bookmakersMedia_QuotAttuale.setBtts_Gol(var_76);
			bookmakersMedia_QuotAttuale.setBtts_NoGol(var_77);
			rowTable.setBookmakersMedia_QuotAttuale(bookmakersMedia_QuotAttuale);
			
			// SAMBET
			Quotazione quotSambet = new Quotazione();
			quotSambet.setMatchOdds_1(var_85);
			quotSambet.setMatchOdds_X(var_86);
			quotSambet.setMatchOdds_2(var_87);
			quotSambet.setOu25_Over(var_88);
			quotSambet.setOu25_Under(var_89);
			quotSambet.setBtts_Gol(var_90);
			quotSambet.setBtts_NoGol(var_91);
			rowTable.setQuotSambet(quotSambet);
			
			// PRONOSTICO SINGOLE
			Pronostico pron_sing = new Pronostico();
			pron_sing.setMatchOdds_1(var_64 != null && var_13 != null && var_64 >= var_13 ? true : false);
			pron_sing.setMatchOdds_X(var_65 != null && var_14 != null && var_65 >= var_14 ? true : false);
			pron_sing.setMatchOdds_2(var_66 != null && var_15 != null && var_66 >= var_15 ? true : false);
			pron_sing.setOu25_Over(var_67 != null && var_22 != null && var_67 >= var_22 ? true : false);
			pron_sing.setOu25_Under(var_68 != null && var_23 != null && var_68 >= var_23 ? true : false);
			pron_sing.setBtts_Gol(var_69 != null && var_30 != null && var_69 >= var_30 ? true : false);
			pron_sing.setBtts_NoGol(var_70 != null && var_31 != null && var_70 >= var_31 ? true : false);
			rowTable.setPronosticiSingole(pron_sing);
			
			// PRONOSTICO MULTIPLE
			Pronostico pron_mult = new Pronostico();
			
			/*
			Quotazione quotApertura = new Quotazione();
			quotApertura.setMatchOdds_1(var_4);
			quotApertura.setMatchOdds_X(var_5);
			quotApertura.setMatchOdds_2(var_6);
			quotApertura.setOu25_Over(var_16);
			quotApertura.setOu25_Under(var_17);
			quotApertura.setBtts_Gol(var_24);
			quotApertura.setBtts_NoGol(var_25);
			
			Quotazione quotMedia = new Quotazione();
			quotMedia.setMatchOdds_1(var_8);
			quotMedia.setMatchOdds_X(var_10);
			quotMedia.setMatchOdds_2(var_12);
			quotMedia.setOu25_Over(var_19);
			quotMedia.setOu25_Under(var_21);
			quotMedia.setBtts_Gol(var_27);
			quotMedia.setBtts_NoGol(var_29);
			
			Quotazione quotAttuale = new Quotazione();
			quotAttuale.setMatchOdds_1(var_13);
			quotAttuale.setMatchOdds_X(var_14);
			quotAttuale.setMatchOdds_2(var_15);
			quotAttuale.setOu25_Over(var_22);
			quotAttuale.setOu25_Under(var_23);
			quotAttuale.setBtts_Gol(var_30);
			quotAttuale.setBtts_NoGol(var_31);
			
			----------------------------------------------------------------------------------------------
			
			Quotazione bookmakersMedia_QuotApertura = new Quotazione();
			bookmakersMedia_QuotApertura.setMatchOdds_1(var_64);
			bookmakersMedia_QuotApertura.setMatchOdds_X(var_65);
			bookmakersMedia_QuotApertura.setMatchOdds_2(var_66);
			bookmakersMedia_QuotApertura.setOu25_Over(var_67);
			bookmakersMedia_QuotApertura.setOu25_Under(var_68);
			bookmakersMedia_QuotApertura.setBtts_Gol(var_69);
			bookmakersMedia_QuotApertura.setBtts_NoGol(var_70);
			
			Quotazione bookmakersMedia_QuotAttuale = new Quotazione();
			bookmakersMedia_QuotAttuale.setMatchOdds_1(var_71);
			bookmakersMedia_QuotAttuale.setMatchOdds_X(var_72);
			bookmakersMedia_QuotAttuale.setMatchOdds_2(var_73);
			bookmakersMedia_QuotAttuale.setOu25_Over(var_74);
			bookmakersMedia_QuotAttuale.setOu25_Under(var_75);
			bookmakersMedia_QuotAttuale.setBtts_Gol(var_76);
			bookmakersMedia_QuotAttuale.setBtts_NoGol(var_77);
			 */
			
// SEGNO 1
if( var_8 != null && var_4 != null && var_13 != null && var_71 != null && var_64 != null &&  
	(var_85 == null || var_87 == null || var_85 < var_87) && var_8.doubleValue() <= var_4.doubleValue() && var_13.doubleValue() <= var_8.doubleValue() 
		&& var_71.doubleValue() <= var_64.doubleValue() + 0.03 && var_13.doubleValue() <= COST_LIMITE_QUOTA ) {
	pron_mult.setMatchOdds_1(true);
}else {
	pron_mult.setMatchOdds_1(false);
}

// SEGNO X
if( var_72 != null && var_65 != null && var_14 != null && var_10 != null && var_5 != null && var_73 != null && var_66 != null && var_71 != null && var_64 != null 
		&& var_72.doubleValue() < var_65.doubleValue() && var_65.doubleValue() < var_14.doubleValue() && var_14.doubleValue() < var_10.doubleValue() 
			&& var_10.doubleValue() <= var_5.doubleValue() && var_73.doubleValue() > var_66.doubleValue() && var_71.doubleValue() > var_64.doubleValue() ) {
	pron_mult.setMatchOdds_X(true);
}else {
	pron_mult.setMatchOdds_X(false);
}

//SEGNO 2
if( var_15 != null && var_12 != null && var_6 != null && var_73 != null && var_66 != null &&
	(var_85 == null || var_87 == null || var_87 < var_85) && var_15.doubleValue() <= var_12.doubleValue() && var_12.doubleValue() <= var_6.doubleValue() 
		&& var_15.doubleValue() <= COST_LIMITE_QUOTA && var_73.doubleValue() <= var_66.doubleValue() && var_73.doubleValue() <= var_15.doubleValue() ) {
	pron_mult.setMatchOdds_2(true) ;
}else {
	pron_mult.setMatchOdds_2(false);
}

//SEGNO OVER 2,5
if( var_74 != null && var_67 != null && var_22 != null && var_19 != null && var_16 != null
		&& var_74.doubleValue() <= COST_OVER_UNDER_GOL_NOGOL && var_74.doubleValue() <= var_67.doubleValue() && var_22.doubleValue() < var_19.doubleValue() 
			&& var_19.doubleValue() <= var_16.doubleValue() ) {
	pron_mult.setOu25_Over(true);
}else {
	pron_mult.setOu25_Over(false);
}

//SEGNO UNDER 2,5
if( var_75 != null && var_68 != null && var_23 != null && var_21 != null && var_17 != null
		&& var_75.doubleValue() <= COST_OVER_UNDER_GOL_NOGOL && var_75.doubleValue() <= var_68.doubleValue() && var_23.doubleValue() < var_21.doubleValue() 
			&& var_21.doubleValue() <= var_17.doubleValue() ) {
	pron_mult.setOu25_Under(true);
}else {
	pron_mult.setOu25_Under(false);
}

//SEGNO GOL
if( var_76 != null && var_69 != null && var_30 != null && var_27 != null && var_24 != null 
		&& var_76.doubleValue() <= COST_OVER_UNDER_GOL_NOGOL && var_76.doubleValue() <= var_69.doubleValue() && var_30.doubleValue() < var_27.doubleValue() 
			&& var_27.doubleValue() <= var_24.doubleValue() ) {
	pron_mult.setBtts_Gol(true);
}else {
	pron_mult.setBtts_Gol(false);
}
	
//SEGNO NO GOL
if( var_77 != null && var_70 != null && var_31 != null && var_29 != null && var_25 != null
		&& var_77.doubleValue() <= COST_OVER_UNDER_GOL_NOGOL && var_77.doubleValue() <= var_70.doubleValue() && var_31.doubleValue() < var_29.doubleValue() 
			&& var_29.doubleValue() <= var_25.doubleValue() ) {
	pron_mult.setBtts_NoGol(true);
}else {
	pron_mult.setBtts_NoGol(false);
}

rowTable.setPronosticiMultiple(pron_mult);
			
			
/******************************** INIZIO FILTRI QUOTE ******/

Double variabile_UNO = null; Double variabile_DUE = null; Double variabile_TRE = null;
String KEY_A = "A", KEY_B = "B", KEY_C = "C", KEY_D = "D", KEY_E = "E", KEY_F = "F";
String OPE_MIN = "<", OPE_MAGG = ">", OPE_UGUALE = "=", OPE_MIN_UGUALE = "<=", OPE_MAGG_UGUALE = ">=", OPE_COMP_TRA = "COMP_TRA";
boolean FiltriQuote_OK = true;

for (int ite = 0; ite < jsonArrayFiltriQuote.length(); ite++) {
	variabile_UNO = CaricaMainTableUtil.GetValQuota(rowTable, ite, jsonArrayFiltriQuote, KEY_A, KEY_B);
	variabile_DUE = CaricaMainTableUtil.GetValQuota(rowTable, ite, jsonArrayFiltriQuote, KEY_A, KEY_D);

	String VarKeyE = jsonArrayFiltriQuote.getJSONObject(ite).getString(KEY_E);
	if( variabile_DUE == null && CaricaMainTableUtil.CheckInputNumberQuota(VarKeyE) ) {
		variabile_DUE = Double.parseDouble(VarKeyE);
	}
	
	String VarKeyF = jsonArrayFiltriQuote.getJSONObject(ite).getString(KEY_F);
	if( CaricaMainTableUtil.CheckInputNumberQuota(VarKeyF) ) {
		variabile_TRE = Double.parseDouble(VarKeyF);
	}
	
	if( variabile_UNO != null && variabile_DUE != null ) {
		if( jsonArrayFiltriQuote.getJSONObject(ite).getString(KEY_C).equals(OPE_MIN) ) {
			if( variabile_UNO.doubleValue() < variabile_DUE.doubleValue() ) {
				FiltriQuote_OK = true;
			}else {
				FiltriQuote_OK = false;
				break;
			}
			
		}else if( jsonArrayFiltriQuote.getJSONObject(ite).getString(KEY_C).equals(OPE_MAGG) ) {
			if( variabile_UNO.doubleValue() > variabile_DUE.doubleValue() ) {
				FiltriQuote_OK = true;
			}else {
				FiltriQuote_OK = false;
				break;
			}
			
		}else if( jsonArrayFiltriQuote.getJSONObject(ite).getString(KEY_C).equals(OPE_UGUALE) ) {
			if( variabile_UNO.doubleValue() == variabile_DUE.doubleValue() ) {
				FiltriQuote_OK = true;
			}else {
				FiltriQuote_OK = false;
				break;
			}
			
		}else if( jsonArrayFiltriQuote.getJSONObject(ite).getString(KEY_C).equals(OPE_MIN_UGUALE) ) {
			if( variabile_UNO.doubleValue() <= variabile_DUE.doubleValue() ) {
				FiltriQuote_OK = true;
			}else {
				FiltriQuote_OK = false;
				break;
			}
			
		}else if( jsonArrayFiltriQuote.getJSONObject(ite).getString(KEY_C).equals(OPE_MAGG_UGUALE) ) {
			if( variabile_UNO.doubleValue() >= variabile_DUE.doubleValue() ) {
				FiltriQuote_OK = true;
			}else {
				FiltriQuote_OK = false;
				break;
			}
			
		}else if( jsonArrayFiltriQuote.getJSONObject(ite).getString(KEY_C).equals(OPE_COMP_TRA) && variabile_TRE != null ) {
			if( variabile_UNO.doubleValue() >= variabile_DUE.doubleValue() && variabile_UNO.doubleValue() <= variabile_TRE.doubleValue() ) {
				FiltriQuote_OK = true;
			}else {
				FiltriQuote_OK = false;
				break;
			}
		}
	}
}
/*********************************** FINE FILTRI QUOTE ******/

			if( FiltriQuote_OK ) {
				listRowTable.add(rowTable);
			}

		}
		
		mainTable.setRowTableList(listRowTable);
		return mainTable;
	}
	
	
	
	private static Integer CalcolaAndamento(Double QuotaMedia, Double QuotAttuale) {
		if( QuotaMedia == null || QuotAttuale == null ) {
			return null;
		}else {
			double andamento = QuotaMedia - QuotAttuale;
			if( andamento <= -0.01 ) {
				return 1; // verde
			}else if( andamento >= 0.01 ) {
				return 3; // rosso
			}else {
				return null;
			}
		}
	}
	
	
}
