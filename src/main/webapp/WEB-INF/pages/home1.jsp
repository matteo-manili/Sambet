<%@ include file="/common/taglibs.jsp"%>
<head>
<title>Home</title>
<meta name="description" content="Monitoraggio di variazioni delle quote sportive che calcola la variazione della quota attuale in relazione alla quota di apertura dei principali mercati">
<link rel="canonical" href="<fmt:message key="https.w3.domain.sambet.name"/>">
<link rel="stylesheet" href="<c:url value="/scripts/vendor/pickadate.js-3.6.2/lib/compressed/themes/classic.css"/>">
<link rel="stylesheet" href="<c:url value="/scripts/vendor/pickadate.js-3.6.2/lib/compressed/themes/classic.date.css"/>">
<link rel="stylesheet" href="<c:url value="/scripts/vendor/pickadate.js-3.6.2/lib/compressed/themes/classic.time.css"/>">
<script src="<c:url value="/scripts/jquery-3.5.1.min.js"/>"></script>
<script src="<c:url value="/scripts/bootstrap-3.4.1-dist/bootstrap.min.js"/>"></script>
<script src="<c:url value="/scripts/vendor/pickadate.js-3.6.2/lib/compressed/picker.js"/>"></script>
<script src="<c:url value="/scripts/vendor/pickadate.js-3.6.2/lib/compressed/picker.date.js"/>"></script>
<script src="<c:url value="/scripts/vendor/pickadate.js-3.6.2/lib/compressed/picker.time.js"/>"></script>
<script src="<c:url value="/scripts/vendor/pickadate.js-3.6.2/lib/compressed/translations/${pickadate_translations}.js"/>"></script>
</head>
<body>
<noscript>
<div class="alert alert-danger h4" role="alert">
<p><strong>JavaScript disabilitato!</strong></p>
<p>Per avere a disposizione tutte le funzionalit&agrave; di questo sito &egrave; necessario abilitare 
Javascript.</p> <p>Qui ci sono tutte le <a href="http://www.enable-javascript.com/it/" target="_blank"> 
istruzioni su come abilitare JavaScript nel tuo browser</a>.</p>
</div>
</noscript>
<div class="container-fluid">
<%@ include file="/common/messages.jsp" %>

<div style="display: none;">
	<p>TOTALE RISULTATI: ${mainTable.totaleRow} </p>
</div>

<form method="get" action="" name="filtraPartiteForm" id="idFiltraPartiteForm">

<div class="row space-top">
	<div class="col-xs-4 col-sm-4 col-md-2 col-lg-2">
	<div class="form-group ">
		<label for="sigla-naz">Nazioni</label>
			<select id="idSiglaNazione" name="sigla-naz" class="form-control"></select>
		</div>
	</div>
	<div class="col-xs-8 col-sm-8 col-md-4 col-lg-4">
	<div class="form-group ">
		<label for="select-comp">Competizioni</label>
			<select id="idSelectComp" name="select-comp" class="form-control" size="5" multiple></select>
		</div>
	</div>
</div>
<div class="row space-top form-inline">
	<div class="col-xs-7 col-sm-7 col-md-7 col-lg-7">
	    <div class="form-group">
	      <label for="email">Data</label>
	     	<input type="text" id="calendarioFrom" class="form-control" placeholder="da" />
			<input type="hidden" value="${mainTableFiltri.dataInizioPartita}" name="dataInizioPartita" id="calendarioFromHidden">
	     	<input type="text" id="calendarioTo" class="form-control " placeholder="a" />
			<input type="hidden" value="${mainTableFiltri.dataFinePartita}" name="dataFinePartita" id="calendarioToHidden">
	    </div>
		<div class="form-group">
			<label for="email">Ora</label>
			<input type="text" value="${mainTableFiltri.oraInizioPartita}" name="oraInizioPartita" id="idOraPartita" class="form-control timepicker" placeholder="da" />
			<input type="text" value="${mainTableFiltri.oraFinePartita}" name="oraFinePartita" id="idOraFinePartita" class="form-control timepicker" placeholder="a" />
		</div>
	</div>
	<div class="col-xs-5 col-sm-5 col-md-5 col-lg-5">
	    <div class="form-group">
	      	<label for="email">Nome Partita</label>
	      	<input type="text" id="idNomePartita" name="nomePartita" class="form-control" placeholder="scrivi nome partita" />
	    </div>
	</div>
