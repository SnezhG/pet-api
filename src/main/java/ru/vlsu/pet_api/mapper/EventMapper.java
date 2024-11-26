package ru.vlsu.pet_api.mapper;

import org.mapstruct.Mapper;
import ru.vlsu.pet_api.dto.EventDTO;
import ru.vlsu.pet_api.entity.Event;

import java.util.List;

@Mapper
public interface EventMapper {
    EventDTO eventToEventDTO(Event event);

    List<EventDTO> eventListToEventDTO(List<Event> events);
}
