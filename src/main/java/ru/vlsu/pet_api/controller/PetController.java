package ru.vlsu.pet_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vlsu.pet_api.dto.PetDTO;
import ru.vlsu.pet_api.mapper.PetMapper;
import ru.vlsu.pet_api.service.PetService;

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

//    @GetMapping
//    public List<PetDTO> getAll() {
//        return mapper.petListToPetDTOList(service)
//    }
}
