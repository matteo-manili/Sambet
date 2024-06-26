package com.sambet.webapp.util.bean;

import java.util.List;

/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
public class MainTable {
	
	Integer totaleRow;
	List<RowTable> rowTableList;
	
	public Integer getTotaleRow() {
		return totaleRow;
	}
	public void setTotaleRow(Integer totaleRow) {
		this.totaleRow = totaleRow;
	}
	public List<RowTable> getRowTableList() {
		return rowTableList;
	}
	public void setRowTableList(List<RowTable> rowTableList) {
		this.rowTableList = rowTableList;
	}


	static public class RowTable {
		private String nazioneNome;
		private String campionatoNome;
		private String partitaNome;
		private String partitaData;
		private String partitaOra;
		
		private Pronostico pronosticiSingole;
		private Pronostico pronosticiMultiple;
		
		private Quotazione quotSambet;
		
		private Quotazione quotApertura;
		private Quotazione quotMedia;
		private Quotazione quotAttuale;
		
		private Integer andamento_1;
		private Integer andamento_X;
		private Integer andamento_2;
		private Integer andamento_Over25;
		private Integer andamento_Under25;
		private Integer andamento_Gol;
		private Integer andamento_noGol;
		
		private Integer quotMediaCount_1;
		private Integer quotMediaCount_X;
		private Integer quotMediaCount_2;
		private Integer quotMediaCount_Over25;
		private Integer quotMediaCount_Under25;
		private Integer quotMediaCount_Gol;
		private Integer quotMediaCount_noGol;
		
		private String nomeBookmaker_1;
		private Quotazione bookmaker_1QuotApertura;
		private Quotazione bookmaker_1QuotAttuale;
		
		private String nomeBookmaker_2;
		private Quotazione bookmaker_2QuotApertura;
		private Quotazione bookmaker_2QuotAttuale;
		
