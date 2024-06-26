package com.sambet.dao;

import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate4.HibernateJdbcException;
import com.sambet.model.Nazioni;


/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
public interface NazioniDao extends GenericDao<Nazioni, Long> {
	
	Nazioni get(Long id);
	
	List<Nazioni> getNazioni();

	Nazioni saveNazioni(Nazioni nazioni) throws DataIntegrityViolationException, HibernateJdbcException;

	Nazioni getNazione_from_SiglaNazione(String term);


}
