package ru.job4j.todo.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.CategoryService;
import ru.job4j.todo.service.PriorityService;
import ru.job4j.todo.service.TaskService;

import javax.servlet.http.HttpSession;
import java.util.List;

import static ru.job4j.todo.utility.TimeZoneUtility.timeZoneWrapper;

@Controller
@AllArgsConstructor
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;
    private final CategoryService categoryService;
    private final PriorityService priorityService;

    @GetMapping
    public String getAll(Model model, HttpSession session) {
        var user = (User) session.getAttribute("user");
        model.addAttribute("tasks", timeZoneWrapper(user, taskService.findAll()));
        return "tasks/list";
    }

    @GetMapping("/create")
    public String getCreationPage(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("priorities", priorityService.findAll());
        return "tasks/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Task task, HttpSession session,
                         @RequestParam List<Integer> categoriesId) {
        var user = (User) session.getAttribute("user");
        task.setUser(user);
        task.getCategories().addAll(categoryService.findAllById(categoriesId));
        taskService.save(task);
        return "redirect:/tasks";
    }

    @GetMapping("/completed")
    public String getAllTrue(Model model, HttpSession session) {
        var user = (User) session.getAttribute("user");
        model.addAttribute("tasks", timeZoneWrapper(user, taskService.findAllTrue()));
        return "tasks/list";
    }

    @GetMapping("/fresh")
    public String getAllFalse(Model model, HttpSession session) {
        var user = (User) session.getAttribute("user");
        model.addAttribute("tasks", timeZoneWrapper(user, taskService.findAllFalse()));
        return "tasks/list";
    }

    @GetMapping("/{id}")
    public String getById(Model model, @PathVariable int id) {
        var taskOptional = taskService.findById(id);
        if (taskOptional.isEmpty()) {
            model.addAttribute("message", "Задание не найдено");
            return "errors/404";
        }
        model.addAttribute("task", taskOptional.get());
        return "tasks/one";
    }

    @GetMapping("/duplicate/{id}")
    public String getByIdDuplicate(Model model, @PathVariable int id) {
        var taskOptional = taskService.findById(id);
        if (taskOptional.isEmpty()) {
            model.addAttribute("message", "Задание не найдено");
            return "errors/404";
        }
        model.addAttribute("task", taskOptional.get());
        model.addAttribute("categoriesList", categoryService.findAll());
        return "tasks/update";
    }

    @GetMapping("/updateDone/{id}")
    public String updateDone(Model model, @PathVariable int id) {
        var isUpdated = taskService.updateDone(id);
        if (!isUpdated) {
            model.addAttribute("message", "Задание не найдено");
            return "errors/404";
        }
        return "redirect:/tasks";
    }

    @GetMapping("/delete/{id}")
    public String delete(Model model, @PathVariable int id) {
        var isDeleted = taskService.deleteById(id);
        if (!isDeleted) {
            model.addAttribute("message", "Задание не найдено");
            return "errors/404";
        }
        return "redirect:/tasks";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Task task, @RequestParam List<Integer> categoriesId) {
        task.getCategories().addAll(categoryService.findAllById(categoriesId));
        taskService.update(task);
        return "redirect:/tasks";
    }
}
