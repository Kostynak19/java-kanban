package tests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import manager.HttpTaskServer;
import model.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


class HttpTaskServerTest {
    
    @BeforeEach
    void init () throws IOException {
        // HttpTaskServer.start();
    }

    @AfterEach
    void stop () {
    //    HttpTaskServer.stop();
    }
    @Test
    void getTasks () throws IOException, InterruptedException {
        final Gson gson = getGson();
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/task");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        
        final List<Task> tasks = gson.fromJson(response.body(), new TypeToken<ArrayList<Task>>() {
        }.getType());
        
        assertNotNull(tasks, "Задачи на возвращаются");
        assertEquals(1, tasks.size(), "Не верное количество задач");
        assertEquals(tasks, tasks.get(0), "Задачи не совпадают");
    }
    
}