package com.sambet.dao;

import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate4.HibernateJdbcException;
import com.sambet.model.RafStatistics;


/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
public interface RafStatisticsDao extends GenericDao<RafStatistics, Long> {
	
	RafStatistics get(Long id);
	
	List<RafStatistics> getRafStatistics();

	RafStatistics saveRafStatistics(RafStatistics nazioni) throws DataIntegrityViolationException, HibernateJdbcException;

	RafStatistics getStatistics_from_idStatistic(int id_raf_competizione, int id_raf_team);


}
