package ru.vlsu.pet_api.service;

import ru.vlsu.pet_api.entity.Pet;

import java.util.List;

public interface PetService {
    Pet getById(Long id);

    List<Pet> getAllByUser(Long userId);

    Pet create(Pet pet);

    Pet update(Pet newPet);

    void delete(Long petId);
}
