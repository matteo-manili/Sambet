package com.sambet.dao;

import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate4.HibernateJdbcException;
import com.sambet.model.Market_Gol;


/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
public interface Market_GolDao extends GenericDao<Market_Gol, Long> {
	
	Market_Gol get(Long id);
	
	List<Market_Gol> getMarket_Gol();

	Market_Gol saveMarket_Gol(Market_Gol market_Gol) throws DataIntegrityViolationException, HibernateJdbcException;

	Market_Gol getMarket_Gol_QuotaGol_QuotaNoGol(Long idEvento, Double quota_gol, Double quota_noGol);

	List<Market_Gol> getMarket_Gol_Evento(long idEvento);


}
