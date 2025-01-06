package ru.vlsu.pet_api.controller;

import jakarta.servlet.http.HttpServletRequest;
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
    public ResponseEntity<PetEventDTO> getById(@PathVariable Long id) {
        PetEventDTO petEventDTO = mapper.toDTO(service.getById(id));
        return ResponseEntity.ok(petEventDTO);
    }

    @GetMapping("/by-user-week")
    public ResponseEntity<List<PetEventDTO>> getAllOnWeekByUser(HttpServletRequest request) {
        List<PetEventDTO> petEventDTOList = mapper.toDTOList(service.getAllOnWeekByUser(request));
        return ResponseEntity.ok(petEventDTOList);
    }

    @GetMapping("/by-user-all")
    public ResponseEntity<List<PetEventDTO>> getAllByUser(HttpServletRequest request) {
        List<PetEventDTO> petEventDTOList = mapper.toDTOList(service.getAllByUser(request));
        return ResponseEntity.ok(petEventDTOList);
    }

    @GetMapping("/by-date/{date}")
    public ResponseEntity<List<PetEventDTO>> getAllByDate(@PathVariable LocalDate date, HttpServletRequest request) {
        List<PetEventDTO> petEventDTOList = mapper.toDTOList(service.getAllByUserAndDate(date, request));
        return ResponseEntity.ok(petEventDTOList);
    }

    @PostMapping("/create")
    public ResponseEntity<Long> create(@RequestBody PetEventDTO petEventDTO, HttpServletRequest request) {
        PetEvent petEvent = mapper.toEntity(petEventDTO);
        Long petEventId = service.create(petEvent, request);
        if (petEventId != null) {
            return ResponseEntity.ok(petEventId);
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/update")
    public ResponseEntity<Long> update(@RequestBody PetEventDTO petEventDTO) {
        PetEvent petEvent = mapper.toEntity(petEventDTO);
        Long petEventId = service.update(petEvent);
        if (petEventId != null) {
            return ResponseEntity.ok(petEventId);
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
