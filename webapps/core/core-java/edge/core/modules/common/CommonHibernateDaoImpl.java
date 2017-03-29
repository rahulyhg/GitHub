package edge.core.modules.common;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CustomExample;
import org.hibernate.criterion.MatchMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CommonHibernateDaoImpl extends HibernateDaoSupport implements CommonHibernateDao{
	
	@Autowired
	private
	NamedParameterJdbcTemplate namedParameterJdbcTemplate; 
	
	/* (non-Javadoc)
	 * @see edge.core.modules.common.CommonHibernateDao#getNamedParameterJdbcTemplate()
	 */
	@Override
	public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
		return namedParameterJdbcTemplate;
	}

	/* (non-Javadoc)
	 * @see edge.core.modules.common.CommonHibernateDao#setNamedParameterJdbcTemplate(org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate)
	 */
	public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}
	
	@Autowired
	public CommonHibernateDaoImpl(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	
	/* (non-Javadoc)
	 * @see edge.core.modules.common.CommonHibernateDao#save(java.lang.Object)
	 */
	@Override
	public void save(Object entity) {
		getHibernateTemplate().save(entity);
	}

	/* (non-Javadoc)
	 * @see edge.core.modules.common.CommonHibernateDao#deleteAll(java.lang.Class)
	 */
	@Override
	public void deleteAll(Class classType) {
		Query query = getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery("delete from " + classType.getSimpleName());
		query.executeUpdate();
	}

	/* (non-Javadoc)
	 * @see edge.core.modules.common.CommonHibernateDao#saveOrUpdateAll(java.util.Collection)
	 */
	@Override
	public void saveOrUpdateAll(Collection entities) {
		getHibernateTemplate().saveOrUpdateAll(entities);
	}

	/* (non-Javadoc)
	 * @see edge.core.modules.common.CommonHibernateDao#saveOrUpdate(java.lang.Object)
	 */
	@Override
	public void saveOrUpdate(Object entity) {
		getHibernateTemplate().saveOrUpdate(entity);
	}

	/* (non-Javadoc)
	 * @see edge.core.modules.common.CommonHibernateDao#update(java.lang.Object)
	 */
	@Override
	public void update(Object entity) {
		getHibernateTemplate().update(entity);
	}

	/* (non-Javadoc)
	 * @see edge.core.modules.common.CommonHibernateDao#merge(java.lang.Object)
	 */
	@Override
	public void merge(Object entity) {
		getHibernateTemplate().merge(entity);
	}
	
	/* (non-Javadoc)
	 * @see edge.core.modules.common.CommonHibernateDao#getEntityByCustomExample(java.lang.Object)
	 */
	@Override
	@Transactional
	public List<Object> getEntityByCustomExample(Object entity){			
		Criteria criteria = getHibernateTemplate().getSessionFactory().getCurrentSession()
			.createCriteria(entity.getClass())
			.add(getCustomExample(entity));
		
		return criteria.list();
	}

	private CustomExample getCustomExample(Object authority) {
		CustomExample customExample = new CustomExample(authority);
		customExample.enableLike(MatchMode.ANYWHERE);
		customExample.enableIn();
		customExample.ignoreCase();
		customExample.excludeZeroes();		
		return customExample;
	}	
	
	/* (non-Javadoc)
	 * @see edge.core.modules.common.CommonHibernateDao#findById(java.lang.Class, java.io.Serializable)
	 */
	@Override
	@Transactional
	public <T> T getEntityById(Class<T> claz, Serializable id){			
		return (T) getHibernateTemplate().getSessionFactory().getCurrentSession().get(claz, id);
	}
	
	/* (non-Javadoc)
	 * @see edge.core.modules.common.CommonHibernateDao#queryForList(java.lang.String, java.util.Map)
	 */
	@Override
	@Transactional
	public List queryForList(String hql, Map<String, ?> paramsMap){
		Query query = getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql);
		setParameters(query, paramsMap);
		return query.list();
	}
	

	/* (non-Javadoc)
	 * @see edge.core.modules.common.CommonHibernateDao#queryForObject(java.lang.String, java.util.Map)
	 */
	@Override
	@Transactional
	public Object queryForObject(String hql, Map<String, ?> paramsMap)
			throws DataAccessException {
		Query query = getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql);
		if(paramsMap != null){
			setParameters(query, paramsMap);
		}		
		return query.uniqueResult();
	}

	private void setParameters(Query query, Map<String, ?> paramMap) {
		for(Map.Entry<String, ?> entry : paramMap.entrySet()){
			String name = entry.getKey();
			Object value = entry.getValue();
			
			if(value instanceof Collection){				 
				query.setParameterList(name, (Collection) value);
			}else{
				query.setParameter(name, value);
			}
		}
	}

}