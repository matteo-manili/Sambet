package com.sambet.service;

import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate4.HibernateJdbcException;

import com.sambet.dao.RafTeamsDao;
import com.sambet.model.RafTeams;


/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
public interface RafTeamsManager extends GenericManager<RafTeams, Long> {
	
	void setRafTeamsDao(RafTeamsDao nazioniDao);
	
	RafTeams get(Long id);
	
	List<RafTeams> getRafTeams();
	
	RafTeams saveRafTeams(RafTeams nazioni) throws DataIntegrityViolationException, HibernateJdbcException;
	
	void removeRafTeams(long idRafTeams);


}
