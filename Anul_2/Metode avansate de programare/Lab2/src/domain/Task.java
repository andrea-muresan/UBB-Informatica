package domain;

import java.util.Objects;

public abstract class Task {
    private String Id;
    private String description;

    public Task(String taskId, String descriere) {
        this.Id = taskId;
        this.description = descriere;
    }

    protected Task() {
    }

    public abstract void execute();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(Id, task.Id) && Objects.equals(description, task.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id, description);
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskId='" + Id + '\'' +
                ", descriere='" + description + '\'' +
                '}';
    }

    // Setters and Getters
    public String getId() {
        return Id;
    }

    public void setId(String id) {
        this.Id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
