import java.time.LocalDateTime;

public class Remove implements Command {

    private CompositeTask baseTask;
    private CompositeTask oldTask;
    private String oldTitle;
    private LocalDateTime oldDate;

    public Remove(CompositeTask task) {
        this.baseTask = task;
    }

    @Override
    public void execute(CompositeTask task) {
        oldTitle =task.getTitle();
        oldDate = task.getDate();
        task.removeTask(baseTask);
    }

    @Override
    public void undo(CompositeTask task) {
        task.addTask(oldTitle, oldDate); // 커맨드 인스턴스 메모리가 각자 자기의 old 를 기억하고 있음...\
        // 실행할 때마다의 값도 다 기억함.. 상태가 있다는 이점을 활용함.
    }



    // Promise 도 then 치는 시점에 flush 할 수 있도록 상태를 갖고 있는 커맨드 객체의 예이다.


}
