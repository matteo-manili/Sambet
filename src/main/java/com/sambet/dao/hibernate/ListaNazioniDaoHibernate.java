package com.sambet.dao.hibernate;

import java.util.List;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate4.HibernateJdbcException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import com.sambet.dao.ListaNazioniDao;
import com.sambet.model.ListaNazioni;


/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
@EnableTransactionManagement
@Repository("ListaNazioniDao")
public class ListaNazioniDaoHibernate extends GenericDaoHibernate<ListaNazioni, Long> implements ListaNazioniDao {

	public ListaNazioniDaoHibernate() {
		super(ListaNazioni.class);
	}
	
	@Override
    @Transactional(readOnly = true)
	public ListaNazioni get(Long id){
		ListaNazioni regioni = (ListaNazioni) getSession().get(ListaNazioni.class, id);
		return regioni;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<ListaNazioni> getListaNazioni() {
        return getSession().createCriteria(ListaNazioni.class).addOrder(Order.asc("id")).list();
	}

	
	@Override
	@Transactional
	public ListaNazioni saveListaNazioni(ListaNazioni listaNazioni) throws DataIntegrityViolationException, HibernateJdbcException {
		getSession().saveOrUpdate(listaNazioni);
		//getSession().flush();
		return listaNazioni;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<ListaNazioni> getListaNazioni_Status(int status) {
        return getSession().createCriteria(ListaNazioni.class).add(Restrictions.and(Restrictions.eq("status", status))).addOrder(Order.asc("id")).list();
	}

	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<ListaNazioni> getListaNazioni_Status_MaxResults(int status, int maxResults){
		String aaa = "SELECT NAZ.id_lista_nazioni AS id, NAZ.siglaNazione AS siglaNazione, NAZ.status AS status FROM api_lista_nazioni NAZ WHERE status = :status LIMIT :maxResults ";
		List<ListaNazioni> result = getSession().createSQLQuery( aaa )
			.addScalar("id", StandardBasicTypes.LONG)
			.addScalar("siglaNazione", StandardBasicTypes.STRING)
			.addScalar("status", StandardBasicTypes.INTEGER)
			.setResultTransformer(Transformers.aliasToBean(ListaNazioni.class))
			.setParameter("status", status).setParameter("maxResults", maxResults).list();
		
		return result;
	}
	
	
	@Override
	@Transactional(readOnly = false)
	public int Update_ListaNazioni_Status(int status){
		String aaa = "UPDATE api_lista_nazioni SET status = :status ";
			return getSession().createSQLQuery( aaa ).setParameter("status", status).executeUpdate();
	}

	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<ListaNazioni> getListaNazioni_Attivo(boolean attivo) {
        return getSession().createCriteria(ListaNazioni.class).add(Restrictions.and(Restrictions.eq("attivo", attivo))).addOrder(Order.asc("id")).list();
	}


	@Override
    @Transactional(readOnly = true)
	public ListaNazioni getListaNazioni_from_siglaNazione(String siglaNazione){
		return (ListaNazioni) getSession().createCriteria(ListaNazioni.class).add(Restrictions.and(Restrictions.eq("siglaNazione", siglaNazione))).uniqueResult();
	}
}
