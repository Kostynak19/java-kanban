package tests;

import enums.Status;
import manager.HistoryManager;
import model.Task;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

abstract class HistoryManagerTest<T extends HistoryManager> {
    protected T historyManager;
    
    @Test
    void add() {
        List<Task> tasksHist = historyManager.getHistory();
        historyManager.add(new Task("TaskName2", "TaskDescript", 1,
         Status.NEW, LocalDateTime.now(), 10));
        List<Task> tasksHist1 = historyManager.getHistory();
        assertTrue(tasksHist.size() != tasksHist1.size(), "История не изменилась");
    }
    
    @Test
    void remove() {
        historyManager.add(new Task("TaskName1", "TaskDescript", 1,
         Status.NEW, LocalDateTime.now(), 10));
        historyManager.add(new Task("TaskName2", "TaskDescript", 2,
         Status.NEW, LocalDateTime.now(), 10));
        historyManager.remove(1);
        List<Task> tasksHist1 = historyManager.getHistory();
        assertEquals(1, tasksHist1.size(), "История не изменилась");
    }
    
    @Test
    void removeAll() {
        historyManager.add(new Task("TaskName1", "TaskDescript", 1,
         Status.NEW, LocalDateTime.now(), 10));
        historyManager.removeAll();
        List<Task> tasksHist1 = historyManager.getHistory();
        assertEquals(0, tasksHist1.size(), "История не пустая");
    }
    
    @Test
    void getHistory() {
        List<Task> tasksHist = historyManager.getHistory();
        historyManager.add(new Task("TaskName2", "TaskDescript", 1,
         Status.NEW, LocalDateTime.now(), 10));
        List<Task> tasksHist1 = historyManager.getHistory();
        assertTrue(tasksHist.size() != tasksHist1.size(), "История не изменилась");
        assertNotNull(tasksHist1,"История не получена");
    }
}