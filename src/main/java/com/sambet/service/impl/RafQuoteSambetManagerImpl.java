package com.sambet.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate4.HibernateJdbcException;
import org.springframework.stereotype.Service;

import com.sambet.dao.RafQuoteSambetDao;
import com.sambet.model.RafQuoteSambet;
import com.sambet.service.RafQuoteSambetManager;

/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
@Service("RafQuoteSambetManager")
public class RafQuoteSambetManagerImpl extends GenericManagerImpl<RafQuoteSambet, Long> implements RafQuoteSambetManager {

	private RafQuoteSambetDao nazioniDao;
	
	@Override
    @Autowired
	public void setRafQuoteSambetDao(RafQuoteSambetDao nazioniDao) {
		this.nazioniDao = nazioniDao;
	}

	
	
	@Override
	public RafQuoteSambet get(Long id) {
		return this.nazioniDao.get(id);
	}
	
	@Override
	public List<RafQuoteSambet> getRafQuoteSambet() {
		return nazioniDao.getRafQuoteSambet();
	}
	

	@Override
	public RafQuoteSambet saveRafQuoteSambet(RafQuoteSambet nazioni) throws DataIntegrityViolationException, HibernateJdbcException {
		return nazioniDao.saveRafQuoteSambet(nazioni);
	}

	
	@Override
    public void removeRafQuoteSambet(long id) {
		nazioniDao.remove(id);
    }
	
	
	
}
