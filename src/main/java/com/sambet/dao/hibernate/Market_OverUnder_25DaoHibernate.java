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
import com.sambet.dao.Market_OverUnder_25Dao;
import com.sambet.model.Market_OverUnder_25;


/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
@EnableTransactionManagement
@Repository("Market_OverUnder_25Dao")
public class Market_OverUnder_25DaoHibernate extends GenericDaoHibernate<Market_OverUnder_25, Long> implements Market_OverUnder_25Dao {

	public Market_OverUnder_25DaoHibernate() {
		super(Market_OverUnder_25.class);
	}
	
	@Override
    @Transactional(readOnly = true)
	public Market_OverUnder_25 get(Long id){
		Market_OverUnder_25 regioni = (Market_OverUnder_25) getSession().get(Market_OverUnder_25.class, id);
		return regioni;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<Market_OverUnder_25> getMarket_OverUnder_25() {
        return getSession().createCriteria(Market_OverUnder_25.class).addOrder(Order.asc("nomeNazione")).list();
	}

	@Override
	@Transactional
	public Market_OverUnder_25 saveMarket_OverUnder_25(Market_OverUnder_25 market_OverUnder_25) throws DataIntegrityViolationException, HibernateJdbcException {
		getSession().saveOrUpdate(market_OverUnder_25);
		//getSession().flush();
		return market_OverUnder_25;
	}
	
	
	@Override
    @Transactional(readOnly = true)
	public Market_OverUnder_25 Market_OverUnder_25_QuotaOver_QuotaUnder(Long idEvento, Double quota_over, Double quota_under) {
		Criterion crit1 = Restrictions.and(
				Restrictions.eq("evento.id", idEvento),
				Restrictions.eq("quota_over", quota_over),
				Restrictions.eq("quota_under", quota_under));
		return (Market_OverUnder_25) getSession().createCriteria(Market_OverUnder_25.class).add( crit1 ).uniqueResult();
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<Market_OverUnder_25> getMarket_OverUnder_25_Evento(long idEvento) {
        return getSession().createCriteria(Market_OverUnder_25.class).add( Restrictions.eq("evento.id", idEvento)).list();
	}

}
