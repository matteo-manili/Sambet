package com.sambet.util;

import java.util.List;
import com.sambet.Constants;
import com.sambet.model.Market_EsitoFinale;
import com.sambet.model.Market_Gol;
import com.sambet.model.Market_OverUnder_25;

public class CheckQuote {

	public boolean QuotaAperturaPresente = false;
	public boolean QuotaAttualePresente = false;
	public boolean QuotaMediaPresente = false;

	private List<Market_EsitoFinale> listMarketEsitoFinale;
	private List<Market_OverUnder_25> listMarket_OverUnder_25;
	private List<Market_Gol> listMarket_Gol;
	
	private Double quota_1; 
	private Double quota_2; 
	private Double quota_X;

	public boolean isQuotaAperturaPresente() {
		return QuotaAperturaPresente;
	}
	public boolean isQuotaAttualePresente() {
		return QuotaAttualePresente;
	}
	public boolean isQuotaMediaPresente() {
		return QuotaMediaPresente;
	}
	
	public void CheckQuote_Market_EsitoFinale(List<Market_EsitoFinale> listMarketEsitoFinale, Double quota_1, Double quota_2, Double quota_X) {
		this.listMarketEsitoFinale = listMarketEsitoFinale;
		this.quota_1 = quota_1;
		this.quota_2 = quota_2;
		this.quota_X = quota_X;
		Check_Market_EsitoFinale_aaa();
		Check_Market_EsitoFinale_bbb();
	}
	
	public void CheckQuote_Market_OverUnder_25(List<Market_OverUnder_25> listMarket_OverUnder_25, Double quota_1, Double quota_2) {
		this.listMarket_OverUnder_25 = listMarket_OverUnder_25;
		this.quota_1 = quota_1;
		this.quota_2 = quota_2;
		Check_Market_OverUnder_25_aaa();
		Check_Market_OverUnder_25_bbb();
	}
	
	public void CheckQuote_Market_Gol(List<Market_Gol> listMarket_Gol, Double quota_1, Double quota_2) {
		this.listMarket_Gol = listMarket_Gol;
		this.quota_1 = quota_1;
		this.quota_2 = quota_2;
		Check_Market_Gol_aaa();
		Check_Market_Gol_bbb();
	}

	///--------------------------------
	//List<Market_EsitoFinale> listMarketEsitoFinale;
	private void Check_Market_EsitoFinale_aaa() {
		for(Market_EsitoFinale ite: listMarketEsitoFinale) {
			if(ite.getTipoQuota().intValue() == Constants.QTA_APERTURA) {
				QuotaAperturaPresente = true;
			}
			if(ite.getTipoQuota().intValue() == Constants.QTA_ATTUALE) {
				QuotaAttualePresente = true;
			}
		}
	}
	private void Check_Market_EsitoFinale_bbb() {
		for(Market_EsitoFinale ite: listMarketEsitoFinale) {
			if( (ite.getQuota_1() == null && quota_1 == null || (ite.getQuota_1() != null && quota_1 != null && ite.getQuota_1().doubleValue() == quota_1))
				 && (ite.getQuota_2() == null && quota_2 == null || (ite.getQuota_2() != null && quota_2 != null && ite.getQuota_2().doubleValue() == quota_2))
				 && (ite.getQuota_X() == null && quota_X == null || (ite.getQuota_X() != null && quota_X != null && ite.getQuota_X().doubleValue() == quota_X))
				 && ite.getTipoQuota().intValue() == Constants.QTA_MEDIA
				) {
				QuotaMediaPresente = true;
				break;
			}
		}
	}
	
	///--------------------------------
	//List<Market_OverUnder_25> listMarket_OverUnder_25;
	private void Check_Market_OverUnder_25_aaa() {
		for(Market_OverUnder_25 ite: listMarket_OverUnder_25) {
			if(ite.getTipoQuota().intValue() == Constants.QTA_APERTURA) {
				QuotaAperturaPresente = true;
			}
			if(ite.getTipoQuota().intValue() == Constants.QTA_ATTUALE) {
				QuotaAttualePresente = true;
			}
		}
	}
	private void Check_Market_OverUnder_25_bbb() {
		for(Market_OverUnder_25 ite: listMarket_OverUnder_25) {
			if( (ite.getQuota_under() == null && quota_1 == null || (ite.getQuota_under() != null && quota_1 != null && ite.getQuota_under().doubleValue() == quota_1)) 
				 && (ite.getQuota_over() == null && quota_2 == null || (ite.getQuota_over() != null && quota_2 != null && ite.getQuota_over().doubleValue() == quota_2) )
				 && ite.getTipoQuota().intValue() == Constants.QTA_MEDIA
				) {
				QuotaMediaPresente = true;
				break;
			}
		}
	}
	
	///--------------------------------
	// List<Market_Gol> listMarket_Gol;
	private void Check_Market_Gol_aaa() {
		for(Market_Gol ite: listMarket_Gol) {
			if(ite.getTipoQuota().intValue() == Constants.QTA_APERTURA) {
				QuotaAperturaPresente = true;
			}
			if(ite.getTipoQuota().intValue() == Constants.QTA_ATTUALE) {
				QuotaAttualePresente = true;
			}
		}
	}
	private void Check_Market_Gol_bbb() {
		for(Market_Gol ite: listMarket_Gol) {
			if( (ite.getQuota_gol() == null && quota_1 == null || (ite.getQuota_gol() != null && quota_1 != null && ite.getQuota_gol().doubleValue() == quota_1))
				 && (ite.getQuota_noGol() == null && quota_2 == null || (ite.getQuota_noGol() != null && quota_2 != null && ite.getQuota_noGol().doubleValue() == quota_2))
				 && ite.getTipoQuota().intValue() == Constants.QTA_MEDIA
				) {
				QuotaMediaPresente = true;
				break;
			}
		}
	}

}
