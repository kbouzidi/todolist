package com.reit.test.utils;

import com.reit.utils.EStates;
import com.reit.model.Project;
import com.reit.model.Task;
import com.reit.model.User;

import java.util.ArrayList;
import java.util.List;

public class Constants {

    public static final String userName = "User";
    public static final String desc = "This is a Task";
    public static final String state = EStates.STARTED.getValue();
    public static final Long one = Long.decode("1");
    public static final Long two = Long.decode("2");
    public static final String projectName = "Project";
    public static final String taskName = "Task";

    private User user1 = new User();

    public static User getUserSample(String suffix) {
        if (suffix == null) {
            suffix = one.toString();
        }
        User user = new User();
        user.setUserName(userName + suffix);
        return user;
    }

    public static Project getProjectSample(String suffix) {
        if (suffix == null) {
            suffix = one.toString();
        }
        Project project = new Project();
        project.setProjectName(projectName + suffix);
        return project;
    }

    public static Task getTaskSample() {
        Task task = new Task();
        task.setDescription(desc);
        task.setName(taskName + one.toString());
        task.setProject(getProjectSample(two.toString()));
        task.setUser(getUserSample(two.toString()));
        task.setState("STARTED");
        return task;
    }

}
