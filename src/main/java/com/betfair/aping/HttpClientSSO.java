package com.betfair.aping;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.StrictHostnameVerifier;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.betfair.aping.exceptions.APINGException;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
 
 
@SuppressWarnings("deprecation")
public class HttpClientSSO {
	private static final Log log = LogFactory.getLog(HttpClientSSO.class);
	
	private static int port = 443;
	private static final String appKeyDelay = "RcauENGWI5OXADRi"; private static final String appKeyLive = "FSPuwHMob40oOP35";
    private static final String parthCertificatoP12 = "keys/client-2048.p12"; private static final String passwordCertificatoP12 = "sambet555+";
    private static final String account_email_betfair_it = "saracaiazzo89@gmail.com"; private static final String account_passw_betfair_it = "sambet555+";
    
    private static final String INVALID_SESSION_INFORMATION = "INVALID_SESSION_INFORMATION";
    private static final String INVALID_SESSION_TOKEN = "INVALID_SESSION_TOKEN";
    
     
    
    // username: saracaiazzo89 Email: saracaiazzo89@gmail.com Passw: sambet555+  // Nome: Sara Caiazzo  // organizzazione: saracaiazzo89.com
    // VRWGR8YwNf+X37rCrDSeN6wvKOT3N4kACB2mO9j+Mzw=
    
   
    public static void AvviaApiBetFair(ServletContext servletContext) throws FileNotFoundException, Exception {
    	try {
    		ApiNGJsonRpc.start(appKeyDelay, HttpClientSSOUtil.GetSessionToken_DB());
    		
    	}catch(APINGException exc_1) {
    		log.info(exc_1.toString());
    		if (exc_1.getErrorCode().equals(INVALID_SESSION_INFORMATION) || exc_1.getErrorCode().equals(INVALID_SESSION_TOKEN)) {
    			try {
					ApiNGJsonRpc.start(appKeyDelay, GetSessionToken(servletContext));
				} catch (APINGException exc_2) {
					log.info(exc_2.toString());
				}
    		}
    	}
    	
    }
    
    public static void AvviaApiBetFairSearchInternatCompet(ServletContext servletContext) throws FileNotFoundException, Exception {
    	try {
    		ApiNGJsonRpcSearchInternatCompet.start(appKeyDelay, HttpClientSSOUtil.GetSessionToken_DB());
    		
    	}catch(APINGException exc_1) {
    		log.info(exc_1.toString());
    		if (exc_1.getErrorCode().equals(INVALID_SESSION_INFORMATION) || exc_1.getErrorCode().equals(INVALID_SESSION_TOKEN)) {
    			try {
    				ApiNGJsonRpcSearchInternatCompet.start(appKeyDelay, GetSessionToken(servletContext));
    			} catch (APINGException exc_2) {
					log.info(exc_2.toString());
				}
    		}
    	}
    }


    
    
    private static String GetSessionToken(ServletContext servletContext) throws FileNotFoundException, Exception {
    	DefaultHttpClient httpClient = new DefaultHttpClient();
        try {
            SSLContext ctx = SSLContext.getInstance("TLS");
            KeyManager[] keyManagers = null;
            try {
            	//String bb = Thread.currentThread().getContextClassLoader().getResource("keys/client-2048.p12").getPath();
            	String aaa = HttpClientSSO.class.getClassLoader().getResource( parthCertificatoP12 ).getPath();
            	//log.info("aaa: "+aaa);
            	keyManagers = getKeyManagers("PKCS12", new FileInputStream(new File(aaa)), passwordCertificatoP12);
            	
            }catch( IOException ioe ) {
            	ioe.printStackTrace();
            	log.info(":::::::::::::::::::::::::::::: LOCALE :::::::::::::::::::::::::::::::::::::::");
            	keyManagers = getKeyManagers("pkcs12", new FileInputStream(new File("C:\\sslcertificato-betfair\\client-2048.p12")), passwordCertificatoP12);
            }

            ctx.init(keyManagers, null, new SecureRandom());
            SSLSocketFactory factory = new SSLSocketFactory(ctx, new StrictHostnameVerifier());
            ClientConnectionManager manager = httpClient.getConnectionManager();
            manager.getSchemeRegistry().register(new Scheme("https", port, factory));
            HttpPost httpPost = new HttpPost("https://identitysso-cert.betfair.it/api/certlogin");
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            nvps.add(new BasicNameValuePair("username", account_email_betfair_it));
            nvps.add(new BasicNameValuePair("password", account_passw_betfair_it));
            httpPost.setEntity(new UrlEncodedFormEntity(nvps));
            httpPost.setHeader("X-Application",appKeyDelay);
 
            //log.info("executing request" + httpPost.getRequestLine());
 
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
 
            //log.info("----------------------------------------");
            //log.info(response.getStatusLine());
            if (entity != null) {
                String responseString = EntityUtils.toString(entity);
                try {
                	//log.info("responseString "+ responseString);
                    JSONObject json = new JSONObject(responseString);
                    log.info("json sessionToken: "+json.get("sessionToken"));
                    
                    HttpClientSSOUtil.AggiornaSessionToken_DB( (String)json.get("sessionToken") );
                    
                    return (String) json.get("sessionToken");
                    
                }catch(JSONException jExc) {
                	return "";
                }
            }
        } finally {
            httpClient.getConnectionManager().shutdown();
        }
        return null;
    }
    
 
    private static KeyManager[] getKeyManagers(String keyStoreType, InputStream keyStoreFile, String keyStorePassword) throws Exception {
        KeyStore keyStore = KeyStore.getInstance(keyStoreType);
        keyStore.load(keyStoreFile, keyStorePassword.toCharArray());
        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        kmf.init(keyStore, keyStorePassword.toCharArray());
        return kmf.getKeyManagers();
    }
}
