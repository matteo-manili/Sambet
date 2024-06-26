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
@Table(name="raf_statistics", uniqueConstraints={@UniqueConstraint(columnNames={"id_raf_competizione","id_raf_team"})})
//@Table(name = "raf_statistics")
public class RafStatistics extends BaseObject implements Serializable {
	private static final long serialVersionUID = -3856099125533159297L;

	private Long id;
	
	private Date dataRichiesta;
	
	private double home_GolFatti;
	private double home_GolSubiti;
	private double home_NumeroPartGiocate;
					
	private double away_GolFatti; 
	private double away_GolSubiti;
	private double away_NumeroPartGiocate;
	
	
	//-------------------- RAF_COMPETIZIONE --------------------------
    RafCompetizioni rafCompetizioni;
    @ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_raf_competizione", nullable = false, unique = false)
    public RafCompetizioni getRafCompetizioni() {
		return rafCompetizioni;
	}
	public void setRafCompetizioni(RafCompetizioni rafCompetizioni) {
		this.rafCompetizioni = rafCompetizioni;
	}
	
	//-------------------- RAF_TEAM --------------------------
	RafTeams rafTeam;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_raf_team", nullable = false, unique = false)
	public RafTeams getRafTeam() {
		return rafTeam;
	}
	public void setRafTeam(RafTeams rafTeam) {
		this.rafTeam = rafTeam;
	}
	

	public RafStatistics(RafCompetizioni rafCompetizioni, RafTeams rafTeam, Date dataRichiesta, double home_GolFatti, double home_GolSubiti, double home_NumeroPartGiocate, 
			double away_GolFatti, double away_GolSubiti, double away_NumeroPartGiocate) {
		super();
		this.rafCompetizioni = rafCompetizioni;
		this.rafTeam = rafTeam;
		this.dataRichiesta = dataRichiesta;
		this.home_GolFatti = home_GolFatti;
		this.home_GolSubiti = home_GolSubiti;
		this.home_NumeroPartGiocate = home_NumeroPartGiocate;
		this.away_GolFatti = away_GolFatti;
		this.away_GolSubiti = away_GolSubiti;
		this.away_NumeroPartGiocate = away_NumeroPartGiocate;
	}
	
	
	public RafStatistics() { }

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_raf_statistics")
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
	
	@Column(nullable = false)
	public double getHome_GolFatti() {
		return home_GolFatti;
	}
	public void setHome_GolFatti(double home_GolFatti) {
		this.home_GolFatti = home_GolFatti;
	}
	
	@Column(nullable = false)
	public double getHome_GolSubiti() {
		return home_GolSubiti;
	}
	public void setHome_GolSubiti(double home_GolSubiti) {
		this.home_GolSubiti = home_GolSubiti;
	}
	
	@Column(nullable = false)
	public double getHome_NumeroPartGiocate() {
		return home_NumeroPartGiocate;
	}
	public void setHome_NumeroPartGiocate(double home_NumeroPartGiocate) {
		this.home_NumeroPartGiocate = home_NumeroPartGiocate;
	}
	
	@Column(nullable = false)
	public double getAway_GolFatti() {
		return away_GolFatti;
	}
	public void setAway_GolFatti(double away_GolFatti) {
		this.away_GolFatti = away_GolFatti;
	}
	
	@Column(nullable = false)
	public double getAway_GolSubiti() {
		return away_GolSubiti;
	}
	public void setAway_GolSubiti(double away_GolSubiti) {
		this.away_GolSubiti = away_GolSubiti;
	}
	
	@Column(nullable = false)
	public double getAway_NumeroPartGiocate() {
		return away_NumeroPartGiocate;
	}
	public void setAway_NumeroPartGiocate(double away_NumeroPartGiocate) {
		this.away_NumeroPartGiocate = away_NumeroPartGiocate;
	}
	
	
	/**
     * {@inheritDoc}
     */
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RafStatistics)) {
            return false;
        }
        final RafStatistics obj = (RafStatistics) o;
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
                .append(this.id).toString();
    }

}
