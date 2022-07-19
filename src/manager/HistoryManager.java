package manager;

import model.Task;

import java.util.HashMap;
import java.util.List;

public interface HistoryManager {
    
    // Добавление задачи в историю.
    void add (Task task);
    
    // Удаление задачи из истории.
    void remove (int id);
    
    
    // Получение истории.
    List<Task> getHistory ();
    
}

