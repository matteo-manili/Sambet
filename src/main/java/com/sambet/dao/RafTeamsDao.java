package com.sambet.dao;

import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate4.HibernateJdbcException;
import com.sambet.model.RafTeams;


/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
public interface RafTeamsDao extends GenericDao<RafTeams, Long> {
	
	RafTeams get(Long id);
	
	List<RafTeams> getRafTeams();

	RafTeams saveRafTeams(RafTeams nazioni) throws DataIntegrityViolationException, HibernateJdbcException;

	RafTeams getTeams_from_idTeam(int team_id);


}
