package com.sambet.dao;

import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate4.HibernateJdbcException;
import com.sambet.model.RafEventi;


/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
public interface RafEventiDao extends GenericDao<RafEventi, Long> {
	
	RafEventi get(Long id);
	
	List<RafEventi> getRafEventi();

	RafEventi saveRafEventi(RafEventi eventi) throws DataIntegrityViolationException, HibernateJdbcException;

	RafEventi GetEvento_from_idFixture(int idFixture);

	List<RafEventi> getRafEventi_EventiCampionato_NonAssegnati(List<Long> listLongIdRafCompetizioni, List<Long> listLongIdRafEventi);

	List<RafEventi> getRafEventi_from_siglaNazione_dataRafEvento_nonPresenteInEventi(String siglaNazione,String eventDate);

	List<RafEventi> getRafEventi_AssegnatiEventi();

	RafEventi getRafEventi_from_fixtureId(int fixture_id);

	List<RafEventi> getRafEventi_ApiMapping(int fixture_id, int updateAt);



}
