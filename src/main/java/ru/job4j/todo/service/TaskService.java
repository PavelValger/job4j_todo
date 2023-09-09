package ru.job4j.todo.service;

import ru.job4j.todo.dto.TaskPreview;
import ru.job4j.todo.model.Task;

import java.util.Collection;
import java.util.Optional;

public interface TaskService {

    Task save(Task task);

    boolean deleteById(int id);

    boolean update(Task task);

    boolean updateDone(int id);

    Optional<Task> findById(int id);

    Collection<TaskPreview> findAll();

    Collection<TaskPreview> findAllTrue();

    Collection<TaskPreview> findAllFalse();
}
