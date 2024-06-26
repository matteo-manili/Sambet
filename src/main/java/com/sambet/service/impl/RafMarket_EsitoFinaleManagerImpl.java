package com.sambet.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate4.HibernateJdbcException;
import org.springframework.stereotype.Service;

import com.sambet.dao.RafMarket_EsitoFinaleDao;
import com.sambet.model.RafMarket_EsitoFinale;
import com.sambet.service.RafMarket_EsitoFinaleManager;

/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
@Service("RafMarket_EsitoFinaleManager")
public class RafMarket_EsitoFinaleManagerImpl extends GenericManagerImpl<RafMarket_EsitoFinale, Long> implements RafMarket_EsitoFinaleManager {

	private RafMarket_EsitoFinaleDao rafMarket_EsitoFinaleDao;
	
	@Override
    @Autowired
	public void setRafMarket_EsitoFinaleDao(RafMarket_EsitoFinaleDao rafMarket_EsitoFinaleDao) {
		this.rafMarket_EsitoFinaleDao = rafMarket_EsitoFinaleDao;
	}

	
	
	@Override
	public RafMarket_EsitoFinale get(Long id) {
		return this.rafMarket_EsitoFinaleDao.get(id);
	}
	
	@Override
	public List<RafMarket_EsitoFinale> getRafMarket_EsitoFinale() {
		return rafMarket_EsitoFinaleDao.getRafMarket_EsitoFinale();
	}
	

	@Override
	public RafMarket_EsitoFinale saveRafMarket_EsitoFinale(RafMarket_EsitoFinale rafMarket_EsitoFinale) throws DataIntegrityViolationException, HibernateJdbcException {
		return rafMarket_EsitoFinaleDao.saveRafMarket_EsitoFinale(rafMarket_EsitoFinale);
	}

	
	@Override
    public void removeRafMarket_EsitoFinale(long id) {
		rafMarket_EsitoFinaleDao.remove(id);
    }
	
	
	
}
