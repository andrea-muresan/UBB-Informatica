package container;

import domain.Task;

public class QueueContainer extends BigContainer{
    public QueueContainer() {
        super();
    }

    @Override
    public Task remove() {
        if (!tasks.isEmpty()) {
            return tasks.remove(0);
        } else {
            return null;
        }
    }

}
