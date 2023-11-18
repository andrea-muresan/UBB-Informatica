package taskRunner;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PrinterTaskRunner extends AbstractTaskRunner{
    public PrinterTaskRunner(TaskRunner taskRunner) {
        super(taskRunner);
    }

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Override
    public void executeOneTask() {
        super.executeOneTask();
        System.out.println(LocalDateTime.now().format(formatter));
    }
}
