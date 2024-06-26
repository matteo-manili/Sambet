package com.sambet.service;

import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate4.HibernateJdbcException;
import com.sambet.dao.EventiDao;
import com.sambet.model.Eventi;
import com.sambet.webapp.util.bean.MainTableFiltri;


/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
public interface EventiManager extends GenericManager<Eventi, Long> {
	
	void setEventiDao(EventiDao eventiDao);
	
	Eventi get(Long id);
	
	List<Eventi> getEventi();
	
	Eventi saveEventi(Eventi nazioni) throws DataIntegrityViolationException, HibernateJdbcException;
	
	void removeEventi(long idEventi);

	List<Object[]> Report_1(MainTableFiltri mainTableFiltri);
}
