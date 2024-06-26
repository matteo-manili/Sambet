package com.rapidapi.apifootball;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.dao.DataIntegrityViolationException;

import com.sambet.dao.RafBookmakersDao;
import com.sambet.model.RafBookmakers;

public class RapidApiApiFootball_Bookmakers extends RapidApiApiFootball_Engine {
	private static final Log log = LogFactory.getLog(RapidApiApiFootball_Bookmakers.class);
		
	public static RafBookmakersDao rafBookmakersDao = (RafBookmakersDao) contextDao.getBean("RafBookmakersDao");

    public static void GetBookmakers() {
        try {
        	String result = GetRequest(null, Url_bookmakers);
        	JSONObject jsonObject = new JSONObject( result );
        	JSONObject api = jsonObject.getJSONObject("api");
        	//Integer results = api.getInt("results");
        	JSONArray bookmakersJsonList = api.getJSONArray("bookmakers");
        	for(int i = 0; i < bookmakersJsonList.length(); i++) {
        		JSONObject object = bookmakersJsonList.getJSONObject(i);
        		try {
	        		RafBookmakers rafBookmakers = rafBookmakersDao.get( new Long(object.getInt("id")) );
					if(rafBookmakers == null) {
						rafBookmakers = new RafBookmakers(object.getInt("id"), object.getString("name"), true);
						rafBookmakers = rafBookmakersDao.saveRafBookmakers(rafBookmakers);
						//log.info("rafBookmakers id:"+rafBookmakers.getId()+" name:"+rafBookmakers.getName());
					}
        		}catch(final DataIntegrityViolationException dataIntegrViolException) {
					//log.info(dataIntegrViolException.getMessage());
    			}
        	}

        }catch( JSONException jsonExc ) {
//    		log.info("JSONException: "+jsonExc.getMessage());
		}catch( Exception exc ) {
        	exc.printStackTrace();
        }

    }
 

}
