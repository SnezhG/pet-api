package ru.vlsu.pet_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vlsu.pet_api.dto.EventDTO;
import ru.vlsu.pet_api.entity.Event;
import ru.vlsu.pet_api.mapper.EventMapper;
import ru.vlsu.pet_api.service.EventService;

import java.util.List;

@RestController
@RequestMapping("/api/event")
public class EventController {
    @Autowired
    private EventService service;
    @Autowired
    private EventMapper mapper;

    @GetMapping("/{id}")
    public EventDTO getById(@PathVariable Long id) {
        return mapper.toDTO(service.getById(id));
    }

    @GetMapping("/by-user/{id}")
    public List<EventDTO> getAllByUser(@PathVariable Long id) {
        return mapper.toDTOList(service.getAllByUser(id));
    }

    @PostMapping("/add")
    public Event create(@RequestBody EventDTO eventDTO) {
        Event event = mapper.toEntity(eventDTO);
        return service.create(event);
    }

    @PostMapping("/edit")
    public Event update(@RequestBody EventDTO eventDTO) {
        Event event = mapper.toEntity(eventDTO);
        return service.update(event);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
