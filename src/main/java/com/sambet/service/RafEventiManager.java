package com.sambet.service;

import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate4.HibernateJdbcException;
import com.sambet.dao.RafEventiDao;
import com.sambet.model.RafEventi;
import com.sambet.webapp.util.bean.MainTableFiltri;


/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
public interface RafEventiManager extends GenericManager<RafEventi, Long> {
	
	void setRafEventiDao(RafEventiDao eventiDao);
	
	RafEventi get(Long id);
	
	List<RafEventi> getRafEventi();
	
	RafEventi saveRafEventi(RafEventi nazioni) throws DataIntegrityViolationException, HibernateJdbcException;
	
	void removeRafEventi(long idRafEventi);


}
