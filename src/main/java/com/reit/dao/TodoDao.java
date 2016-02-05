package com.reit.dao;


import com.reit.model.Todo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.util.List;

/**
 * *
 */
public class TodoDao implements ITodoDao<Todo, Long> {

    private Session currentSession;

    private Transaction currentTransaction;

    public TodoDao() {
    }

    /**
     * *
     *
     * @return
     */
    private static SessionFactory getSessionFactory() {
        Configuration configuration = new Configuration().configure();
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties());
        return configuration.buildSessionFactory(builder.build());
    }


    public Session openCurrentSession() {
        currentSession = getSessionFactory().openSession();
        return currentSession;
    }

    public Session openCurrentSessionwithTransaction() {
        currentSession = getSessionFactory().openSession();
        currentTransaction = currentSession.beginTransaction();
        return currentSession;
    }

    public void closeCurrentSession() {
        currentSession.close();
    }

    public void closeCurrentSessionwithTransaction() {
        currentTransaction.commit();
        currentSession.close();
    }

    public Session getCurrentSession() {
        return currentSession;
    }

    /**
     * Add Todo
     *
     * @param todo
     */
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

    public void deleteAll() {
        List<Todo> todoList = findAll();
        for (Todo todo : todoList) {
            delete(todo);
        }
    }
}
