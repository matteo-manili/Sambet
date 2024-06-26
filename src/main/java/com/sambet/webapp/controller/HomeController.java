package com.sambet.webapp.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.sambet.Constants;
import com.sambet.util.DammiTempoOperazione;
import com.sambet.webapp.controller.home.CaricaMainTable;
import com.sambet.webapp.util.bean.MainTable;
import com.sambet.webapp.util.bean.MainTable.RowTable;
import com.sambet.webapp.util.bean.MainTable.RowTable.Pronostico;
import com.sambet.webapp.util.bean.MainTable.RowTable.Quotazione;
import com.sambet.webapp.util.bean.MainTableFiltri;
import com.sambet.webapp.util.bean.MainTableFiltri.FiltroAscDesc;
import com.sambet.webapp.util.bean.MainTableFiltri.FiltroCampionati;
import com.sambet.webapp.util.bean.MainTableFiltri.FiltroNazioni;

/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
@Controller
public class HomeController extends BaseFormController {
	
	
	@RequestMapping(value = "/", method = RequestMethod.POST)
    public ModelAndView HomePOST(final HttpServletRequest request, final HttpServletResponse response, final String idRicTransfertDuplic, final String coursePartId ) {
		ModelAndView mav = new ModelAndView("home");
		try {
			return CaricaHome(mav, request);
        }catch(final Exception e) {
    		e.printStackTrace();
    		saveError(request, getText("errors.save", request.getLocale()));
    		return new ModelAndView("redirect:/");
        }
    }

	
	private ModelAndView CaricaHome(ModelAndView mav, HttpServletRequest request) throws Exception{
		long startTime = System.nanoTime();
		DammiTempoOperazione.DammiSecondi(startTime, "CaricaFormAcquistoCorsa-1");
    	return mav;
    }
	
	private HashMap<String, String> GetFiltriAd(String pair, int idx, HashMap<String, String> filtriAd) throws UnsupportedEncodingException{
		 Iterator<Entry<String, String>> ite = Constants.FILTRI_ASC_DESC.entrySet().iterator();
	    while (ite.hasNext()) {
	        Map.Entry<String, String> eleIte = (Map.Entry<String, String>)ite.next();
	        if( URLDecoder.decode(pair.substring(0, idx), Constants.ENCODING_UTF_8).equals(eleIte.getKey()) 
	        		&& !filtriAd.containsKey( URLDecoder.decode(pair.substring(0, idx), Constants.ENCODING_UTF_8)) ) {
	        	filtriAd.put(eleIte.getKey(), URLDecoder.decode(pair.substring(idx + 1), Constants.ENCODING_UTF_8));
	        }
	    }
	    return filtriAd;
	}
	
