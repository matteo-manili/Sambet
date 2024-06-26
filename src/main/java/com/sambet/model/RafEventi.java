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
@Table(name = "raf_eventi")
public class RafEventi extends BaseObject implements Serializable {
	private static final long serialVersionUID = 8244330050295054066L;

	private Long id;
	
    private Integer fixture_id;
	private Integer league_id;
	private String event_date;
	private Long event_timestamp;
	private String statusShort;
	private String homeTeam; // nel datatabse impostare manualmente la collation (chiamata "Codifica caratteri" su phpMyAdmin) da latin1_swedish_ci a utf8_bin , per le lettere straniere
	private String awayTeam; // nel datatabse impostare manualmente la collation (chiamata "Codifica caratteri" su phpMyAdmin) da latin1_swedish_ci a utf8_bin , per le lettere straniere


	//-------------------- RAF_TEAM HOME --------------------------
	RafTeams rafTeam_home = new RafTeams();
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_raf_team_home", nullable = true, unique = false)
	public RafTeams getRafTeam_home() {
		return rafTeam_home;
	}
	public void setRafTeam_home(RafTeams rafTeam_home) {
		this.rafTeam_home = rafTeam_home;
	}

	//-------------------- RAF_TEAM AWAY --------------------------
	RafTeams rafTeam_away = new RafTeams();
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_raf_team_away", nullable = true, unique = false)
    public RafTeams getRafTeam_away() {
		return rafTeam_away;
	}
	public void setRafTeam_away(RafTeams rafTeam_away) {
		this.rafTeam_away = rafTeam_away;
	}


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
	
	

    public RafEventi(RafCompetizioni rafCompetizioni, Integer fixture_id, Integer league_id, String event_date, Long event_timestamp, String statusShort, 
    		String homeTeam, String awayTeam, RafTeams rafTeam_home, RafTeams rafTeam_away) {
		super();
		this.rafCompetizioni = rafCompetizioni;
		this.fixture_id = fixture_id;
		this.league_id = league_id;
		this.event_date = event_date;
		this.event_timestamp = event_timestamp;
		this.statusShort = statusShort;
		this.homeTeam = homeTeam;
		this.awayTeam = awayTeam;
		this.rafTeam_home = rafTeam_home;
		this.rafTeam_away = rafTeam_away;
		
		
		
	}
    
	public RafEventi() { }

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_raf_evento")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(nullable = false, unique = true)
    public Integer getFixture_id() {
		return fixture_id;
	}
	public void setFixture_id(Integer fixture_id) {
		this.fixture_id = fixture_id;
	}

	public Integer getLeague_id() {
		return league_id;
	}
	public void setLeague_id(Integer league_id) {
		this.league_id = league_id;
	}

	public String getEvent_date() {
		return event_date;
	}
	public void setEvent_date(String event_date) {
		this.event_date = event_date;
	}

	public Long getEvent_timestamp() {
		return event_timestamp;
	}
	public void setEvent_timestamp(Long event_timestamp) {
		this.event_timestamp = event_timestamp;
	}
	
	public String getStatusShort() {
		return statusShort;
	}
	public void setStatusShort(String statusShort) {
		this.statusShort = statusShort;
	}

	@Column(nullable = false)
	public String getHomeTeam() {
		return homeTeam;
	}
	public void setHomeTeam(String homeTeam) {
		this.homeTeam = homeTeam;
	}

	@Column(nullable = false)
	public String getAwayTeam() {
		return awayTeam;
	}
	public void setAwayTeam(String awayTeam) {
		this.awayTeam = awayTeam;
	}
	
	
	
	/**
     * {@inheritDoc}
     */
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RafEventi)) {
            return false;
        }
        final RafEventi obj = (RafEventi) o;
        return !(fixture_id != null ? !fixture_id.equals(obj.fixture_id) : obj.fixture_id != null);
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
