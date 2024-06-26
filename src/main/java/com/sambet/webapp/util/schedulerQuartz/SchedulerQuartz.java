package com.sambet.webapp.util.schedulerQuartz;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import com.betfair.aping.HttpClientSSO;
import com.rapidapi.apifootball.RapidApiApiFootball_CompetitionEvents;
import com.rapidapi.apifootball.RapidApiApiFootball_Odds;
import com.rapidapi.apifootball.RapidApiApiFootball_Statistics;
import com.sambet.util.PuliziaDB;
import com.sambet.webapp.util.ApplicationUtils;
import com.sambet.webapp.util.controller.gestioneApplicazione.GestioneApplicazioneUtil;

/**
 * @author Matteo - matteo.manili@gmail.com
 *
 *	*******************************************************************************************************************************
 *	IN MONDOSERVER L'APPLICAZIONE VIENE DEPLOYATA SUL TOMCAT DUE VOLTE (PER MOTIVI SISTEMISTICI) SU DUE PATH DIVERSI "" e "/html"
 *	QUINDI IL quartz.Scheduler O LO SpringScheduling SI AVVIA DUE VOLTE. BISOGNA FARNE PARTIRE SOLO UNO !!!
 *	*******************************************************************************************************************************
 *
 *	http://www.quartz-scheduler.org/documentation/quartz-2.2.x/quick-start.html
 *
 * .withSchedule(CronScheduleBuilder.cronSchedule("0 0 0,5,9,13,16,20 ? * 1-7"))
 * .withSchedule(CronScheduleBuilder.cronSchedule("0 0 * ? * 1-7"))
 *
 *	// 0 2 9 ? * 2-6 | funziona!
 *
 * mettere le virgole (,) per esempio nelle ore "0,5,9,13,16,20" significa che vengono considerate quelle ore
 *	1: 0-59 	secondi
 *	2: 0-59 	minuti
 *	3: 0-23 	ore
 *	4: 1-31 	numeri giorni del mese 
 *				[se valorizzato non valorizzare "giorno della settimana" uno dei due DEVE essere valorizzato] se non valorizzato mettere ?
 *	5: 1-12 	mese - per tutti i mesi mettere *
 *	6: 1-7 		giorno della settimana (inizia da domenica)
 *				[se valorizzato non valorizzare "numeri del mese" uno dei due DEVE essere valorizzato] se non valorizzato mettere ? 
 *				si può mettere sua numero così 1 (solo la domenica) e anche così 1-7 (dalla domenica al sabato) 
 *	7: 2020		anno, opzionale, non metterlo. 
 *
 */

//withSchedule(CronScheduleBuilder.cronSchedule("0 0 0,5,9,13,16,20 ? * 1-7"))


public class SchedulerQuartz extends ApplicationUtils implements Job {
	private static final Log log = LogFactory.getLog(SchedulerQuartz.class);
	
	private static ServletContext servletContext;
	//private static DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	private static final String JobMain = "JobMain"; public static final String GroupMain = "GroupMain";
	
	private static final String Trigger_Pulizia_CampionatiEventiMarkets = "Trigger_Pulizia_CampionatiEventiMarkets";
	private static final String Trigger_GetCompetizioniEventi_Associa_BfEventi = "Trigger_GetCompetizioniEventi_Associa_BfEventi";
	private static final String Trigger_GetOddsfrom_idFixture = "Trigger_GetOddsfrom_idFixture";
	
	private static final String Trigger_Statistics_QuoteSambet = "Trigger_Statistics_QuoteSambet";
	
	private static final String Trigger_2_ora = "Trigger_2_ora";
	private static final String Trigger_1_min = "Trigger_1_min"; 
	private static final String Trigger_30_sec = "Trigger_30_sec";
	
