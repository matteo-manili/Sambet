package com.sambet.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate4.HibernateJdbcException;
import org.springframework.stereotype.Service;
import com.sambet.dao.RafEventiDao;
import com.sambet.model.RafEventi;
import com.sambet.service.RafEventiManager;
import com.sambet.webapp.util.bean.MainTableFiltri;

/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
@Service("RafEventiManager")
public class RafEventiManagerImpl extends GenericManagerImpl<RafEventi, Long> implements RafEventiManager {

	private RafEventiDao eventiDao;
	
	@Override
    @Autowired
	public void setRafEventiDao(RafEventiDao eventiDao) {
		this.eventiDao = eventiDao;
	}

	@Override
	public RafEventi get(Long id) {
		return this.eventiDao.get(id);
	}
	
	@Override
	public List<RafEventi> getRafEventi() {
		return eventiDao.getRafEventi();
	}

	@Override
	public RafEventi saveRafEventi(RafEventi eventi) throws DataIntegrityViolationException, HibernateJdbcException {
		return eventiDao.saveRafEventi(eventi);
	}

	@Override
    public void removeRafEventi(long id) {
		eventiDao.remove(id);
    }
	
	

	
	
	
	
}
