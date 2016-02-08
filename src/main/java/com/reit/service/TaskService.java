package com.reit.service;

import com.reit.dao.TaskDao;
import com.reit.model.Task;

import java.util.List;


public class TaskService {

    private static TaskDao taskDao;

    public TaskService() {
        taskDao = new TaskDao();
    }

    public void add(Task task) {
        getTaskDao().openCurrentSessionwithTransaction();
        getTaskDao().add(task);
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

    public TaskDao getTaskDao() {
        return taskDao;
    }
}
