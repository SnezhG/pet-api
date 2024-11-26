package ru.vlsu.pet_api.service;

import ru.vlsu.pet_api.entity.Pet;

public interface PetService {
    Pet getById(Long id);
}
