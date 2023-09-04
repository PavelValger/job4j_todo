package ru.job4j.todo.store;

import lombok.AllArgsConstructor;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;

import java.util.Collection;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibernateTaskStore implements TaskStore {
    private final SessionFactory sf;

    @Override
    public Task save(Task task) {
        return null;
    }

    @Override
    public boolean deleteById(int id) {
        return false;
    }

    @Override
    public boolean update(Task task) {
        return false;
    }

    @Override
    public Optional<Task> findById(int id) {
        return Optional.empty();
    }

    @Override
    public Collection<Task> findAll() {
        return null;
    }
}
