package manager;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class KVClient {
    HttpClient client;
    URL url;
    String token; // KVServer register
    
    
    
    public KVClient (HttpClient client, URL url, String token) {
        this.client = client;
        this.url = url;
        this.token = token;
    }

    
    String load(String key) { // tasks, epics, subtasks , history
        URI uri = URI.create(url + "/load/" + key + "?API TOKEN=DEBUG");
    
        HttpRequest request = HttpRequest.newBuilder()
         .uri(uri)
         .GET()
         .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return key;
    }
    
    void put(String key, String json) {
        URI uri = URI.create(url + "/save/" + key + "?API TOKEN=DEBUG");
        
        HttpRequest request = HttpRequest.newBuilder()
         .uri(uri)
         .POST(HttpRequest.BodyPublishers.ofString(json))
         .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}