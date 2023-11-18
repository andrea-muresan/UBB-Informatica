package taskRunner;

public class DelayTaskRunner extends AbstractTaskRunner {
    public DelayTaskRunner(TaskRunner taskRunner) {
        super(taskRunner);
    }

    @Override
    public void executeOneTask() {
        try {
            Thread.sleep(3000);
            super.executeOneTask();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
