package ru.job4j.todo.store;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;

import java.util.*;
import java.util.function.Consumer;

@Repository
@AllArgsConstructor
public class HibernateTaskStore implements TaskStore {
    private final SessionFactory sf;

    private void closeSession(Session session) {
        session.getTransaction().commit();
        session.close();
    }

    private boolean crud(Consumer<Session> consumer) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            consumer.accept(session);
            closeSession(session);
            return true;
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        return false;
    }

    @Override
    public Task save(Task task) {
        crud(session -> session.save(task));
        return task;
    }

    @Override
    public boolean deleteById(int id) {
        return crud(session -> session.createQuery(
                        "DELETE Task WHERE id = :fId")
                .setParameter("fId", id)
                .executeUpdate());
    }

    @Override
    public boolean update(Task task) {
        return crud(session -> session.update(task));
    }

    @Override
    public Optional<Task> findById(int id) {
        Session session = sf.openSession();
        Optional<Task> result = Optional.empty();
        try {
            session.beginTransaction();
            result = Optional.ofNullable(session.get(Task.class, id));
            closeSession(session);
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        return result;
    }

    @Override
    public Collection<Task> findAll() {
        Session session = sf.openSession();
        List<Task> result = Collections.emptyList();
        try {
            session.beginTransaction();
            result = session.createQuery("from Task", Task.class).list();
            closeSession(session);
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        return new ArrayList<>(result);
    }
}
