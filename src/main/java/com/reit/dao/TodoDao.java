package com.reit.dao;


import com.reit.model.Todo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * *
 */
public class TodoDao extends AbstractDao implements ITodoDao<Todo, Long> {

    public TodoDao() {
    }


    public void add(Todo todo) {
        getCurrentSession().save(todo);
    }

    public void update(Todo todo) {
        getCurrentSession().update(todo);
    }

    public Todo findById(Long id) {
        Todo todo = (Todo) getCurrentSession().get(Todo.class, id);
        return todo;
    }

    public void delete(Todo todo) {

        getCurrentSession().delete(todo);
    }


    public List<Todo> findAll() {
        List<Todo> todoList = (List<Todo>) getCurrentSession().createQuery("from Todo").list();
        return todoList;
    }


    public List<Todo> findbyState(String state) {
        Criteria criteria = getCurrentSession().createCriteria(Todo.class).add(Restrictions.eq("state", state));
        return criteria.list();
    }

    public List<Todo> findbyUser(String user) {
        Criteria criteria = getCurrentSession().createCriteria(Todo.class).add(Restrictions.eq("author", user));
        return criteria.list();
    }


    public void deleteAll() {
        List<Todo> todoList = findAll();
        for (Todo todo : todoList) {
            delete(todo);
        }
    }
}
