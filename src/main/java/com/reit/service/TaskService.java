package com.reit.service;

import com.reit.dao.TaskDaoImpl;
import com.reit.model.Project;
import com.reit.model.Task;
import com.reit.model.User;

import java.util.List;


public class TaskService {

    private static TaskDaoImpl taskDao;

    public TaskService() {
        taskDao = new TaskDaoImpl();
    }

    public void add(Task task) {
        getTaskDao().openCurrentSessionwithTransaction();
        getTaskDao().add(task);
        getTaskDao().closeCurrentSessionwithTransaction();
    }

    public void add(Task task, Project project, User user) {
        getTaskDao().openCurrentSessionwithTransaction();
        getTaskDao().add(task, project, user);
        getTaskDao().closeCurrentSessionwithTransaction();
    }

    public void update(Task entity) {
        getTaskDao().openCurrentSessionwithTransaction();
        getTaskDao().update(entity);
        getTaskDao().closeCurrentSessionwithTransaction();
    }

    public Task findById(Long id) {
        getTaskDao().openCurrentSession();
        Task task = getTaskDao().findById(id);
        getTaskDao().closeCurrentSession();
        return task;
    }


    public List<Task> findByProjectName(String projectName) {
        getTaskDao().openCurrentSession();
        List<Task> task = getTaskDao().findByProjectName(projectName);
        getTaskDao().closeCurrentSession();
        return task;
    }

    public void delete(Long id) {
        getTaskDao().openCurrentSessionwithTransaction();
        Task task = getTaskDao().findById(id);
        getTaskDao().delete(task);
        getTaskDao().closeCurrentSessionwithTransaction();
    }

    public List<Task> findAll() {
        getTaskDao().openCurrentSession();
        List<Task> taskList = taskDao.findAll();
        getTaskDao().closeCurrentSession();
        return taskList;
    }

    public List<Task> findbyState(String state) {
        getTaskDao().openCurrentSession();
        List<Task> taskList = taskDao.findbyState(state);
        getTaskDao().closeCurrentSession();
        return taskList;
    }


    public List<Task> findbyUser(String user) {
        getTaskDao().openCurrentSession();
        List<Task> taskList = taskDao.findbyUser(user);
        getTaskDao().closeCurrentSession();
        return taskList;
    }

    public void deleteAll() {
        getTaskDao().openCurrentSessionwithTransaction();
        getTaskDao().deleteAll();
        getTaskDao().closeCurrentSessionwithTransaction();
    }

    public TaskDaoImpl getTaskDao() {
        return taskDao;
    }
}
