package com.sambet.model;

import java.io.Serializable;
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
@Table(name = "raf_competizioni")
public class RafCompetizioni extends BaseObject implements Serializable {
	private static final long serialVersionUID = 3477546631430206788L;
	
	private Long id;
	
	private Integer league_id;
	private String name;
	private String type;
	private String country;
	private String country_code;
	private int season;
	private String season_start;
	private String season_end;
    
    //-------------------- NAZIONE --------------------------
    Nazioni nazione;
    @ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_nazione", nullable = false, unique = false)
    public Nazioni getNazione() {
		return nazione;
	}
	public void setNazione(Nazioni nazione) {
		this.nazione = nazione;
	}

	
    public RafCompetizioni(Nazioni nazione, Integer league_id, String name, String type, String country, String country_code, int season, String season_start, String season_end) {
		super();
		this.nazione = nazione;
		this.league_id = league_id;
		this.name = name;
		this.type = type;
		this.country = country;
		this.country_code = country_code;
		this.season = season;
		this.season_start = season_start;
		this.season_end = season_end;
	}
    
	public RafCompetizioni() { }

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_raf_competizione")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(nullable = false, unique = true)
    public Integer getLeague_id() {
		return league_id;
	}
	public void setLeague_id(Integer league_id) {
		this.league_id = league_id;
	}
	
	@Column(nullable = false)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
	public String getCountry_code() {
		return country_code;
	}
	public void setCountry_code(String country_code) {
		this.country_code = country_code;
	}
	
	public int getSeason() {
		return season;
	}
	public void setSeason(int season) {
		this.season = season;
	}
	
	public String getSeason_start() {
		return season_start;
	}
	public void setSeason_start(String season_start) {
		this.season_start = season_start;
	}
	
	public String getSeason_end() {
		return season_end;
	}
	public void setSeason_end(String season_end) {
		this.season_end = season_end;
	}
	
	
	/**
     * {@inheritDoc}
     */
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RafCompetizioni)) {
            return false;
        }
        final RafCompetizioni obj = (RafCompetizioni) o;
        return !(name != null ? !name.equals(obj.name) : obj.name != null);
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
