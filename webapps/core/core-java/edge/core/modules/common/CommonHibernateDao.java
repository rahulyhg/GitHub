package edge.core.modules.common;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.orm.hibernate3.HibernateTemplate;

public interface CommonHibernateDao {

	public abstract void save(Object entity);

	public abstract void deleteAll(Class classType);

	public abstract void saveOrUpdateAll(Collection entities);

	public abstract void saveOrUpdate(Object entity);

	public abstract void update(Object entity);

	public abstract void merge(Object entity);

	public abstract List<Object> getEntityByCustomExample(Object entity);

	public abstract <T> T getEntityById(Class<T> claz, Serializable id);

	public abstract NamedParameterJdbcTemplate getNamedParameterJdbcTemplate();

	public abstract List queryForList(String hql, Map<String, ?> paramsMap);
	
	public abstract Object queryForObject(String sql, Map<String, ?> paramsMap) throws DataAccessException;
	
	public abstract HibernateTemplate getHibernateTemplate();

}