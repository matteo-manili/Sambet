package com.sambet.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate4.HibernateJdbcException;
import org.springframework.stereotype.Service;

import com.sambet.dao.ListaNazioniDao;
import com.sambet.model.ListaNazioni;
import com.sambet.service.ListaNazioniManager;

/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
@Service("ListaNazioniManager")
public class ListaNazioniManagerImpl extends GenericManagerImpl<ListaNazioni, Long> implements ListaNazioniManager {

	private ListaNazioniDao listaNazioniDao;
	
	@Override
    @Autowired
	public void setListaNazioniDao(ListaNazioniDao listaNazioniDao) {
		this.listaNazioniDao = listaNazioniDao;
	}

	
	
	@Override
	public ListaNazioni get(Long id) {
		return this.listaNazioniDao.get(id);
	}
	
	@Override
	public List<ListaNazioni> getListaNazioni() {
		return listaNazioniDao.getListaNazioni();
	}
	

	@Override
	public ListaNazioni saveListaNazioni(ListaNazioni listaNazioni) throws DataIntegrityViolationException, HibernateJdbcException {
		return listaNazioniDao.saveListaNazioni(listaNazioni);
	}

	
	@Override
    public void removeListaNazioni(long id) {
		listaNazioniDao.remove(id);
    }
	
	
	
}
