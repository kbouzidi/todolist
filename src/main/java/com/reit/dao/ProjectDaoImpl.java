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

import com.reit.model.Project;
import com.reit.model.User;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.List;

/**
 * <h3 id="target"><a name="user-content-target" href="#target" class="headeranchor-link" aria-hidden="true"><span
 * class="headeranchor"></span></a>DAO used to get information from
 * {@link Project}</h3>
 */
public class ProjectDaoImpl extends AbstractDao implements IGenericDao<Project, Long> {

    public ProjectDaoImpl() {
    }

    /**
     * @see com.reit.dao.IGenericDao#add(com.reit.model.Project)
     */
    public void add(Project entity) {
        getCurrentSession().save(entity);
    }

    /**
     * @see com.reit.dao.IGenericDao#update(com.reit.model.Project)
     */
    @Override
    public void update(Project entity) {
        getCurrentSession().update(entity);
    }

    /**
     * @see com.reit.dao.IGenericDao#findById(java.lang.Long)
     */
    @Override
    public Project findById(Long id) {
        Project project = (Project) getCurrentSession().get(Project.class, id);
        return project;
    }

    /**
     * @see com.reit.dao.IGenericDao#delete(com.reit.model.Project)
     */
    @Override
    public void delete(Project entity) {
        getCurrentSession().delete(entity);
    }

    /**
     * @see com.reit.dao.IGenericDao#findAll()
     */
    @Override
    public List<Project> findAll() {
        List<Project> projectList = (List<Project>) getCurrentSession().createQuery("from Project").list();
        return projectList;
    }

    /**
     * @see com.reit.dao.IGenericDao#deleteAll()
     */
    @Override
    public void deleteAll() {
        List<Project> projectList = findAll();
        for (Project project : projectList) {
            delete(project);
        }
    }

    /**
     * ---------------------------------------------------------
     * <p/>
     * Find By methods
     * <p/>
     * ---------------------------------------------------------
     */
    /**
     * Find By project name
     *
     * @param projectName
     * @return project {@link Project}
     */
    public Project findByProjectName(String projectName) {
        Project project = (Project) getCurrentSession().createCriteria(Project.class)
                .add(Restrictions.eq("projectName", projectName)).uniqueResult();
        return project;
    }

    /**
     *
     * Find By user name
     *
     * @param userName
     * @return project {@link List}
     */
    public List<Project> findByUserName(String userName) {
        User userResult = (User) getCurrentSession().createCriteria(User.class)
                .add(Restrictions.eq("userName", userName)).uniqueResult();
        Criteria criteria = getCurrentSession().createCriteria(Project.class).add(Restrictions.eq("user", userResult));
        return criteria.list();
    }

    /**
     * ---------------------------------------------------------
     * <p/>
     * Update By methods
     * <p/>
     * ---------------------------------------------------------
     */
    /**
     * Unlink Project
     *
     * @param projects
     */
    public void unlinkProject(List<Project> projects) {
        if (!projects.isEmpty()) {
            for (Project project : projects) {
                project.setUser(null);
                getCurrentSession().update(project);
            }
        }

    }

}
