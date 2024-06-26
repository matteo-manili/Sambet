<%@ include file="/common/taglibs.jsp"%>

<head>
<title><fmt:message key="home.title"/></title>
<script src="<c:url value="/scripts/jquery-3.5.1.min.js"/>"></script>

<script type="text/javascript">
var aaa = '${pageContext.request.contextPath}';


jQuery(document).ready(function(){

}); // fine ready
</script>

</script>




</head>
<body>
<%
try{
%>
<div class="content-area home-content">
<div class="container">
<div class="col-md-12">
<%@ include file="/common/messages.jsp" %>


<h1>Pagina di prova WebSocket</h1>

<form>
	<input type="text" id="message" value="eventi">
	<input type="button" onclick="wsSendMessage();" value="Echo" >
	<input type="button" onclick="wsCloseConnection();" value="Disconnect" >
</form>
<br>
<textarea id="echoText" rows="999" cols="100"></textarea>
<script type="text/javascript">

// 192.168.100.102
var webSocket = new WebSocket('wss://localhost:8443/sambet/chiamatasocket', 'protocol1');
//var webSocket = new WebSocket('wss://sambet.it:10191/chiamatasocket', 'protocol1');


var echoText = document.getElementById("echoText");
echoText.value = "";
var message = document.getElementById("message");

//-----------------------------------------------------------
webSocket.onopen = function(message) {
	console.log("webSocket.onopen");
	webSocket.send('start');
	wsOpen(message);
};
webSocket.onmessage = function(message) {
	console.log("webSocket.onmessage");
	wsGetMessage(message);
};
webSocket.onclose = function(message) {
	console.log("webSocket.onclose");
	wsClose(message);
};
webSocket.onerror = function(message) {
	console.log("webSocket.onerror");
	wsError(message);
};
//-----------------------------------------------------------

function wsSendMessage() {
	webSocket.send(message.value);
	//echoText.value += "Message sended to the server : " + message.value + "\n";
}
function wsCloseConnection() {
	webSocket.close();
}


function wsGetMessage(message) {
	console.log("function wsGetMessage");
	echoText.value += "Message received: " + message.data + "\n";
}

function wsOpen(message) {
	console.log("function wsOpen");
	echoText.value += "Connected ... \n";
}

function wsClose(message) {
	console.log("function wsClose");
	echoText.value += "Disconnect ... \n";
}
function wserror(message) {
	console.log("function wserror");
	echoText.value += "Error ... \n";
}
</script>


	<!-- ----------------------------------------------------------------------------------------------------- 
	<div class="row form-group hidden ">
		<input type="button" name="verifica-sms-customer" class="btn btn-sm btn-primary" id="loginButton" value="Verifica Codice SMS">
	</div>
	<div class="row hidden">
		<div>${device}</div>
	</div>
	<div class="hidden">${memory}</div> -->
	<!-- ----------------------------------------------------------------------------------------------------- -->
	
</div>
</div>
</div>

		
<%
}catch(final Exception e) {
	e.getMessage();
	e.printStackTrace();
}
%>
</body>
