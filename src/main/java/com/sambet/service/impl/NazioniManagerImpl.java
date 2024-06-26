package com.sambet.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate4.HibernateJdbcException;
import org.springframework.stereotype.Service;

import com.sambet.dao.NazioniDao;
import com.sambet.model.Nazioni;
import com.sambet.service.NazioniManager;

/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
@Service("NazioniManager")
public class NazioniManagerImpl extends GenericManagerImpl<Nazioni, Long> implements NazioniManager {

	private NazioniDao nazioniDao;
	
	@Override
    @Autowired
	public void setNazioniDao(NazioniDao nazioniDao) {
		this.nazioniDao = nazioniDao;
	}

	
	
	@Override
	public Nazioni get(Long id) {
		return this.nazioniDao.get(id);
	}
	
	@Override
	public List<Nazioni> getNazioni() {
		return nazioniDao.getNazioni();
	}
	

	@Override
	public Nazioni saveNazioni(Nazioni nazioni) throws DataIntegrityViolationException, HibernateJdbcException {
		return nazioniDao.saveNazioni(nazioni);
	}

	
	@Override
    public void removeNazioni(long id) {
		nazioniDao.remove(id);
    }
	
	
	
}
