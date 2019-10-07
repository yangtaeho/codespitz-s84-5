public class Title implements Command {
    private final String title; // 생성당시 받아들이는 모든 인자는 final 이다. 입력 받을 당시를 기억해야 하므로.
    private String oldTitle;
    public Title(String title) {
        this.title = title;
    }

    @Override
    public void execute(CompositeTask task) {
        oldTitle = task.getTitle();
        task.setTitle(title);
    }

    @Override
    public void undo(CompositeTask task) {
        task.setTitle(oldTitle); // 복원은 본인 소관이기 때문에 내부 인자를 이용함.
    }
}
