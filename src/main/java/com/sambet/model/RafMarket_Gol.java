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
@Table(name="raf_market_gol", uniqueConstraints={@UniqueConstraint(columnNames={"id_raf_bookmakers","id_raf_evento","tipoQuota"})})
//@Table(name="raf_market_gol")
public class RafMarket_Gol extends BaseObject implements Serializable {
	private static final long serialVersionUID = -3897538474209512902L;

	private Long id;
	private Integer updateAt;
	private Date dataRichiesta;

    private Double quota_gol;
    private Double quota_noGol;
    
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

	
	public RafMarket_Gol(Integer updateAt, Date dataRichiesta, Double quota_gol, Double quota_noGol, Integer tipoQuota, RafBookmakers rafBookmakers, RafEventi rafEvento) {
		super();
		this.updateAt = updateAt;
		this.dataRichiesta = dataRichiesta;
		this.quota_gol = quota_gol;
		this.quota_noGol = quota_noGol;
		this.tipoQuota = tipoQuota;
		this.rafBookmakers = rafBookmakers;
		this.rafEvento = rafEvento;
	}
	
	
	public RafMarket_Gol() { }

	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_raf_market_gol")
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
        if (!(o instanceof RafMarket_Gol)) {
            return false;
        }
        final RafMarket_Gol obj = (RafMarket_Gol) o;
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