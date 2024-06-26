package com.sambet.webapp.controller.home;

import org.json.JSONArray;
import com.sambet.webapp.util.bean.MainTable.RowTable;

/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
public class CaricaMainTableUtil  {
	
	
	private static String SIGN_1 = "1", SIGN_X = "X", SIGN_2 = "2", SIGN_OV = "OV", SIGN_UN = "UN", SIGN_GOL = "GOL", SIGN_NO_GOL = "NO_GOL";
	
	private static String BOOK_NULL = "--", BOOK_QS = "QS", BOOK_BET_APE = "BET_APE", BOOK_BET_MED = "BET_MED", BOOK_BET_ATT = "BET_ATT", BOOK_PINN_APE = "PINN_APE", 
			BOOK_PINN_ATT = "PINN_ATT", BOOK_BET365_APE = "BET365_APE", BOOK_BET365_ATT = "BET365_ATT", BOOK_MBOOK_APE = "MBOOK_APE", BOOK_MBOOK_ATT = "MBOOK_ATT";
	
	
	protected static boolean CheckInputNumberQuota(String VarKey) {
		if( VarKey != null ) {
			try {
				double d = Double.parseDouble(VarKey);
		    } catch (NumberFormatException nfe) {
		        return false;
		    }
			return true;
		}else {
			return false;
		}
	}
	
	
	protected static Double GetValQuota(RowTable rowTable, int ite, JSONArray jsonArrayFiltriQuote, String KEY, String BOOK) {
		
		Double variabile_N = null;
		
		if( jsonArrayFiltriQuote.getJSONObject(ite).getString(BOOK).equals(BOOK_NULL) ) {
			return null;
			
		}else if( jsonArrayFiltriQuote.getJSONObject(ite).getString(BOOK).equals(BOOK_QS) ) {
			
			if( jsonArrayFiltriQuote.getJSONObject(ite).getString(KEY).equals(SIGN_1) ) {
				variabile_N = rowTable.getQuotSambet().getMatchOdds_1();
				
			}else if( jsonArrayFiltriQuote.getJSONObject(ite).getString(KEY).equals(SIGN_X) ) {
				variabile_N = rowTable.getQuotSambet().getMatchOdds_X();
				
			}else if( jsonArrayFiltriQuote.getJSONObject(ite).getString(KEY).equals(SIGN_2) ) {
				variabile_N = rowTable.getQuotSambet().getMatchOdds_2();
				
			}else if( jsonArrayFiltriQuote.getJSONObject(ite).getString(KEY).equals(SIGN_OV) ) {
				variabile_N = rowTable.getQuotSambet().getOu25_Over();
				
			}else if( jsonArrayFiltriQuote.getJSONObject(ite).getString(KEY).equals(SIGN_UN) ) {
				variabile_N = rowTable.getQuotSambet().getOu25_Under();
				
			}else if( jsonArrayFiltriQuote.getJSONObject(ite).getString(KEY).equals(SIGN_GOL) ) {
				variabile_N = rowTable.getQuotSambet().getBtts_Gol();
				
			}else if( jsonArrayFiltriQuote.getJSONObject(ite).getString(KEY).equals(SIGN_NO_GOL) ) {
				variabile_N = rowTable.getQuotSambet().getBtts_NoGol();
				
			}else {
				return null;
			}
			
		}else if( jsonArrayFiltriQuote.getJSONObject(ite).getString(BOOK).equals(BOOK_BET_APE) ) {
			
			if( jsonArrayFiltriQuote.getJSONObject(ite).getString(KEY).equals(SIGN_1) ) {
				variabile_N = rowTable.getQuotApertura().getMatchOdds_1();
				
			}else if( jsonArrayFiltriQuote.getJSONObject(ite).getString(KEY).equals(SIGN_X) ) {
				variabile_N = rowTable.getQuotApertura().getMatchOdds_X();
				
			}else if( jsonArrayFiltriQuote.getJSONObject(ite).getString(KEY).equals(SIGN_2) ) {
				variabile_N = rowTable.getQuotApertura().getMatchOdds_2();
				
			}else if( jsonArrayFiltriQuote.getJSONObject(ite).getString(KEY).equals(SIGN_OV) ) {
				variabile_N = rowTable.getQuotApertura().getOu25_Over();
				
			}else if( jsonArrayFiltriQuote.getJSONObject(ite).getString(KEY).equals(SIGN_UN) ) {
				variabile_N = rowTable.getQuotApertura().getOu25_Under();
				
			}else if( jsonArrayFiltriQuote.getJSONObject(ite).getString(KEY).equals(SIGN_GOL) ) {
				variabile_N = rowTable.getQuotApertura().getBtts_Gol();
				
			}else if( jsonArrayFiltriQuote.getJSONObject(ite).getString(KEY).equals(SIGN_NO_GOL) ) {
				variabile_N = rowTable.getQuotApertura().getOu25_Under();
				
			}else {
				return null;
			}
			
		}else if( jsonArrayFiltriQuote.getJSONObject(ite).getString(BOOK).equals(BOOK_BET_MED) ) {
			
			if( jsonArrayFiltriQuote.getJSONObject(ite).getString(KEY).equals(SIGN_1) ) {
				variabile_N = rowTable.getQuotMedia().getMatchOdds_1();
				
			}else if( jsonArrayFiltriQuote.getJSONObject(ite).getString(KEY).equals(SIGN_X) ) {
				variabile_N = rowTable.getQuotMedia().getMatchOdds_X();
				
			}else if( jsonArrayFiltriQuote.getJSONObject(ite).getString(KEY).equals(SIGN_2) ) {
				variabile_N = rowTable.getQuotMedia().getMatchOdds_2();
				
			}else if( jsonArrayFiltriQuote.getJSONObject(ite).getString(KEY).equals(SIGN_OV) ) {
				variabile_N = rowTable.getQuotMedia().getOu25_Over();
				
			}else if( jsonArrayFiltriQuote.getJSONObject(ite).getString(KEY).equals(SIGN_UN) ) {
				variabile_N = rowTable.getQuotMedia().getOu25_Under();
				
			}else if( jsonArrayFiltriQuote.getJSONObject(ite).getString(KEY).equals(SIGN_GOL) ) {
				variabile_N = rowTable.getQuotMedia().getBtts_Gol();
				
			}else if( jsonArrayFiltriQuote.getJSONObject(ite).getString(KEY).equals(SIGN_NO_GOL) ) {
				variabile_N = rowTable.getQuotMedia().getOu25_Under();
				
			}else {
				return null;
			}
			
		}else if( jsonArrayFiltriQuote.getJSONObject(ite).getString(BOOK).equals(BOOK_BET_ATT) ) {
			
			if( jsonArrayFiltriQuote.getJSONObject(ite).getString(KEY).equals(SIGN_1) ) {
				variabile_N = rowTable.getQuotAttuale().getMatchOdds_1();
				
			}else if( jsonArrayFiltriQuote.getJSONObject(ite).getString(KEY).equals(SIGN_X) ) {
				variabile_N = rowTable.getQuotAttuale().getMatchOdds_X();
				
			}else if( jsonArrayFiltriQuote.getJSONObject(ite).getString(KEY).equals(SIGN_2) ) {
				variabile_N = rowTable.getQuotAttuale().getMatchOdds_2();
				
			}else if( jsonArrayFiltriQuote.getJSONObject(ite).getString(KEY).equals(SIGN_OV) ) {
				variabile_N = rowTable.getQuotAttuale().getOu25_Over();
				
			}else if( jsonArrayFiltriQuote.getJSONObject(ite).getString(KEY).equals(SIGN_UN) ) {
				variabile_N = rowTable.getQuotAttuale().getOu25_Under();
				
			}else if( jsonArrayFiltriQuote.getJSONObject(ite).getString(KEY).equals(SIGN_GOL) ) {
				variabile_N = rowTable.getQuotAttuale().getBtts_Gol();
				
			}else if( jsonArrayFiltriQuote.getJSONObject(ite).getString(KEY).equals(SIGN_NO_GOL) ) {
				variabile_N = rowTable.getQuotAttuale().getOu25_Under();
				
			}else {
				return null;
			}
			
		}else if( jsonArrayFiltriQuote.getJSONObject(ite).getString(BOOK).equals(BOOK_PINN_APE) ) {
			
			if( jsonArrayFiltriQuote.getJSONObject(ite).getString(KEY).equals(SIGN_1) ) {
				variabile_N = rowTable.getBookmaker_1QuotApertura().getMatchOdds_1();
				
			}else if( jsonArrayFiltriQuote.getJSONObject(ite).getString(KEY).equals(SIGN_X) ) {
				variabile_N = rowTable.getBookmaker_1QuotApertura().getMatchOdds_X();
				
			}else if( jsonArrayFiltriQuote.getJSONObject(ite).getString(KEY).equals(SIGN_2) ) {
				variabile_N = rowTable.getBookmaker_1QuotApertura().getMatchOdds_2();
				
			}else if( jsonArrayFiltriQuote.getJSONObject(ite).getString(KEY).equals(SIGN_OV) ) {
				variabile_N = rowTable.getBookmaker_1QuotApertura().getOu25_Over();
				
			}else if( jsonArrayFiltriQuote.getJSONObject(ite).getString(KEY).equals(SIGN_UN) ) {
				variabile_N = rowTable.getBookmaker_1QuotApertura().getOu25_Under();
				
			}else if( jsonArrayFiltriQuote.getJSONObject(ite).getString(KEY).equals(SIGN_GOL) ) {
				variabile_N = rowTable.getBookmaker_1QuotApertura().getBtts_Gol();
				
			}else if( jsonArrayFiltriQuote.getJSONObject(ite).getString(KEY).equals(SIGN_NO_GOL) ) {
				variabile_N = rowTable.getBookmaker_1QuotApertura().getOu25_Under();
				
			}else {
				return null;
			}
			
		}else if( jsonArrayFiltriQuote.getJSONObject(ite).getString(BOOK).equals(BOOK_PINN_ATT) ) {
			
			if( jsonArrayFiltriQuote.getJSONObject(ite).getString(KEY).equals(SIGN_1) ) {
				variabile_N = rowTable.getBookmaker_1QuotAttuale().getMatchOdds_1();
				
			}else if( jsonArrayFiltriQuote.getJSONObject(ite).getString(KEY).equals(SIGN_X) ) {
				variabile_N = rowTable.getBookmaker_1QuotAttuale().getMatchOdds_X();
				
			}else if( jsonArrayFiltriQuote.getJSONObject(ite).getString(KEY).equals(SIGN_2) ) {
				variabile_N = rowTable.getBookmaker_1QuotAttuale().getMatchOdds_2();
				
			}else if( jsonArrayFiltriQuote.getJSONObject(ite).getString(KEY).equals(SIGN_OV) ) {
				variabile_N = rowTable.getBookmaker_1QuotAttuale().getOu25_Over();
				
			}else if( jsonArrayFiltriQuote.getJSONObject(ite).getString(KEY).equals(SIGN_UN) ) {
				variabile_N = rowTable.getBookmaker_1QuotAttuale().getOu25_Under();
				
			}else if( jsonArrayFiltriQuote.getJSONObject(ite).getString(KEY).equals(SIGN_GOL) ) {
				variabile_N = rowTable.getBookmaker_1QuotAttuale().getBtts_Gol();
				
			}else if( jsonArrayFiltriQuote.getJSONObject(ite).getString(KEY).equals(SIGN_NO_GOL) ) {
				variabile_N = rowTable.getBookmaker_1QuotAttuale().getOu25_Under();
				
			}else {
				return null;
			}
			
		}else if( jsonArrayFiltriQuote.getJSONObject(ite).getString(BOOK).equals(BOOK_BET365_APE) ) {
			
			if( jsonArrayFiltriQuote.getJSONObject(ite).getString(KEY).equals(SIGN_1) ) {
				variabile_N = rowTable.getBookmaker_2QuotApertura().getMatchOdds_1();
				
			}else if( jsonArrayFiltriQuote.getJSONObject(ite).getString(KEY).equals(SIGN_X) ) {
				variabile_N = rowTable.getBookmaker_2QuotApertura().getMatchOdds_X();
				
			}else if( jsonArrayFiltriQuote.getJSONObject(ite).getString(KEY).equals(SIGN_2) ) {
				variabile_N = rowTable.getBookmaker_2QuotApertura().getMatchOdds_2();
				
			}else if( jsonArrayFiltriQuote.getJSONObject(ite).getString(KEY).equals(SIGN_OV) ) {
				variabile_N = rowTable.getBookmaker_2QuotApertura().getOu25_Over();
				
			}else if( jsonArrayFiltriQuote.getJSONObject(ite).getString(KEY).equals(SIGN_UN) ) {
				variabile_N = rowTable.getBookmaker_2QuotApertura().getOu25_Under();
				
			}else if( jsonArrayFiltriQuote.getJSONObject(ite).getString(KEY).equals(SIGN_GOL) ) {
				variabile_N = rowTable.getBookmaker_2QuotApertura().getBtts_Gol();
				
			}else if( jsonArrayFiltriQuote.getJSONObject(ite).getString(KEY).equals(SIGN_NO_GOL) ) {
				variabile_N = rowTable.getBookmaker_2QuotApertura().getOu25_Under();
				
			}else {
				return null;
			}
			
		}else if( jsonArrayFiltriQuote.getJSONObject(ite).getString(BOOK).equals(BOOK_BET365_ATT) ) {
			
			if( jsonArrayFiltriQuote.getJSONObject(ite).getString(KEY).equals(SIGN_1) ) {
				variabile_N = rowTable.getBookmaker_2QuotAttuale().getMatchOdds_1();
				
			}else if( jsonArrayFiltriQuote.getJSONObject(ite).getString(KEY).equals(SIGN_X) ) {
				variabile_N = rowTable.getBookmaker_2QuotAttuale().getMatchOdds_X();
				
			}else if( jsonArrayFiltriQuote.getJSONObject(ite).getString(KEY).equals(SIGN_2) ) {
				variabile_N = rowTable.getBookmaker_2QuotAttuale().getMatchOdds_2();
				
			}else if( jsonArrayFiltriQuote.getJSONObject(ite).getString(KEY).equals(SIGN_OV) ) {
				variabile_N = rowTable.getBookmaker_2QuotAttuale().getOu25_Over();
				
			}else if( jsonArrayFiltriQuote.getJSONObject(ite).getString(KEY).equals(SIGN_UN) ) {
				variabile_N = rowTable.getBookmaker_2QuotAttuale().getOu25_Under();
				
			}else if( jsonArrayFiltriQuote.getJSONObject(ite).getString(KEY).equals(SIGN_GOL) ) {
				variabile_N = rowTable.getBookmaker_2QuotAttuale().getBtts_Gol();
				
			}else if( jsonArrayFiltriQuote.getJSONObject(ite).getString(KEY).equals(SIGN_NO_GOL) ) {
				variabile_N = rowTable.getBookmaker_2QuotAttuale().getOu25_Under();
				
			}else {
				return null;
			}
			
		}else if( jsonArrayFiltriQuote.getJSONObject(ite).getString(BOOK).equals(BOOK_MBOOK_APE) ) {
			
			if( jsonArrayFiltriQuote.getJSONObject(ite).getString(KEY).equals(SIGN_1) ) {
				variabile_N = rowTable.getBookmakersMedia_QuotApertura().getMatchOdds_1();
				
			}else if( jsonArrayFiltriQuote.getJSONObject(ite).getString(KEY).equals(SIGN_X) ) {
				variabile_N = rowTable.getBookmakersMedia_QuotApertura().getMatchOdds_X();
				
			}else if( jsonArrayFiltriQuote.getJSONObject(ite).getString(KEY).equals(SIGN_2) ) {
				variabile_N = rowTable.getBookmakersMedia_QuotApertura().getMatchOdds_2();
				
			}else if( jsonArrayFiltriQuote.getJSONObject(ite).getString(KEY).equals(SIGN_OV) ) {
				variabile_N = rowTable.getBookmakersMedia_QuotApertura().getOu25_Over();
				
			}else if( jsonArrayFiltriQuote.getJSONObject(ite).getString(KEY).equals(SIGN_UN) ) {
				variabile_N = rowTable.getBookmakersMedia_QuotApertura().getOu25_Under();
				
			}else if( jsonArrayFiltriQuote.getJSONObject(ite).getString(KEY).equals(SIGN_GOL) ) {
				variabile_N = rowTable.getBookmakersMedia_QuotApertura().getBtts_Gol();
				
			}else if( jsonArrayFiltriQuote.getJSONObject(ite).getString(KEY).equals(SIGN_NO_GOL) ) {
				variabile_N = rowTable.getBookmakersMedia_QuotApertura().getOu25_Under();
				
			}else {
				return null;
			}
			
		}else if( jsonArrayFiltriQuote.getJSONObject(ite).getString(BOOK).equals(BOOK_MBOOK_ATT) ) {
			
			if( jsonArrayFiltriQuote.getJSONObject(ite).getString(KEY).equals(SIGN_1) ) {
				variabile_N = rowTable.getBookmakersMedia_QuotAttuale().getMatchOdds_1();
				
			}else if( jsonArrayFiltriQuote.getJSONObject(ite).getString(KEY).equals(SIGN_X) ) {
				variabile_N = rowTable.getBookmakersMedia_QuotAttuale().getMatchOdds_X();
				
			}else if( jsonArrayFiltriQuote.getJSONObject(ite).getString(KEY).equals(SIGN_2) ) {
				variabile_N = rowTable.getBookmakersMedia_QuotAttuale().getMatchOdds_2();
				
			}else if( jsonArrayFiltriQuote.getJSONObject(ite).getString(KEY).equals(SIGN_OV) ) {
				variabile_N = rowTable.getBookmakersMedia_QuotAttuale().getOu25_Over();
				
			}else if( jsonArrayFiltriQuote.getJSONObject(ite).getString(KEY).equals(SIGN_UN) ) {
				variabile_N = rowTable.getBookmakersMedia_QuotAttuale().getOu25_Under();
				
			}else if( jsonArrayFiltriQuote.getJSONObject(ite).getString(KEY).equals(SIGN_GOL) ) {
				variabile_N = rowTable.getBookmakersMedia_QuotAttuale().getBtts_Gol();
				
			}else if( jsonArrayFiltriQuote.getJSONObject(ite).getString(KEY).equals(SIGN_NO_GOL) ) {
				variabile_N = rowTable.getBookmakersMedia_QuotAttuale().getOu25_Under();
				
			}else {
				return null;
			}
		}
		
		return variabile_N;
	}
	
	
}
