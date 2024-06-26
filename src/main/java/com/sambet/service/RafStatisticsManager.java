package com.sambet.service;

import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate4.HibernateJdbcException;

import com.sambet.dao.RafStatisticsDao;
import com.sambet.model.RafStatistics;


/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
public interface RafStatisticsManager extends GenericManager<RafStatistics, Long> {
	
	void setRafStatisticsDao(RafStatisticsDao nazioniDao);
	
	RafStatistics get(Long id);
	
	List<RafStatistics> getRafStatistics();
	
	RafStatistics saveRafStatistics(RafStatistics nazioni) throws DataIntegrityViolationException, HibernateJdbcException;
	
	void removeRafStatistics(long idRafStatistics);


}
