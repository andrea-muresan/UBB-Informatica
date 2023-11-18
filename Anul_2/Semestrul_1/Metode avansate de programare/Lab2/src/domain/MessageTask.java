package domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MessageTask extends Task{
    private String message;
    private String from;
    private String to;
    private LocalDateTime date;

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    public MessageTask(String id, String description, String message, String from, String to, LocalDateTime date) {
        super(id, description);
        this.message = message;
        this.from = from;
        this.to = to;
        this.date = date;
    }

    @Override
    public void execute() {
        System.out.println(date.format(DATE_FORMAT) + " : " + message);
    }

    @Override
    public String toString() {
        return "SortingTask{" +
                "id='" + getId() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", mesage='" + message + '\'' +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", date=" + date +
                '}';
    }

}
