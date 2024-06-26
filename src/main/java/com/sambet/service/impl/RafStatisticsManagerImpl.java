package com.sambet.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate4.HibernateJdbcException;
import org.springframework.stereotype.Service;

import com.sambet.dao.RafStatisticsDao;
import com.sambet.model.RafStatistics;
import com.sambet.service.RafStatisticsManager;

/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
@Service("RafStatisticsManager")
public class RafStatisticsManagerImpl extends GenericManagerImpl<RafStatistics, Long> implements RafStatisticsManager {

	private RafStatisticsDao nazioniDao;
	
	@Override
    @Autowired
	public void setRafStatisticsDao(RafStatisticsDao nazioniDao) {
		this.nazioniDao = nazioniDao;
	}

	
	
	@Override
	public RafStatistics get(Long id) {
		return this.nazioniDao.get(id);
	}
	
	@Override
	public List<RafStatistics> getRafStatistics() {
		return nazioniDao.getRafStatistics();
	}
	

	@Override
	public RafStatistics saveRafStatistics(RafStatistics nazioni) throws DataIntegrityViolationException, HibernateJdbcException {
		return nazioniDao.saveRafStatistics(nazioni);
	}

	
	@Override
    public void removeRafStatistics(long id) {
		nazioniDao.remove(id);
    }
	
	
	
}
