package com.sambet.dao;

import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate4.HibernateJdbcException;
import com.sambet.model.RafMarket_EsitoFinale;


/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
public interface RafMarket_EsitoFinaleDao extends GenericDao<RafMarket_EsitoFinale, Long> {
	
	RafMarket_EsitoFinale get(Long id);
	
	List<RafMarket_EsitoFinale> getRafMarket_EsitoFinale();

	RafMarket_EsitoFinale saveRafMarket_EsitoFinale(RafMarket_EsitoFinale rafMarket_EsitoFinale) throws DataIntegrityViolationException, HibernateJdbcException;

	List<RafMarket_EsitoFinale> getRafMarket_EsitoFinale_from_idRafEvento(int idRafEvento);




}
