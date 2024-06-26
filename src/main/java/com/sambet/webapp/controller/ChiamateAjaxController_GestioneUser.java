package com.sambet.webapp.controller;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.WordUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.json.JSONObject;
//import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.sambet.Constants;
import com.sambet.model.User;
import com.sambet.service.GestioneApplicazioneManager;
import com.sambet.service.RoleManager;
import com.sambet.util.customexception.UserExistsException;
import com.sambet.webapp.util.ApplicationUtils;
import com.sambet.webapp.util.VerifyRecaptcha;
import com.sambet.webapp.util.bean.AutistaTerritorio;
import com.sambet.webapp.util.bean.InfoPaymentProvider;
import com.sambet.webapp.util.bean.AutistaTerritorio.AutistiProvincia;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
@Controller
public class ChiamateAjaxController_GestioneUser extends BaseFormController {
	
	private GestioneApplicazioneManager gestioneApplicazioneManager;
    @Autowired
    public void setGestioneApplicazioneManager(final GestioneApplicazioneManager gestioneApplicazioneManager) {
        this.gestioneApplicazioneManager = gestioneApplicazioneManager;
    }

    
    private RoleManager roleManager;
    @Autowired
    public void setRoleManager(final RoleManager roleManager) {
        this.roleManager = roleManager;
    }
    

    @RequestMapping(value = "/check-codice-venditore", method = RequestMethod.POST)
    public void CeckCodiceVenditore(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	JSONObject json = new JSONObject();
    	try {
    	    System.out.println("------VENDITORE----"); 
    	    System.out.println( "CodiceVenditore: "+ request.getParameter("CodiceVenditore") );
    	    System.out.println( "idVenditoreCorsa: "+ request.getParameter("idVenditoreCorsa") );
    	    String CodiceVenditore = request.getParameter("codiceVenditore");
    	    String IdVenditoreCorsa = request.getParameter("idVenditoreCorsa");
    	    if(request.getParameter("disconnettiVenditore") != null && request.getParameter("disconnettiVenditore").equals("true")  ){
    	    	request.getSession().setAttribute(Constants.VENDITORE_ATTRIBUTE_NAME, null);
    	    }else{
	    		if( ApplicationUtils.CheckAmbienteVenditore(getServletContext()) 
	    				&& (!CodiceVenditore.equals("") || !IdVenditoreCorsa.equals("")) ){
	    			Long idUserVenditore = (!CodiceVenditore.equals("")) ? getUserManager().getUserIdVenditore_by_CodiceVenditore(CodiceVenditore) :
	    				Long.parseLong( IdVenditoreCorsa );

	    			TimeUnit.SECONDS.sleep(2); //metto questa pausa per prevenire attacchi esterni
	        		if( idUserVenditore != null && getUserManager().get(idUserVenditore).getRoles().contains(roleManager.getRole(Constants.VENDITORE_ROLE)) ){
	        			request.getSession().setAttribute(Constants.VENDITORE_ATTRIBUTE_NAME, idUserVenditore);
	        			json.put("esito", true); 
	        		}else{
	        			json.put("esito", false); 
	            		json.put("message", "Codice Venditore non Riconosciuto");
	            		json.put("class", "text-danger"); 
	        		}
	    		}else{
	    			json.put("esito", false); 
	        		json.put("message", "Inserisci Codice Venditore");
	        		json.put("class", "text-primary"); 
	    		}
    	    }
    	}catch(Exception e){
    		json.put("esito", false);
    		json.put("message", e.getMessage());
    	    response.getWriter().write(json.toString());
    	} finally {
    		response.setContentType("application/json");
    	    response.setCharacterEncoding(Constants.ENCODING_UTF_8);
    	    response.getWriter().write(json.toString());
    	}
	}
    
    
    
    /**
     * Questo metodo è deprecrato, funziona solo quando ho gli utente con le email fake nomeUtente@sambet.it
     * @throws IOException 
     * 
     */
    @Deprecated
    @RequestMapping(value = "/SalvaEmailClienteRiepilogo", method = RequestMethod.POST)
	public void SalvaEmailClienteRiepilogo(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	log.debug("ChiamateAjaxController SalvaEmailClienteRiepilogo");
		JSONObject esito = new JSONObject();
		try{
			String emailCustomer = request.getParameter("emailCustomer");
			EmailValidator validator = EmailValidator.getInstance();
			if(validator.isValid(emailCustomer)) {
				User user = getUserManager().getUserByUsername(request.getRemoteUser());
				user.setEmail( emailCustomer );
				getUserManager().saveUser(user);
				esito.put("esitoSalvaEmail", true);
				esito.put("esitoSalvaEmail-mesage", getText("user.saved", request.getLocale()));
			}else{
				esito.put("esitoSalvaEmail", false);
	            esito.put("esitoSalvaEmail-mesage", getText("errors.format.email", new Object[] { emailCustomer }, request.getLocale()));
			}
		}catch(final UserExistsException e) {
			esito.put("esitoSalvaEmail", false);
			esito.put("esitoSalvaEmail-mesage", getText("errors.existing.email.user", request.getLocale()));
	    }catch (final Exception e) {
	    	esito.put("esitoSalvaEmail", false);
	    	esito.put("esitoSalvaEmail-mesage", e.getMessage());
		} finally {
			response.setContentType("application/json");
    	    response.setCharacterEncoding(Constants.ENCODING_UTF_8);
    	    response.getWriter().write( esito.toString() );
		}
	}

    



    

    

  
	
	
	
	
	




	

	

	

	
	@RequestMapping(value = "/listaZoneChosen", method = RequestMethod.POST, produces = "text/plain;charset="+Constants.ENCODING_UTF_8)
    public void listaZoneChosen(HttpServletRequest request, HttpServletResponse response) throws IOException {
		log.debug("ChiamateAjaxController listaZoneChosen");
		String json = "";
    	try {
    		request.setCharacterEncoding(Constants.ENCODING_UTF_8);
    		String term = java.net.URLDecoder.decode(request.getParameter("term"), Constants.ENCODING_UTF_8);
    		//request.setCharacterEncoding(Constants.ENCODING_UTF_8);
    		//String term = request.getParameter("term");
    		/*
    		List<Map<String, Serializable>> list = new LinkedList<Map<String, Serializable>>();
    		Map<String, Serializable> valueJson = null;
    		Iterator<Comuni> comuni_ite = getComuniManager().getNomeComuneByLikeNome_Chosen( term ).iterator();
			while(comuni_ite.hasNext()){
				Comuni comuni = comuni_ite.next();
				valueJson = new HashMap<String, Serializable>();
	    		valueJson.put("value", "COM#"+comuni.getId() );
	    		valueJson.put("text", comuni.getNomeComune());
	    		list.add(valueJson);
			}
			*/
    		/*
    		valueJson = new HashMap<String, Serializable>();
    		valueJson.put("value", "2");
    		valueJson.put("text", "ciao porco");
    		list.add(valueJson);
    		valueJson = new HashMap<String, Serializable>();
    		valueJson.put("value", "3");
    		valueJson.put("text", "ciao bello");
    		list.add(valueJson);
    		*/
    		ObjectMapper mapper = new ObjectMapper();

    	} catch(Exception e) {
            e.printStackTrace();
        } finally {
    	    response.setContentType("application/json");
    	    response.setCharacterEncoding(Constants.ENCODING_UTF_8);
    	    response.getWriter().write( json );
		}
    }
	

	

}