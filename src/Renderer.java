import java.util.List;
import java.util.function.Supplier;

public class Renderer {

    private final Supplier<Visitor> factory;

    public Renderer(Supplier<Visitor> factory) {
        this.factory = factory;
    }

    public void render(TaskReport report) {
        render(factory.get(), report, 0, true);
    }

    private void render(Visitor visitor, TaskReport report, int depth, boolean isEnd) {
        visitor.drawTask(report.getTask(), depth);
        List<TaskReport> subList = report.getList();
        int i = subList.size();
        for (TaskReport r : subList) {
            render(visitor, r, depth + 1, --i == 0);
        }
        visitor.end(depth, isEnd);
    }
}