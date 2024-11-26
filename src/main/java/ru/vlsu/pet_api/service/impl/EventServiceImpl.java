package ru.vlsu.pet_api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vlsu.pet_api.entity.Event;
import ru.vlsu.pet_api.repository.EventRepository;
import ru.vlsu.pet_api.service.EventService;

import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImpl implements EventService {
    @Autowired
    private EventRepository repository;

    @Override
    public Event getById(Long id) {
        Optional<Event> event = repository.findById(id);
        return event.orElse(null);
    }

    @Override
    public List<Event> getAllByUser(Long id) {
        return repository.findAllByUser_Id(id);
    }

    @Override
    public Event create(Event event) {
        return repository.save(event);
    }

    @Override
    public Event update(Event newEvent) {
        Event oldEvent = getById(newEvent.getId());
        oldEvent.setDateTime(newEvent.getDateTime());
        oldEvent.setType(newEvent.getType());
        oldEvent.setPet(newEvent.getPet());
        oldEvent.setDescription(newEvent.getDescription());
        oldEvent.setNotificationEnabled(newEvent.isNotificationEnabled());
        return repository.save(oldEvent);
    }

    @Override
    public void delete(Long id) {
        Event event = getById(id);
        repository.delete(event);
    }
}
