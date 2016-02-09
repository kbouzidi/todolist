package com.reit.controller;

import com.google.gson.Gson;
import com.reit.model.Project;
import com.reit.model.User;
import com.reit.service.TaskService;
import com.reit.utils.EStates;
import com.reit.error.ResponseError;

import com.reit.model.Task;
import com.reit.service.ProjectService;
import com.reit.service.UserService;
import com.reit.utils.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

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

    public MainController(final TaskService taskService, final ProjectService projectService, final UserService userService) {

        // Set static file location {/resources/public}
        staticFileLocation("/public");

        // Health check
        get("/ping", (req, res) -> {
            return "pong";
        });

        // Add new user
        post("/add/user", (req, res) -> {
            User user = gson.fromJson(req.body(), User.class);
            userService.add(user);
            return user;
        }, json());

        // Add new project
        post("/add/project", (req, res) -> {
            Project project = gson.fromJson(req.body(), Project.class);
            projectService.add(project);
            return project;
        }, json());

        // Add new task
        post("/add/task", (req, res) -> {
            Task task = gson.fromJson(req.body(), Task.class);
            taskService.add(task, task.getProject(), task.getUser());
            return task;
        }, json());

        // Get all tasks
        get("/todos", (req, res)
                -> taskService.findAll(), json());

        // Update task state
        put("/update", (req, res) -> {
            Task todo = gson.fromJson(req.body(), Task.class);
            taskService.update(todo);
            return todo.getTask();
        }, json());

        // Delete task state
        delete("/todo/:id", (req, res) -> {
            Double id = Double.parseDouble(req.params(":id"));
            taskService.delete(id.longValue());
            return id;
        }, json());

        // Get done tasks 
        get("/done", (req, res)
                -> taskService.findbyState(EStates.DONE.getValue()), json());

        // Get task by User
        get("/todo/:user", (req, res)
                -> taskService.findbyUser(req.params(":user")), json());

        // Reset dashboard
        delete("/todos/all", (req, res) -> {
            taskService.deleteAll();
            return null;
        }, json());

        after((req, res) -> {
            res.type("application/json");
        });

        exception(IllegalArgumentException.class, (e, req, res) -> {
            res.status(400);
            res.body(JsonUtil.toJson(new ResponseError(e)));
        });
    }
}