		private Quotazione bookmakersMedia_QuotApertura;
		private Quotazione bookmakersMedia_QuotAttuale;
		
		
		public String getNazioneNome() {
			return nazioneNome;
		}
		public void setNazioneNome(String nazioneNome) {
			this.nazioneNome = nazioneNome;
		}
		public String getCampionatoNome() {
			return campionatoNome;
		}
		public void setCampionatoNome(String campionatoNome) {
			this.campionatoNome = campionatoNome;
		}
		public String getPartitaNome() {
			return partitaNome;
		}
		public void setPartitaNome(String partitaNome) {
			this.partitaNome = partitaNome;
		}
		public String getPartitaData() {
			return partitaData;
		}
		public void setPartitaData(String partitaData) {
			this.partitaData = partitaData;
		}
		public String getPartitaOra() {
			return partitaOra;
		}
		public void setPartitaOra(String partitaOra) {
			this.partitaOra = partitaOra;
		}
		public Pronostico getPronosticiSingole() {
			return pronosticiSingole;
		}
		public void setPronosticiSingole(Pronostico pronosticiSingole) {
			this.pronosticiSingole = pronosticiSingole;
		}
		public Pronostico getPronosticiMultiple() {
			return pronosticiMultiple;
		}
		public void setPronosticiMultiple(Pronostico pronosticiMultiple) {
			this.pronosticiMultiple = pronosticiMultiple;
		}
		public Quotazione getQuotSambet() {
			return quotSambet;
		}
		public void setQuotSambet(Quotazione quotSambet) {
			this.quotSambet = quotSambet;
		}
		public Quotazione getQuotApertura() {
			return quotApertura;
		}
		public void setQuotApertura(Quotazione quotApertura) {
			this.quotApertura = quotApertura;
		}
		public Quotazione getQuotMedia() {
			return quotMedia;
		}
		public void setQuotMedia(Quotazione quotMedia) {
			this.quotMedia = quotMedia;
		}
		public Quotazione getQuotAttuale() {
			return quotAttuale;
		}
		public void setQuotAttuale(Quotazione quotAttuale) {
			this.quotAttuale = quotAttuale;
		}
		public Integer getAndamento_1() {
			return andamento_1;
		}
		public void setAndamento_1(Integer andamento_1) {
			this.andamento_1 = andamento_1;
		}
		public Integer getAndamento_X() {
			return andamento_X;
		}
		public void setAndamento_X(Integer andamento_X) {
			this.andamento_X = andamento_X;
		}
		public Integer getAndamento_2() {
			return andamento_2;
		}
		public void setAndamento_2(Integer andamento_2) {
			this.andamento_2 = andamento_2;
		}
		public Integer getAndamento_Over25() {
			return andamento_Over25;
		}
		public void setAndamento_Over25(Integer andamento_Over25) {
			this.andamento_Over25 = andamento_Over25;
		}
		public Integer getAndamento_Under25() {
			return andamento_Under25;
		}
		public void setAndamento_Under25(Integer andamento_Under25) {
			this.andamento_Under25 = andamento_Under25;
		}
		public Integer getAndamento_Gol() {
			return andamento_Gol;
		}
		public void setAndamento_Gol(Integer andamento_Gol) {
			this.andamento_Gol = andamento_Gol;
		}
		public Integer getAndamento_noGol() {
			return andamento_noGol;
		}
		public void setAndamento_noGol(Integer andamento_noGol) {
			this.andamento_noGol = andamento_noGol;
		}
		public Integer getQuotMediaCount_1() {
			return quotMediaCount_1;
		}
		public void setQuotMediaCount_1(Integer quotMediaCount_1) {
			this.quotMediaCount_1 = quotMediaCount_1;
		}
		public Integer getQuotMediaCount_X() {
			return quotMediaCount_X;
		}
		public void setQuotMediaCount_X(Integer quotMediaCount_X) {
			this.quotMediaCount_X = quotMediaCount_X;
		}
		public Integer getQuotMediaCount_2() {
			return quotMediaCount_2;
		}
		public void setQuotMediaCount_2(Integer quotMediaCount_2) {
			this.quotMediaCount_2 = quotMediaCount_2;
		}
		public Integer getQuotMediaCount_Over25() {
			return quotMediaCount_Over25;
		}
		public void setQuotMediaCount_Over25(Integer quotMediaCount_Over25) {
			this.quotMediaCount_Over25 = quotMediaCount_Over25;
		}
		public Integer getQuotMediaCount_Under25() {
			return quotMediaCount_Under25;
		}
		public void setQuotMediaCount_Under25(Integer quotMediaCount_Under25) {
			this.quotMediaCount_Under25 = quotMediaCount_Under25;
		}
		public Integer getQuotMediaCount_Gol() {
			return quotMediaCount_Gol;
		}
		public void setQuotMediaCount_Gol(Integer quotMediaCount_Gol) {
			this.quotMediaCount_Gol = quotMediaCount_Gol;
		}
		public Integer getQuotMediaCount_noGol() {
			return quotMediaCount_noGol;
		}
		public void setQuotMediaCount_noGol(Integer quotMediaCount_noGol) {
			this.quotMediaCount_noGol = quotMediaCount_noGol;
		}
		public String getNomeBookmaker_1() {
			return nomeBookmaker_1;
		}
		public void setNomeBookmaker_1(String nomeBookmaker_1) {
			this.nomeBookmaker_1 = nomeBookmaker_1;
		}
		public Quotazione getBookmaker_1QuotApertura() {
			return bookmaker_1QuotApertura;
		}
		public void setBookmaker_1QuotApertura(Quotazione bookmaker_1QuotApertura) {
			this.bookmaker_1QuotApertura = bookmaker_1QuotApertura;
		}
		public Quotazione getBookmaker_1QuotAttuale() {
			return bookmaker_1QuotAttuale;
		}
		public void setBookmaker_1QuotAttuale(Quotazione bookmaker_1QuotAttuale) {
			this.bookmaker_1QuotAttuale = bookmaker_1QuotAttuale;
		}
		public String getNomeBookmaker_2() {
			return nomeBookmaker_2;
		}
		public void setNomeBookmaker_2(String nomeBookmaker_2) {
			this.nomeBookmaker_2 = nomeBookmaker_2;
		}
		public Quotazione getBookmaker_2QuotApertura() {
			return bookmaker_2QuotApertura;
		}
		public void setBookmaker_2QuotApertura(Quotazione bookmaker_2QuotApertura) {
			this.bookmaker_2QuotApertura = bookmaker_2QuotApertura;
		}
		public Quotazione getBookmaker_2QuotAttuale() {
			return bookmaker_2QuotAttuale;
		}
		public void setBookmaker_2QuotAttuale(Quotazione bookmaker_2QuotAttuale) {
			this.bookmaker_2QuotAttuale = bookmaker_2QuotAttuale;
		}
		public Quotazione getBookmakersMedia_QuotApertura() {
			return bookmakersMedia_QuotApertura;
		}
		public void setBookmakersMedia_QuotApertura(Quotazione bookmakersMedia_QuotApertura) {
			this.bookmakersMedia_QuotApertura = bookmakersMedia_QuotApertura;
		}
		public Quotazione getBookmakersMedia_QuotAttuale() {
			return bookmakersMedia_QuotAttuale;
		}
		public void setBookmakersMedia_QuotAttuale(Quotazione bookmakersMedia_QuotAttuale) {
			this.bookmakersMedia_QuotAttuale = bookmakersMedia_QuotAttuale;
		}
		

