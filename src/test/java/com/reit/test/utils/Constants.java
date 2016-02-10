package com.reit.test.utils;

import com.reit.utils.EStates;
import com.reit.model.Project;
import com.reit.model.Task;
import com.reit.model.User;

import java.util.ArrayList;
import java.util.List;

public class Constants {

    public static final String userName = "USER";
    public static final String desc = "This is a Task";
    public static final String state = EStates.TODO.getValue();
    public static final Long one = Long.decode("1");
    public static final Long two = Long.decode("2");
    public static final String projectName = "PROJECT";
    public static final String taskName = "TASK";

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
        task.setTaskName(taskName + one.toString());
        task.setProject(getProjectSample(two.toString()));
        task.setUser(getUserSample(two.toString()));
        task.setState(EStates.ONGOING.getValue());
        return task;
    }

    public static Task getTaskSample2() {
        Task task = new Task();
        task.setDescription(desc);
        task.setTaskName(taskName + two.toString());
        task.setProject(getProjectSample(two.toString()));
        task.setUser(getUserSample(two.toString()));
        task.setState(EStates.ONGOING.getValue());
        return task;
    }


    public static Task getTaskSample3() {
        Task task = new Task();
        task.setDescription(desc);
        task.setTaskName(taskName + two.toString());
        task.setProject(getProjectSample(two.toString()));
        task.setUser(getUserSample(two.toString()));
        task.setState(EStates.TODO.getValue());
        return task;
    }


    public static Task getTaskSample4() {
        Task task = new Task();
        task.setDescription(desc);
        task.setTaskName(taskName + two.toString());
        task.setProject(getProjectSample(one.toString()));
        task.setUser(getUserSample(two.toString()));
        task.setState(EStates.DONE.getValue());
        return task;
    }
}
