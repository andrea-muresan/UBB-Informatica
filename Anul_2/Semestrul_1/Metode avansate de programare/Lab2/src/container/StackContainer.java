package container;

import domain.Task;

public class StackContainer extends BigContainer{

    public StackContainer() {
        super();
    }

    @Override
    public Task remove() {
        if (!tasks.isEmpty()) {
            return tasks.remove(tasks.size() - 1);
        } else {
            return null;
        }
    }
}
