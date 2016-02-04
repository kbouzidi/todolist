package com.reit.service;

import com.reit.dao.TodoDao;
import com.reit.model.ToDo;

import java.util.List;


public class TodoService {

    private static TodoDao todoDao;

    public TodoService() {

        todoDao = new TodoDao();
    }

    public void add(ToDo todo) {
        todoDao.openCurrentSessionwithTransaction();
        todoDao.add(todo);
        todoDao.closeCurrentSessionwithTransaction();
    }

    public void update(ToDo entity) {
        todoDao.openCurrentSessionwithTransaction();
        todoDao.update(entity);
        todoDao.closeCurrentSessionwithTransaction();
    }

    public ToDo findById(String id) {
        todoDao.openCurrentSession();
        ToDo ToDo = todoDao.findById(id);
        todoDao.closeCurrentSession();
        return ToDo;
    }

    public void delete(String id) {
        todoDao.openCurrentSessionwithTransaction();
        ToDo ToDo = todoDao.findById(id);
        todoDao.delete(ToDo);
        todoDao.closeCurrentSessionwithTransaction();
    }

    public List<ToDo> findAll() {
        todoDao.openCurrentSession();
        List<ToDo> ToDos = todoDao.findAll();
        todoDao.closeCurrentSession();
        return ToDos;
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
