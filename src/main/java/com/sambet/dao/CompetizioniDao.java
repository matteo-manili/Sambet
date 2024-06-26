package com.sambet.dao;

import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate4.HibernateJdbcException;
import com.sambet.model.Competizioni;


/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
public interface CompetizioniDao extends GenericDao<Competizioni, Long> {
	
	Competizioni get(Long id);
	
	List<Competizioni> getCompetizioni();

	Competizioni saveCompetizioni(Competizioni competizioni) throws DataIntegrityViolationException, HibernateJdbcException;

	Competizioni GetCompetizione_from_idCompetitionBetFair(String id);

	List<Object[]> Filtri_Nazione_Competizione();

	List<Competizioni> getCompetizioni_from_siglaNazione(String siglaNazione);

	List<Competizioni> getCompetizioni_from_existsEventi();

	List<Competizioni> getCompetizioni_from_siglaNazione_existsEventi(String siglaNazione);


}
