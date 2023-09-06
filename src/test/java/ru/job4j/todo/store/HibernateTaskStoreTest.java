package ru.job4j.todo.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.todo.model.Task;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import static java.time.LocalDateTime.now;
import static org.assertj.core.api.Assertions.*;

class HibernateTaskStoreTest {
    private static SessionFactory sf;
    private static HibernateTaskStore hibernateTaskStore;

    @BeforeAll
    public static void initRepositories() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        hibernateTaskStore = new HibernateTaskStore(sf);
    }

    @AfterEach
    public void clearTasks() {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery(
                            "delete Task")
                    .executeUpdate();
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
    }

    @Test
    public void whenSaveThenGetSame() {
        var creationDate = now().truncatedTo(ChronoUnit.MINUTES);
        Task task = new Task();
        task.setTitle("test");
        task.setDone(true);
        task.setDescription("");
        task.setCreated(creationDate);
        var savedTask = hibernateTaskStore.save(task);
        var findSavedVacancy = hibernateTaskStore.findById(task.getId()).get();
        assertThat(savedTask).usingRecursiveComparison().isEqualTo(findSavedVacancy);
    }

    @Test
    public void whenDeleteByIdThenTrue() {
        var creationDate = now().truncatedTo(ChronoUnit.MINUTES);
        Task task = new Task();
        task.setTitle("test");
        task.setDone(true);
        task.setDescription("");
        task.setCreated(creationDate);
        var savedTask = hibernateTaskStore.save(task);
        assertThat(hibernateTaskStore.deleteById(savedTask.getId())).isTrue();
    }

    @Test
    public void whenDeleteByIdThenFalse() {
        assertThat(hibernateTaskStore.deleteById(0)).isFalse();
    }

    @Test
    public void whenUpdateThenTrue() {
        var creationDate = now().truncatedTo(ChronoUnit.MINUTES);
        Task task = new Task();
        task.setTitle("test");
        task.setDone(true);
        task.setDescription("");
        task.setCreated(creationDate);

        Task updatedTask = new Task();
        updatedTask.setTitle("testUp");
        updatedTask.setDone(false);
        updatedTask.setDescription("qwerty");
        updatedTask.setCreated(creationDate.plusDays(1));
        var saved = hibernateTaskStore.save(task);
        updatedTask.setId(saved.getId());
        var isUpdated = hibernateTaskStore.update(updatedTask);
        var savedTask = hibernateTaskStore.findById(saved.getId());
        assertThat(isUpdated).isTrue();
        assertThat(savedTask.get()).usingRecursiveComparison().isEqualTo(updatedTask);
    }

    @Test
    public void whenUpdatedThenFalse() {
        var creationDate = now().truncatedTo(ChronoUnit.MINUTES);
        Task task = new Task();
        task.setTitle("test");
        task.setDone(true);
        task.setDescription("");
        task.setCreated(creationDate);

        Task updatedTask = new Task();
        updatedTask.setTitle("testUp");
        updatedTask.setDone(false);
        updatedTask.setDescription("qwerty");
        updatedTask.setCreated(creationDate.plusDays(1));
        var saved = hibernateTaskStore.save(task);
        updatedTask.setId(saved.getId());
        hibernateTaskStore.deleteById(saved.getId());
        assertThat(hibernateTaskStore.update(updatedTask)).isFalse();
    }

    @Test
    public void whenFindByIdThenGetSame() {
        var creationDate = now().truncatedTo(ChronoUnit.MINUTES);
        Task task = new Task();
        task.setTitle("test");
        task.setDone(true);
        task.setDescription("");
        task.setCreated(creationDate);
        var savedTask = hibernateTaskStore.save(task);
        assertThat(hibernateTaskStore.findById(savedTask.getId()).get())
                .usingRecursiveComparison().isEqualTo(task);
    }

    @Test
    public void whenFindByIdThenEmpty() {
        assertThat(hibernateTaskStore.findById(0)).isEqualTo(Optional.empty());
    }

    @Test
    public void whenFindAllThenGetSame() {
        var creationDate = now().truncatedTo(ChronoUnit.MINUTES);
        Task task1 = new Task();
        task1.setTitle("test");
        task1.setDone(true);
        task1.setDescription("");
        task1.setCreated(creationDate);
        hibernateTaskStore.save(task1);

        Task task2 = new Task();
        task2.setTitle("testUp");
        task2.setDone(false);
        task2.setDescription("qw");
        task2.setCreated(creationDate.plusDays(1));
        hibernateTaskStore.save(task2);
        assertThat(hibernateTaskStore.findAll()).isEqualTo(List.of(task1, task2));
    }
}