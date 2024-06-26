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
@Table(name = "api_lista_nazioni")
public class ListaNazioni extends BaseObject implements Serializable {
	private static final long serialVersionUID = -828633901760052289L;
	
	private Long id;
    private String siglaNazione;
    private Integer status;
    private boolean attivo;
    
    
    public ListaNazioni(String siglaNazione, Integer status, boolean attivo) {
		super();
		this.siglaNazione = siglaNazione;
		this.status = status;
		this.attivo = attivo;
	}

	public ListaNazioni() { }

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_lista_nazioni")
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
	
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}


	public boolean isAttivo() {
		return attivo;
	}
	public void setAttivo(boolean attivo) {
		this.attivo = attivo;
	}



	/**
     * {@inheritDoc}
     */
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ListaNazioni)) {
            return false;
        }
        final ListaNazioni obj = (ListaNazioni) o;
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
