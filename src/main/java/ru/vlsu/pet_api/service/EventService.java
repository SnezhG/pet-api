package ru.vlsu.pet_api.service;

import ru.vlsu.pet_api.entity.Event;

import java.util.List;

public interface EventService {
    Event getById(Long id);

    List<Event> getAllByUser(Long id);

    Event create(Event event);

    Event update(Event newEvent);

    void delete(Long id);
}
