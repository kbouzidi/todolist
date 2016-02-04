
package com.reit.dao;


import java.io.Serializable;
import java.util.List;


/**
 * 
 * @param <T>
 * @param <Id>
 */
public interface ITodoDao<T, Id extends Serializable> {

    public void add(T entity);

    public void update(T entity);

    public T findById(Id id);

    public void delete(T entity);

    public List<T> findAll();

    public void deleteAll();

}