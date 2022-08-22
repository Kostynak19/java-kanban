package manager;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.time.LocalDateTime;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sun.net.httpserver.HttpServer;
import model.Task;
import enums.Status;
import model.Epic;




public class HttpTaskServer {
    
    public static int PORT = 8080;
    static String apiToken;
    private static HttpServer server;
    
    HttpTasksManager manager = Managers.getManagers();
    
    public HttpTaskServer (HttpServer myServer, int myPORT, String myAPIToken){
        server = myServer;
        PORT = myPORT;
        apiToken = myAPIToken;
    }
    public  Gson getGson () {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter());
        return gsonBuilder.create();
    }
    
//    public static void main (String[] args) throws IOException {
//
//        apiToken = generateApiToken();
//        server = HttpServer.create(new InetSocketAddress("localhost", PORT), 0);
//        final Gson gson = getGson();
//        Task task = new Task("Тест", "Описание", Status.NEW.ordinal());
//        task.setId(1);
//        task.setStartTime(LocalDateTime.now());
//        final HashMap<Integer, Task> map = new HashMap<>();
//        map.put(task.getId(), task);
//
//        Epic epic = new Epic("Эпик", "Описание epic");
//        epic.setId(2);
//        epic.setStartTime(LocalDateTime.now());
//        map.put(epic.getId(), epic);
//
//        System.out.println(gson.toJson(task));
//        System.out.println();
//        final String json = gson.toJson(map);
//        System.out.println(json);
//        final HashMap<Integer, Task> mapRestored = gson.fromJson(json,
//         new TypeToken<HashMap<Integer, Task>>() {
//         }.getType());
//        System.out.println("Restored:");
//        System.out.println(mapRestored);
//        System.out.println(mapRestored.get(1));
//
//        System.out.println("Было:");
//        System.out.println(map.get(1));
//        System.out.println("Стало:");
//        System.out.println(mapRestored.get(2));
//
//
//    }
//
    public void start () {
        System.out.println("Запускаем сервер на порту " + PORT);
        System.out.println("Открой в браузере http://localhost:" + PORT + "/");
        System.out.println("API_TOKEN: " + apiToken);
        server.start();
    }
    public  void stop () {
        server.stop(0);
    }
    private  String generateApiToken () {
        return "" + System.currentTimeMillis();
    }
    
}