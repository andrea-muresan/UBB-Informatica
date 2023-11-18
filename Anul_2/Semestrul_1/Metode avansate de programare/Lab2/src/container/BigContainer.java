package container;

import domain.Task;

import java.util.ArrayList;
import java.util.List;

public abstract class BigContainer implements Container {

    List<Task> tasks;

    public BigContainer() {
        this.tasks = new ArrayList<>();;
    }

    @Override
    public abstract Task remove();

    @Override
    public void add(Task task) {
        tasks.add(task);
    }

    @Override
    public int size() {
        return tasks.size();
    }

    @Override
    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    public List<Task> getTasks() {
        return tasks;
    }
}
