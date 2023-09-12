package ru.job4j.todo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.store.TaskStore;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SimpleTaskService implements TaskService {
    private final TaskStore taskStore;

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
    public Collection<Task> findAll() {
        return taskStore.findAll();
    }

    @Override
    public Collection<Task> findAllTrue() {
        return taskStore.searchByCriterion(true);
    }

    @Override
    public Collection<Task> findAllFalse() {
        return taskStore.searchByCriterion(false);
    }
}
