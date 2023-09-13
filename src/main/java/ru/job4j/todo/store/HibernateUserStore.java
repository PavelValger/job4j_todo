package ru.job4j.todo.store;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.User;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class HibernateUserStore implements UserStore {
    private static final Logger LOG = LoggerFactory.getLogger(HibernateUserStore.class.getName());
    private final SessionFactory sf;

    private void closeSession(Session session) {
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public Optional<User> save(User user) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.save(user);
            closeSession(session);
            return Optional.of(user);
        } catch (Exception e) {
            session.getTransaction().rollback();
            LOG.info("Регистрация зарегистрированного пользователя, Exception in log example", e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findByLoginAndPassword(String login, String password) {
        Session session = sf.openSession();
        Optional<User> rsl = Optional.empty();
        try {
            session.beginTransaction();
            rsl = session
                    .createQuery("from User u where u.login = :fLogin and "
                            + "u.password = :fPassword", User.class)
                    .setParameter("fLogin", login)
                    .setParameter("fPassword", password)
                    .uniqueResultOptional();
            closeSession(session);
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        return rsl;
    }
}
