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

import com.sambet.dao.NazioniDao;
import com.sambet.model.Nazioni;


/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
@EnableTransactionManagement
@Repository("NazioniDao")
public class NazioniDaoHibernate extends GenericDaoHibernate<Nazioni, Long> implements NazioniDao {

	public NazioniDaoHibernate() {
		super(Nazioni.class);
	}
	
	@Override
    @Transactional(readOnly = true)
	public Nazioni get(Long id){
		Nazioni regioni = (Nazioni) getSession().get(Nazioni.class, id);
		return regioni;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<Nazioni> getNazioni() {
        return getSession().createCriteria(Nazioni.class).addOrder(Order.asc("nomeNazione")).list();
	}

	@Override
	@Transactional
	public Nazioni saveNazioni(Nazioni regioni) throws DataIntegrityViolationException, HibernateJdbcException {
		getSession().saveOrUpdate(regioni);
		//getSession().flush();
		return regioni;
	}
	
	
	@Override
    @Transactional(readOnly = true)
	public Nazioni getNazione_from_SiglaNazione(String term){
		return (Nazioni) getSession().createCriteria(Nazioni.class).add( Restrictions.eq("siglaNazione", term) ).uniqueResult();
	}
	


}
