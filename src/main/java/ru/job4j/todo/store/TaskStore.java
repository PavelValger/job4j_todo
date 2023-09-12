package ru.job4j.todo.store;

import ru.job4j.todo.model.Task;

import java.util.Collection;
import java.util.Optional;

public interface TaskStore {

    Task save(Task task);

    boolean deleteById(int id);

    boolean update(Task task);

    boolean updateDone(int id);

    Optional<Task> findById(int id);

    Collection<Task> findAll();

    Collection<Task> searchByCriterion(boolean flag);
}
