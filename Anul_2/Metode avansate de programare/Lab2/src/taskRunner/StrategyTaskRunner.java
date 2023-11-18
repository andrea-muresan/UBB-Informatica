package taskRunner;

import container.Container;
import container.Strategy;
import domain.Task;
import container.TaskContainerFactory;

public class StrategyTaskRunner implements TaskRunner {
    private Container container;

    public StrategyTaskRunner(Strategy strategy) {
        TaskContainerFactory taskContainerFactory = TaskContainerFactory.getInstance();
        container = taskContainerFactory.createContainer(strategy);
    }

    @Override
    public void executeOneTask() {
        container.remove().execute();
    }

    @Override
    public void executeAll() {
        while (!container.isEmpty()) {
            container.remove().execute();
        }
    }

    @Override
    public void addTask(Task t) {
        container.add(t);
    }

    @Override
    public boolean hasTask() {
        return !container.isEmpty();
    }
}