</div>

<div class="row space-top form-inline">
	<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
		<div class="form-group">
			<label>Data</label>
				<select name="dataEventoAd" class="form-control filtro-ad"></select>
			<label>Nazione</label>
				<select name="siglaNazioneAd" class="form-control filtro-ad"></select>
			<label>Campionato</label>
				<select name="nomeCompetizioniAd" class="form-control filtro-ad"></select>
			<label>Partita</label>
				<select name="nomeEventiAd" class="form-control filtro-ad"></select>
			<label>1</label>
				<select name="quota_1Ad" class="form-control filtro-ad"></select>
			<label>X</label>
				<select name="quota_XAd" class="form-control filtro-ad"></select>
			<label>2</label>
				<select name="quota_2Ad" class="form-control filtro-ad"></select>
			<label>Over 2,5</label>
				<select name="quota_overAd" class="form-control filtro-ad"></select>
			<label>Under 2,5</label>
				<select name="quota_underAd" class="form-control filtro-ad"></select>
			<label>Gol</label>
				<select name="quota_golAd" class="form-control filtro-ad"></select>
			<label>No Gol</label>
				<select name="quota_noGolAd" class="form-control filtro-ad"></select>
		</div>
	</div>
</div>

<!-- ---------------------------------- TABELLA ---------------------------------- -->

<div class="space-top">
	<div id="idAjaxTable"></div>
</div>

<script type="text/javascript">
function throttle(f, delay){
    var timer = null;
    return function(){
        var context = this, args = arguments;
        clearTimeout(timer);
        timer = window.setTimeout(function(){
            f.apply(context, args);
        },
        delay || 1000);
    };
}

$('#idSiglaNazione').on('change', function() {
	//$('select[multiple]').empty();
	//document.forms['filtraPartiteForm'].submit();
	var $idSelectComp = $('#idSelectComp'); $idSelectComp.empty();
	getAjaxMainFiltri();
	getAjaxMainTable();
	
});

$('#idSelectComp').on('change', function() {
	getAjaxMainTable();
});

$('#idNomePartita').keyup(throttle(function(){
	getAjaxMainTable();
}));

$('.filtro-ad').on('change', function() {
	getAjaxMainTable();
});


getAjaxMainFiltri();
getAjaxMainTable();

