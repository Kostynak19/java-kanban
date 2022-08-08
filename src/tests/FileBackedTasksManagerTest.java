package tests;
import manager.FileBackedTasksManager;
import model.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class FileBackedTasksManagerTest extends TaskManagerTest<FileBackedTasksManager>{
    
    @BeforeEach
    void setUpFileBacked() {
        taskManager = new FileBackedTasksManager();
        super.setUp();
    }
    
    @AfterEach
    void tearDown(){
        super.tearDown();
    }
    
    @Test
    void saveToFileAndLoadFromFileTest() {
        File tasksStorage = new File("save.csv");
        Task taskT = taskManager.findTaskById(1);
        taskManager.save();
        FileBackedTasksManager.loadFromFile(tasksStorage);
        assertEquals(taskT, taskManager.findTaskById(1), "Задачи не идентичны");
    }
}