package ru.vlsu.pet_api.mapper;

import org.mapstruct.Mapper;
import ru.vlsu.pet_api.dto.EventDTO;
import ru.vlsu.pet_api.entity.Event;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EventMapper {
    EventDTO toDTO(Event event);

    Event toEntity(EventDTO eventDTO);

    List<EventDTO> toDTOList(List<Event> events);
}
