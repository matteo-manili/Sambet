package com.sambet.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate4.HibernateJdbcException;
import org.springframework.stereotype.Service;

import com.sambet.dao.RafTeamsDao;
import com.sambet.model.RafTeams;
import com.sambet.service.RafTeamsManager;

/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
@Service("RafTeamsManager")
public class RafTeamsManagerImpl extends GenericManagerImpl<RafTeams, Long> implements RafTeamsManager {

	private RafTeamsDao nazioniDao;
	
	@Override
    @Autowired
	public void setRafTeamsDao(RafTeamsDao nazioniDao) {
		this.nazioniDao = nazioniDao;
	}

	
	
	@Override
	public RafTeams get(Long id) {
		return this.nazioniDao.get(id);
	}
	
	@Override
	public List<RafTeams> getRafTeams() {
		return nazioniDao.getRafTeams();
	}
	

	@Override
	public RafTeams saveRafTeams(RafTeams nazioni) throws DataIntegrityViolationException, HibernateJdbcException {
		return nazioniDao.saveRafTeams(nazioni);
	}

	
	@Override
    public void removeRafTeams(long id) {
		nazioniDao.remove(id);
    }
	
	
	
}
