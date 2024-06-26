package com.sambet.service;

import java.util.List;

import com.sambet.dao.GestioneApplicazioneDao;
import com.sambet.model.GestioneApplicazione;


/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
public interface GestioneApplicazioneManager extends GenericManager<GestioneApplicazione, Long> {
	
	void setGestioneApplicazioneDao(GestioneApplicazioneDao gestioneApplicazioneDao);
	
	
	GestioneApplicazione get(Long id);
	
	List<GestioneApplicazione> getGestioneApplicazione();
	List<GestioneApplicazione> getGestioneApplicazione_senzaCommenti();
	
	GestioneApplicazione saveGestioneApplicazione(GestioneApplicazione gestioneApplicazione) throws Exception;

	void removeGestioneApplicazione(long idGestioneApplicazione);

	GestioneApplicazione getName(String name);

	List<GestioneApplicazione> getGestioneApplicazioneBy_LIKE(String term);


	


	
	

	

	


	

}
