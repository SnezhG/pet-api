package ru.vlsu.pet_api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vlsu.pet_api.entity.ActivityType;
import ru.vlsu.pet_api.entity.Breed;
import ru.vlsu.pet_api.entity.EventType;
import ru.vlsu.pet_api.entity.Species;
import ru.vlsu.pet_api.repository.ActivityTypeRepository;
import ru.vlsu.pet_api.repository.BreedRepository;
import ru.vlsu.pet_api.repository.EventTypeRepository;
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
    private EventTypeRepository eventTypeRepository;
    @Autowired
    private ActivityTypeRepository activityTypeRepository;

    @Override
    public List<Breed> getAllBreeds() {
        return breedRepository.findAll();
    }

    @Override
    public List<ActivityType> getAllActivityTypes() {
        return activityTypeRepository.findAll();
    }

    @Override
    public List<EventType> getAllEventTypes() {
        return eventTypeRepository.findAll();
    }

    @Override
    public List<Species> getAllSpecies() {
        return speciesRepository.findAll();
    }
}
