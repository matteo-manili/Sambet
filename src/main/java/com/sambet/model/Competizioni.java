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
@Table(name = "bf_competizioni")
public class Competizioni extends BaseObject implements Serializable {
	private static final long serialVersionUID = 3608092506335137999L;
	
	private Long id;
	private String idCompetitionBetFair;
    private String nome;
    boolean attivo;
    private Integer ordinamento;
    private String nomeDisplay;
    
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
    
    
    public Competizioni() { }

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_competizione")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
	@Column(nullable = false, unique = true)
	public String getIdCompetitionBetFair() {
		return idCompetitionBetFair;
	}
	public void setIdCompetitionBetFair(String idCompetitionBetFair) {
		this.idCompetitionBetFair = idCompetitionBetFair;
	}
	
	
	@Column(nullable = false)
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
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
        if (!(o instanceof Competizioni)) {
            return false;
        }
        final Competizioni obj = (Competizioni) o;
        return !(nome != null ? !nome.equals(obj.nome) : obj.nome != null);
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
