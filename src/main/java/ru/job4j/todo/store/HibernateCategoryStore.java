package ru.job4j.todo.store;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Category;

import java.util.Collection;

@Repository
@AllArgsConstructor
public class HibernateCategoryStore implements CategoryStore {
    private final CrudRepository crudRepository;

    @Override
    public Collection<Category> findAll() {
        return crudRepository.query(
                "from Category c order by c.id", Category.class);
    }

    @Override
    public Collection<Category> findAllById(Collection<Integer> categoriesId) {
       return crudRepository.findAllById(categoriesId, Category.class);
    }
}
