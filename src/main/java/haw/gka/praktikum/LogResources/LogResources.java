package haw.gka.praktikum.LogResources;

import java.util.HashMap;
import java.util.Map;

public class LogResources {
    public static Map<String, Task> _tasks = new HashMap<>();

    public static void startTask(String taskName) {
        Runtime runtime = Runtime.getRuntime();
        runtime.gc();
        long memory = runtime.totalMemory() - runtime.freeMemory();

        Task task = new Task(taskName, System.currentTimeMillis(), memory);
        _tasks.put(taskName, task);
    }

    public static void stopTask(String taskName) {
        long finish = System.currentTimeMillis();
        Runtime runtime = Runtime.getRuntime();
        runtime.gc();
        long memory = runtime.totalMemory() - runtime.freeMemory();
        //avoid negative (impossible) memory usage
        if (memory < 0) {
            memory = 0;
        }

        Task task = _tasks.get(taskName);
        if (task != null) {
            _tasks.remove(taskName);
        }

        long timeElapsed = finish - task.getStartTime();
        long memoryElapsed = memory - task.getStartBytes();

        System.out.println("\n-------------------------------");
        System.out.println("Task Name: " + taskName);
        System.out.println("Time elapsed: " + timeElapsed + " ms");
        System.out.println("Used memory (end): " + (double) memoryElapsed / 1_000_000 + " MB");
        System.out.println("-------------------------------");
    }
}