		static public class Quotazione {
			private Double matchOdds_1;
			private Double matchOdds_X;
			private Double matchOdds_2;
			
			private Double ou25_Over;
			private Double ou25_Under;
			
			private Double btts_Gol;
			private Double btts_NoGol;
			
			public Double getMatchOdds_1() {
				return matchOdds_1;
			}
			public void setMatchOdds_1(Double matchOdds_1) {
				this.matchOdds_1 = matchOdds_1;
			}
			public Double getMatchOdds_2() {
				return matchOdds_2;
			}
			public void setMatchOdds_2(Double matchOdds_2) {
				this.matchOdds_2 = matchOdds_2;
			}
			public Double getMatchOdds_X() {
				return matchOdds_X;
			}
			public void setMatchOdds_X(Double matchOdds_X) {
				this.matchOdds_X = matchOdds_X;
			}
			public Double getOu25_Over() {
				return ou25_Over;
			}
			public void setOu25_Over(Double ou25_Over) {
				this.ou25_Over = ou25_Over;
			}
			public Double getOu25_Under() {
				return ou25_Under;
			}
			public void setOu25_Under(Double ou25_Under) {
				this.ou25_Under = ou25_Under;
			}
			public Double getBtts_Gol() {
				return btts_Gol;
			}
			public void setBtts_Gol(Double btts_Gol) {
				this.btts_Gol = btts_Gol;
			}
			public Double getBtts_NoGol() {
				return btts_NoGol;
			}
			public void setBtts_NoGol(Double btts_NoGol) {
				this.btts_NoGol = btts_NoGol;
			}
		}
		
		static public class Pronostico {
			private boolean matchOdds_1;
			private boolean matchOdds_X;
			private boolean matchOdds_2;
			
			private boolean ou25_Over;
			private boolean ou25_Under;
			
			private boolean btts_Gol;
			private boolean btts_NoGol;
			
			public boolean isMatchOdds_1() {
				return matchOdds_1;
			}
			public void setMatchOdds_1(boolean matchOdds_1) {
				this.matchOdds_1 = matchOdds_1;
			}
			public boolean isMatchOdds_X() {
				return matchOdds_X;
			}
			public void setMatchOdds_X(boolean matchOdds_X) {
				this.matchOdds_X = matchOdds_X;
			}
			public boolean isMatchOdds_2() {
				return matchOdds_2;
			}
			public void setMatchOdds_2(boolean matchOdds_2) {
				this.matchOdds_2 = matchOdds_2;
			}
			public boolean isOu25_Over() {
				return ou25_Over;
			}
			public void setOu25_Over(boolean ou25_Over) {
				this.ou25_Over = ou25_Over;
			}
			public boolean isOu25_Under() {
				return ou25_Under;
			}
			public void setOu25_Under(boolean ou25_Under) {
				this.ou25_Under = ou25_Under;
			}
			public boolean isBtts_Gol() {
				return btts_Gol;
			}
			public void setBtts_Gol(boolean btts_Gol) {
				this.btts_Gol = btts_Gol;
			}
			public boolean isBtts_NoGol() {
				return btts_NoGol;
			}
			public void setBtts_NoGol(boolean btts_NoGol) {
				this.btts_NoGol = btts_NoGol;
			}
		}
		
	}
	
	

}
