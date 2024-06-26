package com.sambet.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate4.HibernateJdbcException;
import org.springframework.stereotype.Service;

import com.sambet.dao.CompetizioniDao;
import com.sambet.model.Competizioni;
import com.sambet.service.CompetizioniManager;

/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
@Service("CompetizioniManager")
public class CompetizioniManagerImpl extends GenericManagerImpl<Competizioni, Long> implements CompetizioniManager {

	private CompetizioniDao competizioniDao;
	
	@Override
    @Autowired
	public void setCompetizioniDao(CompetizioniDao competizioniDao) {
		this.competizioniDao = competizioniDao;
	}

	
	
	@Override
	public Competizioni get(Long id) {
		return this.competizioniDao.get(id);
	}
	
	@Override
	public List<Competizioni> getCompetizioni() {
		return competizioniDao.getCompetizioni();
	}
	

	@Override
	public Competizioni saveCompetizioni(Competizioni competizioni) throws DataIntegrityViolationException, HibernateJdbcException {
		return competizioniDao.saveCompetizioni(competizioni);
	}

	
	@Override
    public void removeCompetizioni(long id) {
		competizioniDao.remove(id);
    }
	
	
	
}
