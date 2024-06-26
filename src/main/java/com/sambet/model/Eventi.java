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
@Table(name = "bf_eventi")
public class Eventi extends BaseObject implements Serializable {
	private static final long serialVersionUID = -2124951781540944271L;
	
	private Long id;
	
	private String idEventBetFair;
	private Date dataEvento;
    private String nome;


    //-------------------- COMPETIZIONE --------------------------
    Competizioni competizione;
    @ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_competizione", nullable = false, unique = false)
    public Competizioni getCompetizione() {
		return competizione;
	}
	public void setCompetizione(Competizioni competizione) {
		this.competizione = competizione;
	}
	
	//-------------------- RAF_EVENTI --------------------------
    RafEventi rafEventi;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_raf_evento", nullable = true, unique = true)
	public RafEventi getRafEventi() {
		return rafEventi;
	}
	public void setRafEventi(RafEventi rafEventi) {
		this.rafEventi = rafEventi;
	}
	
	//-------------------- RAF_COMPETIZIONE --------------------------
    RafCompetizioni rafCompetizioni;
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_raf_competizione", nullable = true, unique = false)
    public RafCompetizioni getRafCompetizioni() {
		return rafCompetizioni;
	}
	public void setRafCompetizioni(RafCompetizioni rafCompetizioni) {
		this.rafCompetizioni = rafCompetizioni;
	}


    public Eventi() { }

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_evento")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
	@Column(nullable = false, unique = true)
	public String getIdEventBetFair() {
		return idEventBetFair;
	}
	public void setIdEventBetFair(String idEventBetFair) {
		this.idEventBetFair = idEventBetFair;
	}
	
	
	@Column(nullable = false)
	public Date getDataEvento() {
		return dataEvento;
	}
	public void setDataEvento(Date dataEvento) {
		this.dataEvento = dataEvento;
	}
	
	
	@Column(nullable = false)
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	

	
	
	
	
	/**
     * {@inheritDoc}
     */
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Eventi)) {
            return false;
        }
        final Eventi obj = (Eventi) o;
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
