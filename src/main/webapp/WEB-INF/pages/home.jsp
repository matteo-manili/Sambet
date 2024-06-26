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
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" integrity="sha512-iBBXm8fW90+nuLcSKlbmrPcLa0OT92xO1BIsZ+ywDWZCvqsWgccV3gFoRBv0z+8dLJgyAHIhR35VZc2oM/gI1w==" crossorigin="anonymous" />
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

<div style="color: #ff0000" id="logErroreTable"></div>
<div style="color: #ff0000" id="logErroreMainfiltrijson"></div>


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
	<div class="col-md-4 mb-15">
	    <div class="form-group">
	      <label for="email">Data</label><br>
	     	<input type="text" id="calendarioFrom" class="form-control" placeholder="da" />
			<input type="hidden" value="${mainTableFiltri.dataInizioPartita}" name="dataInizioPartita" id="calendarioFromHidden">
	     	<input type="text" id="calendarioTo" class="form-control " placeholder="a" />
			<input type="hidden" value="${mainTableFiltri.dataFinePartita}" name="dataFinePartita" id="calendarioToHidden">
	    </div>
	</div>
    <div class="col-md-4 mb-15">
		<div class="form-group">
			<label for="email">Ora</label><br>
			<input type="text" value="${mainTableFiltri.oraInizioPartita}" name="oraInizioPartita" id="idOraPartita" class="form-control timepicker" placeholder="da" />
			<input type="text" value="${mainTableFiltri.oraFinePartita}" name="oraFinePartita" id="idOraFinePartita" class="form-control timepicker" placeholder="a" />
		</div>
	</div>
	
	<div class="col-xs-6 col-sm-4 col-md-4 col-lg-4">
	    <div class="form-group d-grid">
	      	<label for="idNomePartita">Nome Partita</label><br>
	      	<input type="text" id="idNomePartita" name="nomePartita" class="form-control" placeholder="scrivi nome partita" />
	    </div>
	</div>
</div>

<div class="row space-top form-inline">

	<div class="col-6 col-xs-6 col-md-2 mb-15">
		<div class="form-group d-grid">
		<label>Data</label><br>
		<select name="dataEventoAd" class="form-control filtro-ad"></select>
		</div>
	</div>
	
	<div class="col-6 col-xs-6 col-md-2 mb-15">
		<div class="form-group d-grid">
		<label>Nazione</label><br>
		<select name="siglaNazioneAd" class="form-control filtro-ad"></select>
		</div>
	</div>
	
	<div class="col-6 col-xs-6 col-md-2 mb-15">
		<div class="form-group d-grid">
		<label>Campionato</label><br>
		<select name="nomeCompetizioniAd" class="form-control filtro-ad"></select>
		</div>
	</div>
	
	<div class="col-6 col-xs-6 col-md-2 mb-15">
		<div class="form-group d-grid">
		<label>Partita</label><br>
		<select name="nomeEventiAd" class="form-control filtro-ad"></select>
		</div>
	</div>
	
	<div class="col-6 col-xs-6 col-md-2 mb-15">
		<div class="form-group d-grid">
		<label>1</label><br>
		<select name="quota_1Ad" class="form-control filtro-ad"></select>
		</div>
	</div>
	
	<div class="col-6 col-xs-6 col-md-2 mb-15">
		<div class="form-group d-grid">
		<label>X</label><br>
		<select name="quota_XAd" class="form-control filtro-ad"></select>
		</div>
	</div>
	
	<div class="col-6 col-xs-6 col-md-2 mb-15">
		<div class="form-group d-grid">
		<label>2</label><br>
		<select name="quota_2Ad" class="form-control filtro-ad"></select>
		</div>
	</div>
	
	<div class="col-6 col-xs-6 col-md-2 mb-15">
		<div class="form-group d-grid">
		<label>Over 2,5</label><br>
		<select name="quota_overAd" class="form-control filtro-ad"></select>
		</div>
	</div>
	
	<div class="col-6 col-xs-6 col-md-2 mb-15">
		<div class="form-group d-grid">
		<label>Under 2,5</label><br>
		<select name="quota_underAd" class="form-control filtro-ad"></select>
		</div>
	</div>
	
	<div class="col-6 col-xs-6 col-md-2 mb-15">
		<div class="form-group d-grid">
		<label>Gol</label><br>
		<select name="quota_golAd" class="form-control filtro-ad"></select>
		</div>
	</div>
	
	<div class="col-6 col-xs-6 col-md-2 mb-15">
		<div class="form-group d-grid">
		<label>No Gol</label><br>
		<select name="quota_noGolAd" class="form-control filtro-ad"></select>
		</div>
	</div>
	
	<div class="col-6 col-xs-6 col-md-2 mb-15">
		<div class="form-group d-grid">
		<label>Azione</label><br>
		<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#filterModal">Filtri (<span id="filterRowNo">0</span>)</button>
		</div>
	</div>
