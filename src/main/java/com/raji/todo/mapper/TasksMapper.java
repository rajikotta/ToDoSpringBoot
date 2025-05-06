package com.raji.todo.mapper;

import com.raji.todo.models.db.TaskEntity;
import com.raji.todo.models.dto.TaskDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TasksMapper implements Mapper<TaskEntity, TaskDto> {

    private final ModelMapper modelMapper;

    public TasksMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public TaskDto mapTo(TaskEntity taskEntity) {
        return modelMapper.map(taskEntity, TaskDto.class);
    }

    @Override
    public TaskEntity mapFrom(TaskDto taskDto) {
        return modelMapper.map(taskDto, TaskEntity.class);
    }
}
