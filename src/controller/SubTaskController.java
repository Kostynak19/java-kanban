package controller;

import model.Epic;
import model.Status;
import model.SubTask;

import java.util.ArrayList;
import java.util.HashMap;

// Класс SubTaskManager содержит список методов для задач типа "подзадача";
public class SubTaskController {
    private Integer counterIDSubTasks = 0;
    private final HashMap<Integer, SubTask> subTasks = new HashMap<>();
    EpicManager epicManager;

    public SubTaskController(EpicManager epicManager) {
        this.epicManager = epicManager;
    }

    // Добавить подзадачу.
    public SubTask create(SubTask subTask, Epic epic) {
        final SubTask newSubTask = new SubTask(subTask.getName(), subTask.getDescription(), ++counterIDSubTasks, epic.getId());
        if (!subTasks.containsKey(newSubTask.getId())) {
            subTasks.put(newSubTask.getId(), newSubTask);
            epicManager.epics.get(epic.getId()).getSubTasks().add(newSubTask);
        } else {
            System.out.println("Задача с таким ID уже существует");
            return null;
        }
        return newSubTask;
    }

    // Получить подзадачу по ID
    public SubTask findById(Integer id) {
        return subTasks.get(id);
    }

    // Обновление подзадачи по ID
    public SubTask update(SubTask task) {
        final SubTask originalTask = subTasks.get(task.getId());
        if (originalTask == null) {
            System.out.println("Задачи с таким ID не существует.");
            return null;
        }
        originalTask.setDescription(task.getDescription());
        originalTask.setName(task.getName());
        originalTask.setStatus(task.getStatus());
        epicManager.epics.get(task.getEpicId()).getSubTasks().remove(originalTask);
        epicManager.epics.get(task.getEpicId()).getSubTasks().add(task);
        refreshStatus(task);
        return originalTask;
    }

    // Удаление задачи по идентификатору.
    public SubTask deleteById(Integer id) {
        final SubTask deletedTask = subTasks.get(id);
        epicManager.epics.get(deletedTask.getEpicId()).getSubTasks().remove(deletedTask);
        subTasks.remove(id);
        return deletedTask;
    }

    // Удаление всех задач.
    public void deleteAll() {
        subTasks.clear();
    }

    // Получение списка всех подзадач определённого эпика.
    public ArrayList<SubTask> findAllOfEpic(Epic epic) {
        return epicManager.epics.get(epic.getId()).getSubTasks();
    }

    // Обновление статуса эпика в зависимости от статуса подзадач
    public void refreshStatus(SubTask task) {
        ArrayList<SubTask> subTasksOfEpic = epicManager.epics.get(task.getEpicId()).getSubTasks();
        int counterNew = 0;
        int counterDone = 0;
        for (SubTask subTask : subTasksOfEpic) {
            if (subTask.getStatus() == Status.NEW) {
                counterNew++;
            } else if (subTask.getStatus() == Status.DONE) {
                counterDone++;
            }
        }
        if (counterNew == subTasksOfEpic.size()) {
            epicManager.epics.get(task.getEpicId()).setStatus(Status.NEW);
        } else if (counterDone == subTasksOfEpic.size()) {
            epicManager.epics.get(task.getEpicId()).setStatus(Status.DONE);
        } else {
            epicManager.epics.get(task.getEpicId()).setStatus(Status.IN_PROGRESS);
        }
    }
}