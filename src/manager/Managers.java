package manager;

public class Managers {
    public static TaskManager getDefault() {
        return new InMemoryTasksTaskManager();
    }
    public static HistoryManager getDefaultHistory(){
        return new InMemoryHistoryManager();
    }
}
