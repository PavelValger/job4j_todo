package ru.job4j.todo.store;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;

import java.util.*;
import java.util.function.Function;

@Repository
@AllArgsConstructor
public class HibernateTaskStore implements TaskStore {
    private final SessionFactory sf;

    private void closeSession(Session session) {
        session.getTransaction().commit();
        session.close();
    }

    private boolean calculation(Function<Session, Integer> function) {
        boolean rsl = false;
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            Integer count = function.apply(session);
            closeSession(session);
            rsl = count > 0;
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        return rsl;
    }

    @Override
    public Task save(Task task) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.save(task);
            closeSession(session);
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        return task;
    }

    @Override
    public boolean deleteById(int id) {
        return calculation(session -> session.createQuery(
                        "DELETE Task WHERE id = :fId")
                .setParameter("fId", id)
                .executeUpdate());
    }

    @Override
    public boolean update(Task task) {
        Session session = sf.openSession();
        boolean rsl = false;
        try {
            session.beginTransaction();
            session.update(task);
            closeSession(session);
            rsl = true;
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        return rsl;
    }

    @Override
    public boolean updateDone(int id) {
        return calculation(session -> session.createQuery(
                        "UPDATE Task SET "
                                + "done = true "
                                + "WHERE id = :fId")
                .setParameter("fId", id)
                .executeUpdate());
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
            result = session.createQuery("from Task t order by t.id", Task.class).list();
            closeSession(session);
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        return new ArrayList<>(result);
    }

    @Override
    public Collection<Task> searchByCriterion(boolean flag) {
        Session session = sf.openSession();
        List<Task> result = Collections.emptyList();
        try {
            session.beginTransaction();
            result = session.createQuery("from Task t where done = :fDone order by t.id", Task.class)
                    .setParameter("fDone", flag)
                    .list();
            closeSession(session);
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        return new ArrayList<>(result);
    }
}
