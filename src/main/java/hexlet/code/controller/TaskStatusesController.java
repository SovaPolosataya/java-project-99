package hexlet.code.controller;

import hexlet.code.dto.TaskStatusCreateDTO;
import hexlet.code.dto.TaskStatusDTO;
import hexlet.code.dto.TaskStatusUpdateDTO;
import hexlet.code.exception.ResourceNotFoundException;
import hexlet.code.mapper.TaskStatusMapper;
import hexlet.code.model.TaskStatus;
import hexlet.code.repository.TaskStatusRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/task_statuses")
public class TaskStatusesController {
    @Autowired
    private TaskStatusRepository statusRepository;
    @Autowired
    private TaskStatusMapper statusMapper;

    @GetMapping(path = "")
    @ResponseStatus(HttpStatus.OK)
    public List<TaskStatusDTO> index() {
        List<TaskStatus> statuses = statusRepository.findAll();
        List<TaskStatusDTO> dto = statuses.stream()
                .map(statusMapper::map)
                .toList();
        return dto;
    }

    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TaskStatusDTO show(@PathVariable Long id) {
        TaskStatus status = statusRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Status with id=" + id + " not found!"));

        TaskStatusDTO dto = statusMapper.map(status);
        return dto;
    }

    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.CREATED)
    public TaskStatusDTO create(@Valid @RequestBody TaskStatusCreateDTO createDto) {
        TaskStatus status = statusMapper.map(createDto);
        statusRepository.save(status);

        TaskStatusDTO dto = statusMapper.map(status);
        return dto;
    }

    @PutMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TaskStatusDTO update(@Valid @RequestBody TaskStatusUpdateDTO updateDto, @PathVariable Long id) {
        TaskStatus status = statusRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Status with id=" + id + " not found!"));
        statusMapper.update(updateDto, status);
        statusRepository.save(status);

        TaskStatusDTO dto = statusMapper.map(status);
        return dto;
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        statusRepository.deleteById(id);
    }
}
