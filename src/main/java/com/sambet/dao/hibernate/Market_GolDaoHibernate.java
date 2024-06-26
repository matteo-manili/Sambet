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
import com.sambet.dao.Market_GolDao;
import com.sambet.model.Market_Gol;


/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
@EnableTransactionManagement
@Repository("Market_GolDao")
public class Market_GolDaoHibernate extends GenericDaoHibernate<Market_Gol, Long> implements Market_GolDao {

	public Market_GolDaoHibernate() {
		super(Market_Gol.class);
	}
	
	@Override
    @Transactional(readOnly = true)
	public Market_Gol get(Long id){
		Market_Gol regioni = (Market_Gol) getSession().get(Market_Gol.class, id);
		return regioni;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<Market_Gol> getMarket_Gol() {
        return getSession().createCriteria(Market_Gol.class).addOrder(Order.asc("nomeNazione")).list();
	}

	@Override
	@Transactional
	public Market_Gol saveMarket_Gol(Market_Gol market_Gol) throws DataIntegrityViolationException, HibernateJdbcException {
		getSession().saveOrUpdate(market_Gol);
		//getSession().flush();
		return market_Gol;
	}
	
	@Override
    @Transactional(readOnly = true)
	public Market_Gol getMarket_Gol_QuotaGol_QuotaNoGol(Long idEvento, Double quota_gol, Double quota_noGol) {
		Criterion crit1 = Restrictions.and(
				Restrictions.eq("evento.id", idEvento),
				Restrictions.eq("quota_gol", quota_gol),
				Restrictions.eq("quota_noGol", quota_noGol));
		return (Market_Gol) getSession().createCriteria(Market_Gol.class).add( crit1 ).uniqueResult();
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<Market_Gol> getMarket_Gol_Evento(long idEvento) {
        return getSession().createCriteria(Market_Gol.class).add( Restrictions.eq("evento.id", idEvento)).list();
	}

}
