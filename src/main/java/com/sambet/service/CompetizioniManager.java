package com.sambet.service;

import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate4.HibernateJdbcException;
import com.sambet.dao.CompetizioniDao;
import com.sambet.model.Competizioni;


/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
public interface CompetizioniManager extends GenericManager<Competizioni, Long> {
	
	void setCompetizioniDao(CompetizioniDao competizioniDao);
	
	Competizioni get(Long id);
	
	List<Competizioni> getCompetizioni();
	
	Competizioni saveCompetizioni(Competizioni competizioni) throws DataIntegrityViolationException, HibernateJdbcException;
	
	void removeCompetizioni(long idCompetizioni);


}
