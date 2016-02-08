package com.reit.controller;

import com.google.gson.Gson;
import com.reit.model.Project;
import com.reit.model.User;
import com.reit.service.TaskService;
import com.reit.utils.EStates;
import com.reit.error.ResponseError;

import com.reit.model.Task;
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

    public static final String userName = "User";
    public static final String desc = "This is a Task";
    public static final String state = EStates.STARTED.getValue();
    public static final Long one = Long.decode("1");
    public static final Long two = Long.decode("2");
    public static final String projectName = "Project";
    public static final String taskName = "Task";

    public static List<Task> getTaskList() {

        List<Task> taskList = new ArrayList<>();

        // init User 1
        User user1 = new User();
        user1.setUserId(one);
        user1.setUserName(userName + one.toString());

        // init User 2
        User user2 = new User();
        user2.setUserId(two);
        user2.setUserName(userName + two.toString());

        // init Project 1
        Project project1 = new Project();
        project1.setProjectId(one);
        project1.setProjectDescription(projectName + one.toString());

        // init Project 2
        Project project2 = new Project();
        project1.setProjectId(two);
        project1.setProjectDescription(projectName + two.toString());


        // init Task 1 -> Project 1 -> User 1
        Task task1 = new Task();
        task1.setId(one);
        task1.setDescription(taskName + one.toString());
        task1.setProjects(project1);
        task1.setState(EStates.STARTED.getValue());
        task1.setUsers(user1);


        // init Task 2 -> Project 1 -> User 1
        Task task2 = new Task();
        task2.setId(one);
        task2.setDescription(taskName + one.toString());
        task2.setProjects(project1);
        task2.setState(EStates.STARTED.getValue());
        task2.setUsers(user1);

        // init Task 3 -> Project 2 -> User 1
        Task task3 = new Task();
        task3.setId(one);
        task3.setDescription(taskName + one.toString());
        task3.setProjects(project2);
        task3.setState(EStates.STARTED.getValue());
        task3.setUsers(user1);

        // init Task 3 -> Project 2 -> User 2
        Task task4 = new Task();
        task4.setId(one);
        task4.setDescription(taskName + one.toString());
        task4.setProjects(project2);
        task4.setState(EStates.STARTED.getValue());
        task4.setUsers(user2);


        taskList.add(task1);
        taskList.add(task2);
        taskList.add(task3);
        taskList.add(task4);
        return taskList;
    }

    public MainController(final TaskService taskService) {

        // Set static file location {/resources/public}
        staticFileLocation("/public");

        // Health check
        get("/ping", (req, res) -> {
            return "pong";
        });

        // Add new task
        post("/add", (req, res) -> {
            Task task = gson.fromJson(req.body(), Task.class);
            taskService.add(task);
            return task;
        }, json());


        // Get all tasks
        get("/todos", (req, res) ->
                taskService.findAll()
                , json());


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
        get("/done", (req, res) ->
                taskService.findbyState(EStates.DONE.getValue())
                , json());

        // Get task by User
        get("/todo/:user", (req, res) ->
                taskService.findbyUser(req.params(":user"))
                , json());


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
