package com.reit.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "todo")
public class ToDo {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "author")
    String author;

    @Column(name = "state")
    String state;

    @Column(name = "description")
    private String description;


    public ToDo() {
    }

    public ToDo(String id, String description, String author, String state) {
        this.id = id;
        this.description = description;
        this.author = author;
        this.state = state;
    }

    public ToDo(String description, String author, String state) {
        this.description = description;
        this.author = author;
        this.state = state;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
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


    @Override
    public String toString() {
        return "Todo : " + this.id + ", " + this.description + ", " + this.author + ", " + this.state;
    }

}
