import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandTask {
    private final CompositeTask task; // 만드느 순간에 확정으로 보고 있다.
    private List<Command> commands = new ArrayList<>();
    private int cursor = 0;
    private final Map<String, String> saved = new HashMap<>();

    public void save(String key) {
        JsonVisitor visitor = new JsonVisitor();
        Renderer renderer1 = new Renderer(()->visitor);
        renderer1.render(task.getReport(CompositeSortType.TITLE_ASC));
        saved.put(key, visitor.getJson());

        // 디자인 패턴 학습
        //  전략 패턴 이 이해가 되면
        //  컴포짓 패턴 이 이해가 되면
        //  커맨드 패턴 (비지터 패턴 등) 이 애하가 되면...!!
    }

    public void load(String key) {
        String json = saved.get(key);
        // 새로 가져왔으니 다 날리고 새로 파싱...
        // 컴포짓으로 순회하면서...

        // subtask 삭제
        // json 순회하면서 복원
        // TODO 이걸 짤 수 있어야 중급이다.....


        // 오브젝트 책의 잘 신천하는 모습은 디자인 패턴을 사용하는 것이다.
        // 더 좋은 패턴을 쓰는 사람도 있지만
    }

    public void redo() {
        // undo 에서 날려버렸기 때문에..... redo 를 할 수 없네......
        // 고로 undo 에서 날라지 말고.... 커서만 이동시킨다....
        // 리두를 핟 ㅏ언두를 하다 어떤 행동을 하면 그 시점에 돌아왔던 미래는 날라간다....

        if (cursor == commands.size() - 1) return;
        commands.get(++cursor).execute(task); // 커서가 뭘까.....
        //현재 커서의 cmd 는 이미 실행이 끝났다....
        // 고로 현재 커서 다음 커맨드를 실행해야 하므로 cursor 를 먼저 올려줘야 함.
    }
    public void undo() {
        if (cursor < 0) return;
        //commands.remove(commands.size() - 1).undo(task); // 리두를 고려하지 않은 undo ....
        // 느껴줬으면 하는 점!@@@
        // 코드가 굉장히 짧아진다.....
        // 디자인패턴이라는 것 자체가 각각의 문제에 대한 가장 짧은 솔룻션.

        //Command cmd = commands.get(cursor--);
        //cmd.undo(task);
        commands.get(cursor--).undo(task);  //코드 트랜잭션 때문에 한 줄로 묶음. 커서 관리가 매우 중요하므로....
    }


    // 위임 메소드를 만드는게 귀찮아서 위임을 잘 안하게 됨...
    public CommandTask(String title, LocalDateTime date) {
        task = new CompositeTask(title, date); // 불변으로 만듦으로서 커맨드 객체를 만드는 부하가 많이 줄어든다.
//        setTitle(title); //컴포짓
//        setDate(date); //컴포짓
    }

    private void addCommand(Command cmd) {
//        if (commands.size() > cursor + 1) {
//            commands.subList(cursor + 1, commands.size()).clear();
//        }
        for (int i = commands.size() - 1; i > cursor; i--) {
            commands.remove(i);
        }
        cmd.execute(task);
        commands.add(cmd);
        cursor = commands.size() - 1; // 불변식으로 if 가 발생하지 않도록 함. cursor++ 하지 말고!! ㅋ
        // 커서가 addCommand  할 때 갱신되어야 함.
    }

    // 서브리스트 로 하면 좋지만 모르니 패스하겠다.... !!!!!!!!!!!!!!!!!!!!!!!!! 서브리스트



    public void toggle() {
        addCommand(new Toggle()); // 2..... toggle 이란 행위가 객체로 빠져 나옴
        // 여기 나와 있는 모든 행위.... 가 전부커맨드 패턴으로 빠질 것임.
//        Command toggle = new Toggle(); // 1.... 최초의 커맨드 객체가 태어남.
//        cmd.execute(task);
//        isComplete = !isComplete;
    }

    public void setTitle(String title) {
        addCommand(new Title(title)); // 커맨드 패턴은 객체가 생성되는 시점에 컨택스트를 커맨드 객체가 기억하도록 한다.
        // 인자를 함수가 소모하고 스택에서 제거하는게 아니라 Title 이 기억해서 재현하도록 한다.
    }

    public void setDate(LocalDateTime date) {
        addCommand(new Date(date));
        // this.date = date;
    }

    public TaskReport getReport(CompositeSortType type) {
        return task.getReport(type);
    }

    public void addTask(String title, LocalDateTime date) {

        addCommand(new Add(title, date));
        //CompositeTask task = new CompositeTask(title, date);
        //list.add(new CompositeTask(title, date));
    }

    public void removeTask(CompositeTask task) {

        addCommand(new Remove(task));
    }

    // 컴포짓 패턴의 객체를 커맨드로...

    // 커맨드는 언두, 리두가 될 뿐이지 세이브 로드가 되지 않음........
    // 어떻게 했길래 모~든 행위가 다 기억되어 있을까.....
    //

    // 영속성.... 을 얻기 위해서..
    // 비지터 (제어 역전을 위한),,,,,
    // 행위 역전 (커맨드)

    // 프레임 워크의 마지막... .이걸 저장해야 해ㅣ......
    // 메멘토 패턴.... 현재 상태를 시리얼라이제이션 할 수 있도록 추상화된 객체....
    // 복원 은 과제???ㄴ

}
