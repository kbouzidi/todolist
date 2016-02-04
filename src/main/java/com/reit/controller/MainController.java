package com.reit.controller;

import java.util.List;

import com.reit.model.Todo;
import com.reit.service.TodoService;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.staticFileLocation;


public class MainController {

    public MainController(final TodoService todoService) {

        // Set static file location {/resources/public}
        staticFileLocation("/public");


        get("/", (request, response) -> {
            return "Done";
        });

        // Health check
        get("/ping", (request, response) -> {
            return "pong";
        });

        post("/add", (request, response) -> {
            Todo todo = new Todo("1", "Test-TODO", "Tester", "On Going");
            todoService.add(todo);
            return "Ok, IN";
        });


        get("/todos", (request, response) -> {
            List<Todo> todoList = todoService.findAll();
            return todoList;
        });
    }
}
