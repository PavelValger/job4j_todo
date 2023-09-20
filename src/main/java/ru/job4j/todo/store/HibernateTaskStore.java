package ru.job4j.todo.store;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;

import java.util.*;

@Repository
@AllArgsConstructor
public class HibernateTaskStore implements TaskStore {
    private static final Logger LOG = LoggerFactory.getLogger(HibernateUserStore.class.getName());
    private final CrudRepository crudRepository;

    @Override
    public Optional<Task> save(Task task) {
        try {
            crudRepository.run(session -> session.persist(task));
            return Optional.of(task);
        } catch (Exception e) {
            LOG.info("Неудачная попытка сохранения задания, Exception in log example", e);
        }
        return Optional.empty();
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
    public boolean updateDone(int id) {
        return crudRepository.numberRowsRequest(
                "UPDATE Task SET "
                        + "done = true "
                        + "WHERE id = :fId",
                Map.of("fId", id)
        ) > 0;
    }

    @Override
    public Optional<Task> findById(int id) {
        return crudRepository.optional(
                "FROM Task t JOIN FETCH t.categories WHERE t.id = :fId", Task.class,
                Map.of("fId", id)
        );
    }

    @Override
    public Collection<Task> findAll() {
        return crudRepository.query(
                "from Task t JOIN FETCH t.priority order by t.id", Task.class);
    }

    @Override
    public Collection<Task> searchByCriterion(boolean flag) {
        return crudRepository.query(
                "from Task t JOIN FETCH t.priority where done = :fDone order by t.id", Task.class,
                Map.of("fDone", flag)
        );
    }
}
