package com.sambet.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate4.HibernateJdbcException;
import org.springframework.stereotype.Service;

import com.sambet.dao.RafBookmakersDao;
import com.sambet.model.RafBookmakers;
import com.sambet.service.RafBookmakersManager;

/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
@Service("RafBookmakersManager")
public class RafBookmakersManagerImpl extends GenericManagerImpl<RafBookmakers, Long> implements RafBookmakersManager {

	private RafBookmakersDao nazioniDao;
	
	@Override
    @Autowired
	public void setRafBookmakersDao(RafBookmakersDao nazioniDao) {
		this.nazioniDao = nazioniDao;
	}

	
	
	@Override
	public RafBookmakers get(Long id) {
		return this.nazioniDao.get(id);
	}
	
	@Override
	public List<RafBookmakers> getRafBookmakers() {
		return nazioniDao.getRafBookmakers();
	}
	

	@Override
	public RafBookmakers saveRafBookmakers(RafBookmakers nazioni) throws DataIntegrityViolationException, HibernateJdbcException {
		return nazioniDao.saveRafBookmakers(nazioni);
	}

	
	@Override
    public void removeRafBookmakers(long id) {
		nazioniDao.remove(id);
    }
	
	
	
}
