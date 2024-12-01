package ru.vlsu.pet_api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vlsu.pet_api.entity.Breed;
import ru.vlsu.pet_api.entity.PetEventType;
import ru.vlsu.pet_api.entity.Sex;
import ru.vlsu.pet_api.entity.Species;
import ru.vlsu.pet_api.repository.BreedRepository;
import ru.vlsu.pet_api.repository.PetEventTypeRepository;
import ru.vlsu.pet_api.repository.SexRepository;
import ru.vlsu.pet_api.repository.SpeciesRepository;
import ru.vlsu.pet_api.service.DictionaryService;

import java.util.List;

@Service
public class DictionaryServiceImpl implements DictionaryService {
    @Autowired
    private BreedRepository breedRepository;
    @Autowired
    private SpeciesRepository speciesRepository;
    @Autowired
    private PetEventTypeRepository petEventTypeRepository;
    @Autowired
    private SexRepository sexRepository;

    @Override
    public List<Breed> getAllBreedsBySpecies(Long id) {
        return breedRepository.findAllBySpecies_Id(id);
    }

    @Override
    public List<PetEventType> getAllEventTypes() {
        return petEventTypeRepository.findAll();
    }

    @Override
    public List<Species> getAllSpecies() {
        return speciesRepository.findAll();
    }

    @Override
    public List<Sex> getAllSexes() {
        return sexRepository.findAll();
    }
}
