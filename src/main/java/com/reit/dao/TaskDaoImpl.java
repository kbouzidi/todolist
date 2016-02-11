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
import com.reit.model.Task;
import com.reit.model.User;
import com.reit.utils.EStates;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * <h3 id="target"><a name="user-content-target" href="#target" class="headeranchor-link" aria-hidden="true"><span
 * class="headeranchor"></span></a>DAO used to get information from
 * {@link Task}</h3>
 */
public class TaskDaoImpl extends AbstractDao implements IGenericDao<Task, Long> {

    public TaskDaoImpl() {
    }

    /**
     * @see com.reit.dao.IGenericDao#add(com.reit.model.Task)
     */
    public void add(Task task) {
        getCurrentSession().save(task);
    }

    /**
     * @see com.reit.dao.IGenericDao#update(com.reit.model.Task)
     */
    public void update(Task task) {
        getCurrentSession().update(task);
    }

    /**
     * @see com.reit.dao.IGenericDao#findById(java.lang.Long)
     */
    public Task findById(Long id) {
        Task task = (Task) getCurrentSession().get(Task.class, id);
        return task;
    }

    /**
     * @see com.reit.dao.IGenericDao#delete(com.reit.model.Task)
     */
    public void delete(Task task) {
        getCurrentSession().delete(task);
    }

    /**
     * @see com.reit.dao.IGenericDao#findAll()
     */
    public List<Task> findAll() {
        List<Task> taskList = (List<Task>) getCurrentSession().createQuery("from Task").list();
        return taskList;
    }

    /**
     * @see com.reit.dao.IGenericDao#deleteAll()
     */
    public void deleteAll() {
        List<Task> taskList = findAll();
        for (Task task : taskList) {
            delete(task);
        }
    }

    /**
     * ---------------------------------------------------------
     *
     * Specific Create
     *
     * ---------------------------------------------------------
     */
    public void add(Task task, Project project, User user) {
        Project projectResul = (Project) getCurrentSession().createCriteria(Project.class)
                .add(Restrictions.eq("projectName", project.getProjectName())).uniqueResult();

        User userResult = (User) getCurrentSession().createCriteria(User.class)
                .add(Restrictions.eq("userName", user.getUserName())).uniqueResult();

        if (projectResul != null && userResult != null) {
            task.setProject(projectResul);
            task.setCreatedBy(userResult);
            getCurrentSession().save(task);
        } else {
            getCurrentSession().save(task);
        }

    }

    /**
     * ---------------------------------------------------------
     *
     * Find By methods
     *
     * ---------------------------------------------------------
     */
    public Task findByTaskName(String taskName) {
        Task task = (Task) getCurrentSession().createCriteria(Task.class)
                .add(Restrictions.eq("taskName", taskName)).uniqueResult();
        return task;
    }

    public List<Task> findByProjectName(String projectName) {
        Project projectResul = (Project) getCurrentSession().createCriteria(Project.class)
                .add(Restrictions.eq("projectName", projectName)).uniqueResult();
        Criteria criteria = getCurrentSession().createCriteria(Task.class).add(Restrictions.eq("project", projectResul));
        return criteria.list();
    }

    public List<Task> findByUserName(String userName) {
        User user = (User) getCurrentSession().createCriteria(User.class)
                .add(Restrictions.eq("userName", userName)).uniqueResult();
        Criteria criteria = getCurrentSession().createCriteria(Task.class).add(Restrictions.eq("createdBy", user));
        return criteria.list();
    }

    public List<Task> findbyState(EStates state) {
        Criteria criteria = getCurrentSession().createCriteria(Task.class).add(Restrictions.eq("state", state));
        return criteria.list();
    }

    /**
     * ---------------------------------------------------------
     *
     * Delete methods
     *
     * ---------------------------------------------------------
     */
    public void deleteAllByProjectName(String projectName) {
        List<Task> taskList = findByProjectName(projectName);
        if (!taskList.isEmpty()) {
            for (Task task : taskList) {
                delete(task);
            }
        }

    }
    
      public void unlinkTaskToUser(String userName) {
        List<Task> taskList = findByUserName(userName);
        if (!taskList.isEmpty()) {
            for (Task task : taskList) {
                task.setCreatedBy(null);
                task.setAssignedTo(null);
                update(task);
            }
        }

    }
      
    

}
