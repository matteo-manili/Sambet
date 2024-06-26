package com.sambet.dao;

import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate4.HibernateJdbcException;
import com.sambet.model.RafCompetizioni;


/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
public interface RafCompetizioniDao extends GenericDao<RafCompetizioni, Long> {
	
	RafCompetizioni get(Long id);
	
	List<RafCompetizioni> getRafCompetizioni();

	RafCompetizioni saveRafCompetizioni(RafCompetizioni competizioni) throws DataIntegrityViolationException, HibernateJdbcException;

	RafCompetizioni GetCompetizione_from_legueId(int league_id);

	List<Object[]> Filtri_Nazione_Competizione();

	List<RafCompetizioni> getRafCompetizioni_from_siglaNazione_attivo(String siglaNazione, boolean attvo);

	List<RafCompetizioni> getRafCompetizioni_AssegnatiEventi();


}
