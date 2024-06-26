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
@Table(name = "bf_nazioni")
public class Nazioni extends BaseObject implements Serializable {
	private static final long serialVersionUID = -828633901760052289L;
	
	private Long id;
    private String siglaNazione;
    boolean attivo;
    private Integer ordinamento;
    private String nomeDisplay;

    
    
    public Nazioni(String siglaNazione, boolean attivo) {
		super();
		this.siglaNazione = siglaNazione;
		this.attivo = attivo;
	}



	public Nazioni() { }



	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_nazione")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
	@Column(length = 10, nullable = false, unique = true)
	public String getSiglaNazione() {
		return siglaNazione;
	}
	public void setSiglaNazione(String siglaNazione) {
		this.siglaNazione = siglaNazione;
	}
	
	
	public boolean isAttivo() {
		return attivo;
	}
	public void setAttivo(boolean attivo) {
		this.attivo = attivo;
	}

	
	public Integer getOrdinamento() {
		return ordinamento;
	}
	public void setOrdinamento(Integer ordinamento) {
		this.ordinamento = ordinamento;
	}
	
	
	public String getNomeDisplay() {
		return nomeDisplay;
	}
	public void setNomeDisplay(String nomeDisplay) {
		this.nomeDisplay = nomeDisplay;
	}



	/**
     * {@inheritDoc}
     */
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Nazioni)) {
            return false;
        }
        final Nazioni obj = (Nazioni) o;
        return !(siglaNazione != null ? !siglaNazione.equals(obj.siglaNazione) : obj.siglaNazione != null);
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
