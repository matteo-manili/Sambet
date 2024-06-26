package com.sambet.dao;

import java.util.ArrayList;
import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate4.HibernateJdbcException;
import com.sambet.model.Eventi;
import com.sambet.webapp.util.bean.MainTableFiltri;


/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
public interface EventiDao extends GenericDao<Eventi, Long> {
	
	Eventi get(Long id);
	
	List<Eventi> getEventi();

	List<Eventi> getEventi_orderBy_idCompetizione();
	
	Eventi saveEventi(Eventi eventi) throws DataIntegrityViolationException, HibernateJdbcException;

	Eventi GetEvento_from_idEventBetFair(String id);

	List<Eventi> GetEventi_from_siglaNazione(String siglaNazione);
	
	List<Object[]> Report_1(MainTableFiltri mainTableFiltri);

	void PuliziaDatabase_Market_Eventi_Campionati();

	List<Eventi> GetEventi_from_siglaNazione_rafEventiIsNULL(String siglaNazione);

	List<Object[]> GetEventi_from_siglaNazione_rafEventiIsNotNULL();

	

	


}
