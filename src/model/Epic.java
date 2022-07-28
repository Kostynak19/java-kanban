package model;

import enums.Status;

import java.util.ArrayList;

// Класс Epic описывает сущность задачи типа "эпик"
public class Epic extends Task {
    final ArrayList<SubTask> subTasks = new ArrayList<>();
    
    public Epic(String type, String title, String description, Integer id, Status status) {
        super(type, title, description, id, status);
    }
    
    public Epic(String name, String description, Integer id) {
        super(name, description, id);
    }
    
    public ArrayList<SubTask> getSubTasks() {
        return subTasks;
    }
    
    public void setSubTasks(ArrayList<SubTask> subTasks) {
         subTasks = new ArrayList<SubTask>();
    }
}