package com.sambet.dao;

import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate4.HibernateJdbcException;
import com.sambet.model.RafBookmakers;


/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
public interface RafBookmakersDao extends GenericDao<RafBookmakers, Long> {
	
	RafBookmakers get(Long id);
	
	List<RafBookmakers> getRafBookmakers();

	RafBookmakers saveRafBookmakers(RafBookmakers nazioni) throws DataIntegrityViolationException, HibernateJdbcException;

	RafBookmakers getBookmakers_from_idBookmaker(int id_bookmaker);


}