	/**
	 *	*******************************************************************************************************************************
	 *	IN MONDOSERVER L'APPLICAZIONE VIENE DEPLOYATA SUL TOMCAT DUE VOLTE (PER MOTIVI SISTEMISTICI) SU DUE PATH DIVERSI "" e "/html"
	 *	QUINDI IL quartz.Scheduler O LO SpringScheduling SI AVVIA DUE VOLTE. BISOGNA FARNE PARTIRE SOLO UNO !!!
	 *	*******************************************************************************************************************************
	 */
	public static Scheduler AvviaScdulatoriQuartz(ServletContext sx) {
		try {
			if(GestioneApplicazioneUtil.CheckContextPathCorretto(sx)) {
				servletContext = sx;
				
				JobKey jobKey = new JobKey(JobMain, GroupMain);
				JobDetail job = JobBuilder.newJob(SchedulerQuartz.class).withIdentity(jobKey).build();
				Set<Trigger> triggerList = new HashSet<Trigger>();
		
				// TRIGGERS
				Trigger trigger_1 = TriggerBuilder.newTrigger().withIdentity(Trigger_2_ora, GroupMain)
						.withSchedule(
						SimpleScheduleBuilder.simpleSchedule()
							.withIntervalInHours(2).repeatForever())
					.startAt(addSeconds(new Date(), 20)) // serve a far partire il tirgger TOT secondi dopo l'inizializzazione
					.build();
				triggerList.add(trigger_1);
				
				Trigger trigger_2 = TriggerBuilder.newTrigger().withIdentity(Trigger_1_min, GroupMain)
					.withSchedule(
						SimpleScheduleBuilder.simpleSchedule()
							.withIntervalInMinutes(1).repeatForever())
							//.withIntervalInSeconds(30).repeatForever())
					.startAt(addSeconds(new Date(), 20)) // serve a far partire il tirgger TOT secondi dopo l'inizializzazione
					.build();
				triggerList.add(trigger_2);
				
				Trigger trigger_3 = TriggerBuilder.newTrigger().withIdentity(Trigger_30_sec, GroupMain)
						.withSchedule(
							SimpleScheduleBuilder.simpleSchedule()
								.withIntervalInSeconds(30).repeatForever())
						.startAt(addSeconds(new Date(), 20)) // serve a far partire il tirgger TOT secondi dopo l'inizializzazione
						.build();
				triggerList.add(trigger_3);
				
				Trigger trigger_4 = TriggerBuilder.newTrigger().withIdentity(Trigger_Pulizia_CampionatiEventiMarkets, GroupMain)
					    .withSchedule(CronScheduleBuilder.cronSchedule("0 0 4 ? * 1-7"))
					    .startAt(addSeconds(new Date(), 20)) // serve a far partire il tirgger TOT secondi dopo l'inizializzazione
					    .build();
				triggerList.add(trigger_4);
				
				Trigger trigger_5 = TriggerBuilder.newTrigger().withIdentity(Trigger_Statistics_QuoteSambet, GroupMain)
					    .withSchedule(CronScheduleBuilder.cronSchedule("0 30 4 ? * 1-7"))
					    .startAt(addSeconds(new Date(), 20)) // serve a far partire il tirgger TOT secondi dopo l'inizializzazione
					    .build();
				triggerList.add(trigger_5);
				
				Trigger trigger_6 = TriggerBuilder.newTrigger().withIdentity(Trigger_GetCompetizioniEventi_Associa_BfEventi, GroupMain)
					    .withSchedule(CronScheduleBuilder.cronSchedule("0 0 0,5,9,13,16,20 ? * 1-7"))
					    .startAt(addSeconds(new Date(), 20)) // serve a far partire il tirgger TOT secondi dopo l'inizializzazione
					    .build();
				triggerList.add(trigger_6);
				
				Trigger trigger_7 = TriggerBuilder.newTrigger().withIdentity(Trigger_GetOddsfrom_idFixture, GroupMain)
						.withSchedule(CronScheduleBuilder.cronSchedule("0 9 * ? * 1-7"))
					    .startAt(addSeconds(new Date(), 20)) // serve a far partire il tirgger TOT secondi dopo l'inizializzazione
					    .build();
				triggerList.add(trigger_7);

				
				// FACCIO PARTIRE I TRIGGERS
				Map<JobDetail, Set<? extends Trigger>> triggersAndJobs = new HashMap<>();
				triggersAndJobs.put(job, triggerList );
				Scheduler scheduler = new StdSchedulerFactory().getScheduler();
				scheduler.start();
				scheduler.scheduleJobs(triggersAndJobs, false);
				
				return scheduler;
			}
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		return null;
	}
	
	private static Date addSeconds(Date date, Integer seconds) {
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    cal.add(Calendar.SECOND, seconds);
	    return cal.getTime();
	}
	
	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		try {
			/*
			log.info("Scheduler Quartz! "+dateFormat.format(new Date()) +" | TriggerName: "+jobExecutionContext.getTrigger().getKey().getName() 
					+" | ServletContextName(): "+servletContext.getServletContextName() );
			*/
			if( jobExecutionContext.getTrigger().getKey().getName().equals(Trigger_Pulizia_CampionatiEventiMarkets) ) { // tutti i gionrni alle ore 4.00
				if(CheckAmbienteProduzione_Static_ContextNameProduzione_AND_IpAddessProduzione(servletContext) ){
					log.info("faccio partire trigger: "+Trigger_Pulizia_CampionatiEventiMarkets +" inizio");
					PuliziaDB.puliziaDatabase_Market_Eventi_Campionati();
					log.info("faccio partire trigger: "+Trigger_Pulizia_CampionatiEventiMarkets+" fine");
				}
			}else if( jobExecutionContext.getTrigger().getKey().getName().equals(Trigger_GetCompetizioniEventi_Associa_BfEventi) ) {
				if(CheckAmbienteProduzione_Static_ContextNameProduzione_AND_IpAddessProduzione(servletContext) ){
					log.info("faccio partire trigger: "+Trigger_GetCompetizioniEventi_Associa_BfEventi);
					RapidApiApiFootball_CompetitionEvents.GetCompetizioniEventi_Associa_BfEventi();
				}
			}else if( jobExecutionContext.getTrigger().getKey().getName().equals(Trigger_GetOddsfrom_idFixture) ) {
				if(CheckAmbienteProduzione_Static_ContextNameProduzione_AND_IpAddessProduzione(servletContext) ){
//					log.info("faccio partire trigger: "+Trigger_GetOddsfrom_idFixture);
					RapidApiApiFootball_Odds.GetOddsfrom_idFixture();
				}
			}else if( jobExecutionContext.getTrigger().getKey().getName().equals(Trigger_Statistics_QuoteSambet) ) {
				if(CheckAmbienteProduzione_Static_ContextNameProduzione_AND_IpAddessProduzione(servletContext) ){
//					log.info("faccio partire trigger: "+Trigger_Statistics_QuoteSambet);
					RapidApiApiFootball_Statistics.GetCompetizioniEventi_Statistics();
				}
			}else if( jobExecutionContext.getTrigger().getKey().getName().equals(Trigger_2_ora) ) {
				if(CheckAmbienteProduzione_Static_ContextNameProduzione_AND_IpAddessProduzione(servletContext) ){
//					log.info("faccio partire trigger: "+Trigger_2_ora);
					HttpClientSSO.AvviaApiBetFairSearchInternatCompet(servletContext);
				}
			}else if(jobExecutionContext.getTrigger().getKey().getName().equals( Trigger_30_sec )) {
//				log.info("faccio partire trigger: "+Trigger_30_sec);
				
			}else if(jobExecutionContext.getTrigger().getKey().getName().equals( Trigger_1_min )) {
				if(CheckAmbienteProduzione_Static_ContextNameProduzione_AND_IpAddessProduzione(servletContext) ){
//					log.info("faccio partire trigger: "+Trigger_1_min);
					HttpClientSSO.AvviaApiBetFair( servletContext );
				}
			}
		}catch(Exception exc) {
			exc.printStackTrace();
		}

	}
	
	
	
	
	
	

	/*
	 * SVILUPPO:
	 * RISULTATO getHostName: wc1tomcat3.mondoserver.com
	RISULTATO getLocalHost: wc1tomcat3.mondoserver.com/192.168.100.103
	RISULTATO getHostAddress: 192.168.100.103
	 */
	/* PRODUZIONE:
	 * RISULTATO getHostName: wc1tomcat3.mondoserver.com
	RISULTATO getLocalHost: wc1tomcat3.mondoserver.com/192.168.100.103
	RISULTATO getLocalHost: wc1tomcat3.mondoserver.com/192.168.100.103
	 */
	
	
}
