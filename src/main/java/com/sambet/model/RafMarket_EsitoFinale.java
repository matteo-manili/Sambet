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
@Table(name="raf_market_esito_finale", uniqueConstraints={@UniqueConstraint(columnNames={"id_raf_bookmakers","id_raf_evento","tipoQuota"})})
//@Table(name="raf_market_esito_finale")
public class RafMarket_EsitoFinale extends BaseObject implements Serializable {
	private static final long serialVersionUID = 190136804282734866L;

	private Long id;
	private Integer updateAt;
    private Date dataRichiesta;
    
    private Double quota_1;
    private Double quota_X;
    private Double quota_2;
    
    private Integer tipoQuota;
    
    
    //-------------------- RAF_BOOKMAKERS --------------------------
    RafBookmakers rafBookmakers;
    @ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_raf_bookmakers", nullable = false, unique = false)
    public RafBookmakers getRafBookmakers() {
		return rafBookmakers;
	}
	public void setRafBookmakers(RafBookmakers rafBookmakers) {
		this.rafBookmakers = rafBookmakers;
	}

	//-------------------- RAF_EVENTO --------------------------
    RafEventi rafEvento;
    @ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_raf_evento", nullable = false, unique = false)
    public RafEventi getRafEvento() {
		return rafEvento;
	}
	public void setRafEvento(RafEventi rafEvento) {
		this.rafEvento = rafEvento;
	}
	
	
	public RafMarket_EsitoFinale(Integer updateAt, Date dataRichiesta, Double quota_1, Double quota_X, Double quota_2, Integer tipoQuota, RafBookmakers rafBookmakers, 
			RafEventi rafEvento) {
		super();
		this.updateAt = updateAt;
		this.dataRichiesta = dataRichiesta;
		this.quota_1 = quota_1;
		this.quota_X = quota_X;
		this.quota_2 = quota_2;
		this.tipoQuota = tipoQuota;
		this.rafBookmakers = rafBookmakers;
		this.rafEvento = rafEvento;
	}
	
	
    public RafMarket_EsitoFinale() { }

    
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_raf_market_esito_finale")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Integer getUpdateAt() {
		return updateAt;
	}
	public void setUpdateAt(Integer updateAt) {
		this.updateAt = updateAt;
	}
	
	public Date getDataRichiesta() {
		return dataRichiesta;
	}
	public void setDataRichiesta(Date dataRichiesta) {
		this.dataRichiesta = dataRichiesta;
	}

	public Double getQuota_1() {
		return quota_1;
	}
	public void setQuota_1(Double quota_1) {
		this.quota_1 = quota_1;
	}
	
	public Double getQuota_X() {
		return quota_X;
	}
	public void setQuota_X(Double quota_X) {
		this.quota_X = quota_X;
	}
	
	public Double getQuota_2() {
		return quota_2;
	}
	public void setQuota_2(Double quota_2) {
		this.quota_2 = quota_2;
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
        if (!(o instanceof RafMarket_EsitoFinale)) {
            return false;
        }
        final RafMarket_EsitoFinale obj = (RafMarket_EsitoFinale) o;
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
