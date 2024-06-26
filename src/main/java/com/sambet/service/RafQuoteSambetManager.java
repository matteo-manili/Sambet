package com.sambet.service;

import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate4.HibernateJdbcException;

import com.sambet.dao.RafQuoteSambetDao;
import com.sambet.model.RafQuoteSambet;


/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
public interface RafQuoteSambetManager extends GenericManager<RafQuoteSambet, Long> {
	
	void setRafQuoteSambetDao(RafQuoteSambetDao nazioniDao);
	
	RafQuoteSambet get(Long id);
	
	List<RafQuoteSambet> getRafQuoteSambet();
	
	RafQuoteSambet saveRafQuoteSambet(RafQuoteSambet nazioni) throws DataIntegrityViolationException, HibernateJdbcException;
	
	void removeRafQuoteSambet(long idRafQuoteSambet);


}
