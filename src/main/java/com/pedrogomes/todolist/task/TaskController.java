package com.pedrogomes.todolist.task;

import com.pedrogomes.todolist.task.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private ITaskRepository taskRepository;

    @PostMapping("/")
    public ResponseEntity create(@RequestBody TaskModel taskModel, HttpServletRequest request) {
        taskModel.setUserId( (UUID) request.getAttribute("userId"));

        if (taskModel.getStartAt().isAfter(taskModel.getEndAt())) {
            return ResponseEntity.status(400).body("A data do fim da tarefa deve ser posterior à data de início");
        }

        var task = this.taskRepository.save(taskModel);
        return ResponseEntity.status(200).body(task);
    }

    @GetMapping("/")
    public List<TaskModel> fetchAll(HttpServletRequest request) {
        return this.taskRepository.findByUserId( (UUID) request.getAttribute("userId"));
    }

    @PutMapping("/{id}")
    public TaskModel update(@RequestBody TaskModel taskModel, @PathVariable UUID id, HttpServletRequest request) {
        var task = (TaskModel) this.taskRepository.findByUserId(id);

        if (task == null) {
            return null;
        }

        Utils.copyNonNullProperties(taskModel, task);
        return this.taskRepository.save(task);
    }
}






