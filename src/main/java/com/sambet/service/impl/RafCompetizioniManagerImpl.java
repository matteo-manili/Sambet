package com.sambet.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate4.HibernateJdbcException;
import org.springframework.stereotype.Service;
import com.sambet.dao.RafCompetizioniDao;
import com.sambet.model.RafCompetizioni;
import com.sambet.service.RafCompetizioniManager;

/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
@Service("RafCompetizioniManager")
public class RafCompetizioniManagerImpl extends GenericManagerImpl<RafCompetizioni, Long> implements RafCompetizioniManager {

	private RafCompetizioniDao competizioniDao;
	
	@Override
    @Autowired
	public void setRafCompetizioniDao(RafCompetizioniDao competizioniDao) {
		this.competizioniDao = competizioniDao;
	}

	
	
	@Override
	public RafCompetizioni get(Long id) {
		return this.competizioniDao.get(id);
	}
	
	@Override
	public List<RafCompetizioni> getRafCompetizioni() {
		return competizioniDao.getRafCompetizioni();
	}
	

	@Override
	public RafCompetizioni saveRafCompetizioni(RafCompetizioni competizioni) throws DataIntegrityViolationException, HibernateJdbcException {
		return competizioniDao.saveRafCompetizioni(competizioni);
	}

	
	@Override
    public void removeRafCompetizioni(long id) {
		competizioniDao.remove(id);
    }
	
	
	
}
