package com.rapidapi.apifootball.util;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.rapidapi.apifootball.RapidApiApiFootball_Base;
import com.sambet.dao.RafCompetizioniDao;
import com.sambet.model.Eventi;
import com.sambet.model.RafEventi;
import info.debatty.java.stringsimilarity.Cosine;
 
public class RapidApiApiFootball_Util extends RapidApiApiFootball_Base {
	private static final Log log = LogFactory.getLog(RapidApiApiFootball_Util.class);
	
	public static RafCompetizioniDao rafCompetizioniDao = (RafCompetizioniDao) contextDao.getBean("RafCompetizioniDao");
	public static final double punteggioSimilarityAlto = 0.9d;
	public static final double punteggioSimilarityMedio = 0.5d;
	public static final double punteggioSimilarityBasso = 0.3d;
    
	
    public static boolean ConfrontoEventi(RafEventi ite_eventi_raf, Eventi ite_eventi_bf, double punteggioSimilarity) {
		if(EventiSameDay(ite_eventi_raf, ite_eventi_bf) 
				&& Punteggio_Somiglianza_Stringhe(ite_eventi_raf.getHomeTeam()+" - "+ite_eventi_raf.getAwayTeam(), ite_eventi_bf.getNome()) > punteggioSimilarity ) {
			return true;
		}else {
			return false;
		}
    }
    
    
    public static boolean EventiSameDay(RafEventi ite_eventi_raf, Eventi ite_eventi_bf) {
    	Calendar cal1 = Calendar.getInstance(); Calendar cal2 = Calendar.getInstance();
		cal1.setTime(new Date(ite_eventi_raf.getEvent_timestamp()*1000L)); cal2.setTime(ite_eventi_bf.getDataEvento());
		boolean sameDay = cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR) && cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
		return sameDay;
    }
    
    
    public static double Punteggio_Somiglianza_Stringhe(String a1, String b1) {
		Cosine cosine1 = new Cosine(2);
		Map<String, Integer> profile1 = cosine1.getProfile(a1); Map<String, Integer> profile2 = cosine1.getProfile(b1);
		double result = cosine1.similarity(profile1, profile2);
		//log.info("sim: "+result+" | "+a1+" | "+b1);
		return result;
    }
 

}
