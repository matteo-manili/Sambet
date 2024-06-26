package com.sambet.dao.hibernate;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.SQLQuery;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate4.HibernateJdbcException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.sambet.Constants;
import com.sambet.dao.EventiDao;
import com.sambet.model.Eventi;
import com.sambet.webapp.util.bean.MainTableFiltri;
import com.sambet.webapp.util.bean.MainTableFiltri.FiltroAscDesc;


/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
@EnableTransactionManagement
@Repository("EventiDao")
public class EventiDaoHibernate extends GenericDaoHibernate<Eventi, Long> implements EventiDao {

	public EventiDaoHibernate() {
		super(Eventi.class);
	}
	
	@Override
    @Transactional(readOnly = true)
	public Eventi get(Long id){
		Eventi regioni = (Eventi) getSession().get(Eventi.class, id);
		return regioni;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<Eventi> getEventi() {
        return getSession().createCriteria(Eventi.class).addOrder(Order.asc("id")).list();
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<Eventi> getEventi_orderBy_idCompetizione() {
        return getSession().createCriteria(Eventi.class).addOrder(Order.asc("competizione")).addOrder(Order.asc("id")).list();
	}

	
	@Override
	@Transactional
	public Eventi saveEventi(Eventi eventi) throws DataIntegrityViolationException, HibernateJdbcException {
		getSession().saveOrUpdate(eventi);
		//getSession().flush();
		return eventi;
	}
	
	
	@Override
    @Transactional(readOnly = true)
	public Eventi GetEvento_from_idEventBetFair(String id){
		return (Eventi) getSession().createCriteria(Eventi.class).add( Restrictions.eq("idEventBetFair", id) ).uniqueResult();
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
    @Transactional(readOnly = true)
	public List<Eventi> GetEventi_from_siglaNazione(String siglaNazione){
		return getSession().createCriteria(Eventi.class).createAlias("competizione", "COMP").createAlias("COMP.nazione", "NAZ")
				.add( Restrictions.eq("NAZ.siglaNazione", siglaNazione) ).addOrder(Order.asc("competizione")).list();
	}
	
	
	@Override
	@Transactional
	public void PuliziaDatabase_Market_Eventi_Campionati() {
		//---------------- EVENTI BETFAIR 
		// Elimina aga_tariffari
		String queryString_1 = "DELETE MARK FROM bf_market_esito_finale MARK " 
				+ "INNER JOIN bf_eventi EVENTO ON MARK.id_evento = EVENTO.id_evento AND EVENTO.dataEvento < NOW() ";
		int result1 = getSession().createSQLQuery( queryString_1 ).executeUpdate();
		
		// Elimina bf_market_over_under_25
		String queryString_2 = "DELETE MARK FROM bf_market_over_under_25 MARK " 
				+ "INNER JOIN bf_eventi EVENTO ON MARK.id_evento = EVENTO.id_evento AND EVENTO.dataEvento < NOW() ";
		int result2 = getSession().createSQLQuery( queryString_2 ).executeUpdate();
		
		// Elimina bf_market_gol
		String queryString_3 = "DELETE MARK FROM bf_market_gol MARK " 
				+ "INNER JOIN bf_eventi EVENTO ON MARK.id_evento = EVENTO.id_evento AND EVENTO.dataEvento < NOW() ";
		int result3 = getSession().createSQLQuery( queryString_3 ).executeUpdate();
		
		// Elimina bf_eventi
		String queryString_4 = "DELETE EVENTO FROM bf_eventi EVENTO " 
				+ "WHERE NOT EXISTS (SELECT MARK.id_market_esito_finale FROM bf_market_esito_finale MARK WHERE MARK.id_evento = EVENTO.id_evento) "
				+ "AND NOT EXISTS (SELECT MARK.id_market_over_under_25 FROM bf_market_over_under_25 MARK WHERE MARK.id_evento = EVENTO.id_evento)"
				+ "AND NOT EXISTS (SELECT MARK.id_market_gol FROM bf_market_gol MARK WHERE MARK.id_evento = EVENTO.id_evento)";
		int result4 = getSession().createSQLQuery( queryString_4 ).executeUpdate();
		
		
		//---------------- EVENTI RAPID_FOOTBAL
		// Elimina raf_market_esito_finale, Per elimnare da timestap: FROM_UNIXTIME(EVENTO.event_timestamp) < NOW() 
		String queryString_5 = "DELETE MARK FROM raf_market_esito_finale MARK " 
				+ "INNER JOIN raf_eventi RAF_EVENTO ON MARK.id_raf_evento = RAF_EVENTO.id_raf_evento "
				+ "WHERE NOT EXISTS (SELECT BF_EVENTO.id_evento FROM bf_eventi BF_EVENTO WHERE BF_EVENTO.id_raf_evento = RAF_EVENTO.id_raf_evento) ";
		int result5 = getSession().createSQLQuery( queryString_5 ).executeUpdate();
		
		// Elimina raf_market_over_under_25
		String queryString_6 = "DELETE MARK FROM raf_market_over_under_25 MARK " 
				+ "INNER JOIN raf_eventi RAF_EVENTO ON MARK.id_raf_evento = RAF_EVENTO.id_raf_evento "
				+ "WHERE NOT EXISTS (SELECT BF_EVENTO.id_evento FROM bf_eventi BF_EVENTO WHERE BF_EVENTO.id_raf_evento = RAF_EVENTO.id_raf_evento) ";
		int result6 = getSession().createSQLQuery( queryString_6 ).executeUpdate();
		
		// Elimina raf_market_gol
		String queryString_7 = "DELETE MARK FROM raf_market_gol MARK " 
				+ "INNER JOIN raf_eventi RAF_EVENTO ON MARK.id_raf_evento = RAF_EVENTO.id_raf_evento "
				+ "WHERE NOT EXISTS (SELECT BF_EVENTO.id_evento FROM bf_eventi BF_EVENTO WHERE BF_EVENTO.id_raf_evento = RAF_EVENTO.id_raf_evento) ";
		int result7 = getSession().createSQLQuery( queryString_7 ).executeUpdate();
		
		// Elimina raf_quote_sambet
		String queryString_8 = "DELETE Q_SAMBET FROM raf_quote_sambet Q_SAMBET " 
				+ "INNER JOIN raf_eventi RAF_EVENTO ON Q_SAMBET.id_raf_evento = RAF_EVENTO.id_raf_evento "
				+ "WHERE NOT EXISTS (SELECT BF_EVENTO.id_evento FROM bf_eventi BF_EVENTO WHERE BF_EVENTO.id_raf_evento = RAF_EVENTO.id_raf_evento) ";
		int result8 = getSession().createSQLQuery( queryString_8 ).executeUpdate();
		
		// Elimina raf_eventi
		String queryString_9 = "DELETE RAF_EVENTO FROM raf_eventi RAF_EVENTO " 
				+ "WHERE NOT EXISTS (SELECT BF_EVENTO.id_evento FROM bf_eventi BF_EVENTO WHERE BF_EVENTO.id_raf_evento = RAF_EVENTO.id_raf_evento) "
				+ "AND NOT EXISTS (SELECT MARK.id_raf_market_esito_finale FROM raf_market_esito_finale MARK WHERE MARK.id_raf_evento = RAF_EVENTO.id_raf_evento) "
				+ "AND NOT EXISTS (SELECT MARK.id_raf_market_over_under_25 FROM raf_market_over_under_25 MARK WHERE MARK.id_raf_evento = RAF_EVENTO.id_raf_evento)"
				+ "AND NOT EXISTS (SELECT MARK.id_raf_market_gol FROM raf_market_gol MARK WHERE MARK.id_raf_evento = RAF_EVENTO.id_raf_evento) "
				+ "AND NOT EXISTS (SELECT Q_SAMBET.id_raf_quote_sambet FROM raf_quote_sambet Q_SAMBET WHERE Q_SAMBET.id_raf_evento = RAF_EVENTO.id_raf_evento) ";
		int result9 = getSession().createSQLQuery( queryString_9 ).executeUpdate();
		
		// Elimina raf_statistics
		String queryString_10 = "DELETE STAT FROM raf_statistics STAT " 
				+ "WHERE NOT EXISTS (SELECT EVENTO.id_raf_evento FROM raf_eventi EVENTO WHERE EVENTO.id_raf_competizione = STAT.id_raf_competizione) ";
		int result10 = getSession().createSQLQuery( queryString_10 ).executeUpdate();
		
		// Elimina raf_competizioni
		String queryString_11 = "DELETE COMP FROM raf_competizioni COMP " 
				+ "WHERE NOT EXISTS (SELECT EVENTO.id_raf_evento FROM raf_eventi EVENTO WHERE EVENTO.id_raf_competizione = COMP.id_raf_competizione) ";
		int result11 = getSession().createSQLQuery( queryString_11 ).executeUpdate();
		
		
		
		//---------------- COMPETIZIONI BETFAIR
		// Update bf_competizioni
		/*
		String queryString_12 = "UPDATE bf_competizioni COMP "
				+ "SET COMP.attivo = false WHERE NOT EXISTS (SELECT EVENTO.id_evento FROM bf_eventi EVENTO WHERE EVENTO.id_competizione = COMP.id_competizione) ";
		int result12 = getSession().createSQLQuery( queryString_12 ).executeUpdate();
		*/
		/*
		log.info("DELETE bf_market_esito_finale: "+result1 
				+" DELETE bf_market_over_under_25: "+result2
				+" DELETE bf_market_gol: "+result3
				+" DELETE bf_eventi: "+result4
				+" DELETE raf_market_esito_finale: "+result5
				+" DELETE raf_market_over_under_25: "+result6
				+" DELETE raf_market_gol: "+result7
				+" DELETE raf_quote_sambet: "+result8
				+" DELETE raf_eventi: "+result9
				+" DELETE raf_statistics: "+result10
				+" DELETE raf_competizioni: "+result11
				//+" UPDATE bf_competizioni: "+result12
				);
			*/
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
    @Transactional(readOnly = true)
	public List<Eventi> GetEventi_from_siglaNazione_rafEventiIsNULL(String siglaNazione) {
		Criterion crit1 = Restrictions.and(Restrictions.eq("NAZ.siglaNazione", siglaNazione), Restrictions.isNull("rafEventi") );
		return getSession().createCriteria(Eventi.class).createAlias("competizione", "COMP").createAlias("COMP.nazione", "NAZ").add( crit1 ).list();
	}
	
	
	/*
	@SuppressWarnings("unchecked")
	@Override
    @Transactional(readOnly = true)
	public List<Eventi> GetEventi_from_siglaNazione_rafEventiIsNotNULL(String siglaNazione) {
		Criterion crit1 = Restrictions.and(Restrictions.eq("NAZ.siglaNazione", siglaNazione), Restrictions.eq("COMP.attivo", true), Restrictions.isNotNull("rafEventi") );
		return getSession().createCriteria(Eventi.class).createAlias("competizione", "COMP").createAlias("COMP.nazione", "NAZ").add( crit1 ).list();
	}
	*/
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<Object[]> GetEventi_from_siglaNazione_rafEventiIsNotNULL() {
		
		String queryString = "SELECT RAF_EVENTO.id_raf_evento, RAF_COMP.league_id, TEAM_HOME.team_id AS TEAM_HOME, TEAM_AWAY.team_id AS TEAM_AWAY "

			+ "FROM bf_nazioni AS NAZ INNER JOIN bf_competizioni AS COMP ON NAZ.id_nazione = COMP.id_nazione AND NAZ.attivo = true AND COMP.attivo = true "
			+ "INNER JOIN bf_eventi AS EVENTO ON COMP.id_competizione = EVENTO.id_competizione AND EVENTO.dataEvento > NOW() "
			+ "INNER JOIN raf_eventi AS RAF_EVENTO ON EVENTO.id_raf_evento = RAF_EVENTO.id_raf_evento "
			
			+ "INNER JOIN raf_competizioni AS RAF_COMP ON RAF_EVENTO.id_raf_competizione = RAF_COMP.id_raf_competizione "
			
			+ "INNER JOIN raf_teams AS TEAM_HOME ON RAF_EVENTO.id_raf_team_home = TEAM_HOME.id_raf_team "
			+ "INNER JOIN raf_teams AS TEAM_AWAY ON RAF_EVENTO.id_raf_team_away = TEAM_AWAY.id_raf_team "
			+"ORDER BY NAZ.id_nazione ASC, COMP.id_competizione ASC, EVENTO.id_evento ASC ";
		
		
		List<Object[]> resultList = this.getSession().createSQLQuery( queryString )
				//.setParameter("siglaNazione", siglaNazione)
				.list();
		
		/*
		System.out.println("resultList.size: "+resultList.size());
		for(Object[] ite_object: resultList) {
			Long 			var_0 = ((BigInteger)ite_object[0]).longValue();
			Integer 		var_1 = (Integer)ite_object[1];
			Integer 		var_2 = (Integer)ite_object[2];
			Integer 		var_3 = (Integer)ite_object[3];
			System.out.println(var_0+" | " +var_1+" | " +var_2+" | " +var_3); 
		}
		*/
		
		return resultList;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<Object[]> Report_1(MainTableFiltri mainTableFiltri) {
		final String _IS_NOT_NULL = "_IS_NOT_NULL ";
		String OrderBy = "";
		if( mainTableFiltri != null ) {
			Iterator<Entry<String, String>> ite_2 = Constants.FILTRI_ASC_DESC.entrySet().iterator();
		    while(ite_2.hasNext()) {
		    	Map.Entry<String, String> pair = (Map.Entry<String, String>)ite_2.next();
		    	for(FiltroAscDesc ite_1: mainTableFiltri.getListFiltriAscDesc()) {
		    		if( pair.getKey().equals(ite_1.getNomeFiltroAscDesc()) && !ite_1.getAscDesc().equals("") ) {
		    			if( pair.getValue().contains(Constants.BOOKMAKERS_) ) {
		    				OrderBy += pair.getValue() + _IS_NOT_NULL + "ASC, "+pair.getValue()+" "+ite_1.getAscDesc()+", "; // questo serve mettere i valori null alla fine
		    			}else {
		    				OrderBy += pair.getValue()+" "+ite_1.getAscDesc()+", ";
		    			}
					}
				}
		    }
		}
	    OrderBy = "ORDER BY "+OrderBy +"EVENTO.dataEvento ASC, NAZ.ordinamento ASC, COMP_NOME ASC, EVENTO_NOME ASC "; // DATA ASC, Nazine ASC, campaionato ASC, partita ASC
		//log.info("OrderBy: "+OrderBy );

		
final String VAR_BOOKMAKERS_ATTUALE_MEDIA_1 = "(SELECT ROUND(AVG(MARK.quota_1), 2) FROM raf_market_esito_finale MARK WHERE MARK.id_raf_evento = RAF_EVENTO.id_raf_evento AND tipoQuota = "+Constants.QTA_ATTUALE+" AND MARK.quota_1 IS NOT NULL) ";
final String VAR_BOOKMAKERS_ATTUALE_MEDIA_X = "(SELECT ROUND(AVG(MARK.quota_X), 2) FROM raf_market_esito_finale MARK WHERE MARK.id_raf_evento = RAF_EVENTO.id_raf_evento AND tipoQuota = "+Constants.QTA_ATTUALE+" AND MARK.quota_X IS NOT NULL) ";
final String VAR_BOOKMAKERS_ATTUALE_MEDIA_2 = "(SELECT ROUND(AVG(MARK.quota_2), 2) FROM raf_market_esito_finale MARK WHERE MARK.id_raf_evento = RAF_EVENTO.id_raf_evento AND tipoQuota = "+Constants.QTA_ATTUALE+" AND MARK.quota_2 IS NOT NULL) ";
final String VAR_BOOKMAKERS_ATTUALE_MEDIA_OVER = "(SELECT ROUND(AVG(MARK.quota_over), 2) FROM raf_market_over_under_25 MARK WHERE MARK.id_raf_evento = RAF_EVENTO.id_raf_evento AND tipoQuota = "+Constants.QTA_ATTUALE+" AND MARK.quota_over IS NOT NULL) ";
final String VAR_BOOKMAKERS_ATTUALE_MEDIA_UNDER = "(SELECT ROUND(AVG(MARK.quota_under), 2) FROM raf_market_over_under_25 MARK WHERE MARK.id_raf_evento = RAF_EVENTO.id_raf_evento AND tipoQuota = "+Constants.QTA_ATTUALE+" AND MARK.quota_under IS NOT NULL) ";
final String VAR_BOOKMAKERS_ATTUALE_MEDIA_GOL = "(SELECT ROUND(AVG(MARK.quota_gol), 2) FROM raf_market_gol MARK WHERE MARK.id_raf_evento = RAF_EVENTO.id_raf_evento AND tipoQuota = "+Constants.QTA_ATTUALE+" AND MARK.quota_gol IS NOT NULL) ";
final String VAR_BOOKMAKERS_ATTUALE_MEDIA_NO_GOL = "(SELECT ROUND(AVG(MARK.quota_noGol), 2) FROM raf_market_gol MARK WHERE MARK.id_raf_evento = RAF_EVENTO.id_raf_evento AND tipoQuota = "+Constants.QTA_ATTUALE+" AND MARK.quota_noGol IS NOT NULL) ";

	    
//----------
String queryString = "SELECT NAZ.siglaNazione, COMP.nome AS COMP_NOME, EVENTO.nome AS EVENTO_NOME, EVENTO.dataEvento, "
// APERTURA bf_market_esito_finale 
+ "(SELECT MARK.quota_1 FROM bf_market_esito_finale MARK WHERE MARK.id_evento = EVENTO.id_evento AND tipoQuota = "+Constants.QTA_APERTURA+" ORDER BY MARK.dataRichiesta ASC) AS APERT_1, "
+ "(SELECT MARK.quota_X FROM bf_market_esito_finale MARK WHERE MARK.id_evento = EVENTO.id_evento AND tipoQuota = "+Constants.QTA_APERTURA+" ORDER BY MARK.dataRichiesta ASC) AS APERT_X, "
+ "(SELECT MARK.quota_2 FROM bf_market_esito_finale MARK WHERE MARK.id_evento = EVENTO.id_evento AND tipoQuota = "+Constants.QTA_APERTURA+" ORDER BY MARK.dataRichiesta ASC) AS APERT_2, "
// MEDIA bf_market_esito_finale 	
+ "(SELECT COUNT(MARK.quota_1) FROM bf_market_esito_finale MARK WHERE MARK.id_evento = EVENTO.id_evento AND tipoQuota = "+Constants.QTA_MEDIA+" AND MARK.quota_1 IS NOT NULL) AS COUNT_1, "			
+ "(SELECT ROUND(AVG(MARK.quota_1), 2) FROM bf_market_esito_finale MARK WHERE MARK.id_evento = EVENTO.id_evento AND tipoQuota = "+Constants.QTA_MEDIA+" AND MARK.quota_1 IS NOT NULL) AS MEDIA_1, "
+ "(SELECT COUNT(MARK.quota_X) FROM bf_market_esito_finale MARK WHERE MARK.id_evento = EVENTO.id_evento AND tipoQuota = "+Constants.QTA_MEDIA+" AND MARK.quota_X IS NOT NULL) AS COUNT_X, "
+ "(SELECT ROUND(AVG(MARK.quota_X), 2) FROM bf_market_esito_finale MARK WHERE MARK.id_evento = EVENTO.id_evento AND tipoQuota = "+Constants.QTA_MEDIA+" AND MARK.quota_X IS NOT NULL) AS MEDIA_X, "
+ "(SELECT COUNT(MARK.quota_2) FROM bf_market_esito_finale MARK WHERE MARK.id_evento = EVENTO.id_evento AND tipoQuota = "+Constants.QTA_MEDIA+" AND MARK.quota_2 IS NOT NULL) AS COUNT_2, "
+ "(SELECT ROUND(AVG(MARK.quota_2), 2) FROM bf_market_esito_finale MARK WHERE MARK.id_evento = EVENTO.id_evento AND tipoQuota = "+Constants.QTA_MEDIA+" AND MARK.quota_2 IS NOT NULL) AS MEDIA_2, "
// ATTUALE bf_market_esito_finale 
+ "(SELECT MARK.quota_1 FROM bf_market_esito_finale MARK WHERE MARK.id_evento = EVENTO.id_evento AND tipoQuota = "+Constants.QTA_ATTUALE+" ORDER BY MARK.dataRichiesta DESC) AS ATTUALE_1, "
+ "(SELECT MARK.quota_X FROM bf_market_esito_finale MARK WHERE MARK.id_evento = EVENTO.id_evento AND tipoQuota = "+Constants.QTA_ATTUALE+" ORDER BY MARK.dataRichiesta DESC) AS ATTUALE_X, "	
+ "(SELECT MARK.quota_2 FROM bf_market_esito_finale MARK WHERE MARK.id_evento = EVENTO.id_evento AND tipoQuota = "+Constants.QTA_ATTUALE+" ORDER BY MARK.dataRichiesta DESC) AS ATTUALE_2, "	
//--------------------------------------------------------------------------
//APERTURA bf_market_over_under_25 
+ "(SELECT MARK.quota_over FROM bf_market_over_under_25 MARK WHERE MARK.id_evento = EVENTO.id_evento AND tipoQuota = "+Constants.QTA_APERTURA+" ORDER BY MARK.dataRichiesta ASC LIMIT 1) AS APERT_OVER, "
+ "(SELECT MARK.quota_under FROM bf_market_over_under_25 MARK WHERE MARK.id_evento = EVENTO.id_evento AND tipoQuota = "+Constants.QTA_APERTURA+" ORDER BY MARK.dataRichiesta ASC LIMIT 1) AS APERT_UNDER, "
//MEDIA bf_market_over_under_25 
+ "(SELECT COUNT(MARK.quota_over) FROM bf_market_over_under_25 MARK WHERE MARK.id_evento = EVENTO.id_evento AND tipoQuota = "+Constants.QTA_MEDIA+" AND MARK.quota_over IS NOT NULL) AS COUNT_OVER, "			
+ "(SELECT ROUND(AVG(MARK.quota_over), 2) FROM bf_market_over_under_25 MARK WHERE MARK.id_evento = EVENTO.id_evento AND tipoQuota = "+Constants.QTA_MEDIA+" AND MARK.quota_over IS NOT NULL) AS MEDIA_OVER, "
+ "(SELECT COUNT(MARK.quota_under) FROM bf_market_over_under_25 MARK WHERE MARK.id_evento = EVENTO.id_evento AND tipoQuota = "+Constants.QTA_MEDIA+" AND MARK.quota_under IS NOT NULL) AS COUNT_UNDER, "
+ "(SELECT ROUND(AVG(MARK.quota_under), 2) FROM bf_market_over_under_25 MARK WHERE MARK.id_evento = EVENTO.id_evento AND tipoQuota = "+Constants.QTA_MEDIA+" AND MARK.quota_under IS NOT NULL) AS MEDIA_UNDER, "
//ATTUALE bf_market_over_under_25 
+ "(SELECT MARK.quota_over FROM bf_market_over_under_25 MARK WHERE MARK.id_evento = EVENTO.id_evento AND tipoQuota = "+Constants.QTA_ATTUALE+" ORDER BY MARK.dataRichiesta DESC) AS ATTUALE_OVER, "
+ "(SELECT MARK.quota_under FROM bf_market_over_under_25 MARK WHERE MARK.id_evento = EVENTO.id_evento AND tipoQuota = "+Constants.QTA_ATTUALE+" ORDER BY MARK.dataRichiesta DESC) AS ATTUALE_UNDER, "	
//--------------------------------------------------------------------------
//APERTURA bf_market_gol
+ "(SELECT MARK.quota_gol FROM bf_market_gol MARK WHERE MARK.id_evento = EVENTO.id_evento AND tipoQuota = "+Constants.QTA_APERTURA+" ORDER BY MARK.dataRichiesta ASC) AS APERT_GOL, "
+ "(SELECT MARK.quota_noGol FROM bf_market_gol MARK WHERE MARK.id_evento = EVENTO.id_evento AND tipoQuota = "+Constants.QTA_APERTURA+" ORDER BY MARK.dataRichiesta ASC) AS APERT_NO_GOL, "
//MEDIA bf_market_gol 	
+ "(SELECT COUNT(MARK.quota_gol) FROM bf_market_gol MARK WHERE MARK.id_evento = EVENTO.id_evento AND tipoQuota = "+Constants.QTA_MEDIA+" AND MARK.quota_gol IS NOT NULL) AS COUNT_GOL, "			
+ "(SELECT ROUND(AVG(MARK.quota_gol), 2) FROM bf_market_gol MARK WHERE MARK.id_evento = EVENTO.id_evento AND tipoQuota = "+Constants.QTA_MEDIA+" AND MARK.quota_gol IS NOT NULL) AS MEDIA_GOL, "
+ "(SELECT COUNT(MARK.quota_noGol) FROM bf_market_gol MARK WHERE MARK.id_evento = EVENTO.id_evento AND tipoQuota = "+Constants.QTA_MEDIA+" AND MARK.quota_noGol IS NOT NULL) AS COUNT_NO_GOL, "
+ "(SELECT ROUND(AVG(MARK.quota_noGol), 2) FROM bf_market_gol MARK WHERE MARK.id_evento = EVENTO.id_evento AND tipoQuota = "+Constants.QTA_MEDIA+" AND MARK.quota_noGol IS NOT NULL) AS MEDIA_NO_GOL, "
//ATTUALE bf_market_gol
+ "(SELECT MARK.quota_gol FROM bf_market_gol MARK WHERE MARK.id_evento = EVENTO.id_evento AND tipoQuota = "+Constants.QTA_ATTUALE+" ORDER BY MARK.dataRichiesta DESC) AS ATTUALE_GOL, "
+ "(SELECT MARK.quota_noGol FROM bf_market_gol MARK WHERE MARK.id_evento = EVENTO.id_evento AND tipoQuota = "+Constants.QTA_ATTUALE+" ORDER BY MARK.dataRichiesta DESC) AS ATTUALE_NO_GOL, "
+ "NAZ.nomeDisplay AS NAZ_DISPLAY, COMP.nomeDisplay AS COMP_DISPLAY, " // 33
//--------------------------------------------------------------------------

// BOOKMAKER_1
+ "(SELECT BOOKMAKER.name FROM raf_bookmakers BOOKMAKER WHERE BOOKMAKER.id_bookmaker = "+Constants.BOOKMAKER_1+" ) AS BOOKMAKER_1_NOME, "
// BOOKMAKER_1 APERTURA
+ "(SELECT MARK.quota_1 FROM raf_market_esito_finale AS MARK INNER JOIN raf_bookmakers AS BOOKMAKER ON MARK.id_raf_bookmakers = BOOKMAKER.id_raf_bookmakers "
+ "AND MARK.tipoQuota = "+Constants.QTA_APERTURA+" AND BOOKMAKER.id_bookmaker = "+Constants.BOOKMAKER_1+" WHERE MARK.id_raf_evento = RAF_EVENTO.id_raf_evento) AS BOOKMAKER_1_APERT_1, "
+ "(SELECT MARK.quota_X FROM raf_market_esito_finale AS MARK INNER JOIN raf_bookmakers AS BOOKMAKER ON MARK.id_raf_bookmakers = BOOKMAKER.id_raf_bookmakers "
+ "AND MARK.tipoQuota = "+Constants.QTA_APERTURA+" AND BOOKMAKER.id_bookmaker = "+Constants.BOOKMAKER_1+" WHERE MARK.id_raf_evento = RAF_EVENTO.id_raf_evento) AS BOOKMAKER_1_APERT_X, "
+ "(SELECT MARK.quota_2 FROM raf_market_esito_finale AS MARK INNER JOIN raf_bookmakers AS BOOKMAKER ON MARK.id_raf_bookmakers = BOOKMAKER.id_raf_bookmakers "
+ "AND MARK.tipoQuota = "+Constants.QTA_APERTURA+" AND BOOKMAKER.id_bookmaker = "+Constants.BOOKMAKER_1+" WHERE MARK.id_raf_evento = RAF_EVENTO.id_raf_evento) AS BOOKMAKER_1_APERT_2, "
+ "(SELECT MARK.quota_over FROM raf_market_over_under_25 AS MARK INNER JOIN raf_bookmakers AS BOOKMAKER ON MARK.id_raf_bookmakers = BOOKMAKER.id_raf_bookmakers "
+ "AND MARK.tipoQuota = "+Constants.QTA_APERTURA+" AND BOOKMAKER.id_bookmaker = "+Constants.BOOKMAKER_1+" WHERE MARK.id_raf_evento = RAF_EVENTO.id_raf_evento) AS BOOKMAKER_1_APERT_OVER, "
+ "(SELECT MARK.quota_under FROM raf_market_over_under_25 AS MARK INNER JOIN raf_bookmakers AS BOOKMAKER ON MARK.id_raf_bookmakers = BOOKMAKER.id_raf_bookmakers  "
+ "AND MARK.tipoQuota = "+Constants.QTA_APERTURA+" AND BOOKMAKER.id_bookmaker = "+Constants.BOOKMAKER_1+" WHERE MARK.id_raf_evento = RAF_EVENTO.id_raf_evento) AS BOOKMAKER_1_APERT_UNDER, "
+ "(SELECT MARK.quota_gol FROM raf_market_gol AS MARK INNER JOIN raf_bookmakers AS BOOKMAKER ON MARK.id_raf_bookmakers = BOOKMAKER.id_raf_bookmakers "
+ "AND MARK.tipoQuota = "+Constants.QTA_APERTURA+" AND BOOKMAKER.id_bookmaker = "+Constants.BOOKMAKER_1+" WHERE MARK.id_raf_evento = RAF_EVENTO.id_raf_evento) AS BOOKMAKER_1_APERT_GOL, "
+ "(SELECT MARK.quota_noGol FROM raf_market_gol AS MARK INNER JOIN raf_bookmakers AS BOOKMAKER ON MARK.id_raf_bookmakers = BOOKMAKER.id_raf_bookmakers "
+ "AND MARK.tipoQuota = "+Constants.QTA_APERTURA+" AND BOOKMAKER.id_bookmaker = "+Constants.BOOKMAKER_1+" WHERE MARK.id_raf_evento = RAF_EVENTO.id_raf_evento) AS BOOKMAKER_1_APERT_NO_GOL, "
// BOOKMAKER_1 ATTUALE
+ "(SELECT MARK.quota_1 FROM raf_market_esito_finale AS MARK INNER JOIN raf_bookmakers AS BOOKMAKER ON MARK.id_raf_bookmakers = BOOKMAKER.id_raf_bookmakers "
+ "AND MARK.tipoQuota = "+Constants.QTA_ATTUALE+" AND BOOKMAKER.id_bookmaker = "+Constants.BOOKMAKER_1+" WHERE MARK.id_raf_evento = RAF_EVENTO.id_raf_evento) AS BOOKMAKER_1_ATTUALE_1, "
+ "(SELECT MARK.quota_X FROM raf_market_esito_finale AS MARK INNER JOIN raf_bookmakers AS BOOKMAKER ON MARK.id_raf_bookmakers = BOOKMAKER.id_raf_bookmakers "
+ "AND MARK.tipoQuota = "+Constants.QTA_ATTUALE+" AND BOOKMAKER.id_bookmaker = "+Constants.BOOKMAKER_1+" WHERE MARK.id_raf_evento = RAF_EVENTO.id_raf_evento) AS BOOKMAKER_1_ATTUALE_X, "
+ "(SELECT MARK.quota_2 FROM raf_market_esito_finale AS MARK INNER JOIN raf_bookmakers AS BOOKMAKER ON MARK.id_raf_bookmakers = BOOKMAKER.id_raf_bookmakers "
+ "AND MARK.tipoQuota = "+Constants.QTA_ATTUALE+" AND BOOKMAKER.id_bookmaker = "+Constants.BOOKMAKER_1+" WHERE MARK.id_raf_evento = RAF_EVENTO.id_raf_evento) AS BOOKMAKER_1_ATTUALE_2, "
+ "(SELECT MARK.quota_over FROM raf_market_over_under_25 AS MARK INNER JOIN raf_bookmakers AS BOOKMAKER ON MARK.id_raf_bookmakers = BOOKMAKER.id_raf_bookmakers "
+ "AND MARK.tipoQuota = "+Constants.QTA_ATTUALE+" AND BOOKMAKER.id_bookmaker = "+Constants.BOOKMAKER_1+" WHERE MARK.id_raf_evento = RAF_EVENTO.id_raf_evento) AS BOOKMAKER_1_ATTUALE_OVER, "
+ "(SELECT MARK.quota_under FROM raf_market_over_under_25 AS MARK INNER JOIN raf_bookmakers AS BOOKMAKER ON MARK.id_raf_bookmakers = BOOKMAKER.id_raf_bookmakers "
+ "AND MARK.tipoQuota = "+Constants.QTA_ATTUALE+" AND BOOKMAKER.id_bookmaker = "+Constants.BOOKMAKER_1+" WHERE MARK.id_raf_evento = RAF_EVENTO.id_raf_evento) AS BOOKMAKER_1_ATTUALE_UNDER, "
+ "(SELECT MARK.quota_gol FROM raf_market_gol AS MARK INNER JOIN raf_bookmakers AS BOOKMAKER ON MARK.id_raf_bookmakers = BOOKMAKER.id_raf_bookmakers "
+ "AND MARK.tipoQuota = "+Constants.QTA_ATTUALE+" AND BOOKMAKER.id_bookmaker = "+Constants.BOOKMAKER_1+" WHERE MARK.id_raf_evento = RAF_EVENTO.id_raf_evento) AS BOOKMAKER_1_ATTUALE_GOL, "
+ "(SELECT MARK.quota_noGol FROM raf_market_gol AS MARK INNER JOIN raf_bookmakers AS BOOKMAKER ON MARK.id_raf_bookmakers = BOOKMAKER.id_raf_bookmakers "
+ "AND MARK.tipoQuota = "+Constants.QTA_ATTUALE+" AND BOOKMAKER.id_bookmaker = "+Constants.BOOKMAKER_1+" WHERE MARK.id_raf_evento = RAF_EVENTO.id_raf_evento) AS BOOKMAKER_1_ATTUALE_NO_GOL, "

// BOOKMAKER_2
+ "(SELECT BOOKMAKER.name FROM raf_bookmakers BOOKMAKER WHERE BOOKMAKER.id_bookmaker = "+Constants.BOOKMAKER_2+" ) AS BOOKMAKER_2_NOME, "
// BOOKMAKER_2 APERTURA
+ "(SELECT MARK.quota_1 FROM raf_market_esito_finale AS MARK INNER JOIN raf_bookmakers AS BOOKMAKER ON MARK.id_raf_bookmakers = BOOKMAKER.id_raf_bookmakers "
+ "AND MARK.tipoQuota = "+Constants.QTA_APERTURA+" AND BOOKMAKER.id_bookmaker = "+Constants.BOOKMAKER_2+" WHERE MARK.id_raf_evento = RAF_EVENTO.id_raf_evento) AS BOOKMAKER_2_APERT_1, "
+ "(SELECT MARK.quota_X FROM raf_market_esito_finale AS MARK INNER JOIN raf_bookmakers AS BOOKMAKER ON MARK.id_raf_bookmakers = BOOKMAKER.id_raf_bookmakers "
+ "AND MARK.tipoQuota = "+Constants.QTA_APERTURA+" AND BOOKMAKER.id_bookmaker = "+Constants.BOOKMAKER_2+" WHERE MARK.id_raf_evento = RAF_EVENTO.id_raf_evento) AS BOOKMAKER_2_APERT_X, "
+ "(SELECT MARK.quota_2 FROM raf_market_esito_finale AS MARK INNER JOIN raf_bookmakers AS BOOKMAKER ON MARK.id_raf_bookmakers = BOOKMAKER.id_raf_bookmakers "
+ "AND MARK.tipoQuota = "+Constants.QTA_APERTURA+" AND BOOKMAKER.id_bookmaker = "+Constants.BOOKMAKER_2+" WHERE MARK.id_raf_evento = RAF_EVENTO.id_raf_evento) AS BOOKMAKER_2_APERT_2, "
+ "(SELECT MARK.quota_over FROM raf_market_over_under_25 AS MARK INNER JOIN raf_bookmakers AS BOOKMAKER ON MARK.id_raf_bookmakers = BOOKMAKER.id_raf_bookmakers "
+ "AND MARK.tipoQuota = "+Constants.QTA_APERTURA+" AND BOOKMAKER.id_bookmaker = "+Constants.BOOKMAKER_2+" WHERE MARK.id_raf_evento = RAF_EVENTO.id_raf_evento) AS BOOKMAKER_2_APERT_OVER, "
+ "(SELECT MARK.quota_under FROM raf_market_over_under_25 AS MARK INNER JOIN raf_bookmakers AS BOOKMAKER ON MARK.id_raf_bookmakers = BOOKMAKER.id_raf_bookmakers "
+ "AND MARK.tipoQuota = "+Constants.QTA_APERTURA+" AND BOOKMAKER.id_bookmaker = "+Constants.BOOKMAKER_2+" WHERE MARK.id_raf_evento = RAF_EVENTO.id_raf_evento) AS BOOKMAKER_2_APERT_UNDER, "
+ "(SELECT MARK.quota_gol FROM raf_market_gol AS MARK INNER JOIN raf_bookmakers AS BOOKMAKER ON MARK.id_raf_bookmakers = BOOKMAKER.id_raf_bookmakers "
+ "AND MARK.tipoQuota = "+Constants.QTA_APERTURA+" AND BOOKMAKER.id_bookmaker = "+Constants.BOOKMAKER_2+" WHERE MARK.id_raf_evento = RAF_EVENTO.id_raf_evento) AS BOOKMAKER_2_APERT_GOL, "
+ "(SELECT MARK.quota_noGol FROM raf_market_gol AS MARK INNER JOIN raf_bookmakers AS BOOKMAKER ON MARK.id_raf_bookmakers = BOOKMAKER.id_raf_bookmakers "
+ "AND MARK.tipoQuota = "+Constants.QTA_APERTURA+" AND BOOKMAKER.id_bookmaker = "+Constants.BOOKMAKER_2+" WHERE MARK.id_raf_evento = RAF_EVENTO.id_raf_evento) AS BOOKMAKER_2_APERT_NO_GOL, "
// BOOKMAKER_2 ATTUALE
+ "(SELECT MARK.quota_1 FROM raf_market_esito_finale AS MARK INNER JOIN raf_bookmakers AS BOOKMAKER ON MARK.id_raf_bookmakers = BOOKMAKER.id_raf_bookmakers "
+ "AND MARK.tipoQuota = "+Constants.QTA_ATTUALE+" AND BOOKMAKER.id_bookmaker = "+Constants.BOOKMAKER_2+" WHERE MARK.id_raf_evento = RAF_EVENTO.id_raf_evento) AS BOOKMAKER_2_ATTUALE_1, "
+ "(SELECT MARK.quota_X FROM raf_market_esito_finale AS MARK INNER JOIN raf_bookmakers AS BOOKMAKER ON MARK.id_raf_bookmakers = BOOKMAKER.id_raf_bookmakers "
+ "AND MARK.tipoQuota = "+Constants.QTA_ATTUALE+" AND BOOKMAKER.id_bookmaker = "+Constants.BOOKMAKER_2+" WHERE MARK.id_raf_evento = RAF_EVENTO.id_raf_evento) AS BOOKMAKER_2_ATTUALE_X, "
+ "(SELECT MARK.quota_2 FROM raf_market_esito_finale AS MARK INNER JOIN raf_bookmakers AS BOOKMAKER ON MARK.id_raf_bookmakers = BOOKMAKER.id_raf_bookmakers "
+ "AND MARK.tipoQuota = "+Constants.QTA_ATTUALE+" AND BOOKMAKER.id_bookmaker = "+Constants.BOOKMAKER_2+" WHERE MARK.id_raf_evento = RAF_EVENTO.id_raf_evento) AS BOOKMAKER_2_ATTUALE_2, "
+ "(SELECT MARK.quota_over FROM raf_market_over_under_25 AS MARK INNER JOIN raf_bookmakers AS BOOKMAKER ON MARK.id_raf_bookmakers = BOOKMAKER.id_raf_bookmakers "
+ "AND MARK.tipoQuota = "+Constants.QTA_ATTUALE+" AND BOOKMAKER.id_bookmaker = "+Constants.BOOKMAKER_2+" WHERE MARK.id_raf_evento = RAF_EVENTO.id_raf_evento) AS BOOKMAKER_2_ATTUALE_OVER, "
+ "(SELECT MARK.quota_under FROM raf_market_over_under_25 AS MARK INNER JOIN raf_bookmakers AS BOOKMAKER ON MARK.id_raf_bookmakers = BOOKMAKER.id_raf_bookmakers "
+ "AND MARK.tipoQuota = "+Constants.QTA_ATTUALE+" AND BOOKMAKER.id_bookmaker = "+Constants.BOOKMAKER_2+" WHERE MARK.id_raf_evento = RAF_EVENTO.id_raf_evento) AS BOOKMAKER_2_ATTUALE_UNDER, "
+ "(SELECT MARK.quota_gol FROM raf_market_gol AS MARK INNER JOIN raf_bookmakers AS BOOKMAKER ON MARK.id_raf_bookmakers = BOOKMAKER.id_raf_bookmakers "
+ "AND MARK.tipoQuota = "+Constants.QTA_ATTUALE+" AND BOOKMAKER.id_bookmaker = "+Constants.BOOKMAKER_2+" WHERE MARK.id_raf_evento = RAF_EVENTO.id_raf_evento) AS BOOKMAKER_2_ATTUALE_GOL, "
+ "(SELECT MARK.quota_noGol FROM raf_market_gol AS MARK INNER JOIN raf_bookmakers AS BOOKMAKER ON MARK.id_raf_bookmakers = BOOKMAKER.id_raf_bookmakers "
+ "AND MARK.tipoQuota = "+Constants.QTA_ATTUALE+" AND BOOKMAKER.id_bookmaker = "+Constants.BOOKMAKER_2+" WHERE MARK.id_raf_evento = RAF_EVENTO.id_raf_evento) AS BOOKMAKER_2_ATTUALE_NO_GOL, "

//dal 41 al 70 

// BOOKMAKERS APERTURA (MEDIA) 
+ "(SELECT ROUND(AVG(MARK.quota_1), 2) FROM raf_market_esito_finale MARK WHERE MARK.id_raf_evento = RAF_EVENTO.id_raf_evento AND tipoQuota = "+Constants.QTA_APERTURA+" "
+ "AND MARK.quota_1 IS NOT NULL) AS BOOKMAKERS_APERT_MEDIA_1, "
+ "(SELECT ROUND(AVG(MARK.quota_X), 2) FROM raf_market_esito_finale MARK WHERE MARK.id_raf_evento = RAF_EVENTO.id_raf_evento AND tipoQuota = "+Constants.QTA_APERTURA+" "
+ "AND MARK.quota_X IS NOT NULL) AS BOOKMAKERS_APERT_MEDIA_X, "
+ "(SELECT ROUND(AVG(MARK.quota_2), 2) FROM raf_market_esito_finale MARK WHERE MARK.id_raf_evento = RAF_EVENTO.id_raf_evento AND tipoQuota = "+Constants.QTA_APERTURA+" "
+ "AND MARK.quota_2 IS NOT NULL) AS BOOKMAKERS_APERT_MEDIA_2, "
+ "(SELECT ROUND(AVG(MARK.quota_over), 2) FROM raf_market_over_under_25 MARK WHERE MARK.id_raf_evento = RAF_EVENTO.id_raf_evento AND tipoQuota = "+Constants.QTA_APERTURA+" "
+ "AND MARK.quota_over IS NOT NULL) AS BOOKMAKERS_APERT_MEDIA_OVER, "
+ "(SELECT ROUND(AVG(MARK.quota_under), 2) FROM raf_market_over_under_25 MARK WHERE MARK.id_raf_evento = RAF_EVENTO.id_raf_evento AND tipoQuota = "+Constants.QTA_APERTURA+" "
+ "AND MARK.quota_under IS NOT NULL) AS BOOKMAKERS_APERT_MEDIA_UNDER, "
+ "(SELECT ROUND(AVG(MARK.quota_gol), 2) FROM raf_market_gol MARK WHERE MARK.id_raf_evento = RAF_EVENTO.id_raf_evento AND tipoQuota = "+Constants.QTA_APERTURA+" "
+ "AND MARK.quota_gol IS NOT NULL) AS BOOKMAKERS_APERT_MEDIA_GOL, "
+ "(SELECT ROUND(AVG(MARK.quota_noGol), 2) FROM raf_market_gol MARK WHERE MARK.id_raf_evento = RAF_EVENTO.id_raf_evento AND tipoQuota = "+Constants.QTA_APERTURA+" "
+ "AND MARK.quota_noGol IS NOT NULL) AS BOOKMAKERS_APERT_MEDIA_NO_GOL, "

//BOOKMAKERS ATTUALE (MEDIA) 
+ VAR_BOOKMAKERS_ATTUALE_MEDIA_1 + 		"AS "+ Constants.BOOKMAKERS_ATTUALE_MEDIA_1+", "
+ VAR_BOOKMAKERS_ATTUALE_MEDIA_X + 		"AS "+ Constants.BOOKMAKERS_ATTUALE_MEDIA_X+", "
+ VAR_BOOKMAKERS_ATTUALE_MEDIA_2 + 		"AS "+ Constants.BOOKMAKERS_ATTUALE_MEDIA_2+", "
+ VAR_BOOKMAKERS_ATTUALE_MEDIA_OVER + 	"AS "+ Constants.BOOKMAKERS_ATTUALE_MEDIA_OVER+", "
+ VAR_BOOKMAKERS_ATTUALE_MEDIA_UNDER + 	"AS "+ Constants.BOOKMAKERS_ATTUALE_MEDIA_UNDER+", "
+ VAR_BOOKMAKERS_ATTUALE_MEDIA_GOL +	"AS "+ Constants.BOOKMAKERS_ATTUALE_MEDIA_GOL+", "
+ VAR_BOOKMAKERS_ATTUALE_MEDIA_NO_GOL +	"AS "+ Constants.BOOKMAKERS_ATTUALE_MEDIA_NO_GOL+", "

+ "CASE WHEN "+ VAR_BOOKMAKERS_ATTUALE_MEDIA_1 + "IS NOT NULL THEN 1 "+ "ELSE 2 END AS "+Constants.BOOKMAKERS_ATTUALE_MEDIA_1+_IS_NOT_NULL+", " 
+ "CASE WHEN "+ VAR_BOOKMAKERS_ATTUALE_MEDIA_X + "IS NOT NULL THEN 1 "+ "ELSE 2 END AS "+Constants.BOOKMAKERS_ATTUALE_MEDIA_X+_IS_NOT_NULL+", " 
+ "CASE WHEN "+ VAR_BOOKMAKERS_ATTUALE_MEDIA_2 + "IS NOT NULL THEN 1 "+ "ELSE 2 END AS "+Constants.BOOKMAKERS_ATTUALE_MEDIA_2+_IS_NOT_NULL+", " 
+ "CASE WHEN "+ VAR_BOOKMAKERS_ATTUALE_MEDIA_OVER + "IS NOT NULL THEN 1 "+ "ELSE 2 END AS "+Constants.BOOKMAKERS_ATTUALE_MEDIA_OVER+_IS_NOT_NULL+", " 
+ "CASE WHEN "+ VAR_BOOKMAKERS_ATTUALE_MEDIA_UNDER + "IS NOT NULL THEN 1 "+ "ELSE 2 END AS "+Constants.BOOKMAKERS_ATTUALE_MEDIA_UNDER+_IS_NOT_NULL+", " 
+ "CASE WHEN "+ VAR_BOOKMAKERS_ATTUALE_MEDIA_GOL + "IS NOT NULL THEN 1 "+ "ELSE 2 END AS "+Constants.BOOKMAKERS_ATTUALE_MEDIA_GOL+_IS_NOT_NULL+", " 
+ "CASE WHEN "+ VAR_BOOKMAKERS_ATTUALE_MEDIA_NO_GOL + "IS NOT NULL THEN 1 "+ "ELSE 2 END AS "+Constants.BOOKMAKERS_ATTUALE_MEDIA_NO_GOL+_IS_NOT_NULL+", " 

//dal 85 al 91

// QUOTE SAMBET
+ "(SELECT Q_SAMBET.quota_1 FROM raf_quote_sambet Q_SAMBET WHERE Q_SAMBET.id_raf_evento = RAF_EVENTO.id_raf_evento) AS QUOTE_SAMBET_1, "
+ "(SELECT Q_SAMBET.quota_X FROM raf_quote_sambet Q_SAMBET WHERE Q_SAMBET.id_raf_evento = RAF_EVENTO.id_raf_evento) AS QUOTE_SAMBET_X, "
+ "(SELECT Q_SAMBET.quota_2 FROM raf_quote_sambet Q_SAMBET WHERE Q_SAMBET.id_raf_evento = RAF_EVENTO.id_raf_evento) AS QUOTE_SAMBET_2, "
+ "(SELECT Q_SAMBET.quota_over FROM raf_quote_sambet Q_SAMBET WHERE Q_SAMBET.id_raf_evento = RAF_EVENTO.id_raf_evento) AS QUOTE_SAMBET_OVER, "
+ "(SELECT Q_SAMBET.quota_under FROM raf_quote_sambet Q_SAMBET WHERE Q_SAMBET.id_raf_evento = RAF_EVENTO.id_raf_evento) AS QUOTE_SAMBET_UNDER, "
+ "(SELECT Q_SAMBET.quota_gol FROM raf_quote_sambet Q_SAMBET WHERE Q_SAMBET.id_raf_evento = RAF_EVENTO.id_raf_evento) AS QUOTE_SAMBET_GOL, "
+ "(SELECT Q_SAMBET.quota_noGol FROM raf_quote_sambet Q_SAMBET WHERE Q_SAMBET.id_raf_evento = RAF_EVENTO.id_raf_evento) AS QUOTE_SAMBET_NO_GOL "


//--------------------------------------------------------------------------
+ "FROM bf_nazioni AS NAZ INNER JOIN bf_competizioni AS COMP ON NAZ.id_nazione = COMP.id_nazione AND NAZ.attivo = true AND COMP.attivo = true "
+ (mainTableFiltri != null && mainTableFiltri.getSiglaNazione() != null && !mainTableFiltri.getSiglaNazione().equals(Constants.OPTION_SELECT_NOME_TUTTI) ? "AND NAZ.siglaNazione = :siglaNazione " : "")
+ (mainTableFiltri != null && CheckNull_idArrayListCompetizione(mainTableFiltri.getIdArrayListCompetizione()) && mainTableFiltri.getIdArrayListCompetizione().get(0) != Constants.OPTION_SELECT_ID_TUTTI 
		&& mainTableFiltri.getIdArrayListCompetizione().size() > 0 ? "AND COMP.id_competizione IN :idArrayListCompetizione " : "")

+ "INNER JOIN bf_eventi AS EVENTO ON COMP.id_competizione = EVENTO.id_competizione AND EVENTO.dataEvento > NOW() "

+ (mainTableFiltri != null && mainTableFiltri.getInizioPartita() != null ? "AND EVENTO.dataEvento >= :inizioPartita " : "" )
+ (mainTableFiltri != null && mainTableFiltri.getFinePartita() != null ? "AND EVENTO.dataEvento <= :finePartita " : "" )
+ (mainTableFiltri != null && mainTableFiltri.getNomePartita() != null ? "AND EVENTO.nome LIKE :nomePartita " : "" )

+ "LEFT JOIN raf_eventi AS RAF_EVENTO ON EVENTO.id_raf_evento = RAF_EVENTO.id_raf_evento "

+ OrderBy;
//+ "ORDER BY NAZ.siglaNazione ASC, COMP.nome ASC, EVENTO.dataEvento ASC, EVENTO.nome ASC ";

//System.out.println( "queryString: "+queryString );

		SQLQuery sQLQuery = this.getSession().createSQLQuery( queryString );
		if(mainTableFiltri != null && mainTableFiltri.getSiglaNazione() != null && !mainTableFiltri.getSiglaNazione().equals(Constants.OPTION_SELECT_NOME_TUTTI) ) {
			sQLQuery.setParameter("siglaNazione", mainTableFiltri.getSiglaNazione());
		}
		if(mainTableFiltri != null && CheckNull_idArrayListCompetizione(mainTableFiltri.getIdArrayListCompetizione()) && mainTableFiltri.getIdArrayListCompetizione().get(0) != Constants.OPTION_SELECT_ID_TUTTI 
				&& mainTableFiltri.getIdArrayListCompetizione().size() > 0 ) {
			sQLQuery.setParameterList("idArrayListCompetizione", mainTableFiltri.getIdArrayListCompetizione());
		}
		if(mainTableFiltri != null && mainTableFiltri.getDataInizioPartita() != null) {
			sQLQuery.setParameter("inizioPartita", mainTableFiltri.getInizioPartita());
		}
		if(mainTableFiltri != null && mainTableFiltri.getDataFinePartita() != null) {
			sQLQuery.setParameter("finePartita", mainTableFiltri.getFinePartita());
		}
		if(mainTableFiltri != null && mainTableFiltri.getNomePartita() != null) {
			sQLQuery.setParameter("nomePartita", "%"+mainTableFiltri.getNomePartita()+"%");
		}
		
		List<Object[]> resultList = sQLQuery.list();
		
		//System.out.println("resultList.size: "+resultList.size());
		
		/*
		for(Object[] ite_object: resultList) {
			String 		var_0 = (String)ite_object[0];
			String 		var_1 = (String)ite_object[1];
			String 		var_2 = (String)ite_object[2];
			Date		var_3 = (Date)ite_object[3];
			
			// APERTURA bf_market_esito_finale -------------------------------------
			Double 		var_4 = ite_object[4] != null ? (Double)ite_object[4] : null;
			var_4 = var_4 != null ? Math.round(var_4 * 100.0) / 100.0 : null;
			Double 		var_5 = ite_object[5] != null ? (Double)ite_object[5] : null;
			var_5 = var_5 != null ? Math.round(var_5 * 100.0) / 100.0 : null; 
			Double 		var_6 = ite_object[6] != null ? (Double)ite_object[6] : null;
			var_6 = var_6 != null ? Math.round(var_6 * 100.0) / 100.0 : null;
			
			// da 85 a 91
			Double 		var_85 = ite_object[85] != null ? (Double)ite_object[85] : null;
			var_85 = var_85 != null ? Math.round(var_85 * 100.0) / 100.0 : null;
			
			//String 		var_2 = (String)ite_object[2];
			//Double 		var_3 = (Double)ite_object[3];
			//Double 		var_4 = (Double)ite_object[4];
			//BigDecimal 	var_5 = (BigDecimal)ite_object[5];
			// distanza
			//Long 		var_6 = ite_object[6] != null ? ((BigInteger)ite_object[6]).longValue() : null;
			System.out.println(var_0+" | " +var_1+" | "+var_2+" | "+var_3+" | "+var_4+" | "+var_5+" | "+var_85); 
			System.out.println("#####################");
		}
		*/
		
		return resultList;
	}
	

	private static boolean CheckNull_idArrayListCompetizione(List<Long> idArrayListCompetizione) {
		if( idArrayListCompetizione != null && idArrayListCompetizione.size() > 0 ) {
			return true;
		}else {
			return false;
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
