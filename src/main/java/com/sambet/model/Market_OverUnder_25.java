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
//@Table(name="bf_market_over_under_25", uniqueConstraints={@UniqueConstraint(columnNames={"id_evento","quota_over","quota_under","tipoQuota"})})
@Table(name="bf_market_over_under_25")
public class Market_OverUnder_25 extends BaseObject implements Serializable {
	private static final long serialVersionUID = -7951792514865155375L;

	private Long id;
	
    private String idMarketBetFair;
    private Date dataRichiesta;

    private Double quota_under;
    private Double quota_over;
    
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

    public Market_OverUnder_25(String idMarketBetFair, Date dataRichiesta, Double quota_under, Double quota_over, Integer tipoQuota, Eventi evento) {
		super();
		this.idMarketBetFair = idMarketBetFair;
		this.dataRichiesta = dataRichiesta;
		this.quota_under = quota_under;
		this.quota_over = quota_over;
		this.tipoQuota = tipoQuota;
		this.evento = evento;
	}
    
	public Market_OverUnder_25() { }

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_market_over_under_25")
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
        if (!(o instanceof Market_OverUnder_25)) {
            return false;
        }
        final Market_OverUnder_25 obj = (Market_OverUnder_25) o;
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
