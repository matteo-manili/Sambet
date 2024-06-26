package com.sambet;

import static java.lang.System.out;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TimeZone;
import java.util.TreeMap;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.imageio.ImageIO;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.servlet.http.HttpServletRequest;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.ws.rs.core.MediaType;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.math3.distribution.PoissonDistribution;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.velocity.app.VelocityEngine;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.search.query.dsl.RangeMatchingContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
//import org.openqa.selenium.remote.DesiredCapabilities;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import com.sambet.dao.GestioneApplicazioneDao;
import com.sambet.dao.RafEventiDao;
import com.sambet.dao.CompetizioniDao;
import com.sambet.dao.EventiDao;
import com.sambet.dao.UserDao;
import com.sambet.model.Nazioni;
import com.sambet.model.RafEventi;
import com.sambet.model.TipoRuoli;
import com.sambet.service.MailEngine;
import com.sambet.service.UserManager;
import com.sambet.util.CreaFriendlyUrl_Slugify;
import com.sambet.util.DammiTempoOperazione;
import com.sambet.util.DateUtil;
import com.sambet.util.Telefono_Prefisso_e_Formato;
import com.sambet.util.Mailin;
import com.sambet.util.NumberUtil;
import com.sambet.util.PropertiesFileUtil;
import com.sambet.util.UrlConnection;
import com.sambet.util.UtilBukowski;
import com.sambet.util.UtilString;
import com.sambet.webapp.controller.home.CaricaMainTable;
import com.sambet.webapp.util.ApplicationUtils;
import com.sambet.webapp.util.ControllerUtil;
import com.sambet.webapp.util.Sitemap;
import com.sambet.webapp.util.schedulerQuartz.*;
import com.sambet.webapp.util.ApplicationUtils.ApplicationMessagesUtil;
import com.sambet.webapp.util.bean.AutistaTerritorio;
import com.sambet.webapp.util.bean.InfoPaymentProvider;
import com.sambet.webapp.util.controller.gestioneApplicazione.GestioneApplicazioneUtil;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.betfair.aping.HttpClientSSO;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import com.rapidapi.apifootball.RapidApiApiFootball_Engine;
import com.rapidapi.apifootball.RapidApiApiFootball_Bookmakers;
import com.rapidapi.apifootball.RapidApiApiFootball_CompetitionEvents;
import com.rapidapi.apifootball.RapidApiApiFootball_Odds;
import com.rapidapi.apifootball.RapidApiApiFootball_Statistics;

import antlr.Version;
import info.debatty.java.stringsimilarity.Cosine;
import info.debatty.java.stringsimilarity.JaroWinkler;
import info.debatty.java.stringsimilarity.Levenshtein;
import info.debatty.java.stringsimilarity.LongestCommonSubsequence;
import info.debatty.java.stringsimilarity.QGram;
import info.debatty.java.stringsimilarity.RatcliffObershelp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.RequestContext;
import java.io.File;
import java.io.IOException;
import java.io.IOException;
import org.json.JSONObject;

/**
 * 
 * 
 * @author Matteo - matteo.manili@gmail.com
 * 
 *         La classe è schedulata per avviarsi ogni 4 ore dal momento dello
 *         start dell'applicaton server. Raccoglie gli annunci dal sito
 *         www.ilcercapadrone.it e li memorizza.
 *
 */
public class SAMBET_LABORATORIO extends ApplicationUtils implements Runnable {
	private final transient static Log log = LogFactory.getLog(SAMBET_LABORATORIO.class);
	public static UserDao userDao = (UserDao) contextDao.getBean("userDao");
	public static GestioneApplicazioneDao gestioneApplicazioneDao = (GestioneApplicazioneDao) contextDao
			.getBean("GestioneApplicazioneDao");

	public static EventiDao eventiDao = (EventiDao) contextDao.getBean("EventiDao");
	public static CompetizioniDao competizioniDao = (CompetizioniDao) contextDao.getBean("CompetizioniDao");

	public static RafEventiDao rafEventiDao = (RafEventiDao) contextDao.getBean("RafEventiDao");

	public static int CalcoloFattoriale(int x) {
	    int i; int f=1;
	    for(i=1; i<=x; i=i+1) {
	    	f=f*i;
	    }
	    return f;
	}
	
	
	public static void main(String[] args) {
		GO_GO_GO();
	}

