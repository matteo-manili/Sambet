package com.sambet.dao.hibernate;

import java.util.List;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate4.HibernateJdbcException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.sambet.dao.RafBookmakersDao;
import com.sambet.model.RafBookmakers;


/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
@EnableTransactionManagement
@Repository("RafBookmakersDao")
public class RafBookmakersDaoHibernate extends GenericDaoHibernate<RafBookmakers, Long> implements RafBookmakersDao {

	public RafBookmakersDaoHibernate() {
		super(RafBookmakers.class);
	}
	
	@Override
    @Transactional(readOnly = true)
	public RafBookmakers get(Long id){
		RafBookmakers bookmakers = (RafBookmakers) getSession().get(RafBookmakers.class, id);
		return bookmakers;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<RafBookmakers> getRafBookmakers() {
        return getSession().createCriteria(RafBookmakers.class).addOrder(Order.asc("id")).list();
	}

	@Override
	@Transactional
	public RafBookmakers saveRafBookmakers(RafBookmakers bookmakers) throws DataIntegrityViolationException, HibernateJdbcException {
		getSession().saveOrUpdate(bookmakers);
		//getSession().flush();
		return bookmakers;
	}
	
	
	@Override
    @Transactional(readOnly = true)
	public RafBookmakers getBookmakers_from_idBookmaker(int id_bookmaker){
		return (RafBookmakers) getSession().createCriteria(RafBookmakers.class).add( Restrictions.eq("id_bookmaker", id_bookmaker) ).uniqueResult();
	}
	


}
