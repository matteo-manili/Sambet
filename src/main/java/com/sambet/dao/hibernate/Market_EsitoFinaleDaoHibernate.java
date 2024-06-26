package com.sambet.dao.hibernate;

import java.util.List;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate4.HibernateJdbcException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import com.sambet.dao.Market_EsitoFinaleDao;
import com.sambet.model.Eventi;
import com.sambet.model.Market_EsitoFinale;


/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
@EnableTransactionManagement
@Repository("Market_EsitoFinaleDao")
public class Market_EsitoFinaleDaoHibernate extends GenericDaoHibernate<Market_EsitoFinale, Long> implements Market_EsitoFinaleDao {

	public Market_EsitoFinaleDaoHibernate() {
		super(Market_EsitoFinale.class);
	}
	
	@Override
    @Transactional(readOnly = true)
	public Market_EsitoFinale get(Long id){
		Market_EsitoFinale regioni = (Market_EsitoFinale) getSession().get(Market_EsitoFinale.class, id);
		return regioni;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<Market_EsitoFinale> getMarket_EsitoFinale() {
        return getSession().createCriteria(Market_EsitoFinale.class).addOrder(Order.desc("id")).list();
	}

	@Override
	@Transactional
	public Market_EsitoFinale saveMarket_EsitoFinale(Market_EsitoFinale market_EsitoFinale) throws DataIntegrityViolationException, HibernateJdbcException {
		getSession().saveOrUpdate(market_EsitoFinale);
		//getSession().flush();
		return market_EsitoFinale;
	}
	
	
	@Override
    @Transactional(readOnly = true)
	public Market_EsitoFinale getMarket_EsitoFinale_Evento_Quota1_Quota2_QuotaX(Long idEvento, Double quota_1, Double quota_2, Double quota_X) {
		Criterion crit1 = Restrictions.and(
				Restrictions.eq("evento.id", idEvento),
				Restrictions.eq("quota_1", quota_1),
				Restrictions.eq("quota_2", quota_2),
				Restrictions.eq("quota_X", quota_X));
		return (Market_EsitoFinale) getSession().createCriteria(Market_EsitoFinale.class).add( crit1 ).uniqueResult();
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<Market_EsitoFinale> getMarket_EsitoFinale_Evento(long idEvento) {
        return getSession().createCriteria(Market_EsitoFinale.class).add( Restrictions.eq("evento.id", idEvento)).list();
	}
	
	

}
