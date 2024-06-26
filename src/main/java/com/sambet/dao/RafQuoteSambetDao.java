package com.sambet.dao;

import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate4.HibernateJdbcException;
import com.sambet.model.RafQuoteSambet;


/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
public interface RafQuoteSambetDao extends GenericDao<RafQuoteSambet, Long> {
	
	RafQuoteSambet get(Long id);
	
	List<RafQuoteSambet> getRafQuoteSambet();

	RafQuoteSambet saveRafQuoteSambet(RafQuoteSambet rafQuoteSambet) throws DataIntegrityViolationException, HibernateJdbcException;

	RafQuoteSambet getQuoteSambet_from_idRafEvento(long idRafEvento);


}
