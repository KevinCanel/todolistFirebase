package com.kcanel.tareas.models;

import java.io.Serializable;

public class TaskModel implements Serializable {

    private String name;
    private String description;
    private String date;
    private String done;

    public TaskModel() {
    }

    public TaskModel(String name, String description, String date) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.done = "0";
    }

    public TaskModel(String name, String description, String date, String done) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.done = done;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDone() {
        return done;
    }

    public void setDone(String done) {
        this.done = done;
    }
}
