package com.sambet.service;

import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate4.HibernateJdbcException;

import com.sambet.dao.RafBookmakersDao;
import com.sambet.model.RafBookmakers;


/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
public interface RafBookmakersManager extends GenericManager<RafBookmakers, Long> {
	
	void setRafBookmakersDao(RafBookmakersDao nazioniDao);
	
	RafBookmakers get(Long id);
	
	List<RafBookmakers> getRafBookmakers();
	
	RafBookmakers saveRafBookmakers(RafBookmakers nazioni) throws DataIntegrityViolationException, HibernateJdbcException;
	
	void removeRafBookmakers(long idRafBookmakers);


}
