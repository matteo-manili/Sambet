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
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author Matteo Manili - matteo.manili@gmail.com
 *
 */
@Entity
//@Table(name="bf_market_esito_finale", uniqueConstraints={@UniqueConstraint(columnNames={"id_evento","quota_1","quota_X","quota_2","tipoQuota"})})
@Table(name="bf_market_esito_finale")
public class Market_EsitoFinale extends BaseObject implements Serializable {
	private static final long serialVersionUID = 1518975156736139890L;

	private Long id;
	
    private String idMarketBetFair;
    private Date dataRichiesta;

    private Double quota_1;
    private Double quota_X;
    private Double quota_2;
    
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

	public Market_EsitoFinale(String idMarketBetFair, Date dataRichiesta, Double quota_1, Double quota_2, Double quota_X, Integer tipoQuota, Eventi evento) {
		super();
		this.idMarketBetFair = idMarketBetFair;
		this.dataRichiesta = dataRichiesta;
		this.quota_1 = quota_1;
		this.quota_2 = quota_2;
		this.quota_X = quota_X;
		this.tipoQuota = tipoQuota;
		this.evento = evento;
	}
	
	/**
     * Default constructor - creates a new instance with no values set.
     */
    public Market_EsitoFinale() { }

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_market_esito_finale")
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
        if (!(o instanceof Market_EsitoFinale)) {
            return false;
        }
        final Market_EsitoFinale obj = (Market_EsitoFinale) o;
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
