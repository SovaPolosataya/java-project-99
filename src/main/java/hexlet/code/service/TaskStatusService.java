package hexlet.code.service;

import hexlet.code.dto.taskStatus.TaskStatusCreateDTO;
import hexlet.code.dto.taskStatus.TaskStatusDTO;
import hexlet.code.dto.taskStatus.TaskStatusUpdateDTO;
import hexlet.code.exception.ResourceNotFoundException;
import hexlet.code.mapper.TaskStatusMapper;
import hexlet.code.model.TaskStatus;
import hexlet.code.repository.TaskStatusRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskStatusService {
    @Autowired
    private TaskStatusRepository taskStatusRepository;

    @Autowired
    private TaskStatusMapper taskStatusMapper;


    public List<TaskStatusDTO> getAll() {
        List<TaskStatus> taskStatuses = taskStatusRepository.findAll();

        List<TaskStatusDTO> dto = taskStatuses.stream()
                .map(taskStatusMapper::map)
                .toList();
        return dto;
    }

    public TaskStatusDTO findById(Long id) {
        TaskStatus taskStatus = taskStatusRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TaskStatus with id=" + id + " not found!"));

        TaskStatusDTO dto = taskStatusMapper.map(taskStatus);
        return dto;
    }

    public TaskStatusDTO create(TaskStatusCreateDTO createDTO) {
        TaskStatus taskStatus = taskStatusMapper.map(createDTO);
        taskStatusRepository.save(taskStatus);

        TaskStatusDTO dto = taskStatusMapper.map(taskStatus);
        return dto;
    }

    public TaskStatusDTO update(TaskStatusUpdateDTO data, Long id) {
        TaskStatus taskStatus = taskStatusRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TaskStatus with id=" + id + " not found!"));
        taskStatusMapper.update(data, taskStatus);
        taskStatusRepository.save(taskStatus);

        TaskStatusDTO dto = taskStatusMapper.map(taskStatus);
        return dto;
    }

    public void delete(Long id) {
        taskStatusRepository.deleteById(id);
    }
}
