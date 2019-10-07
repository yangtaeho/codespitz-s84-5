import java.time.LocalDateTime;

public class Date implements Command {
    private final LocalDateTime date; // 생성당시 받아들이는 모든 인자는 final 이다. 입력 받을 당시를 기억해야 하므로.
    private LocalDateTime oldDate;
    public Date(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public void execute(CompositeTask task) {
        oldDate = task.getDate();
        task.setDate(date);
    }

    @Override
    public void undo(CompositeTask task) {
        task.setDate(oldDate); // 복원은 본인 소관이기 때문에 내부 인자를 이용함.
    }
}
