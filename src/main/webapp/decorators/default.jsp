<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="${language}">
<head>
<!-- Global site tag (gtag.js) - Google Analytics -->
<script async src="https://www.googletagmanager.com/gtag/js?id=G-BDP8T172H0"></script>
<script>
  window.dataLayer = window.dataLayer || [];
  function gtag(){dataLayer.push(arguments);}
  gtag('js', new Date());

  gtag('config', 'G-BDP8T172H0');
</script>
<!-- adSense -->
<script async src="https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js?client=ca-pub-6169841975918547" crossorigin="anonymous"></script>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="google-site-verification" content="1Rx1p-XVDzEdgD2TVggr5KbRBe1v989sSbE8boldSJ4"/>
<meta name="robots" content="index, follow">
<!-- favicon -->
<link rel="icon" type="image/png" sizes="32x32" href="<c:url value='/images/logo SAMBET for WEB 256x256px.png'/>">
<link rel="icon" type="image/png" sizes="16x16" href="<c:url value='/images/logo SAMBET for WEB 256x256px.png'/>">
<title><decorator:title /> | <fmt:message key="webapp.sambet.name" /></title>


<link rel="stylesheet" type="text/css" href="<c:url value="/css/bootstrap-3.4.1-dist/bootstrap.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="/css/style.css"/>">

<!-- 
<t:assets type="css"/> 
-->
<decorator:head />
</head>
<body>
	<nav class="navbar navbar-default">
		<div class="container-fluid">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
					<span class="sr-only">Toggle navigation</span> 
					<span class="icon-bar"></span> <span class="icon-bar"></span> 
					<span class="icon-bar"></span>
				</button>
				<a class="navbar-brand hidden-md hidden-lg hidden-sm" href="<c:url value='/'/>"><fmt:message key="domain.sambet.name"/></a>
				<!-- <a href="#" class="navbar-left hidden-md hidden-lg hidden-sm"><img src="<c:url value='/images/'/>logo.png"></a>  -->
			</div>
			<!-- Collect the nav links, forms, and other content for toggling -->
			<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav navbar-left hidden-xs">
					<a href="<c:url value='/'/>"> <img src="<c:url value='/images/'/>/logo.png" class="img-responsive" alt="<fmt:message key="webapp.sambet.name"/>" 
						title="<fmt:message key="webapp.sambet.name"/>"></a>
				</ul>
				<ul class="nav navbar-nav navbar-right">
				
					
					<!-- 
					<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
					Bookmaker consigliati <span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a target="_blank" href="https://play.originalbet.it/register?ccode=102260">originalbet.it</a></li>
							<li><a target="_blank" href="https://www.overplus.it/main.aspx?page=user&p=registration&mp=d62d818e-fecd-4731-afa2-61a52ec088c9">overplus.it</a></li>
						</ul>
					</li>
					 -->
					 
					 <li><a target="_blank" href="https://www.adm.gov.it/portale/monopoli/giochi/gioco_distanza/gioco_dist_note">Probabilità di vincita</a></li>
					 
					 <li><a target="_blank" href="<c:url value='/recensioni-bookmakers'/>">Recensioni bookmakers</a></li>
					 
					<li><a target="_blank" href="<c:url value='/guida'/>">Guida</a></li>
					
					<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
					Contatti <span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a target="_blank" href="https://www.facebook.com/sambetforbettors">Seguici su Facebook</a></li>
							<li><a target="_blank" href="https://www.instagram.com/sambet.it/">Seguici su Instagram</a></li>
							<li><a><fmt:message key="email.info.sambet"/></a></li>
							<!-- <li role="separator" class="divider"></li> -->
						</ul>
					</li>
				</ul>
			</div>
			<!-- /.navbar-collapse -->
		</div>
		<!-- /.container-fluid -->
	</nav>
	<decorator:body />
	<t:assets type="js" />
	<%=(request.getAttribute("scripts") != null) ? request.getAttribute("scripts") : ""%>
</body>
</html>