package com.cmpe281.team2.miaas.core.mysql;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

@Repository
@SuppressWarnings("unchecked")
public class DataAccess {
    private SessionFactory sessionFactory;

    public Object save(Object entity) throws HibernateException {
        return getSession().save(entity);
    }

    public void update(Object entity) throws HibernateException {
        getSession().update(entity);
    }

    public void update(String hql, Object... params) throws HibernateException {
        Query query = getSession().createQuery(hql);
        for (int i = 0; i < params.length; i++) {
            query.setParameter(i, params[i]);
        }
        query.executeUpdate();
    }

    public void saveOrUpdate(Object entity) throws HibernateException {
        getSession().saveOrUpdate(entity);
    }

    public <T> T getFirstResultByCriteria(Criteria criteria, Class<T> clazz) throws HibernateException {
        List<T> list = criteria.list();
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }

    public <T> T getByUniqueProperty(String propertyName, Object value, Class<T> clazz) throws HibernateException {
        Criteria criteria = getSession().createCriteria(clazz);
        criteria.add(Restrictions.eq(propertyName, value));
        List<T> list = criteria.list();
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public <T> T getOneByHQL(String hql, Object... params) throws HibernateException {
        List<T> result = getByHQL(hql, params);
        if (result.size() == 0) {
            return null;
        }
        return result.get(0);
    }

    public <T> T getOneBySQL(String sql, Object... params) throws HibernateException {
        List<T> result = getBySQL(sql, params);
        if (result.size() == 0) {
            return null;
        }
        return result.get(0);
    }

    public <T> List<T> getByHQL(String hql, Object... params) throws HibernateException {
        Query query = getSession().createQuery(hql);
        for (int i = 0; i < params.length; i++) {
            query.setParameter(i, params[i]);
        }
        List<T> result = query.list();
        if (result == null) {
            result = new ArrayList<T>(0);
        }
        return result;
    }

    public <T> List<T> getBySQL(String sql, Object... params) throws HibernateException {
        Query query = getSession().createSQLQuery(sql);
        for (int i = 0; i < params.length; i++) {
            query.setParameter(i, params[i]);
        }
        List<T> result = query.list();
        if (result == null) {
            result = new ArrayList<T>(0);
        }
        return result;
    }

    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}

