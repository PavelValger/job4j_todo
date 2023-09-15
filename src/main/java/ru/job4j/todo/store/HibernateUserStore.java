package ru.job4j.todo.store;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.User;

import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class HibernateUserStore implements UserStore {
    private static final Logger LOG = LoggerFactory.getLogger(HibernateUserStore.class.getName());
    private final CrudRepository crudRepository;

    public Optional<User> save(User user) {
        try {
            crudRepository.run(session -> session.persist(user));
            return Optional.of(user);
        } catch (Exception e) {
            LOG.info("Регистрация зарегистрированного пользователя, Exception in log example", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findByLoginAndPassword(String login, String password) {
        return crudRepository.optional(
                "from User where login = :fLogin and password = :fPassword", User.class,
                Map.of("fLogin", login,
                        "fPassword", password)
        );
    }
}
