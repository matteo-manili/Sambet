package com.sambet.dao.hibernate;

import java.util.List;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate4.HibernateJdbcException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.sambet.dao.RafTeamsDao;
import com.sambet.model.RafTeams;


/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
@EnableTransactionManagement
@Repository("RafTeamsDao")
public class RafTeamsDaoHibernate extends GenericDaoHibernate<RafTeams, Long> implements RafTeamsDao {

	public RafTeamsDaoHibernate() {
		super(RafTeams.class);
	}
	
	@Override
    @Transactional(readOnly = true)
	public RafTeams get(Long id){
		RafTeams bookmakers = (RafTeams) getSession().get(RafTeams.class, id);
		return bookmakers;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<RafTeams> getRafTeams() {
        return getSession().createCriteria(RafTeams.class).addOrder(Order.asc("id")).list();
	}

	@Override
	@Transactional
	public RafTeams saveRafTeams(RafTeams bookmakers) throws DataIntegrityViolationException, HibernateJdbcException {
		getSession().saveOrUpdate(bookmakers);
		//getSession().flush();
		return bookmakers;
	}
	
	
	@Override
    @Transactional(readOnly = true)
	public RafTeams getTeams_from_idTeam(int team_id){
		return (RafTeams) getSession().createCriteria(RafTeams.class).add( Restrictions.eq("team_id", team_id) ).uniqueResult();
	}
	


}
