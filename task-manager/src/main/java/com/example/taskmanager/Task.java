package com.example.taskmanager;

public class Task {
    private int id;
    private String title;
    private String description;
    private String priority;
    private boolean completed;

    public Task(int id, String title, String description, String priority, boolean completed) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.completed = completed;
    }

    public Task(String title, String description, String priority) {
        this(0, title, description, priority, false);
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getPriority() { return priority; }
    public boolean isCompleted() { return completed; }

    public void setCompleted(boolean completed) { this.completed = completed; }

    @Override
    public String toString() {
        return String.format("[%d] %s (%s) %s", id, title, priority, completed ? "✅" : "❌");
    }
}
