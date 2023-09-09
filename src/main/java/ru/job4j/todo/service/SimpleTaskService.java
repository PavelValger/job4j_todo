package ru.job4j.todo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.dto.TaskPreview;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.store.TaskStore;

import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SimpleTaskService implements TaskService {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    private final TaskStore taskStore;

    private Collection<TaskPreview> findAllStatus(Predicate<Task> predicate) {
        return taskStore.findAll().stream()
                .filter(predicate)
                .map(task -> new TaskPreview(
                        task.getId(),
                        task.getTitle(),
                        FORMATTER.format(task.getCreated()),
                        task.isDone())).collect(Collectors.toList());
    }

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
    public boolean updateDone(int id) {
        return taskStore.updateDone(id);
    }

    @Override
    public Optional<Task> findById(int id) {
        return taskStore.findById(id);
    }

    @Override
    public Collection<TaskPreview> findAll() {
        return taskStore.findAll().stream()
                .map(task -> new TaskPreview(
                        task.getId(),
                        task.getTitle(),
                        FORMATTER.format(task.getCreated()),
                        task.isDone())).collect(Collectors.toList());
    }

    @Override
    public Collection<TaskPreview> findAllTrue() {
        return findAllStatus(Task::isDone);
    }

    @Override
    public Collection<TaskPreview> findAllFalse() {
        return findAllStatus(task -> !task.isDone());
    }
}
