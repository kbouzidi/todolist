package com.reit.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "todo")
public class Todo implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "author")
    String author;

    @Column(name = "state")
    String state;

    @Column(name = "description")
    private String description;

    public Todo() {
    }

    public Todo(Long id, String author, String description, String state) {
        this.id = id;
        this.author = author;
        this.description = description;
        this.state = state;
    }

    public Todo(String author, String description, String state) {
        this.author = author;
        this.description = description;
        this.state = state;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }


    public Todo getTodo() {
        Todo todo = new Todo();
        todo.author = author;
        todo.description = description;
        todo.state = state;
        return todo;
    }

    @Override
    public String toString() {
        return "Todo : " + this.id + ", " + this.description + ", " + this.author + ", " + this.state;
    }

}
