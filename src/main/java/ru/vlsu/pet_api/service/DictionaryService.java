package ru.vlsu.pet_api.service;

import ru.vlsu.pet_api.entity.ActivityType;
import ru.vlsu.pet_api.entity.Breed;
import ru.vlsu.pet_api.entity.EventType;
import ru.vlsu.pet_api.entity.Species;

import java.util.List;

public interface DictionaryService {
    List<Breed> getAllBreedsBySpecies(Long id);

    List<ActivityType> getAllActivityTypes();

    List<EventType> getAllEventTypes();

    List<Species> getAllSpecies();
}
