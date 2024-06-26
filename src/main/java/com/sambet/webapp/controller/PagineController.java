package com.sambet.webapp.controller;

import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.sambet.service.MailEngine;

/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
@Controller
public class PagineController extends BaseFormController {
    
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
	
    private ModelAndView CaricaFormGuida(ModelAndView mav, Locale locale, StringBuilder sb) throws Exception{
    	return mav;
    }
    
    @RequestMapping(value = "/recensioni-bookmakers", method = RequestMethod.GET)
    protected ModelAndView RecensioniBokkmakersGET(final HttpServletRequest request, final HttpServletResponse response) {
//    	log.info("sono in guida GET");
    	final Locale locale = request.getLocale();
    	ModelAndView mav = new ModelAndView("recensioni-bookmakers");
    	try{
    		mav = CaricaFormGuida(mav, locale, null);
	    	return mav;
    	}catch(Exception exc){
    		exc.printStackTrace();
    		saveError(request, getText("errors.cancel", locale));
    		return new ModelAndView("prove");
    	}
    }
    
    @RequestMapping(value = "/guida", method = RequestMethod.GET)
    protected ModelAndView ProveGET(final HttpServletRequest request, final HttpServletResponse response) {
//    	log.info("sono in guida GET");
    	final Locale locale = request.getLocale();
    	ModelAndView mav = new ModelAndView("guida");
    	try{
    		mav = CaricaFormGuida(mav, locale, null);
	    	return mav;
    	}catch(Exception exc){
    		exc.printStackTrace();
    		saveError(request, getText("errors.cancel", locale));
    		return new ModelAndView("prove");
    	}
    }

    
    
} //fine classe