	@SuppressWarnings("unchecked")
	public static void GO_GO_GO() {
		long startTime = System.nanoTime(); 
		try {
			
			
			//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
			//@@@@@@@@@@@@ DA QUI PARTE IL PROCESSO IN BACKGROUND @@@@@@@@@@@@
			//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
			
			
			eventiDao.PuliziaDatabase_Market_Eventi_Campionati();
			//HttpClientSSO.AvviaApiBetFairSearchInternatCompet(null);
			//HttpClientSSO.AvviaApiBetFair(null);


			//RapidApiApiFootball_Engine.GetRequest(null, RapidApiApiFootball_Engine.Url_status);
			//RapidApiApiFootball_CompetitionEvents.GetCompetizioniEventi_Associa_BfEventi(); // 6 volte al giorno
			//RapidApiApiFootball_Statistics.GetCompetizioniEventi_Statistics(); // 1 volta al giorno
			//RapidApiApiFootball_Odds.GetOddsfrom_idFixture();
			
			
			//@@@@@@@@@@@@@@@@@@@@@@@@@
			//handheldfriendly
			//RapidApiApiFootball_Engine.GetRequest_GoogleSearchEngine(null, "orologio");
			
			
			//@@@@@@@@@@@@@@@@@@@@@@@@
			
			
			String meseAnno = "01/2020";
			String giorno = "09/01/2020";

			/*
			 * Scrivete un programma che stampi i numeri da 1 a 100. Ma per multipli di tre stampe "Fizz" al posto del numero e per multipli di cinque stampe "Buzz". 
			 * per i numeri che sono multipli sia di tre che di cinque stampa "FrizzBuzz".
			 */

			/*
			for(int i = 1; i <= 100; i++) {
				
				if (i % 3 == 0 && i % 5 != 0) {
					System.out.println("Fizz");
				}else {
					if (i % 5 == 0 && i % 3 != 0 ) {
						System.out.println("Buzz");
					}else {
						if (i % 5 == 0 && i % 3 == 0 ) {
							System.out.println("FrizzBuzz");
						}else {
							System.out.println( i );
						}
					}
				}
				
			}
			*/
			
			/*
			 * Implementa una classe che, dati due array, troverà la posizione iniziale del secondo array nel primo array, cioè (2,3,4,5) e (4,5) dovrebbero restituire 2. 
			 * Spiega la tua soluzione e scrivi alcuni test per questo.
			 */
			/*
			int[] array_1 = new int[] {2,3,4,5};
			int[] array_2 = new int[] {12,5};
			

			int esito = -1;
			for(int i_1 = 0; i_1 < array_1.length; i_1++) {
				
				//System.out.println( array_1[i_1] );
				
				if( array_1[i_1] == array_2[0] ) {
					
					esito = i_1;
					
				}
			}
			
			System.out.println(esito);
			*/
			
			
			
			
			//DesiredCapabilities options = new DesiredCapabilities();
		    // the website i am scraping uses ssl, but I dont know what version
			
			//options.setJavascriptEnabled(true);
			//options.setCapability("takesScreenshot", true);  
			/*
		    options.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, new String[] {
		          "--ssl-protocol=any"
		      });
		    */
			
			
		    //String parthCertificatoP12_win = "/src/main/resources/phantom/phantomjs.exe";
		    //String parthCertificatoP12_win = "phantomjs.exe"; String parthCertificatoP12_linux = "phantomjs";
		    //String aaa = SAMBET_LABORATORIO_2.class.getClassLoader().getResource( "phantom/"+ parthCertificatoP12_win ).getPath();
		    //System.out.println(aaa);
		    //options.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, parthCertificatoP12_win);
		    
		    //options.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, "C:/phantomjs.exe");
		    
		    
		    
		    //System.out.println("111111111111111  sasasaas");
		    
		    //PhantomJSDriver driver = new PhantomJSDriver(options);
		    
		    //System.out.println("2222222222222222");

		    //driver.get("https://www.google.com/");
		    //driver.get("https://planetbet92.me/betting");

		    //System.out.println("333333333333333 google.com");
		    
		    //List<WebElement> elements = driver.findElementsByClassName("media-title");

		    /*
		    for(WebElement element : elements ){
		        System.out.println(element.getText());
		    }

		    driver.quit();
			*/
			
			
			
			
// -- Riga Pronostrici "Songole"
// VISUALIZZAZIONE RIGA 1
// SOTTO COLONNA Bookmkert scriver "Drop Odds", i valori NULL mettere "casella vuota"

		/*
		Considerare i valori: "MEDIA BOOK apertura" A e "Betfari exc. attuale" B
		se (A >= B) il pronostico avarà valore  
		1.86 >= 2.28 = se è false fare visualizzare "NULL" al contrario invece il SEGNO (1,x,2,over2.5, ecc ecc)
		se uno dei valori solo null lasciamo vuota la casella.
		 */
			
			
			
// Riga Pronostici "Multiple"
// VISUALIZZAZIONE RIGA 2 
// SOTTO COLONNA Bookmkert scriver "pronostici per Multiple", i valori NULL mettere "casella vuota"

/*




#### doble costante_limite_quota = 2; 
#### doble costante_over_under_gol_nogol = 1.8; 


---------- PRonostico segno 1
Considerare "quota sambet segno 1 (A)", "quota sambet segno 2 (B)", "quota betffair apertura segno 1 (C) media segno 1 (D) e attuale segno 1 (E) ", "MEDIA BOOK apertua segno 1 (F) e attuale segno 1 (G)"

(A == NULL || B == NULL || A < B) 
&& D <= C 
&& E <= D 
&& G <= F + 0.03
&& E <= costante_limite_quota  

se vero: il segno sara 1 altrienti NULL










---------- PRonostico segno X
 "quota betffair apertura segno X (C) media segno X (D) e attuale segno X (E) ", "MEDIA BOOK apertua segno X (F) e attuale segno X (G)", 
	"MEDIA BOOK apertua segno 1 (H) e attuale segno 1 (I)", "MEDIA BOOK apertua segno 2 (L) e attuale segno 2 (M)"

G < F && F < E && E < D && D <= C && M > L && I > H 

se vero: il segno sara X altrienti NULL
 
---------- PRonostico segno 2
Considerare "quota sambet segno 1 (A)", "quota sambet segno 2 (B)", "quota betffair apertura segno 2 (C) media segno 2 (D) e attuale segno 2 (E) ", "MEDIA BOOK apertua segno 2 (F) e attuale segno 2 (G)"

(A == NULL || B == NULL || B < A)
&& E <= D 
&& D <= C 
&& E <= costante_limite_quota 
&& G <= F 
&& G <= E 

se vero il segno sarà 2 altrimenti NULL






---------- PRonostico segno OVER 2,5
"quota betffair apertura segno OVER (C) media segno OVER (D) e attuale segno OVER (E) ", "MEDIA BOOK apertua segno OVER (F) e attuale segno OVER (G)"

G <= costante_over_under_gol_nogol && G <= F && {F <= E} && E < D && D <= C 

se vero il segno sarà OVER altrimenti NULL


---------- PRonostico segno UNDER 2,5
"quota betffair apertura segno UNDER (C) media segno UNDER (D) e attuale segno UNDER (E) ", "MEDIA BOOK apertua segno UNDER (F) e attuale segno UNDER (G)"

G <= costante_over_under_gol_nogol && G <= {F <= E} && E < D && D <= C 

se vero il segno sarà UNDER altrimenti NULL

---------- PRonostico segno GOL
"quota betffair apertura segno GOL (C) media segno GOL (D) e attuale segno GOL (E) ", "MEDIA BOOK apertua segno GOL (F) e attuale segno GOL (G)"

G <= costante_over_under_gol_nogol && G <= F && {F <= E} && E < D && D <= C 

se vero il segno sarà GOL altrimenti NULL





---------- PRonostico segno NO_GOL
"quota betffair apertura segno NO_GOL (C) media segno NO_GOL (D) e attuale segno NO_GOL (E) ", "MEDIA BOOK apertua segno NO_GOL (F) e attuale segno NO_GOL (G)"

G <= costante_over_under_gol_nogol && G <= F && {F <= E} && E < D && D <= C 

se vero il segno sarà NO_GOL altrimenti NULL

 */
			
			
			
			

			//-------------------------------------------------------------------------
			//-------------------------------------------------------------------------
			
			//String myString = "Av\u00e2ntul Reghin";
			//String text = StringEscapeUtils.unescapeJava(myString);
			//System.out.println( text );
			// Text will now have Hello
			
			
			
			/*
			
			// -------------- ISTRUZIONI PROGETTO -------------------- 
			
			// per leggere il json della api /statistics/{league_id}/{team_id} prendere i valori:
			
			//per la squadra home: stats --> matchs --> matchsPlayed --> home : "squadra_Home_NumeroPartGiocate"
			//per la squadra home: stats --> goals --> goals for --> home : squadra_Home_GolFatti
			//per la squadra home: stats --> goals --> goals agains -> home : squadra_Home_GolSubiti
			
			// per la squadra Away è uguale ma prender eil valore "away"
			
			// per verificare l'esattezza dei dati confrontarli con diretta.it -->
			
			
			final double COSTANTE_1 = 2.718;
			
			double squadra_Home_GolFatti = 12;
			double squadra_Home_GolSubiti = 5;
			double squadra_Home_NumeroPartGiocate = 5;
							
			double squadra_Away_GolFatti = 3; 
			double squadra_Away_GolSubiti = 13;
			double squadra_Away_NumeroPartGiocate = 5;
			
			double M_HOME = ((squadra_Home_GolFatti / squadra_Home_NumeroPartGiocate) + (squadra_Away_GolSubiti / squadra_Away_NumeroPartGiocate)) / 2;
			
			double M_AWAY = ((squadra_Home_GolSubiti / squadra_Home_NumeroPartGiocate) + (squadra_Away_GolFatti / squadra_Away_NumeroPartGiocate)) / 2;
			
			System.out.println("media_A: "+M_HOME);
			
			int exponent = 8;
			
			double base = M_HOME;
			
	        double D_HOME = Math.pow(base, exponent);
	        System.out.println("potenza = " + D_HOME);
			
	        
	        double VAL_HOME_D = Math.pow(COSTANTE_1, -M_HOME);
			
	        double VAL_HOME_E = VAL_HOME_D * D_HOME;
			
	        System.out.println("VAL_HOME_E: "+VAL_HOME_E);
			
	        
	        double VAL_HOME_F = VAL_HOME_E / CalcoloFattoriale(exponent) ;

	        System.out.println("VAL_HOME_F: "+VAL_HOME_F);
	        
	        // TODO ripetere tutte le operazione da 1 a 10 gli esponenti "exponent" per ambedue le squaddre quindi totale 20 risultati
	        
	        // TODO confrontare tutti valori di una squadra con l'altra (saranno un centinaio....) secondo la formula: =F1*F12*100
	        
	        // sommo tutti i valori di X _ 1 _ 2 per determinare il valore finale da visualizzare (per determinare la X sono 20 per 1 e 2 sono di più). e fare "100 / la somma"
	        
	        
	        // GOL E NO GOL
	        // ------------------- per dewterminare il valor GOL bisogna considere i risultato dove c'è almeno un gol tutte e due le squadre. e fare "100 / la somma"
	        
	        //------------------- per dewterminare il valorr NO GOL bisogna considere i risultato dove c'è almeno una squadra ha fatto 0 incluso il 0 a 0. e fare "100 / la somma"
	        
	        
	        // OVER E UNDER, 2,5
	        // l'over è la somma dei gol di ambedue le squadre che supera i 2,5 gol. e fare "100 / la somma"
	        
	        // l'under è la somma dei gol di ambedue le squadre che non superano i 2,5 gol. e fare "100 / la somma"
	        
	        
	        
	        // VISUALIZZAZIONE
	        // in visualizzazione fare vedere il risultato secondo la forumula "100/il risultato"
	        
			
			// mettere i dati sulla prima linea chiamando la colonna Bookmakers: "Quote sambet" e sotto la colonna Quote: "niente"
			
			// -------------- FINE ISTRUZIONI PROGETTO --------------------
			
			*/
			
			
			
			/*
			info.debatty.java.stringsimilarity.MetricLCS lcs = 
	                new info.debatty.java.stringsimilarity.MetricLCS();
			System.out.println(lcs.distance("HIFK Elsinki Rops", "HIFK - RoPS"));
	        System.out.println(lcs.distance("HIFK Elsinki Rops", "IFK Mariehamn - HJK Helsinki"));
	        System.out.println(lcs.distance("HIFK Elsinki Rops", "SJK 2 Jaro"));
	        System.out.println(lcs.distance("porco", "porca"));
	        System.out.println(lcs.distance("porco", "porco"));
	       	System.out.println("--------------------");
			
	       	RatcliffObershelp ro = new RatcliffObershelp();
	        System.out.println(ro.similarity("HIFK Elsinki Rops", "HIFK - RoPS"));
	        System.out.println(ro.similarity("HIFK Elsinki Rops", "IFK Mariehamn - HJK Helsinki"));
	        System.out.println(ro.similarity("HIFK Elsinki Rops", "SJK 2 Jaro"));
	        System.out.println(ro.similarity("porco", "porca"));
	        System.out.println(ro.similarity("porco", "porco"));
	        System.out.println("--------------------");
	       	
			JaroWinkler jw = new JaroWinkler();
			System.out.println(jw.similarity("HIFK Elsinki Rops", "HIFK - RoPS"));
	        System.out.println(jw.similarity("HIFK Elsinki Rops", "IFK Mariehamn - HJK Helsinki"));
	        System.out.println(jw.similarity("HIFK Elsinki Rops", "SJK 2 Jaro"));
	        System.out.println(jw.similarity("porco", "porca"));
	        System.out.println(jw.similarity("porco", "porco"));
			 
			
	        System.out.println("--------------------");
			String a1 = "HIFK Elsinki Rops"; String b1 = "HIFK - RoPS";
			Cosine cosine1 = new Cosine(2);
			Map<String, Integer> profile1 = cosine1.getProfile(a1); Map<String, Integer> profile2 = cosine1.getProfile(b1);
			System.out.println(cosine1.similarity(profile1, profile2));
			
			String a2 = "HIFK Elsinki Rops"; String b2 = "IFK Mariehamn - HJK Helsinki";
			Cosine cosine2 = new Cosine(2);
			Map<String, Integer> profile3 = cosine2.getProfile(a2); Map<String, Integer> profile4 = cosine2.getProfile(b2);
			System.out.println(cosine2.similarity(profile3, profile4));
			
			String a3 = "HIFK Elsinki Rops"; String b3 = "SJK 2 Jaro";
			Cosine cosine3 = new Cosine(2);
			Map<String, Integer> profile5 = cosine3.getProfile(a3); Map<String, Integer> profile6 = cosine3.getProfile(b3);
			System.out.println(cosine3.similarity(profile5, profile6));
			*/

			
			


			//eventiDao.Report_1(null);
			
			
			
			//PoissonDistribution aa = new PoissonDistribution(12.12, 12.12);
			//System.out.println("PoissonDistribution: "+aa.getSupportLowerBound());
			
			
			//RapidApiApiFootball_Odds.GetOddsfrom_Mapping();
			//RapidApiApiFootball_Odds.GetOddsfrom_Date();
			//RapidApiApiFootball_Odds.GetOddsfrom_idLeague();
			

			//System.out.println( rafEventiDao.getRafEventi_AssegnatiEventi().size() ) ;
			
			/*
			List<RafEventi> aaa = rafEventiDao.getRafEventi_ApiMapping(606802, 1904621123);
			for(RafEventi ite: aaa) {
				System.out.println(ite.getId() +" "+ite.getFixture_id());
			}
			*/
			

			
			
			
			//rafEventiDao.getRafEventi_from_siglaNazione_dataRafEvento_nonPresenteInEventi("GB", "2020-10-31");
			
			/*
			List<Long> listLongIdRafCompetizioni = new ArrayList<Long>(); List<Long> listLongIdRafEventi = new ArrayList<Long>(); 
			listLongIdRafCompetizioni.add(1171l); 
			listLongIdRafEventi.add(19786l);
			rafEventiDao.getRafEventi_EventiCampionato_NonAssegnati(listLongIdRafCompetizioni, listLongIdRafEventi);
			*/
			
			/*
			// http://192.168.5.176/cgi/WebCGI?1500101=account=apiuser&password=password&port=1&destination=15880270900&content=Hello+world!!
			OkHttpClient client = new OkHttpClient();
        	Request request = new Request.Builder()
        		//.url("https://rapidapi.p.rapidapi.com/v2/timezone")
        		// matteomanili.hopto.org
        			
        		.url("http://151.28.222.49/cgi/WebCGI?1000=account=apiuser&password=apipass&port=1&destination=+00393289126869&content=ciaobello")
        		.build();
        		//.url("http://matteomanili.hopto.org/cgi/WebCGI?1000=account=apiuser&password=apipass&port=5038&destination=00393289126869&content=ciaobello").get().build();
        	

        	Response response = client.newCall(request).execute();
        	String result = response.body().string();
        	log.info( "result: "+result );
			*/
			
			/*
			HttpUrl.Builder urlBuilder = HttpUrl.parse("http://matteomanili.hopto.org/cgi/WebCGI?1000").newBuilder();
			urlBuilder.addQueryParameter("account", "apiuser");
			urlBuilder.addQueryParameter("password", "apipass");
			urlBuilder.addQueryParameter("port", "5038");
			urlBuilder.addQueryParameter("destination", "00393289126869");
			urlBuilder.addQueryParameter("content", "ciaobello");

			urlBuilder.addQueryParameter("user", "vogella");
			String url = urlBuilder.build().toString();

			Request request = new Request.Builder().url(url).build();
			

			OkHttpClient client = new OkHttpClient();
			
			Response response = client.newCall(request).execute();
        	String result = response.body().string();
        	log.info( "result: "+result );
			*/
			
			
			
			DammiTempoOperazione.DammiSecondi(startTime, "FINE_LAB- ");
		}catch(Exception ex) {
			System.out.println("Exception SAMBET_LABORATORIO");
			ex.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

}
