package model;

import enums.Status;
import enums.TaskTypes;

import java.time.LocalDateTime;
import java.util.ArrayList;

// Класс Epic описывает сущность задачи типа "эпик"
public class Epic extends Task {
  
    private final String type = TaskTypes.EPIC.toString();
    ArrayList<SubTask> subTasks = new ArrayList<>();
    
    
    public Epic(String title, String description, Integer id, Status status) {
        super(title, description, id, status);
    }
    
    public Epic(String name, String description, Integer id) {
        super(name, description, id);
    }
    
    public ArrayList<SubTask> getSubTasks() {
        return subTasks;
    }
    
    public void setSubTasks(ArrayList<SubTask> subTasks) {
        this.subTasks = subTasks;
    }
    public String getType () {
        return type;
    }
    
    public LocalDateTime getEndTime() {
        for (SubTask subTask:subTasks) {
            endTime.plusMinutes(subTask.getDuration());
        }
        return endTime;
    }
    
    @Override
    public String toString() {
        return type +
         ", " + name +
         ", " + description +
         ", " + id +
         ", " + status;
    }
}
