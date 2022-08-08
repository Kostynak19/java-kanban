package tests;
import manager.InMemoryTaskManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager>{
    
    @BeforeEach
    void setUpInMemory() {
        taskManager = new InMemoryTaskManager();
        super.setUp();
        System.out.println();
    }
    @AfterEach
    void tearDown(){
        super.tearDown();
    }
}
