<%@ include file="/common/taglibs.jsp"%>

<head>
<title><fmt:message key="home.title"/></title>
<script src="<c:url value="/scripts/jquery-3.5.1.min.js"/>"></script>
<script type="text/javascript">
var aaa = '${pageContext.request.contextPath}';
jQuery(document).ready(function(){

}); // fine ready
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

<h1>Pagina di prova</h1>

<p>result: ${RapidApiApiFootball}</p>

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
