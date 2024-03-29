package model;

import enums.Status;
import enums.TaskTypes;

import java.util.Objects;

// Класс SubTask описывает сущность задачи типа "подзадача"
public class SubTask extends Task {
    private Integer epicID;
    
    public SubTask(String name, String description, Integer id, Status status, Integer epicId) {
        super(name, description, id, status);
        this.epicID = epicId;
    }
    
    public SubTask(String name, String description, Integer id, Integer epicID) {
        super(name, description, id);
        this.epicID = epicID;
    }
    
    public Integer getEpicId () {
        return epicID;
    }
    
    
    public void setEpicID(Integer epicID) {
        this.epicID = epicID;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SubTask)) return false;
        if (!super.equals(o)) return false;
        SubTask subTask = (SubTask) o;
        return Objects.equals(getEpicId(), subTask.getEpicId());
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getEpicId());
    }
    
    @Override
    public String toString() {
        return  TaskTypes.SUBTASK.toString() +
         ", " + name +
         ", " + description +
         ", " + id +
         ", " + status +
         ", " + epicID;
    }
}