package com.sambet.dao;

import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate4.HibernateJdbcException;
import com.sambet.model.ListaNazioni;


/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
public interface ListaNazioniDao extends GenericDao<ListaNazioni, Long> {
	
	ListaNazioni get(Long id);
	
	List<ListaNazioni> getListaNazioni();
	
	ListaNazioni saveListaNazioni(ListaNazioni listaNazioni) throws DataIntegrityViolationException, HibernateJdbcException;

	List<ListaNazioni> getListaNazioni_Status(int status);

	List<ListaNazioni> getListaNazioni_Status_MaxResults(int status, int maxResults);
	
	int Update_ListaNazioni_Status(int status);

	List<ListaNazioni> getListaNazioni_Attivo(boolean attivo);

	ListaNazioni getListaNazioni_from_siglaNazione(String siglaNazione);


}
