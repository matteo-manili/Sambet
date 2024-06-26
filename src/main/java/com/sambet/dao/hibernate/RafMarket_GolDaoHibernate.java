package com.sambet.dao.hibernate;

import java.util.List;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate4.HibernateJdbcException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import com.sambet.dao.RafMarket_GolDao;
import com.sambet.model.Eventi;
import com.sambet.model.RafMarket_Gol;


/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
@EnableTransactionManagement
@Repository("RafMarket_GolDao")
public class RafMarket_GolDaoHibernate extends GenericDaoHibernate<RafMarket_Gol, Long> implements RafMarket_GolDao {

	public RafMarket_GolDaoHibernate() {
		super(RafMarket_Gol.class);
	}
	
	@Override
    @Transactional(readOnly = true)
	public RafMarket_Gol get(Long id){
		RafMarket_Gol regioni = (RafMarket_Gol) getSession().get(RafMarket_Gol.class, id);
		return regioni;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<RafMarket_Gol> getRafMarket_Gol() {
        return getSession().createCriteria(RafMarket_Gol.class).addOrder(Order.desc("id")).list();
	}

	@Override
	@Transactional
	public RafMarket_Gol saveRafMarket_Gol(RafMarket_Gol rafMarket_Gol) throws DataIntegrityViolationException, HibernateJdbcException {
		getSession().saveOrUpdate(rafMarket_Gol);
		//getSession().flush();
		return rafMarket_Gol;
	}
	
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<RafMarket_Gol> getRafMarket_Gol_from_idRafEvento(int idRafEvento) {
        return getSession().createCriteria(RafMarket_Gol.class)
        		.createAlias("rafEvento", "RAF_EVENTO").add( Restrictions.eq("RAF_EVENTO.fixture_id", idRafEvento)).list();
	}
	
	

}
