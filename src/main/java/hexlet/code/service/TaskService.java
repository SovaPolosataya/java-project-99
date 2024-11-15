package hexlet.code.service;

import hexlet.code.dto.task.TaskCreateDTO;
import hexlet.code.dto.task.TaskDTO;
import hexlet.code.dto.task.TaskParamsDTO;
import hexlet.code.dto.task.TaskUpdateDTO;
import hexlet.code.exception.ResourceNotFoundException;
import hexlet.code.mapper.TaskMapper;
import hexlet.code.model.Task;
import hexlet.code.repository.TaskRepository;

import hexlet.code.specification.TaskSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private TaskSpecification specBuilder;

    public List<TaskDTO> getAll(TaskParamsDTO params, int page) {
        Specification<Task> spec = specBuilder.build(params);
        Page<Task> tasks = taskRepository.findAll(spec, PageRequest.of(page - 1, 10));

        List<TaskDTO> dto = tasks.map(taskMapper::map).toList();
        return dto;
    }

    public TaskDTO findById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task with id=" + id + " not found!"));

        TaskDTO dto = taskMapper.map(task);
        return dto;
    }

    public TaskDTO create(TaskCreateDTO createDTO) {
        Task task = taskMapper.map(createDTO);
        taskRepository.save(task);

        TaskDTO dto = taskMapper.map(task);
        return dto;
    }

    public TaskDTO update(TaskUpdateDTO data, Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task with id=" + id + " not found!"));
        taskMapper.update(data, task);
        taskRepository.save(task);

        TaskDTO dto = taskMapper.map(task);
        return dto;
    }

    public void delete(Long id) {
        taskRepository.deleteById(id);
    }
}
