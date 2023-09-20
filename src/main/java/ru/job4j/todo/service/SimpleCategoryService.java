package ru.job4j.todo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.store.CategoryStore;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class SimpleCategoryService implements CategoryService {
    private final CategoryStore categoryStore;

    @Override
    public Collection<Category> findAll() {
        return categoryStore.findAll();
    }

    @Override
    public Collection<Category> findAllById(Collection<Integer> categoriesId) {
        return categoryStore.findAllById(categoriesId);
    }
}
