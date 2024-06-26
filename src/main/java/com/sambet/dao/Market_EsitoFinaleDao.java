package com.sambet.dao;

import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate4.HibernateJdbcException;
import com.sambet.model.Market_EsitoFinale;


/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
public interface Market_EsitoFinaleDao extends GenericDao<Market_EsitoFinale, Long> {
	
	Market_EsitoFinale get(Long id);
	
	List<Market_EsitoFinale> getMarket_EsitoFinale();

	Market_EsitoFinale saveMarket_EsitoFinale(Market_EsitoFinale market_EsitoFinale) throws DataIntegrityViolationException, HibernateJdbcException;

	Market_EsitoFinale getMarket_EsitoFinale_Evento_Quota1_Quota2_QuotaX(Long idEvento, Double quota_1, Double quota_2, Double quota_X);

	List<Market_EsitoFinale> getMarket_EsitoFinale_Evento(long idEvento);




}
