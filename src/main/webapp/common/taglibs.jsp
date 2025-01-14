<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%@ taglib uri="http://www.springmodules.org/tags/commons-validator" prefix="v" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://struts-menu.sf.net/tag-el" prefix="menu" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page"%>
<%@ taglib uri="http://www.appfuse.org/tags/spring" prefix="appfuse" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="datePattern"><fmt:message key="date.format"/></c:set>
<c:choose>
	<c:when test="${not empty param['locale'] && param['locale'] != ''}">
		<c:set var="language" scope="session" value="${param['locale']}"/>
	</c:when>
	<c:when test="${not empty language}">
		<c:set var="language" scope="session" value="${language}"/>
	</c:when>
	<c:when test="${pageContext.request.locale.language == 'en' || pageContext.request.locale.language == 'it' || pageContext.request.locale.language == 'es'}">
		<c:set var="language" scope="session" value="${pageContext.request.locale.language}"/>
	</c:when>
	<c:otherwise>
		<!-- quando ApplicationResources.properties sarà scritto in inglese, mettere valore: en -->
		<c:set var="language" scope="session" value="it"/>
	</c:otherwise>
</c:choose>
<c:set var="tooltipGC" value="Servizio Giornata Completa prevede un accompagnamento per l'intera giornata nella relativa zona del Territorio"/>
<c:set var="tooltipMA" value="Servizio Matrimoni è un servizio di accompagnamento per cerimonie o eventi in generale, con automobile (limousine, roll royce..) nella relativa zona del Territorio"/>
<c:set var="RECAPTCHA_PUBLIC_GLOBAL" value="6Leayj0UAAAAABzoe-yIr2qCjcH-E2LZnYkEyNxm"/>
