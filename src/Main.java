import enums.Status;
import manager.FileBackedTasksManager;
import manager.HttpTasksManager;
import manager.Managers;
import model.Epic;
import model.Task;

import java.io.File;

public class Main {
    public static void main (String[] args) {
        HttpTasksManager managers = Managers.getManagers();
    
        Task task = new Task("Тест", "Описание", Status.NEW.ordinal());
        managers.addInHistory(task);
        final Task saveTask = managers.getTasks().get(task.getId());
        if (!task.equals(saveTask)) {
            System.out.println("Ошибка задачи не находится по ид " + task.getId());
        }
        System.out.println(managers.getTasks());
//        managers.deleteTaskById(saveTask.getId());
  //      managers.deleteAllTask();
    
        Epic epic = new Epic("Тест epic", "Описание epic");
        managers.createEpic(epic);
        final Epic saveEpic = managers.findEpicById(epic.getId());
        if (!epic.equals(saveEpic)) {
            System.out.println("Ошибка задачи не находится по ид " + epic.getId());
        }
        System.out.println(managers.findAllEpics());
        managers.deleteEpicById(saveEpic.getId());
        managers.deleteAllEpics();
    
        FileBackedTasksManager backedTasksManager = FileBackedTasksManager.loadFromFile(new File("save.csv"));
        System.out.println();
        System.out.println("Проверка: ");
        System.out.println("Задачи совпадают: " + managers.getTasks().equals(backedTasksManager.getTasks()));
        System.out.println("Эпики совпадают: " + managers.findAllEpics().equals(backedTasksManager.findAllEpics()));
        System.out.println("История совпадает: " + managers.getHistory().equals(backedTasksManager.getHistory()));
    }
}
