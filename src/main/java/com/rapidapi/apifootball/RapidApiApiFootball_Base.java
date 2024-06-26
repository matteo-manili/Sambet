package com.rapidapi.apifootball;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
 
public class RapidApiApiFootball_Base {

	public static ApplicationContext contextDao = new ClassPathXmlApplicationContext("App-Database-Spring-Module-Web.xml");
	
	

}
