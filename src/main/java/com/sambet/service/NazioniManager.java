package com.sambet.service;

import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate4.HibernateJdbcException;

import com.sambet.dao.NazioniDao;
import com.sambet.model.Nazioni;


/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
public interface NazioniManager extends GenericManager<Nazioni, Long> {
	
	void setNazioniDao(NazioniDao nazioniDao);
	
	Nazioni get(Long id);
	
	List<Nazioni> getNazioni();
	
	Nazioni saveNazioni(Nazioni nazioni) throws DataIntegrityViolationException, HibernateJdbcException;
	
	void removeNazioni(long idNazioni);


}
