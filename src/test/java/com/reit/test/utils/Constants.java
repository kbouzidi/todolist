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

    public static User getUserSample() {
        User user = new User();
        user.setUserName(userName + one.toString());
        return user;
    }

    public static Project getProjectSample() {
        Project project = new Project();
        project.setProjectName(projectName + one.toString());
        return project;
    }

    public static Task getTaskSample() {
        Task task = new Task();
        task.setDescription(taskName + one.toString());
        task.setProject(getProjectSample());
        task.setUser(getUserSample());
        task.setState("STARTED");
        
        return task;
    }

    public static List<Task> getTaskList(List<Task> taskList) {

        if (taskList == null) {
            taskList = new ArrayList<>();
        }
        // init User 1

        // user1.setUserId(one);
        // init User 2
        User user2 = new User();
        user2.setUserId(two);
        user2.setUserName(userName + two.toString());

        // init Project 1
        Project project1 = new Project();
        project1.setProjectDescription(projectName + one.toString());
        project1.setProjectName(projectName + one.toString());

        // init Project 2
        Project project2 = new Project();
        project1.setProjectId(two);
        project1.setProjectDescription(projectName + two.toString());

        // init Task 1 -> Project 1 -> User 1
        Task task1 = new Task();
        // task1.setId(one);
        task1.setDescription(taskName + one.toString());
        task1.setProject(project1);
        task1.setState(EStates.STARTED.getValue());
        //task1.setUser(user1);

        taskList.add(task1);
        //taskList.add(task2);
        // taskList.add(task3);
        // taskList.add(task4);
        return taskList;
    }
}
