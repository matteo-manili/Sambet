package com.sambet.service;

import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate4.HibernateJdbcException;

import com.sambet.dao.ListaNazioniDao;
import com.sambet.model.ListaNazioni;


/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
public interface ListaNazioniManager extends GenericManager<ListaNazioni, Long> {
	
	void setListaNazioniDao(ListaNazioniDao listaNazioniDao);
	
	ListaNazioni get(Long id);
	
	List<ListaNazioni> getListaNazioni();
	
	ListaNazioni saveListaNazioni(ListaNazioni listaNazioni) throws DataIntegrityViolationException, HibernateJdbcException;
	
	void removeListaNazioni(long idListaNazioni);


}
