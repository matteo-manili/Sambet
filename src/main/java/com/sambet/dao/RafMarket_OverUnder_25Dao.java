package com.sambet.dao;

import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate4.HibernateJdbcException;
import com.sambet.model.RafMarket_OverUnder_25;


/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
public interface RafMarket_OverUnder_25Dao extends GenericDao<RafMarket_OverUnder_25, Long> {
	
	RafMarket_OverUnder_25 get(Long id);
	
	List<RafMarket_OverUnder_25> getRafMarket_OverUnder_25();

	RafMarket_OverUnder_25 saveRafMarket_OverUnder_25(RafMarket_OverUnder_25 rafMarket_OverUnder_25) throws DataIntegrityViolationException, HibernateJdbcException;

	List<RafMarket_OverUnder_25> getRafMarket_OverUnder_25_from_idRafEvento(int idRafEvento);




}
