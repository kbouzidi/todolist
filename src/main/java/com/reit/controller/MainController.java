package com.reit.controller;

import com.reit.service.TodoService;

import static spark.Spark.get;
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
    }
}
