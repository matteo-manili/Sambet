package com.sambet.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate4.HibernateJdbcException;
import org.springframework.stereotype.Service;

import com.sambet.dao.Market_OverUnder_25Dao;
import com.sambet.model.Market_OverUnder_25;
import com.sambet.service.Market_OverUnder_25Manager;

/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
@Service("Market_OverUnder_25Manager")
public class Market_OverUnder_25ManagerImpl extends GenericManagerImpl<Market_OverUnder_25, Long> implements Market_OverUnder_25Manager {

	private Market_OverUnder_25Dao market_OverUnder_25Dao;
	
	@Override
    @Autowired
	public void setMarket_OverUnder_25Dao(Market_OverUnder_25Dao market_OverUnder_25Dao) {
		this.market_OverUnder_25Dao = market_OverUnder_25Dao;
	}

	
	
	@Override
	public Market_OverUnder_25 get(Long id) {
		return this.market_OverUnder_25Dao.get(id);
	}
	
	@Override
	public List<Market_OverUnder_25> getMarket_OverUnder_25() {
		return market_OverUnder_25Dao.getMarket_OverUnder_25();
	}
	

	@Override
	public Market_OverUnder_25 saveMarket_OverUnder_25(Market_OverUnder_25 market_OverUnder_25) throws DataIntegrityViolationException, HibernateJdbcException {
		return market_OverUnder_25Dao.saveMarket_OverUnder_25(market_OverUnder_25);
	}

	
	@Override
    public void removeMarket_OverUnder_25(long id) {
		market_OverUnder_25Dao.remove(id);
    }
	
	
	
}
