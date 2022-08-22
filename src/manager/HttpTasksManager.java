package manager;

import com.sun.net.httpserver.HttpServer;
public class HttpTasksManager extends FileBackedTasksManager {
    int port;
    public HttpTasksManager(int port) {
        this.port = port; // PORT KVServer
    }

}