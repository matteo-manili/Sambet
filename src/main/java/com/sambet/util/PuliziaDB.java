package com.sambet.util;

import com.sambet.dao.EventiDao;
import com.sambet.webapp.util.ApplicationUtils;

public class PuliziaDB extends ApplicationUtils {

	public static EventiDao eventiDao = (EventiDao) contextDao.getBean("EventiDao");
	
    public static void puliziaDatabase_Market_Eventi_Campionati() {
    	eventiDao.PuliziaDatabase_Market_Eventi_Campionati();
    }
	
}
