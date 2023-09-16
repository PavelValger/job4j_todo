package ru.job4j.todo.service;

import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;

import java.util.Collection;
import java.util.Optional;

public interface TaskService {

    Task save(Task task);

    boolean deleteById(int id);

    void update(Task task);

    boolean updateDone(int id, User user);

    Optional<Task> findById(int id);

    Collection<Task> findAll();

    Collection<Task> findAllTrue();

    Collection<Task> findAllFalse();
}
