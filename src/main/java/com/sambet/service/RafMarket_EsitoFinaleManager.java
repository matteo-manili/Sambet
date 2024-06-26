package com.sambet.service;

import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate4.HibernateJdbcException;
import com.sambet.dao.RafMarket_EsitoFinaleDao;
import com.sambet.model.RafMarket_EsitoFinale;


/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
public interface RafMarket_EsitoFinaleManager extends GenericManager<RafMarket_EsitoFinale, Long> {
	
	void setRafMarket_EsitoFinaleDao(RafMarket_EsitoFinaleDao rafMarket_EsitoFinaleDao);
	
	RafMarket_EsitoFinale get(Long id);
	
	List<RafMarket_EsitoFinale> getRafMarket_EsitoFinale();
	
	RafMarket_EsitoFinale saveRafMarket_EsitoFinale(RafMarket_EsitoFinale rafMarket_EsitoFinale) throws DataIntegrityViolationException, HibernateJdbcException;
	
	void removeRafMarket_EsitoFinale(long idRafMarket_EsitoFinale);


}
