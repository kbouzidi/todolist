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

import com.reit.dao.ProjectDaoImpl;
import com.reit.model.Project;
import java.util.List;

/**
 * <h3 id="target"><a name="user-content-target" href="#target" class="headeranchor-link" aria-hidden="true"><span
 * class="headeranchor"></span></a>Service used to get information from
 * {@link Project}</h3>
 */
public class ProjectService {

    private static ProjectDaoImpl projectrDao;

    public ProjectService() {
        projectrDao = new ProjectDaoImpl();
    }

    public void add(Project task) {
        getProjectDao().openCurrentSessionwithTransaction();
        getProjectDao().add(task);
        getProjectDao().closeCurrentSessionwithTransaction();
    }

    public void update(Project entity) {
        getProjectDao().openCurrentSessionwithTransaction();
        getProjectDao().update(entity);
        getProjectDao().closeCurrentSessionwithTransaction();
    }

    public Project findById(Long id) {
        getProjectDao().openCurrentSession();
        Project task = getProjectDao().findById(id);
        getProjectDao().closeCurrentSession();
        return task;
    }

    public void delete(Long id) {
        getProjectDao().openCurrentSessionwithTransaction();
        Project task = getProjectDao().findById(id);
        getProjectDao().delete(task);
        getProjectDao().closeCurrentSessionwithTransaction();
    }

    public List<Project> findAll() {
        getProjectDao().openCurrentSession();
        List<Project> taskList = projectrDao.findAll();
        getProjectDao().closeCurrentSession();
        return taskList;
    }

    /* public List<Project> findbyState(String state) {
     getProjectDao().openCurrentSession();
     List<Project> taskList = projectrDao.findbyState(state);
     getProjectDao().closeCurrentSession();
     return taskList;
     }

     public List<Project> findbyUser(String user) {
     getProjectDao().openCurrentSession();
     List<Project> taskList = projectrDao.findbyUser(user);
     getProjectDao().closeCurrentSession();
     return taskList;
     }*/
    public void deleteAll() {
        getProjectDao().openCurrentSessionwithTransaction();
        getProjectDao().deleteAll();
        getProjectDao().closeCurrentSessionwithTransaction();
    }

    public ProjectDaoImpl getProjectDao() {
        return projectrDao;
    }
}
