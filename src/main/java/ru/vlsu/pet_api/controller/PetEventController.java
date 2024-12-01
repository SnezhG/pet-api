package ru.vlsu.pet_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vlsu.pet_api.dto.PetEventDTO;
import ru.vlsu.pet_api.entity.PetEvent;
import ru.vlsu.pet_api.mapper.PetEventMapper;
import ru.vlsu.pet_api.service.PetEventService;

import java.util.List;

@RestController
@RequestMapping("/api/event")
public class PetEventController {
    @Autowired
    private PetEventService service;
    @Autowired
    private PetEventMapper mapper;

    @GetMapping("/{id}")
    public PetEventDTO getById(@PathVariable Long id) {
        return mapper.toDTO(service.getById(id));
    }

    @GetMapping("/by-user/{id}")
    public List<PetEventDTO> getAllByUser(@PathVariable Long id) {
        return mapper.toDTOList(service.getAllByUser(id));
    }

    @PostMapping("/add")
    public PetEvent create(@RequestBody PetEventDTO petEventDTO) {
        PetEvent petEvent = mapper.toEntity(petEventDTO);
        return service.create(petEvent);
    }

    @PostMapping("/edit")
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
