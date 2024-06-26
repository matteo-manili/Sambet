package com.rapidapi.apifootball;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.ServletContext;

/**
 * documentazione api: https://rapidapi.com/api-sports/api/api-football
 * dashboard api: https://dashboard.api-football.com/soccer/requests
 * 
 * @author Matteo
 *
 */
public class RapidApiApiFootball_Engine extends RapidApiApiFootball_Base {
	private static final Log log = LogFactory.getLog(RapidApiApiFootball_Engine.class);

	private static final String x_rapidapi_host_1 = "api-football-v1.p.rapidapi.com"; // rapidapi.com/api-sports/api/api-football
	private static final String x_rapidapi_key_1 = "9bc7ad9d86mshe7651f6a5e6045ap1620b4jsndf527d14a8b8"; // rapidapi.com/api-sports/api/api-football
	private static final String Url_Main_1 = "https://rapidapi.p.rapidapi.com/v2/"; // rapidapi.com/api-sports/api/api-football

	private static final String x_rapidapi_host_2 = "v2.api-football.com"; // api-football.com
	private static final String x_rapidapi_key_2 = "dab8c8343154582e03b8fcffb6bc269a"; // api-football.com
	private static final String Url_Main_2 = "https://v2.api-football.com/"; // api-football.com

	private static final String TimeZone = "Europe%2FRome";

	public static final String Url_status = "status";
	protected static final String Url_bookmakers = "odds/bookmakers";
	protected static final String Url_leagues_current = "leagues/current";

	// public static final String Url_odds_fixture = "odds/fixture/608510";

	
	
	// get("https://api-football-v1.p.rapidapi.com/v2/statistics/{league_id}/{team_id}/{date}");
	/**
	 * il valore successivo al "next/" indica il numero di risultati massimo che è
	 * 100, 40 è sufficiente per coprire i prossimi eventi di BetFair
	 * fixtures/league/2857/next/40?timezone=Europe%2FRome
	 */
	public static String Get_Url_Statistics(Integer league_id, Integer team_id) {

		//String url = "statistics/2857/492";
		String url = "statistics/"+league_id+"/"+team_id;

		return url;
	}
	
	
	/**
	 * il valore successivo al "next/" indica il numero di risultati massimo che è
	 * 100, 40 è sufficiente per coprire i prossimi eventi di BetFair
	 * fixtures/league/2857/next/40?timezone=Europe%2FRome
	 */
	protected static String Get_Url_Fixtures_Legue_Next_Timezone(int idLeague) {
		// public static final String Url_fixtures_legue_next_timezone =
		// "fixtures/league/2857/next/30?timezone=Europe%2FRome";
		String url = "fixtures/league/" + idLeague + "/next/40?timezone=" + TimeZone;
		// log.info("Get_Url_Fixtures_Legue_Next_Timezone: "+url);
		return url;
	}

	/**
	 * get("https://api-football-v1.p.rapidapi.com/v2/
	 * odds/date/{yyyy-mm-dd}?page=2?timezone=europe/london");
	 * https://rapidapi.p.rapidapi.com/v2/ odds/date/2020-11-03?page=1
	 */
	protected static String Get_Url_Odds_Date_Timezone(String date, int page) {
		String url = "odds/date/" + date + "?page=" + page + "&timezone=" + TimeZone;
		// log.info("Get_Url_Odds_Date_Timezone: "+url);
		return url;
	}

	/**
	 * .url("https://rapidapi.p.rapidapi.com/v2/odds/fixture/605146")
	 */
	protected static String Get_Url_Odds_fixture_Timezone(int idFixture) {
		String url = "odds/fixture/" + idFixture + "?timezone=" + TimeZone;
		// log.info("Get_Url_Odds_fixture_Timezone: "+url);
		return url;
	}

	/**
	 * .url("https://rapidapi.p.rapidapi.com/v2/odds/league/1383?page=2")
	 * get("https://api-football-v1.p.rapidapi.com/v2/odds/league/{league_id}/label/{label_id}");
	 */
	protected static String Get_Url_Odds_legue_Timezone(int idLeague, int page) {
		String url = "odds/league/" + idLeague + "?page=" + page + "&timezone=" + TimeZone;
		// log.info("Get_Url_Odds_legue_Timezone: "+url);
		return url;
	}

