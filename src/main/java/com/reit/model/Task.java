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
package com.reit.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "REIT_TASK")
public class Task implements Serializable {

    public Task() {
    }

    @Id
    @Column(name = "N_TASK_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long taskId;

    @Column(name = "C_TASK_STATE")
    private String state;

    @Column(name = "C_TASK_NAME")
    private String taskName;

    @Column(name = "C_TASK_DESCRIPTION")
    private String description;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "N_USER_ID")
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "N_PROJECT_ID")
    private Project project;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Task(User user, Project project, String taskName, String description, String state) {
        this.user = user;
        this.project = project;
        this.taskName = taskName;
        this.description = description;
        this.state = state;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Long getId() {
        return taskId;
    }

    public void setId(Long id) {
        this.taskId = id;
    }

    public Task getTask() {
        Task task = new Task();
        task.user = user;
        task.description = description;
        task.taskName = taskName;
        task.state = state;
        return task;
    }

    @Override
    public String toString() {
        return "Todo : " + this.taskId + ", " + this.taskName + ", " + this.description + ", " + this.user + ", " + this.state;
    }

    public void setTaskName(String name) {
        this.taskName = name;
    }

    public String getTaskName() {
        return taskName;
    }

}
