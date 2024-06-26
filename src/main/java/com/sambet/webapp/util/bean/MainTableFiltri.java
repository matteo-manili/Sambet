package com.sambet.webapp.util.bean;

import java.util.Date;
import java.util.List;

/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
public class MainTableFiltri {
	
	private String siglaNazione;
	List<Long> idArrayListCompetizione;
	private List<FiltroNazioni> listNazioni;
	private List<FiltroCampionati> listCampionati;
	private String dataInizioPartita;
	private String oraInizioPartita; 
	private String dataFinePartita;
	private String oraFinePartita;
	private Date inizioPartita;
	private Date finePartita;
	private String nomePartita;
	private List<FiltroAscDesc> listFiltriAscDesc;

	
	public String getSiglaNazione() {
		return siglaNazione;
	}
	public void setSiglaNazione(String siglaNazione) {
		this.siglaNazione = siglaNazione;
	}
	public List<Long> getIdArrayListCompetizione() {
		return idArrayListCompetizione;
	}
	public void setIdArrayListCompetizione(List<Long> idArrayListCompetizione) {
		this.idArrayListCompetizione = idArrayListCompetizione;
	}
	public List<FiltroNazioni> getListNazioni() {
		return listNazioni;
	}
	public void setListNazioni(List<FiltroNazioni> listNazioni) {
		this.listNazioni = listNazioni;
	}
	public List<FiltroCampionati> getListCampionati() {
		return listCampionati;
	}
	public void setListCampionati(List<FiltroCampionati> listCampionati) {
		this.listCampionati = listCampionati;
	}
	public String getDataInizioPartita() {
		return dataInizioPartita;
	}
	public void setDataInizioPartita(String dataInizioPartita) {
		this.dataInizioPartita = dataInizioPartita;
	}
	public String getOraInizioPartita() {
		return oraInizioPartita;
	}
	public void setOraInizioPartita(String oraInizioPartita) {
		this.oraInizioPartita = oraInizioPartita;
	}
	public String getDataFinePartita() {
		return dataFinePartita;
	}
	public void setDataFinePartita(String dataFinePartita) {
		this.dataFinePartita = dataFinePartita;
	}
	public String getOraFinePartita() {
		return oraFinePartita;
	}
	public void setOraFinePartita(String oraFinePartita) {
		this.oraFinePartita = oraFinePartita;
	}
	public Date getInizioPartita() {
		return inizioPartita;
	}
	public void setInizioPartita(Date inizioPartita) {
		this.inizioPartita = inizioPartita;
	}
	public Date getFinePartita() {
		return finePartita;
	}
	public void setFinePartita(Date finePartita) {
		this.finePartita = finePartita;
	}
	public String getNomePartita() {
		return nomePartita;
	}
	public void setNomePartita(String nomePartita) {
		this.nomePartita = nomePartita;
	}
	public List<FiltroAscDesc> getListFiltriAscDesc() {
		return listFiltriAscDesc;
	}
	public void setListFiltriAscDesc(List<FiltroAscDesc> listFiltriAscDesc) {
		this.listFiltriAscDesc = listFiltriAscDesc;
	}


	static public class FiltroNazioni {
		private long idNazione;
		private String nazioneSigla;
		private String nazioneNome;
		private boolean selected;
		
		public FiltroNazioni(long idNazione, String nazioneSigla, String nazioneNome, boolean selected) {
			super();
			this.idNazione = idNazione;
			this.nazioneSigla = nazioneSigla;
			this.nazioneNome = nazioneNome;
			this.selected = selected;
		}
		
		public FiltroNazioni() { }

		public long getIdNazione() {
			return idNazione;
		}
		public void setIdNazione(long idNazione) {
			this.idNazione = idNazione;
		}
		public String getNazioneSigla() {
			return nazioneSigla;
		}
		public void setNazioneSigla(String nazioneSigla) {
			this.nazioneSigla = nazioneSigla;
		}
		public String getNazioneNome() {
			return nazioneNome;
		}
		public void setNazioneNome(String nazioneNome) {
			this.nazioneNome = nazioneNome;
		}
		public boolean isSelected() {
			return selected;
		}
		public void setSelected(boolean selected) {
			this.selected = selected;
		}
	}
	
	
	static public class FiltroCampionati {
		private long idCampionato;
		private String campionatoNome;
		private boolean selected;
		
		public FiltroCampionati(long idCampionato, String campionatoNome, boolean selected) {
			super();
			this.idCampionato = idCampionato;
			this.campionatoNome = campionatoNome;
			this.selected = selected;
		}

		public FiltroCampionati() { }

		public long getIdCampionato() {
			return idCampionato;
		}
		public void setIdCampionato(long idCampionato) {
			this.idCampionato = idCampionato;
		}
		public String getCampionatoNome() {
			return campionatoNome;
		}
		public void setCampionatoNome(String campionatoNome) {
			this.campionatoNome = campionatoNome;
		}
		public boolean isSelected() {
			return selected;
		}
		public void setSelected(boolean selected) {
			this.selected = selected;
		}
	}
	
	
	static public class FiltroAscDesc {
		private String nomeFiltroAscDesc;
		private String ascDesc;;
		
		public FiltroAscDesc(String nomeFiltroAscDesc, String ascDesc) {
			super();
			this.nomeFiltroAscDesc = nomeFiltroAscDesc;
			this.ascDesc = ascDesc;
		}

		public FiltroAscDesc() { }

		public String getNomeFiltroAscDesc() {
			return nomeFiltroAscDesc;
		}
		public void setNomeFiltroAscDesc(String nomeFiltroAscDesc) {
			this.nomeFiltroAscDesc = nomeFiltroAscDesc;
		}
		public String getAscDesc() {
			return ascDesc;
		}
		public void setAscDesc(String ascDesc) {
			this.ascDesc = ascDesc;
		}
	}
	
	
	
	
	
	
	
	
	
}
