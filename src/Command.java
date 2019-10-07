public interface Command {
    void execute(CompositeTask task);
    void undo(CompositeTask task); // CompositeTask 에게 행위를 위임함...
}
