package com.sambet.service;

import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate4.HibernateJdbcException;
import com.sambet.dao.Market_EsitoFinaleDao;
import com.sambet.model.Market_EsitoFinale;


/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
public interface Market_EsitoFinaleManager extends GenericManager<Market_EsitoFinale, Long> {
	
	void setMarket_EsitoFinaleDao(Market_EsitoFinaleDao market_EsitoFinaleDao);
	
	Market_EsitoFinale get(Long id);
	
	List<Market_EsitoFinale> getMarket_EsitoFinale();
	
	Market_EsitoFinale saveMarket_EsitoFinale(Market_EsitoFinale market_EsitoFinale) throws DataIntegrityViolationException, HibernateJdbcException;
	
	void removeMarket_EsitoFinale(long idMarket_EsitoFinale);


}
