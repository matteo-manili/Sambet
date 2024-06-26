package com.sambet.dao.hibernate;

import java.util.List;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate4.HibernateJdbcException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.sambet.dao.RafQuoteSambetDao;
import com.sambet.model.RafQuoteSambet;


/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
@EnableTransactionManagement
@Repository("RafQuoteSambetDao")
public class RafQuoteSambetDaoHibernate extends GenericDaoHibernate<RafQuoteSambet, Long> implements RafQuoteSambetDao {

	public RafQuoteSambetDaoHibernate() {
		super(RafQuoteSambet.class);
	}
	
	@Override
    @Transactional(readOnly = true)
	public RafQuoteSambet get(Long id){
		RafQuoteSambet bookmakers = (RafQuoteSambet) getSession().get(RafQuoteSambet.class, id);
		return bookmakers;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<RafQuoteSambet> getRafQuoteSambet() {
        return getSession().createCriteria(RafQuoteSambet.class).addOrder(Order.asc("id")).list();
	}

	@Override
	@Transactional
	public RafQuoteSambet saveRafQuoteSambet(RafQuoteSambet rafQuoteSambet) throws DataIntegrityViolationException, HibernateJdbcException {
		getSession().saveOrUpdate(rafQuoteSambet);
		//getSession().flush();
		return rafQuoteSambet;
	}
	
	
	@Override
    @Transactional(readOnly = true)
	public RafQuoteSambet getQuoteSambet_from_idRafEvento(long idRafEvento) {
		return (RafQuoteSambet) getSession().createCriteria(RafQuoteSambet.class).add( Restrictions.eq("rafEvento.id", idRafEvento) ).uniqueResult();
	}
	


}
