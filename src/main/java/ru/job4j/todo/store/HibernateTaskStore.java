package ru.job4j.todo.store;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;

import java.util.*;

@Repository
@AllArgsConstructor
public class HibernateTaskStore implements TaskStore {
    private final CrudRepository crudRepository;

    @Override
    public Task save(Task task) {
        crudRepository.run(session -> session.persist(task));
        return task;
    }

    @Override
    public boolean deleteById(int id) {
        return crudRepository.numberRowsRequest(
                "delete Task where id = :fId",
                Map.of("fId", id)
        ) > 0;
    }

    @Override
    public void update(Task task) {
        crudRepository.run(session -> session.merge(task));
    }

    @Override
    public boolean updateDone(int id, User user) {
        return crudRepository.numberRowsRequest(
                "UPDATE Task SET "
                        + "done = true, "
                        + "user = :fUser "
                        + "WHERE id = :fId",
                Map.of("fId", id, "fUser", user)
        ) > 0;
    }

    @Override
    public Optional<Task> findById(int id) {
        return crudRepository.optional(
                "from Task where id = :fId", Task.class,
                Map.of("fId", id)
        );
    }

    @Override
    public Collection<Task> findAll() {
        return crudRepository.query(
                "from Task t order by t.id", Task.class);
    }

    @Override
    public Collection<Task> searchByCriterion(boolean flag) {
        return crudRepository.query(
                "from Task t where done = :fDone order by t.id", Task.class,
                Map.of("fDone", flag)
        );
    }
}
