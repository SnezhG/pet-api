package ru.vlsu.pet_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vlsu.pet_api.dto.PetDTO;
import ru.vlsu.pet_api.entity.Pet;
import ru.vlsu.pet_api.mapper.PetMapper;
import ru.vlsu.pet_api.service.PetService;

import java.util.List;

@RestController
@RequestMapping("/api/pet")
public class PetController {
    @Autowired
    private PetService service;
    @Autowired
    private PetMapper mapper;

    @GetMapping("/{id}")
    public PetDTO getById(@PathVariable Long id) {
        return mapper.petToPetDTO(service.getById(id));
    }

    @GetMapping("/by-user/{id}")
    public List<PetDTO> getAllByUser(@PathVariable Long id) {
        return mapper.petListToPetDTOList(service.getAllByUser(id));
    }

    @PostMapping("/add")
    public Pet create(@RequestBody PetDTO petDTO) {
        Pet pet = mapper.petDTOToPet(petDTO);
        return service.create(pet);
    }

    @PostMapping("/edit")
    public Pet update(@RequestBody PetDTO petDTO) {
        Pet pet = mapper.petDTOToPet(petDTO);
        return service.update(pet);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
