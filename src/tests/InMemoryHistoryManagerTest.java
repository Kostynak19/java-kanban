package tests;
import manager.InMemoryHistoryManager;
import org.junit.jupiter.api.BeforeEach;


class InMemoryHistoryManagerTest extends HistoryManagerTest<InMemoryHistoryManager> {
    @BeforeEach
    void setUp(){
        historyManager = new InMemoryHistoryManager();
    }
}