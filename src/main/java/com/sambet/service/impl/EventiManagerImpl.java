package com.sambet.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate4.HibernateJdbcException;
import org.springframework.stereotype.Service;
import com.sambet.dao.EventiDao;
import com.sambet.model.Eventi;
import com.sambet.service.EventiManager;
import com.sambet.webapp.util.bean.MainTableFiltri;

/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
@Service("EventiManager")
public class EventiManagerImpl extends GenericManagerImpl<Eventi, Long> implements EventiManager {

	private EventiDao eventiDao;
	
	@Override
    @Autowired
	public void setEventiDao(EventiDao eventiDao) {
		this.eventiDao = eventiDao;
	}

	@Override
	public Eventi get(Long id) {
		return this.eventiDao.get(id);
	}
	
	@Override
	public List<Eventi> getEventi() {
		return eventiDao.getEventi();
	}

	@Override
	public Eventi saveEventi(Eventi eventi) throws DataIntegrityViolationException, HibernateJdbcException {
		return eventiDao.saveEventi(eventi);
	}

	@Override
    public void removeEventi(long id) {
		eventiDao.remove(id);
    }
	
	
	@Override
	public List<Object[]> Report_1(MainTableFiltri mainTableFiltri) {
		return eventiDao.Report_1(mainTableFiltri);
	}
	
	
	
	
}
