package com.sambet.service;

import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate4.HibernateJdbcException;
import com.sambet.dao.Market_OverUnder_25Dao;
import com.sambet.model.Market_OverUnder_25;


/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
public interface Market_OverUnder_25Manager extends GenericManager<Market_OverUnder_25, Long> {
	
	void setMarket_OverUnder_25Dao(Market_OverUnder_25Dao market_OverUnder_25Dao);
	
	Market_OverUnder_25 get(Long id);
	
	List<Market_OverUnder_25> getMarket_OverUnder_25();
	
	Market_OverUnder_25 saveMarket_OverUnder_25(Market_OverUnder_25 market_OverUnder_25) throws DataIntegrityViolationException, HibernateJdbcException;
	
	void removeMarket_OverUnder_25(long idMarket_OverUnder_25);


}
