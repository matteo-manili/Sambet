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
@Table(name="raf_market_over_under_25", uniqueConstraints={@UniqueConstraint(columnNames={"id_raf_bookmakers","id_raf_evento","tipoQuota"})})
//@Table(name="raf_market_over_under_25")
public class RafMarket_OverUnder_25 extends BaseObject implements Serializable {
	private static final long serialVersionUID = -5981469248655643828L;

	private Long id;
	private Integer updateAt;
	private Date dataRichiesta;
    
    private Double quota_under;
    private Double quota_over;
    
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

	
	public RafMarket_OverUnder_25(Integer updateAt, Date dataRichiesta, Double quota_over, Double quota_under, Integer tipoQuota, RafBookmakers rafBookmakers, RafEventi rafEvento) {
		super();
		this.updateAt = updateAt;
		this.dataRichiesta = dataRichiesta;
		this.quota_over = quota_over;
		this.quota_under = quota_under;
		this.tipoQuota = tipoQuota;
		this.rafBookmakers = rafBookmakers;
		this.rafEvento = rafEvento;
	}
	
	
	public RafMarket_OverUnder_25() { }

	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_raf_market_over_under_25")
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
	
	public Double getQuota_under() {
		return quota_under;
	}
	public void setQuota_under(Double quota_under) {
		this.quota_under = quota_under;
	}
	
	public Double getQuota_over() {
		return quota_over;
	}
	public void setQuota_over(Double quota_over) {
		this.quota_over = quota_over;
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
        if (!(o instanceof RafMarket_OverUnder_25)) {
            return false;
        }
        final RafMarket_OverUnder_25 obj = (RafMarket_OverUnder_25) o;
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
