package com.sambet.webapp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sambet.Constants;
import com.sambet.model.TipoRuoli;
import com.sambet.model.User;
import com.sambet.service.RoleManager;
import com.sambet.service.TipoRuoliManager;
import com.sambet.service.UserManager;
import com.sambet.util.UtilString;
import com.sambet.util.customexception.UserExistsException;
import com.sambet.webapp.util.ApplicationUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Implementation of <strong>SimpleFormController</strong> that interacts with
 * the {@link UserManager} to retrieve/persist values to the database.
 *
 * <p><a href="UserFormController.java.html"><i>View Source</i></a>
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
@Controller
@RequestMapping("/userform*")
public class UserFormController extends BaseFormController  /*implements HandlerExceptionResolver*/ {


    
    private TipoRuoliManager tipoRuoliManager;
    @Autowired
    public void setTipoRuoliManager(final TipoRuoliManager tipoRuoliManager) {
        this.tipoRuoliManager = tipoRuoliManager;
    }
    
    private RoleManager roleManager;
    @Autowired
    public void setRoleManager(final RoleManager roleManager) {
        this.roleManager = roleManager;
    }
    
    
    @RequestMapping(method = RequestMethod.POST)
    protected ModelAndView onSubmit(@ModelAttribute("user") User userMod, final BindingResult errors, final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {
    	log.debug("sono in UserFormController.onSubmit POST");
    	ModelAndView mav = new ModelAndView("userform");
    	final Locale locale = request.getLocale();
    	
    	if (request.getParameter("cancel") != null) {
			return caricaFormUserProfilo(request, mav, getUserManager().getUserByUsername(request.getRemoteUser()));
        }
        
        if(request.getParameter("delete") != null) {

			userMod.setEnabled(false);
    		getUserManager().saveUser(userMod);

    		request.getSession().invalidate();
    		if (request.getSession(false) != null) {
    			request.getSession().invalidate();
    		}
    		//roba di logout.jsp
    		Cookie terminate = new Cookie(TokenBasedRememberMeServices.SPRING_SECURITY_REMEMBER_ME_COOKIE_KEY, null);
    		String contextPath = request.getContextPath();
    		terminate.setPath(contextPath != null && contextPath.length() > 0 ? contextPath : "/");
    		terminate.setMaxAge(0);
    		response.addCookie(terminate);
    		
            saveMessage(request, getText("user.deletedAccout", /* user.getFullName(),*/ locale ));
            mav = new ModelAndView("redirect:/login");
            return caricaFormUserProfilo(request, mav, userMod);
        }

        if (validator != null) { // validator is null during testing
            validator.validate(userMod, errors);
            if (errors.hasErrors() && request.getParameter("delete") == null) { // don't validate when deleting
                //return "userform";
            	return caricaFormUserProfilo(request, mav, userMod);
            }
        }
        

        
        if(request.getParameter("number-tel-autista-pre") != null && request.getParameter("number-tel-autista-pre").equals("")){
			errors.rejectValue("phoneNumber", "errors.required", new Object[] { "telefono" }, "");
			userMod.setPhoneNumber("");
		}else{
			if( userMod.getPhoneNumber() != null && userMod.getPhoneNumber().equals("") || userMod.getPhoneNumber().equals("noValidNumber")){
				errors.rejectValue("phoneNumber", "errors.format.phoneNumber", new Object[] { request.getParameter("number-tel-autista-pre") }, "");
				userMod.setPhoneNumber("");
			}
		}
        if( !getUserManager().get(userMod.getId()).getPhoneNumber().equals( userMod.getPhoneNumber() )){
	        if( getUserManager().userTelephoneExist( userMod.getPhoneNumber() )) {
				errors.rejectValue("phoneNumber", "errors.existing.autista.phoneNumber.user", new Object[] { userMod.getPhoneNumber() }, "");
				//return "userform";
				return caricaFormUserProfilo(request, mav, userMod);
			}
        }
        
        // CONTROLLO CODICE VENDITORE
        if( request.isUserInRole(Constants.VENDITORE_ROLE) && userMod.getCodiceVenditore().equals("") ){
			errors.rejectValue("codiceVenditore", "Devi inserire un codice Venditore");
			//return "userform";
			return caricaFormUserProfilo(request, mav, userMod);
        }
        
        userMod.setFirstName( UtilString.PrimaLetteraMaiuscola( userMod.getFirstName() ).trim() );
        userMod.setLastName( UtilString.PrimaLetteraMaiuscola( userMod.getLastName() ).trim() );
        

        
        /**
         * RETURN VISUALIZZO ERRORI 
         */
        if (errors.hasErrors()) { // don't validate when deleting
            //return "userform";
            return caricaFormUserProfilo(request, mav, userMod);
        }
        
        if(request.getParameter("tipoVenditoreCheck") != null){
        	if(!request.isUserInRole(Constants.VENDITORE_ROLE)){
	        	userMod.addRole(roleManager.getRole(Constants.VENDITORE_ROLE));
	        	if(userMod.getCodiceVenditore() == null || userMod.getCodiceVenditore().equals("")){
	        		String codiceVenditore = userMod.getFirstName().toUpperCase();
	        		int conta = 1;
	            	while( getUserManager().getUserIdVenditore_by_CodiceVenditore(codiceVenditore) != null ){
	            		codiceVenditore = userMod.getFirstName().toUpperCase() + conta++;
	            	}
	            	userMod.setCodiceVenditore(codiceVenditore);
	        	}
	        	saveMessage(request, "profilo Venditore Attivato");
        	}
        }else{
        	if(request.isUserInRole(Constants.VENDITORE_ROLE)){
        		userMod.getRoles().remove(roleManager.getRole(Constants.VENDITORE_ROLE));
        		userMod.setCodiceVenditore(null);
        		saveMessage(request, "profilo Venditore Disattivato");
        	}
        }
        


        final Integer originalVersion = userMod.getVersion();
        try {
        	if( request.getParameter("country-select") != null ){
        		userMod.getBillingInformation().setCountry( request.getParameter("country-select") );
        	}
            getUserManager().saveUser(userMod);
            saveMessage(request, getText("user.saved", userMod.getFullName(), locale));
            return caricaFormUserProfilo(request, mav, userMod);
            
        }catch(final AccessDeniedException ade) {
            // thrown by UserSecurityAdvice configured in aop:advisor userManagerSecurity
            log.warn(ade.getMessage());
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            //return null;
            return caricaFormUserProfilo(request, mav, userMod);
        }catch(final UserExistsException e) {
            errors.rejectValue("username", "errors.existing.user",
                    new Object[] { userMod.getEmail(), userMod.getPhoneNumber() }, "duplicate user");
            userMod.setVersion(originalVersion);
            return caricaFormUserProfilo(request, mav, userMod);
        }
    }


    @ModelAttribute("user")
    protected User loadUser(final HttpServletRequest request) {
        return getUserManager().getUserByUsername(request.getRemoteUser());
    }
    
    
    private ModelAndView caricaFormUserProfilo(HttpServletRequest request, ModelAndView mav, User user) throws Exception{


        List<TipoRuoli> tipoRuoliList = new ArrayList<TipoRuoli>();
		tipoRuoliList.addAll( user.getTipoRuoli() );
		mav.addObject("tipoRuoliList", tipoRuoliList);

        mav.addObject("user", user);
        if( ApplicationUtils.CheckAmbienteVenditore(getServletContext()) ){
    		mav.addObject("ambienteVenditore", true);
    	}
		return mav;
	}
    
    
    @RequestMapping(method = RequestMethod.GET)
    protected ModelAndView showForm(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
    	ModelAndView mav = new ModelAndView("userform");
    	log.debug("sono in UserFormController.showForm GET");
        User user = getUserManager().getUserByUsername(request.getRemoteUser());
        return caricaFormUserProfilo(request, mav, user);
    }



}