//MAIN FILTRI
function getAjaxMainFiltri(){
console.log( 'getAjaxMainFiltri' );		
$.ajax({
	type: 'POST',
	url: '${pageContext.request.contextPath}/mainfiltrijson',
	dataType: "json",
	data: {
		dataForm : $('#idFiltraPartiteForm').serialize()
	},
	beforeSend: function(){ },
	success: function(result) {

		var $idSiglaNazione = $('#idSiglaNazione'); $idSiglaNazione.empty();
		$.each(result['jsonMainTableFiltriNazionale'], function(index, value){
			var selected = (value.selected) ? 'selected' : '';
			$idSiglaNazione.append('<option value="'+value.nazioneSigla+'" '+ selected+'>' +value.nazioneNome+ '</option>')
		});
		
		var $idSelectComp = $('#idSelectComp'); $idSelectComp.empty();
		$.each(result['jsonMainTableFiltriCampionato'], function(index, value){
			var selected = (value.selected) ? 'selected' : '';
			$idSelectComp.append('<option value="'+value.idCampionato+'" '+ selected+'>' +value.campionatoNome+ '</option>')
		});
		
		// Filtri ASC DESC ----------------
		var $siglaNazione = $('select[name ="siglaNazioneAd"]'); $('select[name = "siglaNazioneAd"]').empty();
		$.each(result['jsonMainTableElementsAscDesc'], function(index, value){
			$siglaNazione.append('<option value="'+value.key+'" >' +value.value+ '</option>')
		});
		var $nomeCompetizioniAd = $('select[name ="nomeCompetizioniAd"]'); $('select[name = "nomeCompetizioniAd"]').empty();
		$.each(result['jsonMainTableElementsAscDesc'], function(index, value){
			$nomeCompetizioniAd.append('<option value="'+value.key+'" >' +value.value+ '</option>')
		});
		var $nomeEventiAd = $('select[name ="nomeEventiAd"]'); $('select[name = "nomeEventiAd"]').empty();
		$.each(result['jsonMainTableElementsAscDesc'], function(index, value){
			$nomeEventiAd.append('<option value="'+value.key+'" >' +value.value+ '</option>')
		});
		var $dataEventoAd = $('select[name ="dataEventoAd"]'); $('select[name = "dataEventoAd"]').empty();
		$.each(result['jsonMainTableElementsAscDesc'], function(index, value){
			$dataEventoAd.append('<option value="'+value.key+'" >' +value.value+ '</option>')
		});
		var $quota_1Ad = $('select[name ="quota_1Ad"]'); $('select[name = "quota_1Ad"]').empty();
		$.each(result['jsonMainTableElementsAscDesc'], function(index, value){
			$quota_1Ad.append('<option value="'+value.key+'" >' +value.value+ '</option>')
		});
		var $quota_XAd = $('select[name ="quota_XAd"]'); $('select[name = "quota_XAd"]').empty();
		$.each(result['jsonMainTableElementsAscDesc'], function(index, value){
			$quota_XAd.append('<option value="'+value.key+'" >' +value.value+ '</option>')
		});
		var $quota_2Ad = $('select[name ="quota_2Ad"]'); $('select[name = "quota_2Ad"]').empty();
		$.each(result['jsonMainTableElementsAscDesc'], function(index, value){
			$quota_2Ad.append('<option value="'+value.key+'" >' +value.value+ '</option>')
		});
		var $quota_overAd = $('select[name ="quota_overAd"]'); $('select[name = "quota_overAd"]').empty();
		$.each(result['jsonMainTableElementsAscDesc'], function(index, value){
			$quota_overAd.append('<option value="'+value.key+'" >' +value.value+ '</option>')
		});
		var $quota_underAd = $('select[name ="quota_underAd"]'); $('select[name = "quota_underAd"]').empty();
		$.each(result['jsonMainTableElementsAscDesc'], function(index, value){
			$quota_underAd.append('<option value="'+value.key+'" >' +value.value+ '</option>')
		});
		var $quota_golAd = $('select[name ="quota_golAd"]'); $('select[name = "quota_golAd"]').empty();
		$.each(result['jsonMainTableElementsAscDesc'], function(index, value){
			$quota_golAd.append('<option value="'+value.key+'" >' +value.value+ '</option>')
		});
		var $quota_noGolAd = $('select[name ="quota_noGolAd"]'); $('select[name = "quota_noGolAd"]').empty();
		$.each(result['jsonMainTableElementsAscDesc'], function(index, value){
			$quota_noGolAd.append('<option value="'+value.key+'" >' +value.value+ '</option>')
		});
		
		// assegno i select
		$.each(result['jsonMainTableListFiltriAscDesc'], function(index, value){
			console.log( value.key+' | '+value.value);
			if( value.nomeFiltroAscDesc === 'siglaNazioneAd' ){
				$('select[name = "siglaNazioneAd"]').val( value.ascDesc );
			}
			if( value.nomeFiltroAscDesc === 'nomeCompetizioniAd' ){
				$('select[name = "nomeCompetizioniAd"]').val( value.ascDesc );
			}
			if( value.nomeFiltroAscDesc === 'nomeEventiAd' ){
				$('select[name = "nomeEventiAd"]').val( value.ascDesc );
			}
			if( value.nomeFiltroAscDesc === 'dataEventoAd' ){
				$('select[name = "dataEventoAd"]').val( value.ascDesc );
			}
			if( value.nomeFiltroAscDesc === 'quota_1Ad' ){
				$('select[name = "quota_1Ad"]').val( value.ascDesc );
			}
			if( value.nomeFiltroAscDesc === 'quota_XAd' ){
				$('select[name = "quota_XAd"]').val( value.ascDesc );
			}
			if( value.nomeFiltroAscDesc === 'quota_2Ad' ){
				$('select[name = "quota_2Ad"]').val( value.ascDesc );
			}
			if( value.nomeFiltroAscDesc === 'quota_overAd' ){
				$('select[name = "quota_overAd"]').val( value.ascDesc );
			}
			if( value.nomeFiltroAscDesc === 'quota_underAd' ){
				$('select[name = "quota_underAd"]').val( value.ascDesc );
			}
			if( value.nomeFiltroAscDesc === 'quota_golAd' ){
				$('select[name = "quota_golAd"]').val( value.ascDesc );
			}
			if( value.nomeFiltroAscDesc === 'quota_noGolAd' ){
				$('select[name = "quota_noGolAd"]').val( value.ascDesc );
			}
		});
		
	},
	complete: function(){ },
	error: function (req, status, error) {
		alert('errore ajax mainfiltrijson');
		//window.location.replace('${pageContext.request.contextPath}/home-user');
		location.reload(true);
	}
});
};

