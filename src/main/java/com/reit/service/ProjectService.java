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

import java.util.ArrayList;
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

    public Long add(Project project) {
        getProjectDao().openCurrentSessionwithTransaction();
        Long projectId = getProjectDao().add(project);
        getProjectDao().closeCurrentSessionwithTransaction();
        return projectId;
    }

    public void update(Project entity) {
        getProjectDao().openCurrentSessionwithTransaction();
        getProjectDao().update(entity);
        getProjectDao().closeCurrentSessionwithTransaction();
    }

    public void delete(Long id) {
        // Get project byID
        getProjectDao().openCurrentSessionwithTransaction();
        Project project = getProjectDao().findById(id);
        getProjectDao().closeCurrentSessionwithTransaction();

        // unlink user
        project.setUser(null);
        update(project);

        // Delete project
        getProjectDao().openCurrentSessionwithTransaction();
        getProjectDao().delete(project);
        getProjectDao().closeCurrentSessionwithTransaction();
    }


    public void deleteAll() {
        getProjectDao().openCurrentSessionwithTransaction();
        getProjectDao().deleteAll();
        getProjectDao().closeCurrentSessionwithTransaction();
    }

    public List<Project> findAll() {
        getProjectDao().openCurrentSession();
        List<Project> projectList = projectrDao.findAll();
        getProjectDao().closeCurrentSession();
        return projectList;
    }

    public Project findById(Long id) {
        getProjectDao().openCurrentSession();
        Project project = getProjectDao().findById(id);
        getProjectDao().closeCurrentSession();
        return project;
    }

    public List<Project> findByUserId(Long userId) {
        getProjectDao().openCurrentSession();
        List<Project> projectList = getProjectDao().findByUserId(userId);
        getProjectDao().closeCurrentSession();
        return projectList;
    }

    public void unlinkProjectsToUser(Long userId) {
        getProjectDao().openCurrentSession();
        List<Project> projectList = getProjectDao().findByUseId(userId);
        getProjectDao().closeCurrentSession();
        if (!projectList.isEmpty()) {
            getProjectDao().openCurrentSessionwithTransaction();
            getProjectDao().unlinkProject(projectList);
            getProjectDao().closeCurrentSessionwithTransaction();
        }

    }


    public ProjectDaoImpl getProjectDao() {
        return projectrDao;
    }
}
