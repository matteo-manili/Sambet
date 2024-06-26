package com.sambet.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author Matteo Manili - matteo.manili@gmail.com
 *
 */
@Entity
@Table(name = "raf_bookmakers")
public class RafBookmakers extends BaseObject implements Serializable {
	private static final long serialVersionUID = -6318050764522091788L;
	
	private Long id;
	private Integer id_bookmaker;
    private String name;
    
    boolean visibile;


	public RafBookmakers(Integer id_bookmaker, String name, boolean visibile) {
		super();
		this.id_bookmaker = id_bookmaker;
		this.name = name;
		this.visibile = visibile;
	}


	public RafBookmakers() { }


	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_raf_bookmakers")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(nullable = false, unique = true)
	public Integer getId_bookmaker() {
		return id_bookmaker;
	}
	public void setId_bookmaker(Integer id_bookmaker) {
		this.id_bookmaker = id_bookmaker;
	}

	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}



	public boolean isVisibile() {
		return visibile;
	}
	public void setVisibile(boolean visibile) {
		this.visibile = visibile;
	}



	/**
     * {@inheritDoc}
     */
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RafBookmakers)) {
            return false;
        }
        final RafBookmakers obj = (RafBookmakers) o;
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
