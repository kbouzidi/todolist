package com.reit.test.utils;

import com.reit.dao.EStates;
import com.reit.model.Todo;

import java.util.List;


public class Constants {

    public static final String user = "User";
    public static final String desc = "This is a Task";
    public static final String state = EStates.STARTED.getValue();

    public static List<Todo> getTodoList(List<Todo> todoList) {
        Todo t1 = new Todo(Long.decode("1"), user, desc, state);
        Todo t2 = new Todo(Long.decode("2"), user + "2", desc + "2", state);
        todoList.add(t1);
        todoList.add(t2);
        return todoList;
    }
}
