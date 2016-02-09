package com.reit.dao;


import com.reit.model.Project;
import com.reit.model.Task;
import com.reit.model.User;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * *
 */
public class TaskDaoImpl extends AbstractDao implements IGenericDao<Task, Long> {

    public TaskDaoImpl() {
    }

    public void add(Task task, Project project, User user) {
        Project projectResul = (Project) getCurrentSession().createCriteria(Project.class)
                .add(Restrictions.eq("projectName", project.getProjectName())).uniqueResult();

        User userResult = (User) getCurrentSession().createCriteria(User.class)
                .add(Restrictions.eq("userName", user.getUserName())).uniqueResult();

        if (projectResul != null && userResult != null) {
            task.setProject(projectResul);
            task.setUser(userResult);
            getCurrentSession().save(task);
        } else {
            getCurrentSession().save(task);
        }

    }

    public void add(Task task) {
        getCurrentSession().save(task);
    }

    public void update(Task task) {
        getCurrentSession().update(task);
    }

    public Task findById(Long id) {
        Task task = (Task) getCurrentSession().get(Task.class, id);
        return task;
    }

    public void delete(Task task) {

        getCurrentSession().delete(task);
    }


    public List<Task> findAll() {
        List<Task> taskList = (List<Task>) getCurrentSession().createQuery("from Task").list();
        return taskList;
    }


    public List<Task> findbyState(String state) {
        Criteria criteria = getCurrentSession().createCriteria(Task.class).add(Restrictions.eq("state", state));
        return criteria.list();
    }

    public List<Task> findbyUser(String user) {
        Criteria criteria = getCurrentSession().createCriteria(Task.class).add(Restrictions.eq("author", user));
        return criteria.list();
    }


    public void deleteAll() {
        List<Task> taskList = findAll();
        for (Task task : taskList) {
            delete(task);
        }
    }
}
