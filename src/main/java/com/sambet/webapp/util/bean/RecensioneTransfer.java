package com.sambet.webapp.util.bean;



/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
public class RecensioneTransfer {
	
	private long idRicercaTransfert;
	private Boolean recensioneApprovata;
	private String recensione;
	private Integer punteggioStelleRecensione;
	private Long idUser;
	
	public long getIdRicercaTransfert() {
		return idRicercaTransfert;
	}
	public void setIdRicercaTransfert(long idRicercaTransfert) {
		this.idRicercaTransfert = idRicercaTransfert;
	}
	
	public Boolean getRecensioneApprovata() {
		return recensioneApprovata;
	}
	public void setRecensioneApprovata(Boolean recensioneApprovata) {
		this.recensioneApprovata = recensioneApprovata;
	}
	
	public String getRecensione() {
		return recensione;
	}
	public void setRecensione(String recensione) {
		this.recensione = recensione;
	}
	
	public Integer getPunteggioStelleRecensione() {
		return punteggioStelleRecensione;
	}
	public void setPunteggioStelleRecensione(Integer punteggioStelleRecensione) {
		this.punteggioStelleRecensione = punteggioStelleRecensione;
	}
	
	public Long getIdUser() {
		return idUser;
	}
	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}
	
}