	@RequestMapping(value = "/mainfiltrijson", method = RequestMethod.POST)
	public void mainfiltrijson(HttpServletRequest request, HttpServletResponse response) {
		log.debug("mainfiltrijson");
		try{
			String siglaNazione = null; List<Long> idArrayListCompetizione = new ArrayList<Long>();
			HashMap<String, String> filtriAd = new HashMap<String, String>();
		    String query = request.getParameter("dataForm"); String[] pairs = query.split("&");
		    Map<String, String> mapParameters = new LinkedHashMap<String, String>();
		    for(String pair: pairs) {
		       int idx = pair.indexOf("=");
		       mapParameters.put(URLDecoder.decode(pair.substring(0, idx), Constants.ENCODING_UTF_8), URLDecoder.decode(pair.substring(idx + 1), Constants.ENCODING_UTF_8));
		       if( URLDecoder.decode(pair.substring(0, idx), Constants.ENCODING_UTF_8).equals("sigla-naz") ) {
		    	   siglaNazione = URLDecoder.decode(pair.substring(idx + 1), Constants.ENCODING_UTF_8);
		       }
		       if( URLDecoder.decode(pair.substring(0, idx), Constants.ENCODING_UTF_8).equals("select-comp") ) {
		    	   long idCompetizione = Long.parseLong(URLDecoder.decode(pair.substring(idx + 1), Constants.ENCODING_UTF_8));
		    	   idArrayListCompetizione.add(idCompetizione);
		       }
		       filtriAd = GetFiltriAd(pair, idx, filtriAd);
		    }
		    
		    MainTableFiltri mainTableFiltri = CaricaMainTable.Carica_Filtri(siglaNazione, idArrayListCompetizione, filtriAd);
		    JSONObject jsonMain = new JSONObject();
		    JSONArray jsonMainTableFiltriNazionale = new JSONArray();
		    for(FiltroNazioni ite: mainTableFiltri.getListNazioni()) {
		    	JSONObject detail = new JSONObject();
		    	detail.put("nazioneSigla", ite.getNazioneSigla());
		    	detail.put("nazioneNome", ite.getNazioneNome());
		    	detail.put("selected", ite.isSelected());
		    	jsonMainTableFiltriNazionale.put(detail);
		    }
		    jsonMain.put("jsonMainTableFiltriNazionale", jsonMainTableFiltriNazionale);
		    
		    JSONArray jsonMainTableFiltriCampionato = new JSONArray();
		    for(FiltroCampionati ite: mainTableFiltri.getListCampionati()) {
		    	JSONObject detail = new JSONObject();
		    	detail.put("idCampionato", ite.getIdCampionato());
		    	detail.put("campionatoNome", ite.getCampionatoNome());
		    	detail.put("selected", ite.isSelected());
		    	jsonMainTableFiltriCampionato.put(detail);
		    }
		    jsonMain.put("jsonMainTableFiltriCampionato", jsonMainTableFiltriCampionato);
		    
		    
		    JSONArray jsonMainTableListFiltriAscDesc = new JSONArray();
		    for(FiltroAscDesc ite: mainTableFiltri.getListFiltriAscDesc()) {
		    	JSONObject detail = new JSONObject();
		    	detail.put("nomeFiltroAscDesc", ite.getNomeFiltroAscDesc());
		    	detail.put("ascDesc", ite.getAscDesc());
		    	jsonMainTableListFiltriAscDesc.put(detail);
		    }
		    jsonMain.put("jsonMainTableListFiltriAscDesc", jsonMainTableListFiltriAscDesc);
		    
		    
		    JSONArray jsonMainTableElementsAscDesc = new JSONArray();
		    Iterator<Entry<String, String>> ite = Constants.ELEMENTS_ASC_DESC.entrySet().iterator();
		    while (ite.hasNext()) {
		        Map.Entry<String, String> pair = (Map.Entry<String, String>)ite.next();
		        JSONObject detail = new JSONObject();
		        detail.put("key", pair.getKey());
		        detail.put("value", pair.getValue());
		        jsonMainTableElementsAscDesc.put(detail);
		    }
		    jsonMain.put("jsonMainTableElementsAscDesc", jsonMainTableElementsAscDesc);
		    
//		    log.info( "jsonMain: "+jsonMain );
		    
    	    response.setContentType("application/json");
    	    response.setCharacterEncoding(Constants.ENCODING_UTF_8);
    	    response.getWriter().write(jsonMain.toString());
		}catch(Exception ee){
			ee.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/maintabellajson", method = RequestMethod.POST)
	public void maintabellajson(HttpServletRequest request, HttpServletResponse response) {
		log.debug("maintabellajson");
		try{
			String siglaNazione = null; List<Long> idArrayListCompetizione = new ArrayList<Long>();
			String dataInizioPartita = null; String oraInizioPartita = null; String dataFinePartita = null; String oraFinePartita = null; 
			String nomePartita = null; HashMap<String, String> filtriAd = new HashMap<String, String>(); 
			
		    String query = request.getParameter("dataForm"); String[] pairs = query.split("&");
		    
		    String filtri_quote = request.getParameter("filtri_quote");
			//System.out.println("filtri_quote: "+filtri_quote);
		    
		    
		    Map<String, String> mapParameters = new LinkedHashMap<String, String>();
		    for(String pair: pairs) {
		       int idx = pair.indexOf("=");
		       mapParameters.put(URLDecoder.decode(pair.substring(0, idx), Constants.ENCODING_UTF_8), URLDecoder.decode(pair.substring(idx + 1), Constants.ENCODING_UTF_8));
		       if( URLDecoder.decode(pair.substring(0, idx), Constants.ENCODING_UTF_8).equals("sigla-naz") ) {
		    	   siglaNazione = URLDecoder.decode(pair.substring(idx + 1), Constants.ENCODING_UTF_8);
		       }
		       if( URLDecoder.decode(pair.substring(0, idx), Constants.ENCODING_UTF_8).equals("select-comp") ) {
		    	   long idCompetizione = Long.parseLong(URLDecoder.decode(pair.substring(idx + 1), Constants.ENCODING_UTF_8));
		    	   idArrayListCompetizione.add(idCompetizione);
		       }
		       if( URLDecoder.decode(pair.substring(0, idx), Constants.ENCODING_UTF_8).equals("dataInizioPartita") ) {
		    	   dataInizioPartita = URLDecoder.decode(pair.substring(idx + 1), Constants.ENCODING_UTF_8);
		       }
		       if( URLDecoder.decode(pair.substring(0, idx), Constants.ENCODING_UTF_8).equals("oraInizioPartita") ) {
		    	   oraInizioPartita = URLDecoder.decode(pair.substring(idx + 1), Constants.ENCODING_UTF_8);
		       }
		       if( URLDecoder.decode(pair.substring(0, idx), Constants.ENCODING_UTF_8).equals("dataFinePartita") ) {
		    	   dataFinePartita = URLDecoder.decode(pair.substring(idx + 1), Constants.ENCODING_UTF_8);
		       }
		       if( URLDecoder.decode(pair.substring(0, idx), Constants.ENCODING_UTF_8).equals("oraFinePartita") ) {
		    	   oraFinePartita = URLDecoder.decode(pair.substring(idx + 1), Constants.ENCODING_UTF_8);
		       }
		       if( URLDecoder.decode(pair.substring(0, idx), Constants.ENCODING_UTF_8).equals("nomePartita") ) {
		    	   nomePartita = URLDecoder.decode(pair.substring(idx + 1), Constants.ENCODING_UTF_8);
		       }
		       
		       filtriAd = GetFiltriAd(pair, idx, filtriAd);
		    }
		    
		    //System.out.println("maintabellajson filtriAd: "+filtriAd);
		    
		    MainTableFiltri mainTableFiltri = CaricaMainTable.Carica_Table(siglaNazione, idArrayListCompetizione, dataInizioPartita, oraInizioPartita, dataFinePartita, 
		    		oraFinePartita, nomePartita, filtriAd );
		    JSONObject jsonMain = new JSONObject();
		    
		    MainTable mainTable = CaricaMainTable.CaricaBean(mainTableFiltri, new JSONArray(filtri_quote) );
		    JSONArray jsonMainTableRow = new JSONArray();
		    for(RowTable ite: mainTable.getRowTableList()) {
		    	JSONObject detail = new JSONObject();
		    	detail.put("nazioneNome", ite.getNazioneNome());
		    	detail.put("campionatoNome", ite.getCampionatoNome());
		    	detail.put("partitaNome", ite.getPartitaNome());
		    	detail.put("partitaData", ite.getPartitaData());
		    	detail.put("partitaOra", ite.getPartitaOra());
		 
		    	Pronostico pSing = ite.getPronosticiSingole();
		    	detail.put("pSing_matchOdds_1", pSing.isMatchOdds_1());
		    	detail.put("pSing_matchOdds_2", pSing.isMatchOdds_2());
		    	detail.put("pSing_matchOdds_X", pSing.isMatchOdds_X());
		    	detail.put("pSing_ou25_Over", pSing.isOu25_Over());
		    	detail.put("pSing_ou25_Under", pSing.isOu25_Under());
		    	detail.put("pSing_btts_Gol", pSing.isBtts_Gol());
		    	detail.put("pSing_btts_NoGol", pSing.isBtts_NoGol());
		    	
		    	Pronostico pMult = ite.getPronosticiMultiple();
		    	detail.put("pMult_matchOdds_1", pMult.isMatchOdds_1());
		    	detail.put("pMult_matchOdds_2", pMult.isMatchOdds_2());
		    	detail.put("pMult_matchOdds_X", pMult.isMatchOdds_X());
		    	detail.put("pMult_ou25_Over", pMult.isOu25_Over());
		    	detail.put("pMult_ou25_Under", pMult.isOu25_Under());
		    	detail.put("pMult_btts_Gol", pMult.isBtts_Gol());
		    	detail.put("pMult_btts_NoGol", pMult.isBtts_NoGol());
		    	
		    	Quotazione quotSambet = ite.getQuotSambet();
		    	detail.put("qSambet_matchOdds_1", quotSambet.getMatchOdds_1());
		    	detail.put("qSambet_matchOdds_2", quotSambet.getMatchOdds_2());
		    	detail.put("qSambet_matchOdds_X", quotSambet.getMatchOdds_X());
		    	detail.put("qSambet_ou25_Over", quotSambet.getOu25_Over());
		    	detail.put("qSambet_ou25_Under", quotSambet.getOu25_Under());
		    	detail.put("qSambet_btts_Gol", quotSambet.getBtts_Gol());
		    	detail.put("qSambet_btts_NoGol", quotSambet.getBtts_NoGol());
		    	
		    	Quotazione quotApertura = ite.getQuotApertura();
		    	detail.put("apert_matchOdds_1", quotApertura.getMatchOdds_1());
		    	detail.put("apert_matchOdds_2", quotApertura.getMatchOdds_2());
		    	detail.put("apert_matchOdds_X", quotApertura.getMatchOdds_X());
		    	detail.put("apert_ou25_Over", quotApertura.getOu25_Over());
		    	detail.put("apert_ou25_Under", quotApertura.getOu25_Under());
		    	detail.put("apert_btts_Gol", quotApertura.getBtts_Gol());
		    	detail.put("apert_btts_NoGol", quotApertura.getBtts_NoGol());
		    	
		    	Quotazione quotMedia = ite.getQuotMedia();
		    	detail.put("media_matchOdds_1", quotMedia.getMatchOdds_1());
		    	detail.put("media_matchOdds_2", quotMedia.getMatchOdds_2());
		    	detail.put("media_matchOdds_X", quotMedia.getMatchOdds_X());
		    	detail.put("media_ou25_Over", quotMedia.getOu25_Over());
		    	detail.put("media_ou25_Under", quotMedia.getOu25_Under());
		    	detail.put("media_btts_Gol", quotMedia.getBtts_Gol());
		    	detail.put("media_btts_NoGol", quotMedia.getBtts_NoGol());
		    	
		    	Quotazione quotAttuale = ite.getQuotAttuale();
		    	detail.put("attual_matchOdds_1", quotAttuale.getMatchOdds_1());
		    	detail.put("attual_matchOdds_2", quotAttuale.getMatchOdds_2());
		    	detail.put("attual_matchOdds_X", quotAttuale.getMatchOdds_X());
		    	detail.put("attual_ou25_Over", quotAttuale.getOu25_Over());
		    	detail.put("attual_ou25_Under", quotAttuale.getOu25_Under());
		    	detail.put("attual_btts_Gol", quotAttuale.getBtts_Gol());
		    	detail.put("attual_btts_NoGol", quotAttuale.getBtts_NoGol());
		    	
		    	detail.put("count_media_matchOdds_1", ite.getQuotMediaCount_1());
		    	detail.put("count_media_matchOdds_2", ite.getQuotMediaCount_2());
		    	detail.put("count_media_matchOdds_X", ite.getQuotMediaCount_X());
		    	detail.put("count_media_ou25_Over", ite.getQuotMediaCount_Over25());
		    	detail.put("count_media_ou25_Under", ite.getQuotMediaCount_Under25());
		    	detail.put("count_media_btts_Gol", ite.getQuotMediaCount_Gol());
		    	detail.put("count_media_btts_NoGol", ite.getQuotMediaCount_noGol());
		    	
		    	detail.put("andamento_matchOdds_1", ite.getAndamento_1());
		    	detail.put("andamento_matchOdds_2", ite.getAndamento_2());
		    	detail.put("andamento_matchOdds_X", ite.getAndamento_X());
		    	detail.put("andamento_ou25_Over", ite.getAndamento_Over25());
		    	detail.put("andamento_ou25_Under", ite.getAndamento_Under25());
		    	detail.put("andamento_btts_Gol", ite.getAndamento_Gol());
		    	detail.put("andamento_btts_NoGol", ite.getAndamento_noGol());
		    	
		    	// Nuove Api
		    	
		    	detail.put("nomeBookmaker1", ite.getNomeBookmaker_1());
		    	Quotazione quotAperturaBook1 = ite.getBookmaker_1QuotApertura();
		    	detail.put("book1_apert_matchOdds_1", quotAperturaBook1.getMatchOdds_1());
		    	detail.put("book1_apert_matchOdds_2", quotAperturaBook1.getMatchOdds_2());
		    	detail.put("book1_apert_matchOdds_X", quotAperturaBook1.getMatchOdds_X());
		    	detail.put("book1_apert_ou25_Over", quotAperturaBook1.getOu25_Over());
		    	detail.put("book1_apert_ou25_Under", quotAperturaBook1.getOu25_Under());
		    	detail.put("book1_apert_btts_Gol", quotAperturaBook1.getBtts_Gol());
		    	detail.put("book1_apert_btts_NoGol", quotAperturaBook1.getBtts_NoGol());
		    	
		    	Quotazione quotAttualeBook1 = ite.getBookmaker_1QuotAttuale();
		    	detail.put("book1_attual_matchOdds_1", quotAttualeBook1.getMatchOdds_1());
		    	detail.put("book1_attual_matchOdds_2", quotAttualeBook1.getMatchOdds_2());
		    	detail.put("book1_attual_matchOdds_X", quotAttualeBook1.getMatchOdds_X());
		    	detail.put("book1_attual_ou25_Over", quotAttualeBook1.getOu25_Over());
		    	detail.put("book1_attual_ou25_Under", quotAttualeBook1.getOu25_Under());
		    	detail.put("book1_attual_btts_Gol", quotAttualeBook1.getBtts_Gol());
		    	detail.put("book1_attual_btts_NoGol", quotAttualeBook1.getBtts_NoGol());
		    	
		    	detail.put("nomeBookmaker2", ite.getNomeBookmaker_2());
		    	Quotazione quotAperturaBook2 = ite.getBookmaker_2QuotApertura();
		    	detail.put("book2_apert_matchOdds_1", quotAperturaBook2.getMatchOdds_1());
		    	detail.put("book2_apert_matchOdds_2", quotAperturaBook2.getMatchOdds_2());
		    	detail.put("book2_apert_matchOdds_X", quotAperturaBook2.getMatchOdds_X());
		    	detail.put("book2_apert_ou25_Over", quotAperturaBook2.getOu25_Over());
		    	detail.put("book2_apert_ou25_Under", quotAperturaBook2.getOu25_Under());
		    	detail.put("book2_apert_btts_Gol", quotAperturaBook2.getBtts_Gol());
		    	detail.put("book2_apert_btts_NoGol", quotAperturaBook2.getBtts_NoGol());
		    	
		    	Quotazione quotAttualeBook2 = ite.getBookmaker_2QuotAttuale();
		    	detail.put("book2_attual_matchOdds_1", quotAttualeBook2.getMatchOdds_1());
		    	detail.put("book2_attual_matchOdds_2", quotAttualeBook2.getMatchOdds_2());
		    	detail.put("book2_attual_matchOdds_X", quotAttualeBook2.getMatchOdds_X());
		    	detail.put("book2_attual_ou25_Over", quotAttualeBook2.getOu25_Over());
		    	detail.put("book2_attual_ou25_Under", quotAttualeBook2.getOu25_Under());
		    	detail.put("book2_attual_btts_Gol", quotAttualeBook2.getBtts_Gol());
		    	detail.put("book2_attual_btts_NoGol", quotAttualeBook2.getBtts_NoGol());
		    	
		    	Quotazione bookmakersMedia_QuotApertura = ite.getBookmakersMedia_QuotApertura();
		    	detail.put("bookMedia_apert_matchOdds_1", bookmakersMedia_QuotApertura.getMatchOdds_1());
		    	detail.put("bookMedia_apert_matchOdds_2", bookmakersMedia_QuotApertura.getMatchOdds_2());
		    	detail.put("bookMedia_apert_matchOdds_X", bookmakersMedia_QuotApertura.getMatchOdds_X());
		    	detail.put("bookMedia_apert_ou25_Over", bookmakersMedia_QuotApertura.getOu25_Over());
		    	detail.put("bookMedia_apert_ou25_Under", bookmakersMedia_QuotApertura.getOu25_Under());
		    	detail.put("bookMedia_apert_btts_Gol", bookmakersMedia_QuotApertura.getBtts_Gol());
		    	detail.put("bookMedia_apert_btts_NoGol", bookmakersMedia_QuotApertura.getBtts_NoGol());
		    	
		    	Quotazione bookmakersMedia_QuotAttuale = ite.getBookmakersMedia_QuotAttuale();
		    	detail.put("bookMedia_attual_matchOdds_1", bookmakersMedia_QuotAttuale.getMatchOdds_1());
		    	detail.put("bookMedia_attual_matchOdds_2", bookmakersMedia_QuotAttuale.getMatchOdds_2());
		    	detail.put("bookMedia_attual_matchOdds_X", bookmakersMedia_QuotAttuale.getMatchOdds_X());
		    	detail.put("bookMedia_attual_ou25_Over", bookmakersMedia_QuotAttuale.getOu25_Over());
		    	detail.put("bookMedia_attual_ou25_Under", bookmakersMedia_QuotAttuale.getOu25_Under());
		    	detail.put("bookMedia_attual_btts_Gol", bookmakersMedia_QuotAttuale.getBtts_Gol());
		    	detail.put("bookMedia_attual_btts_NoGol", bookmakersMedia_QuotAttuale.getBtts_NoGol());
		    	
		    	jsonMainTableRow.put(detail);
		    }
		    jsonMain.put("jsonMainTableRow", jsonMainTableRow);
		    
			if( mainTable.getTotaleRow() > 0 ) {
				jsonMain.put("totaleRow", mainTable.getTotaleRow());
			}
    	    response.setContentType("application/json");
    	    response.setCharacterEncoding(Constants.ENCODING_UTF_8);
    	    response.getWriter().write(jsonMain.toString());
		}catch(Exception ee){
			ee.printStackTrace();
		}
	}
    
	
	//@RequestMapping(value={"/", "home"}, method = RequestMethod.GET)
	@RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView HomeGET( final HttpServletRequest request, final HttpServletResponse response,
    		@RequestParam(value = "sigla-naz", required = false) String siglaNazione,
    		@RequestParam(value = "select-comp", required = false) Long[] idArrayCompetizione,
    		
    		@RequestParam(value = "dataInizioPartita", required = false) String dataInizioPartita,
    		@RequestParam(value = "oraInizioPartita", required = false) String oraInizioPartita,
    		@RequestParam(value = "dataFinePartita", required = false) String dataFinePartita,
    		@RequestParam(value = "oraFinePartita", required = false) String oraFinePartita
    		
    		) throws Exception {
    	log.debug("sono in HomeGET GET");
    	ModelAndView mav = new ModelAndView("home");
    	final Locale locale = request.getLocale();
    	try{
    		
    		mav.addObject("pickadate_translations", request.getLocale().getLanguage()+"_"+request.getLocale().getCountry()); 
    		return CaricaHome(mav, request);

    	}catch(Exception exc){
    		exc.printStackTrace();
    		saveError(request, getText("errors.cancel", locale));
    		return CaricaHome(mav, request);
    	}
    }
    
} //fine classe
