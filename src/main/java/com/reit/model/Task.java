package com.reit.model;

import org.hibernate.annotations.IndexColumn;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "task")
public class Task implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "state")
    private String state;

    @Column(name = "description")
    private String description;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne // many task have one project
    @JoinColumn(name = "project_id")
    private Project project;


    public Task() {
    }

    public Task(Long id, User user, String description, String state) {
        this.id = id;
        this.user = user;
        this.description = description;
        this.state = state;
    }

    public Task(User user, String description, String state) {
        this.user = user;
        this.description = description;
        this.state = state;
    }

    public Project getProject() {
        return project;
    }

    public void setProjects(Project project) {
        this.project = project;
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

    public User getUsers() {
        return user;
    }

    public void setUsers(User user) {
        this.user = user;
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
