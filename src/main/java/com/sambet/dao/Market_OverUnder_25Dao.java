package com.sambet.dao;

import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate4.HibernateJdbcException;
import com.sambet.model.Market_OverUnder_25;


/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
public interface Market_OverUnder_25Dao extends GenericDao<Market_OverUnder_25, Long> {
	
	Market_OverUnder_25 get(Long id);
	
	List<Market_OverUnder_25> getMarket_OverUnder_25();

	Market_OverUnder_25 saveMarket_OverUnder_25(Market_OverUnder_25 market_OverUnder_25) throws DataIntegrityViolationException, HibernateJdbcException;

	Market_OverUnder_25 Market_OverUnder_25_QuotaOver_QuotaUnder(Long idEvento, Double quota_over, Double quota_under);

	List<Market_OverUnder_25> getMarket_OverUnder_25_Evento(long idEvento);


}
