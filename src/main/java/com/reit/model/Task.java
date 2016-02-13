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

import com.reit.utils.EStates;

import javax.persistence.*;
import java.io.Serializable;

/**
 * <h3 id="target"><a name="user-content-target" href="#target" class="headeranchor-link" aria-hidden="true"><span
 * class="headeranchor"></span></a>Task Model</h3>
 */
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
    @Enumerated(EnumType.STRING)
    private EStates state;

    @Column(name = "C_TASK_NAME")
    private String taskName;

    @Column(name = "C_TASK_DESCRIPTION")
    private String description;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "N_CREATED_BY", nullable = true)
    private User createdBy;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "N_ASSIGNED_TO", nullable = true)
    private User assignedTo;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "N_PROJECT_ID", nullable = true)
    private Project project;


    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Task(User createdBy, Project project, String taskName, String description, EStates state, User assignedTo) {
        this.createdBy = createdBy;
        this.project = project;
        this.taskName = taskName;
        this.description = description;
        this.state = state;
        this.assignedTo = assignedTo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Task getTask() {
        Task task = new Task();
        task.createdBy = createdBy;
        task.description = description;
        task.taskName = taskName;
        task.state = state;
        task.assignedTo = assignedTo;
        return task;
    }

    public User getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(User assignedTo) {
        this.assignedTo = assignedTo;
    }

    @Override
    public String toString() {
        return "Todo : " + this.taskId + ", " + this.taskName + ", " + this.description + ", " + this.createdBy + ", " + this.state;
    }

    public void setTaskName(String name) {
        this.taskName = name;
    }

    public String getTaskName() {
        return taskName;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public EStates getState() {
        return state;
    }

    public void setState(EStates state) {
        this.state = state;
    }

}
