package com.sambet.dao;

import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate4.HibernateJdbcException;
import com.sambet.model.RafMarket_Gol;


/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
public interface RafMarket_GolDao extends GenericDao<RafMarket_Gol, Long> {
	
	RafMarket_Gol get(Long id);
	
	List<RafMarket_Gol> getRafMarket_Gol();

	RafMarket_Gol saveRafMarket_Gol(RafMarket_Gol rafMarket_Gol) throws DataIntegrityViolationException, HibernateJdbcException;

	List<RafMarket_Gol> getRafMarket_Gol_from_idRafEvento(int idRafEvento);




}
