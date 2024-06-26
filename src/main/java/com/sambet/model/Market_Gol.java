package com.sambet.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author Matteo Manili - matteo.manili@gmail.com
 *
 */
@Entity
//@Table(name="bf_market_gol", uniqueConstraints={@UniqueConstraint(columnNames={"id_evento","quota_gol","quota_noGol","tipoQuota"})})
@Table(name="bf_market_gol")
public class Market_Gol extends BaseObject implements Serializable {
	private static final long serialVersionUID = -1239913849564508468L;

	private Long id;
	
    private String idMarketBetFair;
    private Date dataRichiesta;

    private Double quota_gol;
    private Double quota_noGol;
    
    private Integer tipoQuota;
    
    //-------------------- EVENTO --------------------------
    Eventi evento;
    @ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_evento", nullable = false, unique = false)
	public Eventi getEvento() {
		return evento;
	}
	public void setEvento(Eventi evento) {
		this.evento = evento;
	}

    public Market_Gol(String idMarketBetFair, Date dataRichiesta, Double quota_gol, Double quota_noGol, Integer tipoQuota, Eventi evento) {
		super();
		this.idMarketBetFair = idMarketBetFair;
		this.dataRichiesta = dataRichiesta;
		this.quota_gol = quota_gol;
		this.quota_noGol = quota_noGol;
		this.tipoQuota = tipoQuota;
		this.evento = evento;
	}
    
	public Market_Gol() { }

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_market_gol")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	

	@Column(nullable = false, unique = false)
	public String getIdMarketBetFair() {
		return idMarketBetFair;
	}
	public void setIdMarketBetFair(String idMarketBetFair) {
		this.idMarketBetFair = idMarketBetFair;
	}
	
	
	public Date getDataRichiesta() {
		return dataRichiesta;
	}
	public void setDataRichiesta(Date dataRichiesta) {
		this.dataRichiesta = dataRichiesta;
	}
	

	public Double getQuota_gol() {
		return quota_gol;
	}
	public void setQuota_gol(Double quota_gol) {
		this.quota_gol = quota_gol;
	}
	
	public Double getQuota_noGol() {
		return quota_noGol;
	}
	public void setQuota_noGol(Double quota_noGol) {
		this.quota_noGol = quota_noGol;
	}
	
	@Column(nullable = false, unique = false)
	public Integer getTipoQuota() {
		return tipoQuota;
	}
	public void setTipoQuota(Integer tipoQuota) {
		this.tipoQuota = tipoQuota;
	}
	
	/**
     * {@inheritDoc}
     */
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Market_Gol)) {
            return false;
        }
        final Market_Gol obj = (Market_Gol) o;
        return !(id != null ? !id.equals(obj.id) : obj.id != null);
    }
    
    /**
     * {@inheritDoc}
     */
    public int hashCode() {
        return (id != null ? id.hashCode() : 0);
    }

    /**
     * {@inheritDoc}
     */
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE)
                .append(this.id)
                .toString();
    }

}
