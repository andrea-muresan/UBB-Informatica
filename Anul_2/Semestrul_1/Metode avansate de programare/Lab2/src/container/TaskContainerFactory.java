package container;

public class TaskContainerFactory implements Factory{
    private static final TaskContainerFactory instance = new TaskContainerFactory();

    private TaskContainerFactory() {
    }

    public static TaskContainerFactory getInstance() {
        return instance;
    }

    public Container createContainer(Strategy strategy) {
        return switch (strategy) {
            case FIFO -> new QueueContainer();
            case LIFO -> new StackContainer();
        };
    }
}
