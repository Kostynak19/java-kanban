package manager;

import model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    
    private static class Node {
        
        private Task task;
        private Node prev;
        private Node next;
        private Node (Task task) {
            this.task = task;
        }
    }
    private HashMap<Integer, Node> map = new HashMap<>();
    private Node head;
    private Node tail;
    @Override
    public void add (Task task) {
        if (task == null) {
            return;
        }
        linkLast(task);
    }
    private void linkLast (Task task) {
        remove(task.getId());
        final Node newNode = new Node(task);
        if (head == null) {
            head = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
        }
        tail = newNode;
        map.put(task.getId(), newNode);
    }
    @Override
    public void remove (int id) {
        final Node oldNode = map.remove(id);
        if (oldNode != null) {
            if (oldNode == head) {
                head = oldNode.next;
                if (head != null) {
                    head.prev = null;
                }
            } else if (oldNode == tail) {
                tail = oldNode.prev;
                if (tail != null) {
                    tail.next = null;
                }
            } else {
                oldNode.prev.next = oldNode.next;
                oldNode.next.prev = oldNode.prev;
            }
        }
    }
    
    @Override
    public void removeAll() {
        /*Node */head = null;
        /*Node */tail = null;
        /*HashMap<Integer, Node> */map = new HashMap<>();
    }
    @Override
    public List<Task> getHistory () {
        final ArrayList<Task> tasks = new ArrayList<>();
        Node current = head;
        while (current != null) {
            tasks.add(current.task);
            current = current.next;
        }
        return tasks;
    }
}

