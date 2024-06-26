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
@Table(name = "raf_teams")
public class RafTeams extends BaseObject implements Serializable {
	private static final long serialVersionUID = -8688211871963649399L;

	private Long id;
	
    private Integer team_id;
	private String team_name; // nel datatabse impostare manualmente la collation (chiamata "Codifica caratteri" su phpMyAdmin) da latin1_swedish_ci a utf8_bin , per le lettere straniere

	public RafTeams(Integer team_id, String team_name) {
		super();
		this.team_id = team_id;
		this.team_name = team_name;
	}

	public RafTeams() { }

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_raf_team")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(nullable = false, unique = true)
	public Integer getTeam_id() {
		return team_id;
	}

	public void setTeam_id(Integer team_id) {
		this.team_id = team_id;
	}

	@Column(nullable = false, unique = false)
	public String getTeam_name() {
		return team_name;
	}
	public void setTeam_name(String team_name) {
		this.team_name = team_name;
	}

	
    
	
	
	
	/**
     * {@inheritDoc}
     */
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RafTeams)) {
            return false;
        }
        final RafTeams obj = (RafTeams) o;
        return !(team_id != null ? !team_id.equals(obj.team_id) : obj.team_id != null);
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
