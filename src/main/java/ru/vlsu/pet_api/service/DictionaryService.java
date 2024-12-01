package ru.vlsu.pet_api.service;

import ru.vlsu.pet_api.entity.Breed;
import ru.vlsu.pet_api.entity.PetEventType;
import ru.vlsu.pet_api.entity.Sex;
import ru.vlsu.pet_api.entity.Species;

import java.util.List;

public interface DictionaryService {
    List<Breed> getAllBreedsBySpecies(Long id);

    List<PetEventType> getAllEventTypes();

    List<Species> getAllSpecies();

    List<Sex> getAllSexes();
}
