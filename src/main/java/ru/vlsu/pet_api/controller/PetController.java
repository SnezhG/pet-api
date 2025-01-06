package ru.vlsu.pet_api.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vlsu.pet_api.dto.PetDTO;
import ru.vlsu.pet_api.entity.Pet;
import ru.vlsu.pet_api.mapper.PetMapper;
import ru.vlsu.pet_api.service.PetService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/pet")
public class PetController {
    @Autowired
    private PetService service;
    @Autowired
    private PetMapper mapper;

    @GetMapping("/{id}")
    public ResponseEntity<PetDTO> getById(@PathVariable Long id) throws IOException {
        PetDTO petDTO = mapper.petToPetDTO(service.getById(id));
        return ResponseEntity.ok(petDTO);
    }

    @GetMapping("/by-user")
    public ResponseEntity<List<PetDTO>> getAllByUser(HttpServletRequest request) throws IOException {
        List<PetDTO> petDTOList = mapper.petListToPetDTOList(service.getAllByUser(request));
        return ResponseEntity.ok(petDTOList);
    }

    @PostMapping("/create")
    public ResponseEntity<Long> create(@RequestBody PetDTO petDTO, HttpServletRequest request) throws IOException {
        Pet pet = mapper.petDTOToPet(petDTO);
        Long petId = service.create(pet, request);
        if (petId != null) {
            return ResponseEntity.ok(petId);
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/update")
    public ResponseEntity<Long> update(@RequestBody PetDTO petDTO) throws IOException {
        Pet pet = mapper.petDTOToPet(petDTO);
        Long petId = service.update(pet);
        if (petId != null) {
            return ResponseEntity.ok(petId);
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws IOException {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
