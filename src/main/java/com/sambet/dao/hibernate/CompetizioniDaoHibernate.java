package com.sambet.dao.hibernate;

import java.math.BigInteger;
import java.util.List;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate4.HibernateJdbcException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import com.sambet.dao.CompetizioniDao;
import com.sambet.model.Competizioni;


/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
@EnableTransactionManagement
@Repository("CompetizioniDao")
public class CompetizioniDaoHibernate extends GenericDaoHibernate<Competizioni, Long> implements CompetizioniDao {

	public CompetizioniDaoHibernate() {
		super(Competizioni.class);
	}
	
	@Override
    @Transactional(readOnly = true)
	public Competizioni get(Long id){
		Competizioni competizioni = (Competizioni) getSession().get(Competizioni.class, id);
		return competizioni;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<Competizioni> getCompetizioni() {
        return getSession().createCriteria(Competizioni.class).addOrder(Order.asc("nazione")).list();
	}

	@Override
	@Transactional
	public Competizioni saveCompetizioni(Competizioni competizioni) throws DataIntegrityViolationException, HibernateJdbcException {
		getSession().saveOrUpdate(competizioni);
		//getSession().flush();
		return competizioni;
	}
	
	@Override
    @Transactional(readOnly = true)
	public Competizioni GetCompetizione_from_idCompetitionBetFair(String id){
		return (Competizioni) getSession().createCriteria(Competizioni.class).add( Restrictions.eq("idCompetitionBetFair", id) ).uniqueResult();
	}
	

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<Object[]> Filtri_Nazione_Competizione() {
		String queryString = "SELECT NAZ.id_nazione, NAZ.siglaNazione, NAZ.nomeDisplay AS NAZ_NOMEDISPLAY, COMP.id_competizione, COMP.nome, COMP.nomeDisplay AS COMP_NOMEDISPLAY "
		+ "FROM bf_nazioni AS NAZ INNER JOIN bf_competizioni AS COMP ON NAZ.id_nazione = COMP.id_nazione AND NAZ.attivo = true AND COMP.attivo = true "
		+ "INNER JOIN bf_eventi EVENTO ON COMP.id_competizione = EVENTO.id_competizione AND EVENTO.dataEvento > NOW() "
		+ "GROUP BY(COMP.id_competizione) " //serve a fare la distinct
		+ "ORDER BY NAZ.ordinamento ASC, NAZ.id_nazione ASC, COMP.ordinamento ASC, COMP.id_competizione ASC ";
		
		List<Object[]> resultList = this.getSession().createSQLQuery( queryString ).list();
		//System.out.println("resultList.size: "+resultList.size());
		/*
		for(Object[] ite_object: resultList) {
			Long 		var_0 = ite_object[0] != null ? ((BigInteger)ite_object[0]).longValue() : null;
			String 		var_1 = (String)ite_object[1];
			String 		var_2 = (String)ite_object[2];
			Long 		var_3 = ite_object[3] != null ? ((BigInteger)ite_object[3]).longValue() : null;
			String 		var_4 = (String)ite_object[4];
			String 		var_5 = (String)ite_object[5];
			System.out.println(var_0+" | " +var_1+" | "+var_2+" | "+var_3+" | "+var_4+" | "+var_5); 
		}
		System.out.println("-----------------");
		*/
		return resultList;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<Competizioni> getCompetizioni_from_siglaNazione(String siglaNazione) {
        return getSession().createCriteria(Competizioni.class).createAlias("nazione", "NAZ").add( Restrictions.eq("NAZ.siglaNazione", siglaNazione)).list();
	}

	
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<Competizioni> getCompetizioni_from_existsEventi() {
		String aaa = "FROM Competizioni AS COMP WHERE EXISTS (FROM Eventi AS EVENTO WHERE EVENTO.competizione = COMP) ";
		List<Competizioni> result = getSession().createQuery( aaa ).list();
		return result;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<Competizioni> getCompetizioni_from_siglaNazione_existsEventi(String siglaNazione) {
		String aaa = "FROM Competizioni AS COMP WHERE EXISTS (FROM Eventi AS EVENTO WHERE EVENTO.competizione = COMP) AND nazione.siglaNazione = :siglaNazione ";
		List<Competizioni> result = getSession().createQuery( aaa ).setParameter("siglaNazione", siglaNazione).list();
		return result;
	}

	
}
