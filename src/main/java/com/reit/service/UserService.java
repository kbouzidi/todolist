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
package com.reit.service;

import com.reit.dao.UserDaoImpl;
import com.reit.model.User;

import java.util.List;

/**
 * <h3 id="target"><a name="user-content-target" href="#target" class="headeranchor-link" aria-hidden="true"><span
 * class="headeranchor"></span></a>Service used to get information from
 * {@link User}</h3>
 */
public class UserService {
    private static UserDaoImpl userDao;

    public UserService() {
        userDao = new UserDaoImpl();
    }

    public Long add(User task) {
        getUserDao().openCurrentSessionwithTransaction();
        Long id = getUserDao().add(task);
        getUserDao().closeCurrentSessionwithTransaction();
        return id;
    }

    public void update(User entity) {
        getUserDao().openCurrentSessionwithTransaction();
        getUserDao().update(entity);
        getUserDao().closeCurrentSessionwithTransaction();
    }


    public void delete(Long id) {
        getUserDao().openCurrentSessionwithTransaction();
        User task = getUserDao().findById(id);
        getUserDao().delete(task);
        getUserDao().closeCurrentSessionwithTransaction();
    }

    public void delete(String userName) {
        getUserDao().openCurrentSessionwithTransaction();
        User task = getUserDao().findByUserName(userName);
        getUserDao().delete(task);
        getUserDao().closeCurrentSessionwithTransaction();
    }


    public void deleteAll() {
        getUserDao().openCurrentSessionwithTransaction();
        getUserDao().deleteAll();
        getUserDao().closeCurrentSessionwithTransaction();
    }


    public User findByUserName(String userName) {
        getUserDao().openCurrentSessionwithTransaction();
        User user = getUserDao().findByUserName(userName);
        getUserDao().closeCurrentSessionwithTransaction();
        return user;
    }

    public User findById(Long id) {
        getUserDao().openCurrentSession();
        User user = getUserDao().findById(id);
        getUserDao().closeCurrentSession();
        return user;
    }

    public List<User> findAll() {
        getUserDao().openCurrentSession();
        List<User> userList = userDao.findAll();
        getUserDao().closeCurrentSession();
        return userList;
    }


    public UserDaoImpl getUserDao() {
        return userDao;
    }
}
