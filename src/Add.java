import java.time.LocalDateTime;

public class Add implements Command {
    private final String title;
    private final LocalDateTime date;

    private CompositeTask oldTask;

    public Add(String title, LocalDateTime date) {
        this.title = title;
        this.date = date;
    }

    @Override
    public void execute(CompositeTask task) {
        oldTask = task;
        task.addTask(title, date); // 대칭성을 가진 인터페이스여야 언두가 가능함.....
    }

    @Override
    public void undo(CompositeTask task) {
        task.removeTask(oldTask);
    }
}
