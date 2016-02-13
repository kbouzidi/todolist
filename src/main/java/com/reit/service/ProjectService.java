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

    public void add(Project project) {
        getProjectDao().openCurrentSessionwithTransaction();
        getProjectDao().add(project);
        getProjectDao().closeCurrentSessionwithTransaction();
    }

    public Long addNew(Project project) {
        getProjectDao().openCurrentSessionwithTransaction();
        Long id = getProjectDao().addNewProject(project);
        getProjectDao().closeCurrentSessionwithTransaction();
        return id;
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

    public void deleteByProjectName(String projectName) {
        getProjectDao().openCurrentSessionwithTransaction();
        Project project = getProjectDao().findByProjectName(projectName);
        if (project != null) {
            getProjectDao().delete(project);
        }
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

    public Project findByProjectName(String projectName) {
        getProjectDao().openCurrentSession();
        Project project = getProjectDao().findByProjectName(projectName);
        getProjectDao().closeCurrentSession();
        return project;
    }

    public void unlinkProjectsToUser(String userName) {
        getProjectDao().openCurrentSession();
        List<Project> projectList = getProjectDao().findByUserName(userName);
        getProjectDao().closeCurrentSession();
        getProjectDao().openCurrentSessionwithTransaction();
        getProjectDao().unlinkProject(projectList);
        getProjectDao().closeCurrentSessionwithTransaction();
    }

    // TODO implement method
    public List<Project> findByUserName(String projectName) {
        return new ArrayList<>();
    }

    public ProjectDaoImpl getProjectDao() {
        return projectrDao;
    }
}
