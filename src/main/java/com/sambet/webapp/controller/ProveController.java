package com.sambet.webapp.controller;

import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.rapidapi.apifootball.RapidApiApiFootball_CompetitionEvents;
import com.rapidapi.apifootball.RapidApiApiFootball_Odds;
import com.rapidapi.apifootball.RapidApiApiFootball_Statistics;
import com.sambet.service.MailEngine;
import com.sambet.util.PuliziaDB;

/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
@Controller
public class ProveController extends BaseFormController {
    
	protected MailEngine mailEngine = null;
	protected SimpleMailMessage message = null;
	
	
	@Autowired
    public void setMailEngine(MailEngine mailEngine) {
        this.mailEngine = mailEngine;
    }
	
	@Autowired
    public void setMessage(SimpleMailMessage message) {
        this.message = message;
    }

    @RequestMapping(value = "/prove", method = RequestMethod.POST)
	public String HomePost(final HttpServletRequest request, final String idRicTransfertDuplic ){
    	log.debug("sono in onSubmitProve POST");
		try {
			final String[] groupRadioClassiAutoveicoli = request.getParameterValues("tipoUser");
            if(groupRadioClassiAutoveicoli != null) {
                for(final String groupRadioCalleAutoveicoli_check : groupRadioClassiAutoveicoli) {
                    //System.out.println(groupRadioCalleAutoveicoli_check);
                }
            }
            if( request.getParameterValues("tipoUser")[0].equals("2") ){

            }
	        return "redirect:/prove";
        }
		catch (final Exception e) {
    		e.printStackTrace();
    		return "redirect:/prove";
        }
    }
    
    
    private ModelAndView CaricaFormProve(ModelAndView mav, Locale locale, StringBuilder sb) throws Exception{
    	// ROBA PAYPAL
    	return mav;
    }
    
    
    @RequestMapping(value = "/prove", method = RequestMethod.GET)
    protected ModelAndView ProveGET(final HttpServletRequest request, final HttpServletResponse response,
    		@RequestParam(value = "var", required = false) String var) {
    	
    	log.debug("sono in prove GET");
    	final Locale locale = request.getLocale();
    	ModelAndView mav = new ModelAndView("prove");
    	try{
    		//------------------------------
    		
    		
    		final String RapidApiApiFootball = "RapidApiApiFootball";
    		if( var != null && var.equals("1") ) {
    			RapidApiApiFootball_CompetitionEvents.GetCompetizioniEventi_Associa_BfEventi();
    			mav.addObject(RapidApiApiFootball, "RapidApiApiFootball_CompetitionEvents.GetCompetizioniEventi_Associa_BfEventi");
    			
    		}else if(var != null && var.equals("2")) {
    			RapidApiApiFootball_Odds.GetOddsfrom_idFixture();
    			mav.addObject(RapidApiApiFootball, "RapidApiApiFootball_Odds.GetOddsfrom_idFixture");
    			
			}else if(var != null && var.equals("3")) {
				RapidApiApiFootball_Statistics.GetCompetizioniEventi_Statistics();
				mav.addObject(RapidApiApiFootball, "RapidApiApiFootball_Statistics.GetCompetizioniEventi_Statistics");
				
			}else if(var != null && var.equals("4")) {
				PuliziaDB.puliziaDatabase_Market_Eventi_Campionati();
				mav.addObject(RapidApiApiFootball, "PuliziaDB.puliziaDatabase_Market_Eventi_Campionati");
			}

    		mav = CaricaFormProve(mav, locale, null);
	    	return mav;
    	}catch(Exception exc){
    		exc.printStackTrace();
    		saveError(request, getText("errors.cancel", locale));
    		return new ModelAndView("prove");
    	}
    }

    
    @RequestMapping(value = "/provews", method = RequestMethod.GET)
    protected ModelAndView provewsGET(final HttpServletRequest request, final HttpServletResponse response) {
    	log.debug("sono in provews GET");
    	final Locale locale = request.getLocale();
    	ModelAndView mav = new ModelAndView( "prove_06" );
    	try{
    		
    		mav = CaricaFormProve(mav, locale, null);
	    	return mav;
    	}catch(Exception exc){
    		exc.printStackTrace();
    		saveError(request, getText("errors.cancel", locale));
    		return new ModelAndView( "prove_06" );
    	}
    }
    
} //fine classe
