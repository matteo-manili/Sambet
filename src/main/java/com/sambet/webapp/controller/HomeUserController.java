package com.sambet.webapp.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.sambet.Constants;
import com.sambet.model.TipoRuoli;
import com.sambet.model.User;
import com.sambet.service.TipoRuoliManager;



/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
@Controller
@RequestMapping("/home-user*")
public class HomeUserController extends BaseFormController {

	

	private TipoRuoliManager tipoRuoliManager;
    @Autowired
    public void setTipoRuoliManager(final TipoRuoliManager tipoRuoliManager) {
        this.tipoRuoliManager = tipoRuoliManager;
    }
	
	private ModelAndView CaricaFormUtenteHome(ModelAndView mav, User user, Locale locale, HttpServletRequest request, boolean registrazioneEseguita) throws Exception {
		mav.addObject("registrazioneEseguita", registrazioneEseguita);
		mav.addObject("user", user);

		List<TipoRuoli> tipoRuoliList = new ArrayList<TipoRuoli>();
		tipoRuoliList.addAll( user.getTipoRuoli() );
		mav.addObject("tipoRuoliList", tipoRuoliList);


		if( tipoRuoliList.contains(tipoRuoliManager.getTipoRuoliByName(Constants.CLIENTE)) ) {
			


		}
    	return mav;
    }
	
	
    @RequestMapping(method = RequestMethod.GET)
    protected ModelAndView showHome(final HttpServletRequest request, final HttpServletResponse response, 
    		@RequestParam(required = false, value = "registrazioneEseguita") String registrazioneEseguita) {
    	final Locale locale = request.getLocale();
    	ModelAndView mav = new ModelAndView("home-user");
    	try{
    		Boolean registrEseguita = Boolean.valueOf( registrazioneEseguita );
    		User user = getUserManager().getUserByUsername(request.getRemoteUser());
    		return CaricaFormUtenteHome(mav, user, locale, request, registrEseguita);
    	}catch(Exception exc){
    		exc.printStackTrace();
    		saveError(request, getText("errors.cancel", locale));
    		return new ModelAndView("home-user");
    	}
    }

    


}
