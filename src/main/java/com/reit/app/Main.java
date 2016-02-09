package com.reit.app;

import com.reit.controller.MainController;
import com.reit.service.ProjectService;
import com.reit.service.TaskService;
import com.reit.service.UserService;
import spark.servlet.SparkApplication;

public class Main implements SparkApplication {

    @Override
    public void init() {
        new MainController(new TaskService(), new ProjectService(), new UserService());
    }
}
