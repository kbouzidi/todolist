package com.reit.controller;

import com.google.gson.Gson;
import com.reit.error.ResponseError;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

import com.reit.model.Todo;
import com.reit.service.TodoService;
import com.reit.utils.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.reit.utils.JsonUtil.json;
import static spark.Spark.after;
import static spark.Spark.exception;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;
import static spark.Spark.delete;
import static spark.Spark.staticFileLocation;

public class MainController {

    static Logger logger = LoggerFactory.getLogger(MainController.class);

    Gson gson = new Gson();

    public MainController(final TodoService todoService) {

        // Set static file location {/resources/public}
        staticFileLocation("/public");

        // Health check
        get("/ping", (req, res) -> {
            return "pong";
        });

        // Add new task
        post("/add", (req, res) -> {
            Todo todo = gson.fromJson(req.body(), Todo.class);
            todoService.add(todo);
            return todo;
        }, JsonUtil.json());


        // Get all tasks
        get("/todos", (req, res) -> todoService.findAll(), json());


        // Update task state
        put("/update", (req, res) -> {
            Todo todo = gson.fromJson(req.body(), Todo.class);
            todoService.update(todo);
            return todo.getTodo();
        }, JsonUtil.json());


        // Delete task state
        delete("/delete", (req, res) -> {
            return "Delete";
        }, JsonUtil.json());

        after((req, res) -> {
            res.type("application/json");
        });

        exception(IllegalArgumentException.class, (e, req, res) -> {
            res.status(400);
            res.body(JsonUtil.toJson(new ResponseError(e)));
        });
    }
}
