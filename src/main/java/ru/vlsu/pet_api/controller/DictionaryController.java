package ru.vlsu.pet_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<BreedDTO>> getAllBreedsBySpecies(@PathVariable Long id) {
        List<BreedDTO> breedDTOList = mapper.breedListToBreedDTOList(service.getAllBreedsBySpecies(id));
        return ResponseEntity.ok(breedDTOList);
    }

    @GetMapping("/all-species")
    public ResponseEntity<List<SpeciesDTO>> getAllSpecies() {
        List<SpeciesDTO> speciesDTOList = mapper.speciesListToSpeciesDTOList(service.getAllSpecies());
        return ResponseEntity.ok(speciesDTOList);
    }

    @GetMapping("/all-event-types")
    public ResponseEntity<List<PetEventTypeDTO>> getAllEventTypes() {
        List<PetEventTypeDTO> petEventTypeDTOList = mapper.eventTypeListToEventTypeDTOList(service.getAllEventTypes());
        return ResponseEntity.ok(petEventTypeDTOList);
    }

    @GetMapping("/all-sexes")
    public ResponseEntity<List<SexDTO>> getAllSexes() {
        List<SexDTO> sexDTOList = mapper.sexListTosexDTOList(service.getAllSexes());
        return ResponseEntity.ok(sexDTOList);
    }
}