</div>

<!-- Modal Filtri -->
<div class="modal fade" id="filterModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header" style="overflow: hidden;">
                <h4 class="modal-title" id="exampleModalLabel" style="float: left;">Filtri</h4>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close" style="float: right;">
                    <span aria-hidden="true">&#x2715;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="container-fluid">
                    <div class="row">
                        <ul id="modalRowsContent" class = "list-unstyled"></ul>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <span id="addRowModal" class="btn btn-primary">Aggiungi riga +</span>
                        </div>
                    </div>
                </div>
                
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Chiudi</button>
                <button type="button" id="submitModalForm" class="btn btn-primary" onclick="getAjaxMainTable();" data-dismiss="modal">Applica filtro</button>
            </div>
        </div>
    </div>
</div>
<!-- End modal filtri -->

<!-- ---------------------------------- TABELLA ---------------------------------- -->

<div class="space-top">
	<div id="idAjaxTable"></div>
</div>

<script type="text/javascript">

var list_filter = [];
var n = 0;
var elementCount = 0;

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
	beforeSend: function(){ $('#logErroreMainfiltrijson').empty(); },
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
		console.log('errore ajax mainfiltrijson');
		$('#logErroreMainfiltrijson').append('<p>logErroreMainfiltrijson: '+ req.responseText +' | '+ status +' | '+ error +'</p>');
		//window.location.replace('${pageContext.request.contextPath}/home-user');
		//location.reload(true);
	}
});
};


// MAIN TABLE
setInterval(function(){getAjaxMainTable();}, 10000); // <-- commentare qui per disabilitare il loop

