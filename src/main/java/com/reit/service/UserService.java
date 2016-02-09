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
 *
 * @author kbouzidi
 */
public class UserService {
     private static UserDaoImpl userDao;

    public UserService() {
        userDao = new UserDaoImpl();
    }

    public void add(User task) {
        getUserDao().openCurrentSessionwithTransaction();
        getUserDao().add(task);
        getUserDao().closeCurrentSessionwithTransaction();
    }

    public void update(User entity) {
        getUserDao().openCurrentSessionwithTransaction();
        getUserDao().update(entity);
        getUserDao().closeCurrentSessionwithTransaction();
    }

    public User findById(Long id) {
        getUserDao().openCurrentSession();
        User task = getUserDao().findById(id);
        getUserDao().closeCurrentSession();
        return task;
    }

    public void delete(Long id) {
        getUserDao().openCurrentSessionwithTransaction();
        User task = getUserDao().findById(id);
        getUserDao().delete(task);
        getUserDao().closeCurrentSessionwithTransaction();
    }

    public List<User> findAll() {
        getUserDao().openCurrentSession();
        List<User> taskList = userDao.findAll();
        getUserDao().closeCurrentSession();
        return taskList;
    }

    /*public List<User> findbyState(String state) {
        getUserDao().openCurrentSession();
        List<User> taskList = userDao.findbyState(state);
        getUserDao().closeCurrentSession();
        return taskList;
    }

    public List<User> findbyUser(String user) {
        getUserDao().openCurrentSession();
        List<User> taskList = userDao.findbyUser(user);
        getUserDao().closeCurrentSession();
        return taskList;
    }*/

    public void deleteAll() {
        getUserDao().openCurrentSessionwithTransaction();
        getUserDao().deleteAll();
        getUserDao().closeCurrentSessionwithTransaction();
    }

    public UserDaoImpl getUserDao() {
        return userDao;
    }
}
