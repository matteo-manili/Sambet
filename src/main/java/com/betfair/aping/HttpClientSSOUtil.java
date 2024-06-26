package com.betfair.aping;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sambet.model.GestioneApplicazione;
import com.sambet.webapp.util.ApplicationUtils;

/**
 * Hello world!
 *
 */
public class HttpClientSSOUtil extends ApplicationUtils {

	private static final Log log = LogFactory.getLog(HttpClientSSOUtil.class);
	private static final String BETFAIR_SESSION_TOKEN = "BETFAIR_SESSION_TOKEN";

	
	public static void AggiornaSessionToken_DB(String sessionToken) {
		try {
			log.info("AggiornaSessionToken_DB");
			GestioneApplicazione gestApp = gestioneApplicazioneDao.getName(BETFAIR_SESSION_TOKEN);
			gestApp.setValueString(sessionToken);
			gestioneApplicazioneDao.saveGestioneApplicazione(gestApp);
			gestioneApplicazioneDao.getName(BETFAIR_SESSION_TOKEN).getValueString(); 

		} catch (Exception exc) {
			log.info(exc.toString());
		}
	}
	
	
	public static String GetSessionToken_DB() {
		try {
			log.info("GetSessionToken_DB");
			String sessionToken = gestioneApplicazioneDao.getName(BETFAIR_SESSION_TOKEN).getValueString(); 
			return sessionToken;
			
		} catch (Exception exc) {
			log.info(exc.toString());
		}
		
		return null;
	}
	
	

}
