package com.reit.controller;

import com.reit.error.ResponseError;
import java.util.List;

import com.reit.model.Todo;
import com.reit.service.TodoService;
import com.reit.utils.JsonUtil;
import static com.reit.utils.JsonUtil.json;
import static spark.Spark.after;
import static spark.Spark.exception;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.staticFileLocation;

public class MainController {

    public MainController(final TodoService todoService) {

        // Set static file location {/resources/public}
        staticFileLocation("/public");

        get("/", (req, res) -> {
            return "Done";
        });

        // Health check
        get("/ping", (req, res) -> {
            return "pong";
        });

        post("/add/:user/:desc/:state", (req, res) -> {
            String user = req.params(":user");
            String description = req.params(":desc");
            String state = req.params(":state");
            Todo todo = new Todo(user, description, state);
            todoService.add(todo);
            return todo;
        }, JsonUtil.json());


        /*get("/todos", (request, response) -> {
         List<Todo> todoList = todoService.findAll();
         return todoList;
         });*/
        get("/todos", (req, res) -> todoService.findAll(), json());

        after((req, res) -> {
            res.type("application/json");
        });

        exception(IllegalArgumentException.class, (e, req, res) -> {
            res.status(400);
            res.body(JsonUtil.toJson(new ResponseError(e)));
        });
    }
}
