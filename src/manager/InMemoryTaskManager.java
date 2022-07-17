package manager;


import enums.Status;
import model.Epic;
import model.SubTask;
import model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// Класс controller.InMemoryTaskManager содержит список методов для всех типов задач.
public class InMemoryTaskManager implements TaskManager {
    
    Integer counterEpic = 0;
    HistoryManager inMemoryHistoryManager = Managers.getDefaultHistory();
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private Integer counterIDSubTasks = 0;
    private HashMap<Integer, SubTask> subTasks = new HashMap<>();
    private HashMap<Integer, Task> tasks = new HashMap<>();
    private Integer counterIDTasks = 0;
    // Получение истории.
    public List<Task> getHistory () {
        return inMemoryHistoryManager.getHistory();
    }
    
    // Удаление всей истории.
    public void removeAllHistory () {
        inMemoryHistoryManager.removeAll();
    }
    
    // Добавление задачи в историю.
    public void addInHistory (Task task) {
        inMemoryHistoryManager.add(task);
    }
    
    // Удаление задачи из истории по ИД.
    public void removeFromHistoryById (int id) {
        inMemoryHistoryManager.remove(id);
    }
    
    // Получение эпика по ID
    public Epic findEpicById (Integer id) {
        final Epic epic = epics.get(id);
        addInHistory(epic);
        return epic;
    }
    
    // Получение списка всех эпиков
    public ArrayList<Epic> findAllEpics () {
        return new ArrayList<>(epics.values());
    }
    
    // Обновление эпика по ID
    public Epic updateEpicByID (Epic epic) {
        final Epic originalTask = epics.get(epic.getId());
        if (originalTask == null) {
            System.out.println("Задачи с таким ID не существует.");
            return null;
        }
        originalTask.setDescription(epic.getDescription());
        originalTask.setName(epic.getName());
        return originalTask;
    }
    
    //    Создание нового эпика
    public Epic createEpic (Epic task) {
        final Epic newTask = new Epic(task.getName(), task.getDescription(), ++counterEpic);
        if (!epics.containsKey(newTask.getId())) {
            epics.put(newTask.getId(), newTask);
        } else {
            System.out.println("Задача с таким ID уже существует");
            return null;
        }
        return newTask;
    }
    
    // Удаление эпика по идентификатору.
    public void deleteEpicById (Integer id) {
        epics.remove(id);
    }
    
    // Удаление всех эпиков.
    public void deleteAllEpics () {
        epics.clear();
    }
    
    
    // Добавить подзадачу.
    public SubTask createSubTask (SubTask subTask, Epic epic) {
        final SubTask newSubTask = new SubTask(subTask.getName(), subTask.getDescription(), ++counterIDSubTasks, epic.getId());
        if (!subTasks.containsKey(newSubTask.getId())) {
            subTasks.put(newSubTask.getId(), newSubTask);
            this.findEpicById(epic.getId()).getSubTasks().add(newSubTask);
        } else {
            System.out.println("Задача с таким ID уже существует");
            return null;
        }
        return newSubTask;
    }
    // Получить подзадачу по ID
    public SubTask findSubTaskById (Integer id) {
        final SubTask subTask = subTasks.get(id);
        addInHistory(subTask);
        return subTask;
    }
    
    // Обновление подзадачи по ID
    public SubTask updateSubTaskByID (SubTask task) {
        final SubTask originalTask = subTasks.get(task.getId());
        if (originalTask == null) {
            System.out.println("Задачи с таким ID не существует.");
            return null;
        }
        originalTask.setDescription(task.getDescription());
        originalTask.setName(task.getName());
        originalTask.setStatus(task.getStatus());
        this.findEpicById(task.getEpicId()).getSubTasks().remove(originalTask);
        this.findEpicById(task.getEpicId()).getSubTasks().add(task);
        refreshStatus(task);
        return originalTask;
    }
    
    // Удаление задачи по идентификатору.
    public void deleteSubTaskById (Integer id) {
        removeFromHistoryById(id);
        final SubTask subTaskToDelete = subTasks.get(id);
        this.findEpicById(subTaskToDelete.getEpicId()).getSubTasks().remove(subTaskToDelete);
        subTasks.remove(id);
    }
    
    // Удаление всех задач.
    public void deleteAllSubTasks () {
        subTasks.clear();
    }
    
    // Получение списка всех подзадач определённого эпика.
    public ArrayList<SubTask> findAllSubTasksOfEpic (Epic epic) {
        return this.findEpicById(epic.getId()).getSubTasks();
    }
    
    // Обновление статуса эпика в зависимости от статуса подзадач
    public void refreshStatus (SubTask task) {
        ArrayList<SubTask> subTasksOfEpic = this.findEpicById(task.getEpicId()).getSubTasks();
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
            this.findEpicById(task.getEpicId()).setStatus(Status.NEW);
        } else if (counterDone == subTasksOfEpic.size()) {
            this.findEpicById(task.getEpicId()).setStatus(Status.DONE);
        } else {
            this.findEpicById(task.getEpicId()).setStatus(Status.IN_PROGRESS);
        }
    }
    
    
    public HashMap<Integer, Task> getTasks () {
        return tasks;
    }
    // Получение списка всех задач
    public ArrayList<Task> findAllTasks () {
        return new ArrayList<>(tasks.values());
    }
    
    // Получение задачи по ID
    public Task findTaskById (Integer id) {
        final Task task = tasks.get(id);
        addInHistory(task);
        return task;
    }
    
    //    Создание новой задачи
    public Task createTask (Task task) {
        final Task newTask = new Task(task.getName(), task.getDescription(), ++counterIDTasks);
        if (!tasks.containsKey(newTask.getId()))
            tasks.put(newTask.getId(), newTask);
        else {
            System.out.println("Задача с таким ID уже существует");
            return null;
        }
        return newTask;
    }
    
    
    // Обновление задачи по ID
    public Task updateTaskByID (Task task) {
        final Task originalTask = tasks.get(task.getId());
        if (originalTask == null) {
            System.out.println("Задачи с таким ID не существует.");
            return null;
        }
        originalTask.setDescription(task.getDescription());
        originalTask.setName(task.getName());
        originalTask.setStatus(task.getStatus());
        return originalTask;
    }
    
    
    // Удаление задачи по идентификатору.
    public void deleteTaskById (Integer id) {
        removeFromHistoryById(id);
        tasks.remove(id);
    }
    
    // Удаление всех задач.
    @Override
    public void deleteAllTask () {
        // Если удаляемые задачи присутствуют в истории, то очищаем и их.
        if (!inMemoryHistoryManager.getMap().isEmpty()) {
            for (var taskKey : this.getTasks().keySet()) {
                if (inMemoryHistoryManager.getMap().containsKey(taskKey)) {
                    Task task = inMemoryHistoryManager.getMap().get(taskKey).getTask();
                    inMemoryHistoryManager.remove(task.getId());
                }
            }
        }
//        if (!inMemoryHistoryManager.getMap().isEmpty()) {
//            for (var historyTask : inMemoryHistoryManager.getMap().values()) {
//                for (var task : this.getTasks().values()) {
//                    if (task.equals(historyTask.getTask())) {
//                        inMemoryHistoryManager.remove(historyTask.getTask().getId());
//                    }
//                }
//            }
//        }
        tasks.clear();
    }
}
