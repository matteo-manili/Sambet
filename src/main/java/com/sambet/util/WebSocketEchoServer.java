package com.sambet.util;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.annotation.WebServlet;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session; 
import javax.websocket.server.ServerEndpoint;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.betfair.aping.ApiNGJsonRpc;
import com.sambet.dao.EventiDao;
  
/** 
 * In locale funziona ma in Produzione da errore "java.lang.UnsupportedOperationException: HTTP upgrade is not supported by the AJP protocol" 
 * perché c'è da installare il modulo per tomcat: mod_proxy_wstunnel in produzione.
 * vedere: https://serverfault.com/questions/878629/apache-cluster-tomcat-websocket

 * Nella pagina client copiare questo codice:
 * 
<form>
	<input id="message" type="text" value="message_test">
	<input onclick="wsSendMessage();" value="Echo" type="button">
	<input onclick="wsCloseConnection();" value="Disconnect" type="button">
</form>
<br>
<textarea id="echoText" rows="5" cols="30"></textarea>
<script type="text/javascript">
//var webSocket = new WebSocket("wss://localhost:8443/sambet/chiamatasocket");
var webSocket = new WebSocket("wss://www.sambet.it:443/chiamatasocket");
var echoText = document.getElementById("echoText");
echoText.value = "";
var message = document.getElementById("message");
webSocket.onopen = function(message){ wsOpen(message);};
webSocket.onmessage = function(message){ wsGetMessage(message);};
webSocket.onclose = function(message){ wsClose(message);};
webSocket.onerror = function(message){ wsError(message);};
function wsOpen(message){
	echoText.value += "Connected ... \n";
}
function wsSendMessage(){
	webSocket.send(message.value);
	echoText.value += "Message sended to the server : " + message.value + "\n";
	//message.value = "";
}
function wsCloseConnection(){
	webSocket.close();
}
function wsGetMessage(message){
	echoText.value += "Message received from to the server : " + message.data + "\n";
}
function wsClose(message){
	echoText.value += "Disconnect ... \n";
}
function wserror(message){
	echoText.value += "Error ... \n";
}
</script>

 */
@ServerEndpoint("/app/chiamatasocket")
public class WebSocketEchoServer {
	private static final Log log = LogFactory.getLog(WebSocketEchoServer.class);
	public static ApplicationContext contextDao = new ClassPathXmlApplicationContext("App-Database-Spring-Module-Web.xml");
	private static EventiDao eventiDao = (EventiDao) contextDao.getBean("EventiDao");
	
	
	@OnOpen
	public void onOpen(Session userSession){
		try {
			log.info("Open Connection ...:"+userSession.getId());
		} catch (Exception e) { 

		}
	}

	@OnClose
	public void onClose(Session userSession){
		try {
			log.info("Close Connection ...:"+userSession.getId());
		} catch (Exception e) { 

		}
	}
	
	/*
	@OnMessage
	public String onMessage(String message, Session userSession){
		log.info("Message from the client: " + message);
		return message;
	}
	*/
	
	@OnMessage
	public void onMessage(Session session, String msg) {
		try {
			log.info("msg: "+msg);

			for (Session sess : session.getOpenSessions()) {
				log.info("sess.getId: "+sess.getId());
				if (sess.isOpen()) {
					
					//sess.getBasicRemote().sendText(msg);
					try {
						String aa = "";
						if( msg.equals("eventi") ) {
							aa = eventiDao.getEventi().get(5).getNome();
						}
						
						sess.getBasicRemote().sendText( aa );
						//sess.getBasicRemote().sendObject(new Object());

						
					} catch (IOException ioe) { 
						log.info("ioe: "+ioe.getMessage());
					}
				}
			}




		}catch (Exception exc) {
			log.info("exc: "+exc.getMessage());
		}
	}

	@OnError
	public void onError(Throwable e){
		e.printStackTrace();
	}
	
}
