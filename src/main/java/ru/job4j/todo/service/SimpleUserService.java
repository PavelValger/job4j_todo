package ru.job4j.todo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.User;
import ru.job4j.todo.store.UserStore;

import java.util.Collection;
import java.util.Optional;
import java.util.TimeZone;

@Service
@RequiredArgsConstructor
public class SimpleUserService implements UserService {
    private final UserStore userStore;

    @Override
    public Optional<User> save(User user) {
        return userStore.save(user);
    }

    @Override
    public Optional<User> findByLoginAndPassword(String login, String password) {
        return userStore.findByLoginAndPassword(login, password);
    }

    @Override
    public Collection<TimeZone> getAllTimeZones() {
        return userStore.getAllTimeZones();
    }
}
