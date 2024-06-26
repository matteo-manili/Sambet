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

import com.sambet.dao.RafStatisticsDao;
import com.sambet.model.RafStatistics;


/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
@EnableTransactionManagement
@Repository("RafStatisticsDao")
public class RafStatisticsDaoHibernate extends GenericDaoHibernate<RafStatistics, Long> implements RafStatisticsDao {

	public RafStatisticsDaoHibernate() {
		super(RafStatistics.class);
	}
	
	@Override
    @Transactional(readOnly = true)
	public RafStatistics get(Long id){
		RafStatistics statistics = (RafStatistics) getSession().get(RafStatistics.class, id);
		return statistics;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<RafStatistics> getRafStatistics() {
        return getSession().createCriteria(RafStatistics.class).addOrder(Order.asc("id")).list();
	}

	@Override
	@Transactional
	public RafStatistics saveRafStatistics(RafStatistics statistics) throws DataIntegrityViolationException, HibernateJdbcException {
		getSession().saveOrUpdate(statistics);
		//getSession().flush();
		return statistics;
	}
	
	
	@Override
    @Transactional(readOnly = true)
	public RafStatistics getStatistics_from_idStatistic(int id_raf_competizione, int id_raf_team) {
		
		Criterion crit1 = Restrictions.and(
				Restrictions.eq("COMP.league_id", id_raf_competizione), Restrictions.eq("TEAM.team_id", id_raf_team));
		
		return (RafStatistics) getSession().createCriteria(RafStatistics.class)
				.createAlias("rafCompetizioni", "COMP").createAlias("rafTeam", "TEAM")
				.add( crit1 ).uniqueResult();
	}
	


}
