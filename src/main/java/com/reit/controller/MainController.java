/*
 * The MIT License
 *
 * Copyright 2016 kbouzidi.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.reit.controller;

import com.google.gson.Gson;
import com.reit.model.Project;
import com.reit.model.User;
import com.reit.service.TaskService;
import com.reit.error.ErrorHandler;

import com.reit.model.Task;
import com.reit.service.ProjectService;
import com.reit.service.UserService;
import com.reit.utils.EStates;
import com.reit.utils.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.reit.utils.JsonUtil.json;
import static spark.Spark.after;
import static spark.Spark.exception;

import static spark.Spark.port;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;
import static spark.Spark.delete;
import static spark.Spark.options;
import static spark.Spark.before;
import static spark.Spark.staticFileLocation;

/**
 * <h3 id="target"><a name="user-content-target" href="#target" class="headeranchor-link" aria-hidden="true"><span
 * class="headeranchor"></span></a>Main routes controller</h3>
 */
public class MainController {

    static Logger logger = LoggerFactory.getLogger(MainController.class);

    Gson gson = new Gson();

    static final String SUCCESS = "SUCCESS";

    /**
     * Main Controller constructor
     *
     * @param taskService {@link TaskService}
     * @param projectService {@link ProjectService}
     * @param userService {@link UserService}
     * @param port {@link Integer}
     */
    public MainController(final TaskService taskService, final ProjectService projectService, final UserService userService, Integer port) {

        if (port != null) {
            port(port);
        }
        // Set static file location {/resources/public}
        staticFileLocation("/public");

        /**
         * ---------------------------------------------------------
         *
         * Health check routes For monitoring purpose
         *
         * ---------------------------------------------------------
         */
        // Health check
        get("/ping", (req, res) -> {
            return "pong";
        });

        /**
         * ---------------------------------------------------------
         *
         * User routes
         *
         * ---------------------------------------------------------
         */
        // Add new user
        post("/user/add", (req, res) -> {
            User user = gson.fromJson(req.body(), User.class);
            userService.add(user);
            return user;
        }, json());

        /**
         * Find all users*
         */
        get("/users", (req, res)
                -> userService.findAll(), json());

        /**
         * Get users by project Name *
         */
        /*get("/user/:projectName", (req, res) -> {
         String projectName = req.params(":projectName");
         return userService.findByProjectName(projectName);
         }, json());*/
        /**
         * Update task state *
         */
        put("/user/update", (req, res) -> {
            User user = gson.fromJson(req.body(), User.class);
            userService.update(user);
            return user.getUserName();
        }, json());

        /**
         * Delete user by user name *
         */
        delete("/user/:userName", (req, res) -> {
            String userName = req.params(":userName");
            projectService.unlinkProjectsToUser(userName);
            taskService.unlinkTaskToUser(userName);
            userService.delete(userName);
            return userName;
        }, json());

        /**
         * Delete all users *
         */
        delete("/users/all", (req, res) -> {
            userService.deleteAll();
            return SUCCESS;
        }, json());

        /**
         * ---------------------------------------------------------
         *
         * Tasks routes
         *
         * ---------------------------------------------------------
         */
        /**
         * Add new task *
         */
        post("/task/add", (req, res) -> {
            Task task = gson.fromJson(req.body(), Task.class);
            taskService.add(task, task.getProject(), task.getCreatedBy());
            return task;
        }, json());

        /**
         * Get all tasks *
         */
        get("/tasks", (req, res)
                -> taskService.findAll(), json());

        /**
         * Get tasks by project Name *
         */
        get("/tasks/:projectName", (req, res) -> {
            String projectName = req.params(":projectName");
            return taskService.findByProjectName(projectName);
        }, json());

        /**
         * Get tasks by user name *
         */
        get("/tasks/:userName", (req, res) -> {
            String userName = req.params(":userName");
            return taskService.findByUserName(userName);
        }, json());

        /**
         * Get DONE tasks*
         */
        get("/tasks/done", (req, res)
                -> taskService.findbyState(EStates.DONE), json());

        /**
         * Get ONGOING tasks *
         */
        get("/tasks/ongoing", (req, res)
                -> taskService.findbyState(EStates.ONGOING), json());

        /**
         * Get TO-DO tasks *
         */
        get("/tasks/todo", (req, res)
                -> taskService.findbyState(EStates.TODO), json());

        /**
         * Update task state *
         */
        put("/task/:id/:state", (req, res) -> {
            logger.warn(req.params(":id"));
            logger.warn(req.params(":state"));
            Double id = Double.parseDouble(req.params(":id"));
            Task task = taskService.findById(id.longValue());
            EStates state = EStates.fromString(req.params(":state"));
            logger.warn(state.getValue());
            task.setState(state);
            taskService.update(task);
            return SUCCESS;
        }, json());

        /**
         * Assign task *
         */
        put("/task/assign", (req, res) -> {
            Task task = gson.fromJson(req.body(), Task.class);
            User user = userService.findByUserName(task.getAssignedTo().getUserName());
            task.setAssignedTo(user);
            taskService.merge(task);
            return SUCCESS;
        }, json());

        /**
         * Delete task by Task Name *
         */
        delete("/task/name/:taskName", (req, res) -> {
            String taskName = req.params(":taskName");
            taskService.delete(taskName);
            return taskName;
        }, json());

        /**
         * Delete task by Task Id*
         */
        delete("/task/:id", (req, res) -> {
            Double id = Double.parseDouble(req.params(":id"));
            taskService.delete(id.longValue());
            return id;
        }, json());

        /**
         * Delete all tasks*
         */
        delete("/tasks/all", (req, res) -> {
            taskService.deleteAll();
            return SUCCESS;
        }, json());

        /**
         * ---------------------------------------------------------
         *
         * Project routes
         *
         * ---------------------------------------------------------
         */
        /**
         * Add new project*
         */
        post("/project/add", (req, res) -> {
            Project project = gson.fromJson(req.body(), Project.class);
            projectService.add(project);
            return project;
        }, json());

        /**
         * Get all projects *
         */
        get("/projects", (req, res)
                -> projectService.findAll(), json());

        /**
         * Get projects by user name *
         */
        get("/projects/:userName", (req, res) -> {
            String userName = req.params(":userName");
            return projectService.findByProjectName(userName);
        }, json());

        /**
         * Delete project by project Name*
         */
        delete("/project/:projectName", (req, res) -> {
            String projectName = req.params(":projectName");
            taskService.deleteAllByProjectName(projectName);
            projectService.deleteByProjectName(projectName);
            return null;
        }, json());

        /**
         * Delete all projects*
         */
        delete("/projects/all", (req, res) -> {
            taskService.deleteAll();
            projectService.deleteAll();
            return null;
        }, json());
        /**
         * ---------------------------------------------------------
         *
         * Request/Response parameter + CORS conf
         *
         * ---------------------------------------------------------
         */
        after((req, res) -> {
            res.type("application/json");
        });

        exception(IllegalArgumentException.class, (e, req, res) -> {
            res.status(400);
            res.body(JsonUtil.toJson(new ErrorHandler(e)));
        });

        options("/*", (req, res) -> {

            String accessControlRequestHeaders = req.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                res.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }
            String accessControlRequestMethod = req.headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                res.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }
            return "OK";

        });

        before((req, res) -> {
            res.header("Access-Control-Allow-Origin", "*");
        });
    }
}
