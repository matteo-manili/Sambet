package com.sambet.service;

import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate4.HibernateJdbcException;

import com.sambet.dao.Market_GolDao;
import com.sambet.model.Market_Gol;


/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
public interface Market_GolManager extends GenericManager<Market_Gol, Long> {
	
	void setMarket_GolDao(Market_GolDao market_GolDao);
	
	Market_Gol get(Long id);
	
	List<Market_Gol> getMarket_Gol();
	
	Market_Gol saveMarket_Gol(Market_Gol market_Gol) throws DataIntegrityViolationException, HibernateJdbcException;
	
	void removeMarket_Gol(long idMarket_Gol);


}
