package ru.job4j.todo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.dto.TaskPreview;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.store.TaskStore;

import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
@RequiredArgsConstructor
public class SimpleTaskService implements TaskService {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    private final TaskStore taskStore;
    private final ConcurrentHashMap<Integer, TaskPreview> taskStorage = new ConcurrentHashMap<>();

    @Override
    public Task save(Task task) {
        return taskStore.save(task);
    }

    @Override
    public boolean deleteById(int id) {
        return taskStore.deleteById(id);
    }

    @Override
    public boolean update(Task task) {
        return taskStore.update(task);
    }

    @Override
    public Optional<Task> findById(int id) {
        return taskStore.findById(id);
    }

    @Override
    public Collection<TaskPreview> findAll() {
        var taskList = taskStore.findAll();
        for (Task task : taskList) {
            var taskPreview = new TaskPreview(
                    task.getId(),
                    task.getTitle(),
                    FORMATTER.format(task.getCreated()),
                    task.isDone());
            taskStorage.putIfAbsent(task.getId(), taskPreview);
        }
        return taskStorage.values();
    }
}
