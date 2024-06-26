package com.sambet.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate4.HibernateJdbcException;
import org.springframework.stereotype.Service;

import com.sambet.dao.Market_EsitoFinaleDao;
import com.sambet.model.Market_EsitoFinale;
import com.sambet.service.Market_EsitoFinaleManager;

/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
@Service("Market_EsitoFinaleManager")
public class Market_EsitoFinaleManagerImpl extends GenericManagerImpl<Market_EsitoFinale, Long> implements Market_EsitoFinaleManager {

	private Market_EsitoFinaleDao market_EsitoFinaleDao;
	
	@Override
    @Autowired
	public void setMarket_EsitoFinaleDao(Market_EsitoFinaleDao market_EsitoFinaleDao) {
		this.market_EsitoFinaleDao = market_EsitoFinaleDao;
	}

	
	
	@Override
	public Market_EsitoFinale get(Long id) {
		return this.market_EsitoFinaleDao.get(id);
	}
	
	@Override
	public List<Market_EsitoFinale> getMarket_EsitoFinale() {
		return market_EsitoFinaleDao.getMarket_EsitoFinale();
	}
	

	@Override
	public Market_EsitoFinale saveMarket_EsitoFinale(Market_EsitoFinale market_EsitoFinale) throws DataIntegrityViolationException, HibernateJdbcException {
		return market_EsitoFinaleDao.saveMarket_EsitoFinale(market_EsitoFinale);
	}

	
	@Override
    public void removeMarket_EsitoFinale(long id) {
		market_EsitoFinaleDao.remove(id);
    }
	
	
	
}
