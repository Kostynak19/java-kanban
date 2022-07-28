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
    
    private Integer counterEpic = 0;
    private final HistoryManager inMemoryHistoryManager = Managers.getDefaultHistory();
    protected static final HashMap<Integer, Epic> epics = new HashMap<>();
    private Integer counterIDSubTasks = 0;
    protected static final HashMap<Integer, SubTask> subTasks = new HashMap<>();
    protected static final HashMap<Integer, Task> tasks = new HashMap<>();
    private Integer counterIDTasks = 0;
    
    public void deleteEpicById (Integer id) {
    }
    // Получение истории.
    public List<Task> getHistory () {
        return inMemoryHistoryManager.getHistory();
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
    public Epic updateEpicById (Epic epic) {
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
    public SubTask updateSubTaskById (SubTask task) {
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
    @Override
    public Task deleteTaskById (Integer id) {
        return null;
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
    public Task updateTaskById (Task task) {
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
    
    
    // Удаление всех задач.
    @Override
    public void deleteAllTask () {
        // Если удаляемые задачи присутствуют в истории, то очищаем и их.
            for (var taskKey : this.getTasks().keySet()) {
                    inMemoryHistoryManager.remove(taskKey);
            }
        tasks.clear();
    }
}
