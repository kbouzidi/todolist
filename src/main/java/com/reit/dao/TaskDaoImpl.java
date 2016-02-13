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
     * ---------------------------------------------------------
     * <p/>
     * Generic methods
     * <p/>
     * ---------------------------------------------------------
     */
    /**
     * @see com.reit.dao.IGenericDao#add(com.reit.model.Task)
     */
    @Override
    public Long add(Task entity) {
        return (Long) getCurrentSession().save(entity);
    }

    /**
     * @see com.reit.dao.IGenericDao#update(com.reit.model.Task)
     */
    @Override
    public void update(Task entity) {
        getCurrentSession().update(entity);
    }

    /**
     * @see com.reit.dao.IGenericDao#findById(java.lang.Long)
     */
    @Override
    public Task findById(Long id) {
        Task task = (Task) getCurrentSession().get(Task.class, id);
        return task;
    }

    /**
     * @param task {@link Task}
     * @see com.reit.dao.IGenericDao#delete(com.reit.model.Task)
     */
    @Override
    public void delete(Task task) {
        getCurrentSession().delete(task);
    }

    /**
     * @see com.reit.dao.IGenericDao#findAll()
     */
    @Override
    public List<Task> findAll() {
        List<Task> taskList = (List<Task>) getCurrentSession().createQuery("from Task").list();
        return taskList;
    }

    /**
     * @see com.reit.dao.IGenericDao#deleteAll()
     */
    @Override
    public void deleteAll() {
        List<Task> taskList = findAll();
        taskList.stream().forEach((task) -> {
            delete(task);
        });
    }

    public void merge(Task entity) {
        getCurrentSession().merge(entity);
    }
    /**
     * ---------------------------------------------------------
     *
     * Create methods
     *
     * ---------------------------------------------------------
     */
    /**
     * Add task
     *
     * @param task
     * @param project
     * @param user
     */
    public Task add(Task task, Project project, User user) {


        Project projectResult = (Project) getCurrentSession().get(Project.class, project.getProjectId());

        User userResult = (User) getCurrentSession().get(User.class, user.getUserId());

        if (projectResult != null && userResult != null) {
            task.setProject(projectResult);
            task.setCreatedBy(userResult);
            Long id = (Long) getCurrentSession().save(task);
            task.setTaskId(id);
            return task;
        }
        return null;

    }

    /**
     * ---------------------------------------------------------
     *
     * Find methods
     *
     * ---------------------------------------------------------
     */
    /**
     * Find task by name
     *
     * @param taskName
     * @return task {@link Task}
     */
    public Task findByTaskName(String taskName) {
        Task task = (Task) getCurrentSession().createCriteria(Task.class)
                .add(Restrictions.ilike("taskName", taskName)).uniqueResult();
        return task;
    }

    /**
     * Find task by project name
     *
     * @param projectName
     * @return {@link List<>}
     */
    public List<Task> findByProjectName(String projectName) {
        Project projectResul = (Project) getCurrentSession().createCriteria(Project.class)
                .add(Restrictions.eq("project", projectName)).uniqueResult();
        Criteria criteria = getCurrentSession().createCriteria(Task.class).add(Restrictions.eq("project", projectResul));
        return criteria.list();
    }

    /**
     * Find task by user name
     *
     * @param userName
     * @return {@link List<>}
     */
    public List<Task> findByUserName(String userName) {
        User user = (User) getCurrentSession().createCriteria(User.class)
                .add(Restrictions.eq("userName", userName)).uniqueResult();
        Criteria criteria = getCurrentSession().createCriteria(Task.class).add(Restrictions.eq("createdBy", user));
        return criteria.list();
    }

    /**
     * Find task by state
     *
     * @param state
     * @return {@link List<>}
     */
    public List<Task> findbyState(EStates state) {
        Criteria criteria = getCurrentSession().createCriteria(Task.class).add(Restrictions.eq("state", state));
        return criteria.list();
    }

    public List<Task> findByProject(Project project) {
        Criteria criteria = getCurrentSession().createCriteria(Task.class).add(Restrictions.eq("project", project));
        return criteria.list();
    }

    public List<Task> findByProjectId(Long projectId) {
        Criteria criteria = getCurrentSession().createCriteria(Task.class).add(Restrictions.eq("project.projectId", projectId));
        return criteria.list();
    }

    public List<Task> findByUserId(Long userId) {
        Criteria criteria = getCurrentSession().createCriteria(Task.class).add(Restrictions.eq("createdBy.userId", userId));
        return criteria.list();
    }
    /**
     * ---------------------------------------------------------
     *
     * Delete methods
     *
     * ---------------------------------------------------------
     */
    /**
     * Delete all tasks related to a project
     *
     * @param taskList
     */
    public void deleteAllTasks(List<Task> taskList) {
        if (!taskList.isEmpty()) {
            taskList.stream().forEach((task) -> {
                delete(task);
            });
        }

    }

    /**
     * ---------------------------------------------------------
     *
     * Update methods
     *
     * ---------------------------------------------------------
     */
    /**
     * Unlink Task from user
     *
     * @param userName
     */
    public void unlinkTaskToUser(String userName) {
        List<Task> taskList = findByUserName(userName);
        if (!taskList.isEmpty()) {
            taskList.stream().map((task) -> {
                task.setCreatedBy(null);
                return task;
            }).map((task) -> {
                task.setAssignedTo(null);
                return task;
            }).forEach((task) -> {
                update(task);
            });
        }

    }

}
