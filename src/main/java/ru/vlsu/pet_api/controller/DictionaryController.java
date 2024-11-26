package ru.vlsu.pet_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vlsu.pet_api.dto.ActivityTypeDTO;
import ru.vlsu.pet_api.dto.BreedDTO;
import ru.vlsu.pet_api.dto.EventTypeDTO;
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

    @GetMapping("/all-breeds")
    public List<BreedDTO> getAllBreeds() {
        return mapper.breedListToBreedDTOList(service.getAllBreeds());
    }

    @GetMapping("/all-species")
    public List<SpeciesDTO> getAllSpecies() {
        return mapper.speciesListToSpeciesDTOList(service.getAllSpecies());
    }

    @GetMapping("/all-event-types")
    public List<EventTypeDTO> getAllEventTypes() {
        return mapper.eventTypeListToEventTypeDTOList(service.getAllEventTypes());
    }

    @GetMapping("/all-activity-types")
    public List<ActivityTypeDTO> getAllActivityTypes() {
        return mapper.activityTypeListToActivityTypeDTOList(service.getAllActivityTypes());
    }
}
