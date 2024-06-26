package com.sambet.dao.hibernate;

import java.util.List;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate4.HibernateJdbcException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.sambet.Constants;
import com.sambet.dao.RafEventiDao;
import com.sambet.model.RafCompetizioni;
import com.sambet.model.RafEventi;


/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
@EnableTransactionManagement
@Repository("RafEventiDao")
public class RafEventiDaoHibernate extends GenericDaoHibernate<RafEventi, Long> implements RafEventiDao {

	public RafEventiDaoHibernate() {
		super(RafEventi.class);
	}
	
	@Override
    @Transactional(readOnly = true)
	public RafEventi get(Long id){
		RafEventi regioni = (RafEventi) getSession().get(RafEventi.class, id);
		return regioni;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<RafEventi> getRafEventi() {
        return getSession().createCriteria(RafEventi.class).addOrder(Order.asc("id")).list();
	}

	@Override
	@Transactional
	public RafEventi saveRafEventi(RafEventi eventi) throws DataIntegrityViolationException, HibernateJdbcException {
		getSession().saveOrUpdate(eventi);
		//getSession().flush();
		return eventi;
	}
	
	@Override
    @Transactional(readOnly = true)
	public RafEventi GetEvento_from_idFixture(int idFixture) {
		return (RafEventi) getSession().createCriteria(RafEventi.class).add( Restrictions.eq("fixture_id", idFixture) ).uniqueResult();
	}
	
	

	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<RafEventi> getRafEventi_EventiCampionato_NonAssegnati(List<Long> listLongIdRafCompetizioni, List<Long> listLongIdRafEventi) {
		Criterion crit1 = Restrictions.and(
				Restrictions.not(Restrictions.in("id", listLongIdRafEventi)),
				Restrictions.in("rafCompetizioni.id", listLongIdRafCompetizioni));
		List<RafEventi> result = getSession().createCriteria(RafEventi.class).createAlias("rafCompetizioni", "rafCompetizioni").add(crit1).addOrder(Order.asc("id")).list();
		return result;
	}
	

	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<RafEventi> getRafEventi_from_siglaNazione_dataRafEvento_nonPresenteInEventi(String siglaNazione, String eventDate) {
		
		String aaa = "SELECT RAF_EVENTO.id_raf_evento AS id, RAF_EVENTO.homeTeam AS homeTeam, RAF_EVENTO.awayTeam AS awayTeam, RAF_EVENTO.event_timestamp AS event_timestamp "
				
				+ "FROM raf_eventi AS RAF_EVENTO INNER JOIN raf_competizioni AS RAF_COMP ON RAF_EVENTO.id_raf_competizione = RAF_COMP.id_raf_competizione "
				+ "INNER JOIN bf_nazioni AS NAZ ON RAF_COMP.id_nazione = NAZ.id_nazione AND NAZ.siglaNazione = :siglaNazione AND RAF_EVENTO.event_date LIKE :eventDate "
				+ "WHERE RAF_EVENTO.id_raf_evento NOT IN (SELECT id_raf_evento FROM bf_eventi WHERE id_raf_evento IS NOT NULL ) "
				+ "ORDER BY RAF_EVENTO.id_raf_evento ASC ";
		
		List<RafEventi> result = getSession().createSQLQuery( aaa )
				.addScalar("id", StandardBasicTypes.LONG)
				.addScalar("homeTeam", StandardBasicTypes.STRING)
				.addScalar("awayTeam", StandardBasicTypes.STRING)
				.addScalar("event_timestamp", StandardBasicTypes.LONG)
				.setResultTransformer(Transformers.aliasToBean(RafEventi.class))
				.setParameter("siglaNazione", siglaNazione).setParameter("eventDate", eventDate+"%").list();
		
		/*
		log.info("SIZE: "+result.size());
		for(RafEventi ite: result) {
			log.info("id: "+ite.getId());
		}
		*/
		
		return result;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<RafEventi> getRafEventi_AssegnatiEventi() {
		String aaa = "SELECT RAF_EVENTO.id_raf_evento AS id, RAF_EVENTO.fixture_id AS fixture_id, RAF_EVENTO.league_id AS league_id "
				+ "FROM bf_eventi AS BF_EVENTO INNER JOIN raf_eventi RAF_EVENTO ON BF_EVENTO.id_raf_evento = RAF_EVENTO.id_raf_evento AND BF_EVENTO.dataEvento > NOW() "
				+ "ORDER BY RAF_EVENTO.id_raf_evento ASC ";
		
		List<RafEventi> result = getSession().createSQLQuery( aaa )
				.addScalar("id", StandardBasicTypes.LONG)
				.addScalar("fixture_id", StandardBasicTypes.INTEGER)
				.addScalar("league_id", StandardBasicTypes.INTEGER)
				.setResultTransformer(Transformers.aliasToBean(RafEventi.class)).list();
		return result;
	}
	
	
	@Override
    @Transactional(readOnly = true)
	public RafEventi getRafEventi_from_fixtureId(int fixture_id) {
		return (RafEventi) getSession().createCriteria(RafEventi.class).add( Restrictions.eq("fixture_id", fixture_id) ).uniqueResult();
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<RafEventi> getRafEventi_ApiMapping(int fixture_id, int updateAt) {
		String aaa = "SELECT  RAF_EVENTO.id_raf_evento AS id, RAF_EVENTO.fixture_id AS fixture_id, RAF_EVENTO.league_id AS league_id "
			+ "FROM bf_eventi AS BF_EVENTO INNER JOIN raf_eventi RAF_EVENTO ON BF_EVENTO.id_raf_evento = RAF_EVENTO.id_raf_evento AND BF_EVENTO.dataEvento > NOW() "
			+ "AND RAF_EVENTO.fixture_id = :fixture_id "
			+ "WHERE RAF_EVENTO.id_raf_evento IN (SELECT MARK_ESITO.id_raf_evento FROM raf_market_esito_finale MARK_ESITO WHERE MARK_ESITO.updateAt < :updateAt ) "
			+ "OR RAF_EVENTO.id_raf_evento IN (SELECT MARK_OVER_UNDER.id_raf_evento FROM raf_market_over_under_25 MARK_OVER_UNDER WHERE MARK_OVER_UNDER.updateAt < :updateAt ) "
			+ "OR RAF_EVENTO.id_raf_evento IN (SELECT MARK_GOL.id_raf_evento FROM raf_market_gol MARK_GOL WHERE MARK_GOL.updateAt < :updateAt ) "
			+ "ORDER BY RAF_EVENTO.id_raf_evento ASC ";
		
		List<RafEventi> result = getSession().createSQLQuery( aaa )
				.addScalar("id", StandardBasicTypes.LONG)
				.addScalar("fixture_id", StandardBasicTypes.INTEGER)
				.addScalar("league_id", StandardBasicTypes.INTEGER)
				.setResultTransformer(Transformers.aliasToBean(RafEventi.class))
				.setParameter("fixture_id", fixture_id).setParameter("updateAt", updateAt).list();
		return result;
	}
	
	
}
