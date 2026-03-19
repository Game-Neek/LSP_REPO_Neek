package org.howard.edu.lsp.midterm.crccards;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manages a collection of Task objects.
 * Provides functionality to add, find, and filter tasks by status.
 */
public class TaskManager {
    // Map is used for O(1) lookup and easy duplicate ID prevention
    private Map<String, Task> tasks;

    /**
     * Initializes an empty TaskManager.
     */
    public TaskManager() {
        this.tasks = new HashMap<>();
    }

    /**
     * Adds a task to the manager.
     * * @param task the task to be added
     * @throws IllegalArgumentException if a task with the same ID already exists
     */
    public void addTask(Task task) {
        if (tasks.containsKey(task.getTaskId())) {
            throw new IllegalArgumentException("Task ID " + task.getTaskId() + " already exists.");
        }
        tasks.put(task.getTaskId(), task);
    }

    /**
     * Finds a task by its unique ID.
     * * @param taskId the ID to search for
     * @return the Task object if found, or null otherwise
     */
    public Task findTask(String taskId) {
        return tasks.get(taskId);
    }

    /**
     * Retrieves a list of all tasks that match the specified status.
     * * @param status the status to filter by
     * @return a List of Task objects with the matching status
     */
    public List<Task> getTasksByStatus(String status) {
        List<Task> filteredTasks = new ArrayList<>();
        for (Task task : tasks.values()) {
            if (task.getStatus().equals(status)) {
                filteredTasks.add(task);
            }
        }
        return filteredTasks;
    }
}