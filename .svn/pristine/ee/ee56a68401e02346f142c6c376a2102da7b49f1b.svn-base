/**
 * 
 */
package com.infosys.westing.commerce.dao;

import java.lang.reflect.ParameterizedType;
import java.math.BigInteger;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import com.infosys.westing.commerce.model.Pager;
import com.infosys.westing.commerce.model.SystemRequest;
import com.infosys.westing.commerce.model.SystemRequestHolder;


/**
 * @author Administrator
 *
 */
@SuppressWarnings("unchecked")
public class BaseDao<T> implements IBaseDao<T> {
	
	private SessionFactory sessionFactory;
	/**
	 * 创建一个Class的对象来获取泛型的class
	 */
	private Class<?> clz;
	
	protected SystemRequest getSystemRequest() {
		SystemRequest sr = SystemRequestHolder.getSystemRequest();
		if(sr==null) sr = new SystemRequest();
		return sr;
	}
	
	public Class<?> getClz() {
		if(clz==null) {
			//获取泛型的Class对象
			clz = ((Class<?>)
					(((ParameterizedType)(this.getClass().getGenericSuperclass())).getActualTypeArguments()[0]));
		}
		return clz;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	@Inject
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	/* (non-Javadoc)
	 * @see org.konghao.baisc.dao.IBaseDao#add(java.lang.Object)
	 */
	@Override
	public T add(T t) {
		getSession().save(t);
		return t;
	}
	
	public Object addEntity(Object entity) {
		getSession().save(entity);
		return entity;
	}

	/* (non-Javadoc)
	 * @see org.konghao.baisc.dao.IBaseDao#update(java.lang.Object)
	 */
	@Override
	public void update(T t) {
		getSession().update(t);
	}
	
	public void updateEntity(Object entity) {
		getSession().save(entity);
	}

	/* (non-Javadoc)
	 * @see org.konghao.baisc.dao.IBaseDao#delete(int)
	 */
	@Override
	public void delete(int id) {
		getSession().delete(this.load(id));
	}
	
	public void deleteEntity(Object entity) {
		getSession().delete(entity);
	}
	
	public void saveOrUpdateEntity(Object entity) {
		this.getSession().saveOrUpdate(entity);
	}

	/* (non-Javadoc)
	 * @see org.konghao.baisc.dao.IBaseDao#load(int)
	 */
	@Override
	public T load(long id) {
		return (T)getSession().load(getClz(), id);
	}
	
	@SuppressWarnings("rawtypes")
	public Object loadEntity(int id,Class clz) {
		return (Object)getSession().load(clz, id);
	}
	
	public <N extends Object>List<N> listObj(String hql,Map<String,Object> alias,Object ...args) {
		hql = initSort(hql);
		Query query = getSession().createQuery(hql);
		setAliasParameter(query, alias);
		setParameter(query, args);
		return query.list();
	}
	
//	public List<Object[]> listObjArray(String hql,Object[] args,Map<String,Object> alias) {
//		hql = initSort(hql);
//		Query query = getSession().createQuery(hql);
//		setAliasParameter(query, alias);
//		setParameter(query, args);
//		return query.list();
//	}
	
	/* (non-Javadoc)
	 * @see org.konghao.baisc.dao.IBaseDao#list(java.lang.String, java.lang.Object[], java.util.Map)
	 */
	public List<T> list(String hql, Map<String, Object> alias,Object ...args) {
		hql = initSort(hql);
		Query query = getSession().createQuery(hql);
		setAliasParameter(query, alias);
		setParameter(query, args);
		return query.list();
	}
	
	/* (non-Javadoc)
	 * @see org.konghao.baisc.dao.IBaseDao#find(java.lang.String, java.lang.Object[], java.util.Map)
	 */
	public Pager<T> find(String hql, Map<String, Object> alias,Object ...args) {
		hql = initSort(hql);
		String cq = getCountHql(hql,true);
		Query cquery = getSession().createQuery(cq);
		Query query = getSession().createQuery(hql);
		//设置别名参数
		setAliasParameter(query, alias);
		setAliasParameter(cquery, alias);
		//设置参数
		setParameter(query, args);
		setParameter(cquery, args);
		Pager<T> pages = new Pager<T>();
		setPagers(query,pages);
		List<T> datas = query.list();
		pages.setDatas(datas);
		long total = (Long)cquery.uniqueResult();
		pages.setTotal(total);
		return pages;
	}
	
	public <N extends Object>Pager<N> findObj(String hql, Map<String, Object> alias,Object ...args) {
		hql = initSort(hql);
		String cq = getCountHql(hql,true);
		Query cquery = getSession().createQuery(cq);
		Query query = getSession().createQuery(hql);
		//设置别名参数
		setAliasParameter(query, alias);
		setAliasParameter(cquery, alias);
		//设置参数
		setParameter(query, args);
		setParameter(cquery, args);
		Pager<N> pages = new Pager<N>();
		setPagers(query,pages);
		List<N> datas = query.list();
		pages.setDatas(datas);
		long total = (Long)cquery.uniqueResult();
		pages.setTotal(total);
		return pages;
	}
	
	public Pager<T> findNoCount(String hql, Map<String, Object> alias,Object ...args) {
		hql = initSort(hql);
		//String cq = getCountHql(hql,true);
		//Query cquery = getSession().createQuery(cq);
		Query query = getSession().createQuery(hql);
		//设置别名参数
		setAliasParameter(query, alias);
		//setAliasParameter(cquery, alias);
		//设置参数
		setParameter(query, args);
		//setParameter(cquery, args);
		Pager<T> pages = new Pager<T>();
		setPagers(query,pages);
		List<T> datas = query.list();
		pages.setDatas(datas);
		return pages;
	}
	
	/* (non-Javadoc)
	 * @see org.konghao.baisc.dao.IBaseDao#updateByHql(java.lang.String, java.lang.Object[])
	 */
	public void updateByHql(String hql, Object ...args) {
		Query query = getSession().createQuery(hql);
		setParameter(query, args);
		query.executeUpdate();
	}
	
	public <N extends Object>List<N> listBySql(String sql,
			Map<String, Object> alias, Class<?> clz, boolean hasEntity,Object ...args) {
		sql = initSort(sql);
		SQLQuery sq = getSession().createSQLQuery(sql);
		setAliasParameter(sq, alias);
		setParameter(sq, args);
		if(hasEntity) {
			sq.addEntity(clz);
		} else 
			sq.setResultTransformer(Transformers.aliasToBean(clz));
		return sq.list();
	}
	
	public <N extends Object>Pager<N> findBySql(String sql,
			Map<String, Object> alias, Class<?> clz, boolean hasEntity,Object ...args) {
		sql = initSort(sql);
		String cq = getCountHql(sql,false);
		SQLQuery sq = getSession().createSQLQuery(sql);
		SQLQuery cquery = getSession().createSQLQuery(cq);
		setAliasParameter(sq, alias);
		setAliasParameter(cquery, alias);
		setParameter(sq, args);
		setParameter(cquery, args);
		Pager<N> pages = new Pager<N>();
		setPagers(sq, pages);
		if(hasEntity) {
			sq.addEntity(clz);
		} else {
			sq.setResultTransformer(Transformers.aliasToBean(clz));
		}
		List<N> datas = sq.list();
		pages.setDatas(datas);
		long total = ((BigInteger)cquery.uniqueResult()).longValue();
		pages.setTotal(total);
		return pages;
	}
	
	public Object queryObject(String hql,
			Map<String, Object> alias,Object ...args) {
		Query query = getSession().createQuery(hql);
		setAliasParameter(query, alias);
		setParameter(query, args);
		return query.uniqueResult();
	}
	
	public <N extends Object>List<N> listBySql(String sql,Class<?> clz, boolean hasEntity,Object ...args) {
		return this.listBySql(sql,null, clz, hasEntity, args);
	}
	
	public <N extends Object>Pager<N> findBySql(String sql,Class<?> clz, boolean hasEntity,Object ...args) {
		return this.findBySql(sql, null, clz, hasEntity, args);
	}
	
	public Pager<T> findNoCount(String hql,Object ...args) {
		return this.findNoCount(hql, null, args);
	}
	
	public Pager<T> find(String hql,Object ...args) {
		return this.find(hql, null,args);
	}
	
	public <N extends Object>Pager<N> findObj(String hql,Object ...args) {
		return this.findObj(hql, null,args);
	}
	
	public List<T> list(String hql,Object ...args) {
		return this.list(hql, null, args);
	}
	
	public <N extends Object>List<N> listObj(String hql,Object ...args) {
		return this.listObj(hql,null, args);
	}
	
	public Object queryObject(String hql,Object ...args) {
		return queryObject(hql, null, args);
	}

	private String initSort(String hql) {
		String order = getSystemRequest().getOrder();
		String sort = getSystemRequest().getSort();
		if(sort!=null&&!"".equals(sort.trim())) {
			hql+=" order by "+sort;
			if(!"desc".equals(order)) hql+=" asc";
			else hql+=" desc";
		}
		return hql;
	}
	
	@SuppressWarnings("rawtypes")
	private void setAliasParameter(Query query,Map<String,Object> alias) {
		if(alias!=null) {
			Set<String> keys = alias.keySet();
			for(String key:keys) {
				Object val = alias.get(key);
				if(val instanceof Collection) {
					//查询条件是列表
					query.setParameterList(key, (Collection)val);
				} else {
					query.setParameter(key, val);
				}
			}
		}
	}
	
	private void setParameter(Query query,Object[] args) {
		if(args!=null&&args.length>0) {
			int index = 0;
			for(Object arg:args) {
				query.setParameter(index++, arg);
			}
		}
	}
	

	/* (non-Javadoc)
	 * @see org.konghao.baisc.dao.IBaseDao#list(java.lang.String, java.util.Map)
	 */
	public List<T> listByAlias(String hql, Map<String, Object> alias) {
		return this.list(hql, null, alias);
	}

	@SuppressWarnings("rawtypes")
	protected void setPagers(Query query,Pager pages) {
		Integer pageSize = getSystemRequest().getPageSize();
		Integer pageOffset = getSystemRequest().getPageOffset();
		if(pageOffset==null||pageOffset<0) pageOffset = 0;
		if(pageSize==null||pageSize<0) pageSize = 15;
		pages.setOffset(pageOffset);
		pages.setSize(pageSize);
		query.setFirstResult(pageOffset).setMaxResults(pageSize);
	}
	
	protected String getCountHql(String hql,boolean isHql) {
		String e = hql.substring(hql.indexOf("from"));
		String c = "select count(*) "+e;
		if(isHql)
			c = c.replaceAll("fetch", "");
		return c;
	}


	/* (non-Javadoc)
	 * @see org.konghao.baisc.dao.IBaseDao#find(java.lang.String, java.util.Map)
	 */
	public Pager<T> findByAlias(String hql, Map<String, Object> alias) {
		return this.find(hql,null, alias);
	}
	
	/* (non-Javadoc)
	 * @see org.konghao.baisc.dao.IBaseDao#find(java.lang.String, java.util.Map)
	 */
	public <N extends Object>Pager<N> findObjByAlias(String hql, Map<String, Object> alias) {
		return this.findObj(hql,null, alias);
	}


	public <N extends Object>List<N> listByAliasSql(String sql, Map<String, Object> alias,
			Class<?> clz, boolean hasEntity) {
		return this.listBySql(sql, alias, clz, hasEntity);
	}


	/* (non-Javadoc)
	 * @see org.konghao.baisc.dao.IBaseDao#findBySql(java.lang.String, java.util.Map, java.lang.Class, boolean)
	 */
	public <N extends Object>Pager<N> findByAliasSql(String sql, Map<String, Object> alias,
			Class<?> clz, boolean hasEntity) {
		return this.findBySql(sql, alias, clz, hasEntity);
	}

	public Object queryObjectByAlias(String hql, Map<String, Object> alias) {
		return this.queryObject(hql,null,alias);
	}
	
	public int getMaxOrder(Integer pid,String clz) {
		String hql = "select max(o.orderNum) from "+clz+" o where o.parent.id="+pid;
		if(pid==null||pid==0) hql = "select max(o.orderNum) from "+clz+" o where o.parent is null";
		Object obj = this.queryObject(hql);
		if(obj==null) return 0;
		return (Integer)obj;
	}
	
	public int getMaxOrder(String clz) {
		String hql = "select max(o.orderNum) from "+clz+" o ";
		Object obj = this.queryObject(hql);
		if(obj==null) return 0;
		return (Integer)obj;
	}
	
	public void updateSort(Integer[] ids,String clz) {
		int index = 1;
		String hql = "update "+clz+" m set m.orderNum=? where m.id=?";
		for(Integer id:ids) {
			this.updateByHql(hql, new Object[]{index++,id});
		}	
	}
	
	public Object loadBySn(String sn,String clz) {
		String hql = "select c from "+clz+" c where c.sn=?";
		return this.queryObject(hql, sn);
	}

}