// MAIN TABLE
setInterval(function(){getAjaxMainTable();}, 10000); // <-- commentare qui per disabilitare il loop
function getAjaxMainTable(){
console.log( 'getAjaxMainTable' );
$.ajax({
	type: 'POST',
	url: '${pageContext.request.contextPath}/maintabellajson',
	dataType: "json",
	data: {
		dataForm : $('#idFiltraPartiteForm').serialize()
	},
	beforeSend: function(){ },
	success: function(result) {
		var htmlMainTable = '<div class=" tableFixHead"><table class="table table-striped mainTable ">'
			+'<thead><tr><th>Nazione</th><th>Campionato</th><th>Partita</th><th>Data</th><th>Ora</th><th>Bookmakers</th><th>Quote</th><th>1</th><th>X</th><th>2</th><th>Over 2,5</th><th>Under 2,5</th><th>Gol</th><th>No Gol</th></tr></thead><tbody>';
		if( result['totaleRow'] > 0 ){
			$.each(result['jsonMainTableRow'], function(index, value){
				htmlMainTable += '<tr><td rowspan="12">'+value.nazioneNome+'</td><td rowspan="12">'+value.campionatoNome+'</td><td rowspan="12">'+value.partitaNome+'</td><td rowspan="12">'+value.partitaData+'</td><td rowspan="12">'+value.partitaOra+'</td>'
				
				+'<td colspan="2">Drop Odds</td>'
				+'<td class="pron_'+value.pSing_matchOdds_1+'_1"></td>'
				+'<td class="pron_'+value.pSing_matchOdds_X+'_X"></td>'
				+'<td class="pron_'+value.pSing_matchOdds_2+'_2"></td>'
				+'<td class="pron_'+value.pSing_ou25_Over+'_OVER"></td>'
				+'<td class="pron_'+value.pSing_ou25_Under+'_UNDER"></td>'
				+'<td class="pron_'+value.pSing_btts_Gol+'_GOL"></td>'
				+'<td class="pron_'+value.pSing_btts_NoGol+'_NO_GOL"></td></tr>'
				
				+'<td colspan="2">Pronostici per Multiple</td>'
				+'<td class="pron_'+value.pMult_matchOdds_1+'_1"></td>'
				+'<td class="pron_'+value.pMult_matchOdds_X+'_X"></td>'
				+'<td class="pron_'+value.pMult_matchOdds_2+'_2"></td>'
				+'<td class="pron_'+value.pMult_ou25_Over+'_OVER"></td>'
				+'<td class="pron_'+value.pMult_ou25_Under+'_UNDER"></td>'
				+'<td class="pron_'+value.pMult_btts_Gol+'_GOL"></td>'
				+'<td class="pron_'+value.pMult_btts_NoGol+'_NO_GOL"></td></tr>'
				
				+'<td colspan="2">Quote Sambet</td>'
				+'<td>'+value.qSambet_matchOdds_1+'</td>'
				+'<td>'+value.qSambet_matchOdds_X+'</td>'
				+'<td>'+value.qSambet_matchOdds_2+'</td>'
				+'<td>'+value.qSambet_ou25_Over+'</td>'
				+'<td>'+value.qSambet_ou25_Under+'</td>'
				+'<td>'+value.qSambet_btts_Gol+'</td>'
				+'<td>'+value.qSambet_btts_NoGol+'</td></tr>'
				
				+'<tr><td rowspan="3">Betfair Exchange</td>'
				+'<td>apertura</td>'
				+'<td>'+value.apert_matchOdds_1+'</td>'
				+'<td>'+value.apert_matchOdds_X+'</td>'
				+'<td>'+value.apert_matchOdds_2+'</td>'
				+'<td>'+value.apert_ou25_Over+'</td>'
				+'<td>'+value.apert_ou25_Under+'</td>'
				+'<td>'+value.apert_btts_Gol+'</td>'
				+'<td>'+value.apert_btts_NoGol+'</td></tr>'
				
				+'<tr><td>q.media</td><td>('+value.count_media_matchOdds_1+') '+value.media_matchOdds_1+'</td>'
				+'<td>('+value.count_media_matchOdds_X+') '+value.media_matchOdds_X+'</td>'
				+'<td>('+value.count_media_matchOdds_2+') '+value.media_matchOdds_2+'</td>'
				+'<td>('+value.count_media_ou25_Over+') '+value.media_ou25_Over+'</td>'
				+'<td>('+value.count_media_ou25_Under+') '+value.media_ou25_Under+'</td>'
				+'<td>('+value.count_media_btts_Gol+') '+value.media_btts_Gol+'</td>'
				+'<td>('+value.count_media_btts_NoGol+') '+value.media_btts_NoGol+'</td></tr>'
				
				+'<tr><td>attuale</td><td class="andamento_'+value.andamento_matchOdds_1+'">'+value.attual_matchOdds_1+'</td>'
				+'<td class="andamento_'+value.andamento_matchOdds_X+'">'+value.attual_matchOdds_X+'</td>'
				+'<td class="andamento_'+value.andamento_matchOdds_2+'">'+value.attual_matchOdds_2+'</td>'
				+'<td class="andamento_'+value.andamento_ou25_Over+'">'+value.attual_ou25_Over+'</td>'
				+'<td class="andamento_'+value.andamento_ou25_Under+'">'+value.attual_ou25_Under+'</td>'
				+'<td class="andamento_'+value.andamento_btts_Gol+'">'+value.attual_btts_Gol+'</td>'
				+'<td class="andamento_'+value.andamento_btts_NoGol+'">'+value.attual_btts_NoGol+'</td></tr>'
				
				// PINNACLE
				+'<tr><td rowspan="2">'+value.nomeBookmaker1+'</td>'
				+'<td>apertura</td>'
				+'<td>'+value.book1_apert_matchOdds_1+'</td>'
				+'<td>'+value.book1_apert_matchOdds_X+'</td>'
				+'<td>'+value.book1_apert_matchOdds_2+'</td>'
				+'<td>'+value.book1_apert_ou25_Over+'</td>'
				+'<td>'+value.book1_apert_ou25_Under+'</td>'
				+'<td>'+value.book1_apert_btts_Gol+'</td>'
				+'<td>'+value.book1_apert_btts_NoGol+'</td></tr>'
				
				+'<tr><td>attuale</td>'
				+'<td>'+value.book1_attual_matchOdds_1+'</td>'
				+'<td>'+value.book1_attual_matchOdds_X+'</td>'
				+'<td>'+value.book1_attual_matchOdds_2+'</td>'
				+'<td>'+value.book1_attual_ou25_Over+'</td>'
				+'<td>'+value.book1_attual_ou25_Under+'</td>'
				+'<td>'+value.book1_attual_btts_Gol+'</td>'
				+'<td>'+value.book1_attual_btts_NoGol+'</td></tr>'
				
				// BET365
				+'<tr><td rowspan="2">'+value.nomeBookmaker2+'</td>'
				+'<td>apertura</td>'
				+'<td>'+value.book2_apert_matchOdds_1+'</td>'
				+'<td>'+value.book2_apert_matchOdds_X+'</td>'
				+'<td>'+value.book2_apert_matchOdds_2+'</td>'
				+'<td>'+value.book2_apert_ou25_Over+'</td>'
				+'<td>'+value.book2_apert_ou25_Under+'</td>'
				+'<td>'+value.book2_apert_btts_Gol+'</td>'
				+'<td>'+value.book2_apert_btts_NoGol+'</td></tr>'
				
				+'<tr><td>attuale</td>'
				+'<td>'+value.book2_attual_matchOdds_1+'</td>'
				+'<td>'+value.book2_attual_matchOdds_X+'</td>'
				+'<td>'+value.book2_attual_matchOdds_2+'</td>'
				+'<td>'+value.book2_attual_ou25_Over+'</td>'
				+'<td>'+value.book2_attual_ou25_Under+'</td>'
				+'<td>'+value.book2_attual_btts_Gol+'</td>'
				+'<td>'+value.book2_attual_btts_NoGol+'</td></tr>'
				
				// MEDIA BOOK
				+'<tr><td rowspan="2">MEDIA BOOK</td>'
				+'<td>apertura</td>'
				+'<td>'+value.bookMedia_apert_matchOdds_1+'</td>'
				+'<td>'+value.bookMedia_apert_matchOdds_X+'</td>'
				+'<td>'+value.bookMedia_apert_matchOdds_2+'</td>'
				+'<td>'+value.bookMedia_apert_ou25_Over+'</td>'
				+'<td>'+value.bookMedia_apert_ou25_Under+'</td>'
				+'<td>'+value.bookMedia_apert_btts_Gol+'</td>'
				+'<td>'+value.bookMedia_apert_btts_NoGol+'</td></tr>'
				
				+'<tr><td>attuale</td>'
				+'<td>'+value.bookMedia_attual_matchOdds_1+'</td>'
				+'<td>'+value.bookMedia_attual_matchOdds_X+'</td>'
				+'<td>'+value.bookMedia_attual_matchOdds_2+'</td>'
				+'<td>'+value.bookMedia_attual_ou25_Over+'</td>'
				+'<td>'+value.bookMedia_attual_ou25_Under+'</td>'
				+'<td>'+value.bookMedia_attual_btts_Gol+'</td>'
				+'<td>'+value.bookMedia_attual_btts_NoGol+'</td></tr>'
				
				+'<tr><td colspan=14"></td></tr>';
				
				// <td rowspan="3" style="background-color:<c:if test="${varObj.andamento_noGol == 1}">green</c:if><c:if test="${varObj.andamento_noGol == 2}">yellow</c:if>
				// <c:if test="${varObj.andamento_noGol == 3}">red</c:if>"></td>
			});
  		}else{
  			htmlMainTable += '';
  		}
		htmlMainTable += '</tbody></table></div>'; $('#idAjaxTable').html( htmlMainTable );
	},
	error: function (req, status, error) {
		alert('errore ajax maintabellajson');
		//window.location.replace('${pageContext.request.contextPath}/home-user');
		location.reload(true);
	}
});
};

