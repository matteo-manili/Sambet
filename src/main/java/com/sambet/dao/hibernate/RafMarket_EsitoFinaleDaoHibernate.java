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
import com.sambet.dao.RafMarket_EsitoFinaleDao;
import com.sambet.model.Eventi;
import com.sambet.model.RafMarket_EsitoFinale;


/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
@EnableTransactionManagement
@Repository("RafMarket_EsitoFinaleDao")
public class RafMarket_EsitoFinaleDaoHibernate extends GenericDaoHibernate<RafMarket_EsitoFinale, Long> implements RafMarket_EsitoFinaleDao {

	public RafMarket_EsitoFinaleDaoHibernate() {
		super(RafMarket_EsitoFinale.class);
	}
	
	@Override
    @Transactional(readOnly = true)
	public RafMarket_EsitoFinale get(Long id){
		RafMarket_EsitoFinale regioni = (RafMarket_EsitoFinale) getSession().get(RafMarket_EsitoFinale.class, id);
		return regioni;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<RafMarket_EsitoFinale> getRafMarket_EsitoFinale() {
        return getSession().createCriteria(RafMarket_EsitoFinale.class).addOrder(Order.desc("id")).list();
	}

	@Override
	@Transactional
	public RafMarket_EsitoFinale saveRafMarket_EsitoFinale(RafMarket_EsitoFinale rafMarket_EsitoFinale) throws DataIntegrityViolationException, HibernateJdbcException {
		getSession().saveOrUpdate(rafMarket_EsitoFinale);
		//getSession().flush();
		return rafMarket_EsitoFinale;
	}
	
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<RafMarket_EsitoFinale> getRafMarket_EsitoFinale_from_idRafEvento(int idRafEvento) {
        return getSession().createCriteria(RafMarket_EsitoFinale.class)
        		.createAlias("rafEvento", "RAF_EVENTO").add( Restrictions.eq("RAF_EVENTO.fixture_id", idRafEvento)).list();
	}
	
	

}
