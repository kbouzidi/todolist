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

import java.util.List;

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
     * @param taskService    {@link TaskService}
     * @param projectService {@link ProjectService}
     * @param userService    {@link UserService}
     * @param port           {@link Integer}
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
            Long id = userService.add(user);
            user.setUserId(id);
            return user;
        }, json());

        /**
         * Find all users*
         */
        get("/users", (req, res)
                -> userService.findAll(), json());

        /**
         * Delete user by user name *
         */
        delete("/user/:id", (req, res) -> {
            Double userId = Double.parseDouble(req.params(":id"));
            projectService.unlinkProjectsToUser(userId.longValue());
            List<Task> taskList = taskService.findByUserId(userId.longValue());
            taskService.unlinkTaskToUser(taskList);
            userService.delete(userId.longValue());
            return SUCCESS;
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
            task = taskService.add(task, task.getProject(), task.getCreatedBy());
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
        get("/tasks/name/:projectName", (req, res) -> {
            String projectName = req.params(":projectName");
            return taskService.findByProjectName(projectName);
        }, json());

        /**
         * Get tasks by project Id *
         */
        get("/tasks/:id", (req, res) -> {
            Double projectId = Double.parseDouble(req.params(":id"));
            return taskService.findByProjectId(projectId.longValue());
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
        get("/done", (req, res)
                -> taskService.findbyState(EStates.DONE), json());

        /**
         * Get ONGOING tasks *
         */
        get("/ongoing", (req, res)
                -> taskService.findbyState(EStates.ONGOING), json());

        /**
         * Get TO-DO tasks *
         */
        get("/todo", (req, res)
                -> taskService.findbyState(EStates.TODO), json());

        /**
         * Update task state *
         */
        put("/task/:id/:state", (req, res) -> {
            Double id = Double.parseDouble(req.params(":id"));
            Task task = taskService.findById(id.longValue());
            EStates state = EStates.fromString(req.params(":state"));
            task.setState(state);
            taskService.update(task);
            return SUCCESS;
        }, json());

        /**
         * Assign task *
         */
        put("/task/assign/:taskId/:userId", (req, res) -> {
            Double taskId = Double.parseDouble(req.params(":taskId"));
            Double userId = Double.parseDouble(req.params(":userId"));
            Task task = taskService.findById(taskId.longValue());
            User user = userService.findById(userId.longValue());
            task.setAssignedTo(user.getUserId());
            task.setAssignedToName(user.getUserName());
            taskService.update(task);
            return user.getUserName();
        }, json());

        /**
         * Delete task by Task Id*
         */
        delete("/task/:id", (req, res) -> {
            Double id = Double.parseDouble(req.params(":id"));
            Task task = taskService.findById(id.longValue());
            taskService.delete(task);
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
            Long projectId = projectService.add(project);
            project.setProjectId(projectId);
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
        get("/projects/:userId", (req, res) -> {
            Double userId = Double.parseDouble(req.params(":userId"));
            return projectService.findByUserId(userId.longValue());
        }, json());

        /**
         * Delete project by ID*
         */
        delete("/project/:id", (req, res) -> {
            Double projectId = Double.parseDouble(req.params(":id"));
            // Project project = projectService.findById(projectId.longValue());
            List<Task> taskList = taskService.findByProjectId(projectId.longValue());
            taskService.deleteAllTasks(taskList);
            projectService.delete(projectId.longValue());
            return SUCCESS;
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
