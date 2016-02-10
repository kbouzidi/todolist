/*
 * The MIT License
 *
 * Copyright 2016 kbouzidi.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.reit.dao;

import com.reit.model.User;

import java.util.List;

/**
 * <h3 id="target"><a name="user-content-target" href="#target" class="headeranchor-link" aria-hidden="true"><span
 * class="headeranchor"></span></a>DAO used to get information from
 * {@link User}</h3>
 */
public class UserDaoImpl extends AbstractDao implements IGenericDao<User, Long> {

    /**
     * @see com.reit.dao.IGenericDao#add(com.reit.model.User) 
     */
    @Override
    public void add(User entity) {
        getCurrentSession().save(entity);
    }

    /**
     * @see com.reit.dao.IGenericDao#update(com.reit.model.User)
     */
    @Override
    public void update(User entity) {
        getCurrentSession().update(entity);
    }

    /**
     * @see com.reit.dao.IGenericDao#findById(java.lang.Long)
     */
    @Override
    public User findById(Long id) {
        User user = (User) getCurrentSession().get(User.class, id);
        return user;
    }

    /**
     * @see com.reit.dao.IGenericDao#delete(com.reit.model.User)
     */
    @Override
    public void delete(User entity) {
        getCurrentSession().delete(entity);
    }

    /**
     * @see com.reit.dao.IGenericDao#findAll()
     */
    @Override
    public List<User> findAll() {
        List<User> userList = (List<User>) getCurrentSession().createQuery("from User").list();
        return userList;
    }

    /**
     * @see com.reit.dao.IGenericDao#deleteAll()
     */
    @Override
    public void deleteAll() {
        List<User> userList = findAll();
        for (User user : userList) {
            delete(user);
        }
    }

}
