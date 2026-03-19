The tool used was Gemini. Below is the prompt and output given.

Prompt: 

I will be implementing classes for CRC Cards. I will give you the code that cannot be modified then you will help me develop the code that will be needed. 
1 - Implement Classes from CRC Cards (40 points)

The following CRC cards describe a simple Task Management

System. Using these CRC cards, implement the required Java classes.

Requirements

·       Each class must be in its own .java file.

·       Use the package

org.howard.edu.lsp.midterm.crccards in every Java class.

GitHub Push Requirement:



Push your solution to your GitHub repository early and often during the exam.

Students are strongly encouraged to push their work multiple times throughout

the exam to avoid losing work due to system failures. Only code that exists in

the GitHub repository at the end of the exam will be graded.

·       Your implementation must work with the Driver

class provided below.

·       Do not modify the Driver class.

·       TaskManager must select and use an appropriate

Java data structure to manage Task objects according to the requirements.

·       All classes and public methods must include

Javadoc comments.

·       You must include the required documentation

files described below.

·       You may use any standard classes from the Java

Collections Framework (for example, List, ArrayList, Map, etc.).

CRC Cards





Class





Responsibilities





Collaborators







Task





• store task information



• update task status



• provide task details





none







TaskManager





• store tasks



• add new tasks



• find a task by ID



• return tasks by status





Task





Part 1 -

Implement Task

Fields: taskId, description, status

Constructor:

public Task(String taskId, String description)

Rule: The default status must be "OPEN".

Required methods:

public String getTaskId()

public String getDescription()

public String getStatus()

public void setStatus(String status)

public String toString()

The toString() method must produce output in the following

format:

T1 Write report [OPEN]

 

Where the above is defined by: taskId description [status]

Valid status values include: OPEN, IN_PROGRESS, COMPLETE.

Status comparisons are case-sensitive.

If setStatus() is called with any value other than the valid

status values listed above, the task's status must be set to UNKNOWN.

Part 2 -

Implement TaskManager

TaskManager manages a collection of Task objects.

You must select and use an appropriate Java data structure

to store tasks internally. Your design should support:

·       storing tasks

·       locating a task by its taskId

·       preventing duplicate task IDs

·       retrieving tasks by status

The internal data structure you choose is up to you,

provided it supports the required behavior.

Required methods:

public void addTask(Task task)

public Task findTask(String taskId)

public List<Task> getTasksByStatus(String status)

Important note: getTasksByStatus() must return a

List<Task> containing all tasks whose status matches the specified value.

This return type does not restrict the internal data structure used by

TaskManager.

Duplicate Task Rule: TaskManager must not allow duplicate

task IDs. If a task with the same taskId already exists, addTask(Task task)

must throw IllegalArgumentException.

Additional behavior: If findTask() cannot locate the

requested task ID, it must return null. Below this is the code that was given and cannot be modified: package org.howard.edu.lsp.midterm.crccards;



import java.util.List;



public class Driver {



public static void main(String[] args) {



TaskManager manager = new TaskManager();



Task t1 = new Task("T1", "Write report");

Task t2 = new Task("T2", "Study for exam");

Task t3 = new Task("T3", "Submit homework");



manager.addTask(t1);

manager.addTask(t2);

manager.addTask(t3);



t2.setStatus("IN_PROGRESS");



System.out.println("OPEN TASKS");



List<Task> openTasks = manager.getTasksByStatus("OPEN");



for (Task t : openTasks) {

System.out.println(t.toString());

}



System.out.println("\nTESTING DUPLICATE TASK ID");



try {

Task duplicate = new Task("T1", "Duplicate task");

manager.addTask(duplicate);

} catch (IllegalArgumentException e) {

System.out.println("Duplicate task ID detected.");

}



System.out.println("\nTESTING INVALID STATUS");



Task t4 = new Task("T4", "Invalid status test");

t4.setStatus("DONE");

System.out.println(t4.toString());



System.out.println("\nTESTING FIND TASK");



Task found = manager.findTask("T2");

System.out.println(found);



Task notFound = manager.findTask("T99");



if (notFound == null) {

System.out.println("Task not found.");

}

}

}

Output:

This task requires a clean, object-oriented implementation based on the CRC (Class-Responsibility-Collaborator) design. For the TaskManager, using a Map<String, Task> (like a HashMap) is the most efficient choice because it allows for $O(1)$ constant-time lookups by taskId and inherently helps in preventing duplicates.Here is the implementation of the two required classes.1. Task.javaJavapackage org.howard.edu.lsp.midterm.crccards;

import java.util.Arrays;
import java.util.List;

/**
 * Represents a single task in the Task Management System.
 * Stores task ID, description, and status.
 */
public class Task {
    private String taskId;
    private String description;
    private String status;

    // List of valid statuses for validation
    private static final List<String> VALID_STATUSES = Arrays.asList("OPEN", "IN_PROGRESS", "COMPLETE");

    /**
     * Constructs a new Task with a given ID and description.
     * The default status is set to "OPEN".
     * * @param taskId the unique identifier for the task
     * @param description a brief description of the task
     */
    public Task(String taskId, String description) {
        this.taskId = taskId;
        this.description = description;
        this.status = "OPEN";
    }

    /**
     * Returns the task ID.
     * @return the taskId
     */
    public String getTaskId() {
        return taskId;
    }

    /**
     * Returns the task description.
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the current status of the task.
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Updates the task status. 
     * If the status is not OPEN, IN_PROGRESS, or COMPLETE, it is set to UNKNOWN.
     * * @param status the new status to set
     */
    public void setStatus(String status) {
        if (VALID_STATUSES.contains(status)) {
            this.status = status;
        } else {
            this.status = "UNKNOWN";
        }
    }

    /**
     * Returns a string representation of the task in the format:
     * taskId description [status]
     * * @return formatted task string
     */
    @Override
    public String toString() {
        return String.format("%s %s [%s]", taskId, description, status);
    }
}
2. TaskManager.javaJavapackage org.howard.edu.lsp.midterm.crccards;

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

Implementation Highlights:
Encapsulation: The fields in Task are private, accessed through getters and setters.

Validation: In setStatus, I used a List of valid strings to check the input, ensuring the "UNKNOWN" fallback logic works as requested.

Efficiency: Using HashMap in TaskManager ensures that checking for duplicates (via containsKey) and finding tasks (via get) is extremely fast, even as the number of tasks grows.

Javadoc: All public members include standard Javadoc comments for documentation.

Would you like me to help you draft a README.md or a pom.xml file to go along with these classes for your GitHub submission?