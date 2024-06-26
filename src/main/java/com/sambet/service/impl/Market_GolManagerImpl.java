package com.sambet.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate4.HibernateJdbcException;
import org.springframework.stereotype.Service;

import com.sambet.dao.Market_GolDao;
import com.sambet.model.Market_Gol;
import com.sambet.service.Market_GolManager;

/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
@Service("Market_GolManager")
public class Market_GolManagerImpl extends GenericManagerImpl<Market_Gol, Long> implements Market_GolManager {

	private Market_GolDao market_GolDao;
	
	@Override
    @Autowired
	public void setMarket_GolDao(Market_GolDao market_GolDao) {
		this.market_GolDao = market_GolDao;
	}

	
	
	@Override
	public Market_Gol get(Long id) {
		return this.market_GolDao.get(id);
	}
	
	@Override
	public List<Market_Gol> getMarket_Gol() {
		return market_GolDao.getMarket_Gol();
	}
	

	@Override
	public Market_Gol saveMarket_Gol(Market_Gol market_Gol) throws DataIntegrityViolationException, HibernateJdbcException {
		return market_GolDao.saveMarket_Gol(market_Gol);
	}

	
	@Override
    public void removeMarket_Gol(long id) {
		market_GolDao.remove(id);
    }
	
	
	
}