//----- CALENDARIO -----------------
var from_$input = $('#calendarioFrom').pickadate({
	min : true,
	format : 'dd/mm/yyyy',
	formatSubmit : 'yyyy/mm/dd',
	onStart : function() {
		if ($('#calendarioFromHidden').val() != '') {
			date.setTime($('#calendarioFromHidden').val());
			this.set('select', date);
		}
	},
	onClose : function() {
		$(document.activeElement).blur();
		getAjaxMainTable(); 
	}
}), from_picker = from_$input.pickadate('picker');

var to_$input = $('#calendarioTo').pickadate({
	min : true,
	format : 'dd/mm/yyyy',
	formatSubmit : 'yyyy/mm/dd',
	onStart : function() {
		if ($('#calendarioToHidden').val() != '') {
			date.setTime($('#calendarioToHidden').val());
			this.set('select', date);
		}
	},
	onClose : function() {
		$(document.activeElement).blur();
		getAjaxMainTable();
	}
}), to_picker = to_$input.pickadate('picker'); 

if (from_picker.get('value')) {
	to_picker.set('min', from_picker.get('select'));
}
if (to_picker.get('value')) {
	from_picker.set('max', to_picker.get('select'));
}

from_picker.on('set', function(event) {
	if (event.select) {
		$('#calendarioFromHidden').val(event.select);
		to_picker.set('min', from_picker.get('select'));
	} else if ('clear' in event) {
		$('#calendarioFromHidden').val('');
		to_picker.set('min', false);
	}
});

to_picker.on('set', function(event) {
	if (event.select) {
		$('#calendarioToHidden').val(event.select);
		from_picker.set('max', to_picker.get('select'));
	} else if ('clear' in event) {
		$('#calendarioToHidden').val('');
		from_picker.set('max', false);
	}
});

//-----OROLOGIO--------------
$('.timepicker').pickatime({
	format : 'HH:i',
	//formatSubmit: 'h:i',
	interval : 30,
	/* onStart: function (){ this.set( 'select', '12:00' ); } */
	onClose : function() {
		$(document.activeElement).blur();
		getAjaxMainTable();
	}
});
</script>
</form>
</div> <!-- fine container-fluid -->
</body>