	/**
	 * .url("https://rapidapi.p.rapidapi.com/v2/odds/mapping?page=2")
	 */
	protected static String Get_Url_Odds_mapping(int page) {
		String url = "odds/mapping?page=" + page;
		// log.info("Get_Url_Odds_mapping: "+url);
		return url;
	}

	

	public static String GetRequest(ServletContext servletContext, final String Url) {
		try {
			OkHttpClient client = getUnsafeOkHttpClient();
			client.setReadTimeout(2, TimeUnit.MINUTES); // socket timeout
			Request request = new Request.Builder()
					// .url("https://rapidapi.p.rapidapi.com/v2/timezone")
					.url(Url_Main_2 + Url).get().addHeader("x-rapidapi-host", x_rapidapi_host_2)
					.addHeader("x-rapidapi-key", x_rapidapi_key_2).build();
			Response response = client.newCall(request).execute();
			String result = response.body().string();
			//log.info("result: " + result);
			return result;

		} catch (Exception exc) {
			exc.printStackTrace();
			return null;
		}
	}
	
	
	// AIzaSyARDB_NFxfFcQ7DIjJgMAIJJTc4Wgv8Wh0
	// <script async src="https://cse.google.com/cse.js?cx=0cdc081daa3847122"></script>
	
	// https://programmablesearchengine.google.com/cse/all
	// https://developers.google.com/custom-search/docs/overview
	// https://developers.google.com/custom-search/v1/using_rest
	
	public static String GetRequest_GoogleSearchEngine(ServletContext servletContext, final String Url) {
		try {
			
			// https://programmablesearchengine.google.com/
			
			OkHttpClient client = getUnsafeOkHttpClient();
			client.setReadTimeout(2, TimeUnit.MINUTES); // socket timeout
			Request request = new Request.Builder()
					// .url("https://rapidapi.p.rapidapi.com/v2/timezone")
					.url("https://www.googleapis.com/customsearch/v1?key=AIzaSyARDB_NFxfFcQ7DIjJgMAIJJTc4Wgv8Wh0&cx=6cae866b87cb60520&lr=it&q=" + Url).get()
					//.addHeader("x-rapidapi-host", x_rapidapi_host_2)
					//.addHeader("x-rapidapi-key", x_rapidapi_key_2)
					.build();
			Response response = client.newCall(request).execute();
			String result = response.body().string();
			//log.info("result: " + result);
			return result;

		} catch (Exception exc) {
			exc.printStackTrace();
			return null;
		}
	}
	
	
	/**
	 * preso da https://gist.github.com/chalup/8706740
	 * 
	 * Get OkHttpClient which ignores all SSL errors.
	 * 
	 * per non fare scattare l'eccezione:
	 * javax.net.ssl.SSLHandshakeException: sun.security.validator.ValidatorException: PKIX path building failed: sun.security.provider.certpath.SunCertPathBuilderException: 
	 * unable to find valid certification path to requested target
	 */
	private static OkHttpClient getUnsafeOkHttpClient() {
		try {
			// Create a trust manager that does not validate certificate chains
			final TrustManager[] trustAllCerts = new TrustManager[] { 
					new X509TrustManager() {
				@Override
				public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType)
						throws CertificateException {
				}

				@Override
				public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType)
						throws CertificateException {
				}

				@Override
				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					return null;
				}
			} };

			// Install the all-trusting trust manager
			final SSLContext sslContext = SSLContext.getInstance("SSL");
			sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
			// Create an ssl socket factory with our all-trusting manager
			final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

			OkHttpClient okHttpClient = new OkHttpClient();
			okHttpClient.setSslSocketFactory(sslSocketFactory);
			okHttpClient.setHostnameVerifier(new HostnameVerifier() {
				@Override
				public boolean verify(String hostname, SSLSession session) {
					return true;
				}
			});

			return okHttpClient;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
