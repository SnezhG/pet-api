package ru.vlsu.pet_api.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vlsu.pet_api.dto.PetEventDTO;
import ru.vlsu.pet_api.entity.PetEvent;
import ru.vlsu.pet_api.mapper.PetEventMapper;
import ru.vlsu.pet_api.service.PetEventService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/event")
@Slf4j
public class PetEventController {
    @Autowired
    private PetEventService service;
    @Autowired
    private PetEventMapper mapper;

    @GetMapping("/{id}")
    public PetEventDTO getById(@PathVariable Long id) {
        return mapper.toDTO(service.getById(id));
    }

    @GetMapping("/by-user-week/{id}")
    public List<PetEventDTO> getAllOnWeekByUser(@PathVariable Long id) {
        return mapper.toDTOList(service.getAllOnWeekByUser(id));
    }

    @GetMapping("/by-user-all/{id}")
    public List<PetEventDTO> getAllByUser(@PathVariable Long id) {
        return mapper.toDTOList(service.getAllByUser(id));
    }

    @GetMapping("/by-date/{id}/{date}")
    public List<PetEventDTO> getAllByDate(@PathVariable Long id, @PathVariable LocalDate date) {
        return mapper.toDTOList(service.getAllByUserAndDate(id, date));
    }

    @PostMapping("/create")
    public PetEvent create(@RequestBody PetEventDTO petEventDTO) {
        PetEvent petEvent = mapper.toEntity(petEventDTO);
        return service.create(petEvent);
    }

    @PostMapping("/update")
    public PetEvent update(@RequestBody PetEventDTO petEventDTO) {
        PetEvent petEvent = mapper.toEntity(petEventDTO);
        return service.update(petEvent);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
