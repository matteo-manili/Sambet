package com.sambet.service;

import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate4.HibernateJdbcException;
import com.sambet.dao.RafCompetizioniDao;
import com.sambet.model.RafCompetizioni;


/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
public interface RafCompetizioniManager extends GenericManager<RafCompetizioni, Long> {
	
	void setRafCompetizioniDao(RafCompetizioniDao competizioniDao);
	
	RafCompetizioni get(Long id);
	
	List<RafCompetizioni> getRafCompetizioni();
	
	RafCompetizioni saveRafCompetizioni(RafCompetizioni competizioni) throws DataIntegrityViolationException, HibernateJdbcException;
	
	void removeRafCompetizioni(long idRafCompetizioni);


}
