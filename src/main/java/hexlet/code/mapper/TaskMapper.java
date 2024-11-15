package hexlet.code.mapper;

import hexlet.code.dto.task.TaskCreateDTO;
import hexlet.code.dto.task.TaskDTO;
import hexlet.code.dto.task.TaskUpdateDTO;
import hexlet.code.model.Label;
import hexlet.code.model.Task;
import hexlet.code.model.TaskStatus;
import hexlet.code.repository.LabelRepository;
import hexlet.code.repository.TaskStatusRepository;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(
        uses = { JsonNullableMapper.class, ReferenceMapper.class },
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class TaskMapper {
    @Autowired
    private LabelRepository labelRepository;
    @Autowired
    private TaskStatusRepository taskStatusRepository;

    @Mapping(target = "name", source = "title")
    @Mapping(target = "description", source = "content")
    @Mapping(target = "assignee", source = "assigneeId")
    @Mapping(target = "taskStatus", source = "status", qualifiedByName = "slugToStatus")
    @Mapping(target = "labels", source = "taskLabelIds", qualifiedByName = "listIdsToListLabels")
    public abstract Task map(TaskCreateDTO dto);


    @Mapping(source = "name", target = "title")
    @Mapping(source = "description", target = "content")
    @Mapping(source = "assignee.id", target = "assigneeId")
    @Mapping(source = "taskStatus.slug", target = "status")
    @Mapping(source = "labels", target = "taskLabelIds", qualifiedByName = "listLabelsToListIds")
    public abstract TaskDTO map(Task model);

    @Mapping(source = "title", target = "name")
    @Mapping(source = "content", target = "description")
    @Mapping(source = "assigneeId", target = "assignee.id")
    @Mapping(source = "status", target = "taskStatus.slug")
    @Mapping(source = "taskLabelIds", target = "labels", qualifiedByName = "listIdsToListLabels")
    public abstract Task map(TaskDTO dto);

    @Mapping(target = "assignee", source = "assigneeId")
    @Mapping(target = "name", source = "title")
    @Mapping(target = "description", source = "content")
    @Mapping(target = "taskStatus", source = "status", qualifiedByName = "slugToStatus")
    @Mapping(target = "labels", source = "taskLabelIds", qualifiedByName = "listIdsToListLabels")
    public abstract void update(TaskUpdateDTO dto, @MappingTarget Task model);

    @Named("listLabelsToListIds")
    public Set<Long> listLabelsToListIds(Set<Label> models) {
        return models == null ? null : models.stream()
                .map(Label::getId)
                .collect(Collectors.toSet());
    }

    @Named("listIdsToListLabels")
    public Set<Label> listIdsToListLabels(Set<Long> ids) {
         List<Label> labels = labelRepository.findByIdIn(new ArrayList<>(ids));
         return new HashSet<>(labels);
    }

    @Named("slugToStatus")
    public TaskStatus slugToStatus(String slug) {
        return taskStatusRepository.findBySlug(slug)
                .orElseThrow();
    }
}
