package com.reit.model;

import org.hibernate.annotations.IndexColumn;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "REIT_TASK")
public class Task implements Serializable {

    public Task() {
    }

    @Id
    @Column(name = "N_TASK_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "C_TASK_STATE")
    private String state;

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

    public Task(Long id, User user, Project project, String description, String state) {
        this.id = id;
        this.user = user;
        this.project = project;
        this.description = description;
        this.state = state;
    }

    public Task(User user, Project project, String description, String state) {
        this.user = user;
        this.project = project;
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
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Task getTask() {
        Task task = new Task();
        task.user = user;
        task.description = description;
        task.state = state;
        return task;
    }

    @Override
    public String toString() {
        return "Todo : " + this.id + ", " + this.description + ", " + this.user + ", " + this.state;
    }

}
