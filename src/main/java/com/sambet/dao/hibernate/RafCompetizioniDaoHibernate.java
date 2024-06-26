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
import com.sambet.dao.RafCompetizioniDao;
import com.sambet.model.RafCompetizioni;


/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
@EnableTransactionManagement
@Repository("RafCompetizioniDao")
public class RafCompetizioniDaoHibernate extends GenericDaoHibernate<RafCompetizioni, Long> implements RafCompetizioniDao {

	public RafCompetizioniDaoHibernate() {
		super(RafCompetizioni.class);
	}
	
	@Override
    @Transactional(readOnly = true)
	public RafCompetizioni get(Long id){
		RafCompetizioni competizioni = (RafCompetizioni) getSession().get(RafCompetizioni.class, id);
		return competizioni;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<RafCompetizioni> getRafCompetizioni() {
        return getSession().createCriteria(RafCompetizioni.class).addOrder(Order.asc("name")).list();
	}

	@Override
	@Transactional
	public RafCompetizioni saveRafCompetizioni(RafCompetizioni competizioni) throws DataIntegrityViolationException, HibernateJdbcException {
		getSession().saveOrUpdate(competizioni);
		//getSession().flush();
		return competizioni;
	}
	
	@Override
    @Transactional(readOnly = true)
	public RafCompetizioni GetCompetizione_from_legueId(int league_id) {
		return (RafCompetizioni) getSession().createCriteria(RafCompetizioni.class).add( Restrictions.eq("league_id", league_id) ).uniqueResult();
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Object[]> Filtri_Nazione_Competizione() {
		
		return null;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<RafCompetizioni> getRafCompetizioni_from_siglaNazione_attivo(String siglaNazione, boolean attvo) {
		Criterion crit1 = Restrictions.and(Restrictions.eq("NAZ.siglaNazione", siglaNazione), Restrictions.eq("attivo", attvo));
		
        return getSession().createCriteria(RafCompetizioni.class).createAlias("nazione", "NAZ").add( crit1 ).list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<RafCompetizioni> getRafCompetizioni_AssegnatiEventi() {
		
		String aaa = "SELECT RAF_COMP.id_raf_competizione AS id, RAF_COMP.league_id AS league_id "
				+ "FROM raf_competizioni AS RAF_COMP INNER JOIN raf_eventi AS RAF_EVENTO ON RAF_COMP.id_raf_competizione = RAF_EVENTO.id_raf_competizione  "
				+ "WHERE RAF_EVENTO.id_raf_evento IN (SELECT id_raf_evento FROM bf_eventi WHERE id_raf_evento IS NOT NULL AND dataEvento > NOW() ) "
				+ "ORDER BY RAF_COMP.id_raf_competizione ASC ";
		
		List<RafCompetizioni> result = getSession().createSQLQuery( aaa )
				.addScalar("id", StandardBasicTypes.LONG)
				.addScalar("league_id", StandardBasicTypes.INTEGER)
				.setResultTransformer(Transformers.aliasToBean(RafCompetizioni.class)).list();
		return result;
	}

}
