package ru.vlsu.pet_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vlsu.pet_api.dto.BreedDTO;
import ru.vlsu.pet_api.dto.PetEventTypeDTO;
import ru.vlsu.pet_api.dto.SexDTO;
import ru.vlsu.pet_api.dto.SpeciesDTO;
import ru.vlsu.pet_api.mapper.DictionaryMapper;
import ru.vlsu.pet_api.service.DictionaryService;

import java.util.List;

@RestController
@RequestMapping("/api/dictionary")
public class DictionaryController {
    @Autowired
    private DictionaryService service;
    @Autowired
    private DictionaryMapper mapper;

    @GetMapping("/all-breeds-by-species/{id}")
    public List<BreedDTO> getAllBreedsBySpecies(@PathVariable Long id) {
        return mapper.breedListToBreedDTOList(service.getAllBreedsBySpecies(id));
    }

    @GetMapping("/all-species")
    public List<SpeciesDTO> getAllSpecies() {
        return mapper.speciesListToSpeciesDTOList(service.getAllSpecies());
    }

    @GetMapping("/all-event-types")
    public List<PetEventTypeDTO> getAllEventTypes() {
        return mapper.eventTypeListToEventTypeDTOList(service.getAllEventTypes());
    }

    @GetMapping("/all-sexes")
    public List<SexDTO> getAllSexes() {
        return mapper.sexListTosexDTOList(service.getAllSexes());
    }
}
