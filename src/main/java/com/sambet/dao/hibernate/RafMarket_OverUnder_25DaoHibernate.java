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
import com.sambet.dao.RafMarket_OverUnder_25Dao;
import com.sambet.model.Eventi;
import com.sambet.model.RafMarket_OverUnder_25;


/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
@EnableTransactionManagement
@Repository("RafMarket_OverUnder_25Dao")
public class RafMarket_OverUnder_25DaoHibernate extends GenericDaoHibernate<RafMarket_OverUnder_25, Long> implements RafMarket_OverUnder_25Dao {

	public RafMarket_OverUnder_25DaoHibernate() {
		super(RafMarket_OverUnder_25.class);
	}
	
	@Override
    @Transactional(readOnly = true)
	public RafMarket_OverUnder_25 get(Long id){
		RafMarket_OverUnder_25 regioni = (RafMarket_OverUnder_25) getSession().get(RafMarket_OverUnder_25.class, id);
		return regioni;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<RafMarket_OverUnder_25> getRafMarket_OverUnder_25() {
        return getSession().createCriteria(RafMarket_OverUnder_25.class).addOrder(Order.desc("id")).list();
	}

	@Override
	@Transactional
	public RafMarket_OverUnder_25 saveRafMarket_OverUnder_25(RafMarket_OverUnder_25 rafMarket_OverUnder_25) throws DataIntegrityViolationException, HibernateJdbcException {
		getSession().saveOrUpdate(rafMarket_OverUnder_25);
		//getSession().flush();
		return rafMarket_OverUnder_25;
	}
	
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<RafMarket_OverUnder_25> getRafMarket_OverUnder_25_from_idRafEvento(int idRafEvento) {
        return getSession().createCriteria(RafMarket_OverUnder_25.class)
        		.createAlias("rafEvento", "RAF_EVENTO").add( Restrictions.eq("RAF_EVENTO.fixture_id", idRafEvento)).list();
	}
	
	

}
