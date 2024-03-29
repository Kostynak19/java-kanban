package manager;

import model.Epic;
import model.SubTask;
import model.Task;

import java.util.List;
import java.util.Set;

public interface TaskManager {
    
    //    Получение списка всех задач.
    List<Task> findAllTasks ();
    
    //    Получение списка всех эпиков.
    List<Epic> findAllEpics ();
    
    // Получение списка всех подзадач определённого эпика.
    List<SubTask> findAllSubTasksOfEpic (Epic epic);
    
    // Получение подзадачи по идентификатору
    SubTask findSubTaskById (Integer id);
    
    // Получение задачи по идентификатору
    Task findTaskById (Integer id);
    
    // Получение эпика по идентификатору
    Epic findEpicById (Integer id);
    
    // Добавление задачи.
    Task createTask (Task task);
    
    // Добавление подзадачи.
    SubTask createSubTask (SubTask subTask, Epic epic);
    
    // Добавление Эпика.
    Epic createEpic (Epic epic);
    
    // Обновление задачи.
    Task updateTaskById (Task task);
    
    // Обновление подзадачи.
    SubTask updateSubTaskById (SubTask subTask);
    
    // Обновление эпика.
    Task updateEpicById (Epic epic);
    
    // Удаление всех задач.
    void deleteAllTask ();
    
    // Удаление всех подзадач.
    void deleteAllSubTasks ();
    
    // Удаление всех эпиков.
    void deleteAllEpics ();
    
    // Удаление подзадач по ID.
    void deleteSubTaskById (Integer id);
    
    // Удаление задачи по ID.
    Task deleteTaskById (Integer id);
    
    // Удаление задачи из истории по ИД.
    void removeFromHistoryById (int id);
    void removeAllHistory();
    
    // Получение истории.
    List<Task> getHistory ();
    
    // Добавление задачи в историю.
    void addInHistory (Task task);
    
    Set<Task> getPrioritizedTasks();
}
