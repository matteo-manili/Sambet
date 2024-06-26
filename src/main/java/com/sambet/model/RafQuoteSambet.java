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
@Table(name="raf_quote_sambet")
public class RafQuoteSambet extends BaseObject implements Serializable {
	private static final long serialVersionUID = -5346507713551110189L;
	
	private Long id;
    private Date dataRichiesta;
    
    private Double quota_1;
    private Double quota_X;
    private Double quota_2;
    
    private Double quota_under;
    private Double quota_over;
    
    private Double quota_gol;
    private Double quota_noGol;
    
   
    
    
	//-------------------- RAF_EVENTO --------------------------
    RafEventi rafEvento;
    @ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_raf_evento", nullable = false, unique = true)
    public RafEventi getRafEvento() {
		return rafEvento;
	}
	public void setRafEvento(RafEventi rafEvento) {
		this.rafEvento = rafEvento;
	}
	
	
    public RafQuoteSambet(RafEventi rafEvento, Date dataRichiesta, Double quota_1, Double quota_X, Double quota_2, Double quota_over, Double quota_under, Double quota_gol, 
    		Double quota_noGol) {
		super();
		this.rafEvento = rafEvento;
		this.dataRichiesta = dataRichiesta;
		this.quota_1 = quota_1;
		this.quota_X = quota_X;
		this.quota_2 = quota_2;
		this.quota_over = quota_over;
		this.quota_under = quota_under;
		this.quota_gol = quota_gol;
		this.quota_noGol = quota_noGol;
	}
    
    
	public RafQuoteSambet() { }

    
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_raf_quote_sambet")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	
	/**
     * {@inheritDoc}
     */
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RafQuoteSambet)) {
            return false;
        }
        final RafQuoteSambet obj = (RafQuoteSambet) o;
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
