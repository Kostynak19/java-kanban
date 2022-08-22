package manager;

public class Managers {
    
    public static HttpTasksManager getManagers () {
        return new HttpTasksManager(8078);
    }
//    public static TaskManager getDefault () {
//        return new InMemoryTaskManager();
//    }
    public static HistoryManager getDefaultHistory () {
        return new InMemoryHistoryManager();
    }
}