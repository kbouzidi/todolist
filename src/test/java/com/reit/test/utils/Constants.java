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
package com.reit.test.utils;

import com.reit.utils.EStates;
import com.reit.model.Project;
import com.reit.model.Task;
import com.reit.model.User;

public class Constants {

    public static final String userName = "USER";
    public static final String desc = "This is a Task";
    public static final String state = EStates.TODO.getValue();
    public static final Long one = Long.decode("1");
    public static final Long two = Long.decode("2");
    public static final String projectName = "PROJECT";
    public static final String taskName = "TASK";

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
        project.setUser(getUserSample(null));
        return project;
    }

    public static Task getTaskSample() {
        Task task = new Task();
        task.setDescription(desc);
        task.setTaskName(taskName + one.toString());
        task.setProject(getProjectSample(two.toString()));
        task.setUser(getUserSample(two.toString()));
        task.setState(EStates.ONGOING);
        return task;
    }

    public static Task getTaskSample2() {
        Task task = new Task();
        task.setDescription(desc);
        task.setTaskName(taskName + two.toString());
        task.setProject(getProjectSample(two.toString()));
        task.setUser(getUserSample(two.toString()));
        task.setState(EStates.ONGOING);
        return task;
    }

    public static Task getTaskSample3() {
        Task task = new Task();
        task.setDescription(desc);
        task.setTaskName(taskName + two.toString());
        task.setProject(getProjectSample(two.toString()));
        task.setUser(getUserSample(two.toString()));
        task.setState(EStates.TODO);
        return task;
    }

    public static Task getTaskSample4() {
        Task task = new Task();
        task.setDescription(desc);
        task.setTaskName(taskName + two.toString());
        task.setProject(getProjectSample(one.toString()));
        task.setUser(getUserSample(two.toString()));
        task.setState(EStates.DONE);
        return task;
    }
}