function getAjaxMainTable(){

if(list_filter == null){
    console.log("list_filter is undefined");
	return;
}

var jsonObj = [];
// $(".filtro-ad").each(function() {

for(let i = 0; i < list_filter.length; i++){
    var filtro1 = document.getElementsByName("filtro1")[i].value;
    var filtro2 = document.getElementsByName("filtro2")[i].value;
    var filtro3 = document.getElementsByName("filtro3")[i].value;
    var filtro4 = document.getElementsByName("filtro4")[i].value;
    var filtro5 = document.getElementsByName("filtro5")[i].value;
    var filtro6 = document.getElementsByName("filtro6")[i].value;

    item = {}
    item ['A'] = filtro1;
    item ['B'] = filtro2;
    item ['C'] = filtro3;
    item ['D'] = filtro4;
    item ["E"] = filtro5;
    item ["F"] = filtro6

    jsonObj.push(item);
}


var jsonString = JSON.stringify(jsonObj);

console.log( jsonObj );
	
	
console.log( 'getAjaxMainTable' );
$.ajax({
	type: 'POST',
	url: '${pageContext.request.contextPath}/maintabellajson',
	dataType: "json",
	data: {
		dataForm : $('#idFiltraPartiteForm').serialize(),
        filtri_quote: jsonString
	},
	beforeSend: function(){ $('#logErroreTable').empty(); },
	success: function(result) {
		// table-responsive 
		var htmlMainTable = '<div class=" tableFixHead "><table class="table table-striped mainTable ">'
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
		console.log('errore ajax maintabellajson');
		$('#logErroreTable').append('<p>maintabellajson: '+ req.responseText +' | '+ status +' | '+ error +'</p>');
		//window.location.replace('${pageContext.request.contextPath}/home-user');
		//location.reload(true);
	}
});


document.getElementById('filterRowNo').innerHTML = n;

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


// LOGICA MODAL INIZIO 

function resetIncrement() {
    document.getElementById('filterRowNo').innerHTML = 0;
    n = 0;
}

function increment() {
    elementCount++;
    ++n;
}

function decrement() {
    --n;
}

function removeModaRow(value) {
    var rows = document.getElementById('modalRowsContent').children;

    var object;
    
    for(let i = 0; i < rows.length; i++){
        if(value == rows.item(i).id){
            object = rows.item(i);
            break;
        }
    }
    object.remove();
    
    for(let i = 0; i < list_filter.length; i++){
        if(list_filter[i].id == value){
            list_filter.splice(i,1);
        }
    }
    decrement();
}

$('#addRowModal').on('click', () => {
    var obj = {};

    var row = '<li id="' + elementCount +'">'+
            '<div id="filterRow" class="filterRow col-md-12 col-xs-12">'+
                '<div class="col-md-11">'+
                    '<div class="col-md-4 col-xs-6">'+
                        '<div class="form-group d-grid">'+
                            '<label>Mercato</label>'+
                            '<select name="filtro1" class="form-control filtro-ad" data-conv="json">'+
                                '<option value="--" selected>---</option>'+
                                '<option value="1">1</option>'+
                                '<option value="X">x</option>'+
                                '<option value="2">2</option>'+
                                '<option value="OV">Over 2,5</option>'+
                                '<option value="UN">Under 2,5</option>'+
                                '<option value="GOL">Gol</option>'+
                                '<option value="NO_GOL">No Gol</option>'+
                            '</select>'+
                        '</div>'+
                   '</div>'+
                    '<div class="col-md-4 col-xs-6">'+
                        '<div class="form-group d-grid">'+
                            '<label>Book</label>'+
                            '<select name="filtro2" class="form-control filtro-ad" data-conv="json">'+
                                '<option value="--" selected>---</option>'+
                                '<option value="QS"> Quote Sambet </option>'+
                                '<option value="BET_APE"> Betfair apertura </option>'+
                                '<option value="BET_MED"> Betfair q. media </option>'+
                                '<option value="BET_ATT"> Betfair attuale </option>'+
                                '<option value="PINN_APE"> Pinnacle apertura </option>'+
                                '<option value="PINN_ATT"> Pinnacle attuale </option>'+
                                '<option value="BET365_APE"> Bet365 apertura </option>'+
                                '<option value="BET365_ATT"> Bet365 attuale </option>'+
                                '<option value="MBOOK_APE"> MEDIA BOOK apertura </option>'+
                                '<option value="MBOOK_ATT"> MEDIA BOOK attuale </option>'+
                            '</select>'+
                        '</div>'+
                    '</div>'+
                    '<div class="col-md-4 col-xs-6">'+
                        '<div class="form-group d-grid">'+
                            '<label>Segno</label>'+
                            '<select id="includingSelect'+elementCount+'" name="filtro3" data-conv="json" class="form-control filtro-ad includingSelect${elementCount}" onchange="includingSelected('+elementCount+');">'+
                                '<option value="--" selected>---</option>'+
                                '<option value="<"> < </option>'+
                                '<option value=">"> > </option>'+
                                '<option value="="> = </option>'+
                                '<option value="<="> <= </option>'+
                                '<option value=">="> >= </option>'+
                                '<option value="COMP_TRA"> COMPRESO TRA </option>'+
                            '</select>'+
                        '</div>'+
                    '</div>'+
                    '<div class="col-md-4 col-xs-6">'+
                        '<div class="form-group d-grid">'+
                            '<label>Book</label>'+
                            '<select name="filtro4" class="form-control filtro-ad" data-conv="json">'+
                                '<option value="--" selected>-- Val. Num.</option>'+
                                '<option value="QS"> Quote Sambet </option>'+
                                '<option value="BET_APE"> Betfair apertura </option>'+
                                '<option value="BET_MED"> Betfair q. media </option>'+
                                '<option value="BET_ATT"> Betfair attuale </option>'+
                                '<option value="PINN_APE"> Pinnacle apertura </option>'+
                                '<option value="PINN_ATT"> Pinnacle attuale </option>'+
                                '<option value="BET365_APE"> Bet365 apertura </option>'+
                                '<option value="BET365_ATT"> Bet365 attuale </option>'+
                                '<option value="MBOOK_APE"> MEDIA BOOK apertura </option>'+
                                '<option value="MBOOK_ATT"> MEDIA BOOK attuale </option>'+
                            '</select>'+
                        '</div>'+
                    '</div>'+
                    '<div class="col-md-4 col-xs-6">'+
                        '<div class="form-group d-grid">'+
                            '<label>Valore N.</label>'+
                            '<input type="number" name="filtro5" class="form-control" value="0.00" data-conv="json" />'+
                        '</div>'+
                    '</div>'+
                    '<div id="includingTextInput'+elementCount+'" class="col-md-4 col-xs-6" style="display:none !important;" >'+
                        '<div class="form-group d-grid">'+
                            '<label>Valore N.</label>'+
                            '<input type="number" name="filtro6" class="form-control" value="0.00" data-conv="json" />'+
                        '</div>'+
                    '</div>'+
                '</div>'+
                '<div class="col-md-4 col-md-offset-4 col-xs-12">'+
                    '<div class="form-group d-grid">'+
                        '<label>&nbsp;</label>'+
                        '<span class="btn btn-danger" onclick="removeModaRow('+elementCount+');">'+
                            '<i class="fas fa-trash-alt"></i>'+
                        '</span>'+
                    '</div>'+
                '</div>'+
            '</div>'+
        '</li>';

    obj['id'] = elementCount;
    obj['content'] = row;
    
    
    list_filter.push(obj);
    document.querySelector('#modalRowsContent').insertAdjacentHTML(
        'beforeend',
        row
    )
    increment();
})

function includingSelected(id) {
    var selectBox = document.getElementById("includingSelect"+id);
    var selectedValue = selectBox.options[selectBox.selectedIndex].value;
    if (selectedValue === 'COMP_TRA') {
        document.getElementById("includingTextInput"+id).style.display = "block";
    } else {
        document.getElementById("includingTextInput"+id).style.display = "none";
    }
}

// LOGICA MODAL FINE 

</script>


</form>
</div> <!-- fine container-fluid -->
</body>