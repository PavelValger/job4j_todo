package ru.job4j.todo.store;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Priority;

import java.util.Collection;

@Repository
@AllArgsConstructor
public class HibernatePriorityStore implements PriorityStore {
    private final CrudRepository crudRepository;

    @Override
    public Collection<Priority> findAll() {
        return crudRepository.query(
                "from Priority c order by c.id", Priority.class);
    }
}
