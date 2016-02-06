package com.reit.service;

import com.reit.dao.TodoDao;
import com.reit.model.Todo;

import java.util.List;


public class TodoService {

    private static TodoDao todoDao;

    public TodoService() {

        todoDao = new TodoDao();
    }

    public void add(Todo todo) {
        todoDao.openCurrentSessionwithTransaction();
        todoDao.add(todo);
        todoDao.closeCurrentSessionwithTransaction();
    }

    public void update(Todo entity) {
        todoDao.openCurrentSessionwithTransaction();
        todoDao.update(entity);
        todoDao.closeCurrentSessionwithTransaction();
    }

    public Todo findById(Long id) {
        todoDao.openCurrentSession();
        Todo todo = todoDao.findById(id);
        todoDao.closeCurrentSession();
        return todo;
    }

    public void delete(Long id) {
        todoDao.openCurrentSessionwithTransaction();
        Todo todo = todoDao.findById(id);
        todoDao.delete(todo);
        todoDao.closeCurrentSessionwithTransaction();
    }

    public List<Todo> findAll() {
        todoDao.openCurrentSession();
        List<Todo> todoList = todoDao.findAll();
        todoDao.closeCurrentSession();
        return todoList;
    }

    public List<Todo> findbyState(String state) {
        todoDao.openCurrentSession();
        List<Todo> todoList = todoDao.findbyState(state);
        todoDao.closeCurrentSession();
        return todoList;
    }


    public List<Todo> findbyUser(String user) {
        todoDao.openCurrentSession();
        List<Todo> todoList = todoDao.findbyUser(user);
        todoDao.closeCurrentSession();
        return todoList;
    }

    public void deleteAll() {
        todoDao.openCurrentSessionwithTransaction();
        todoDao.deleteAll();
        todoDao.closeCurrentSessionwithTransaction();
    }

    public TodoDao todoDao() {
        return todoDao;
    }
}
