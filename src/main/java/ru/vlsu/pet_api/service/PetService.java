package ru.vlsu.pet_api.service;

import ru.vlsu.pet_api.entity.Pet;

import java.io.IOException;
import java.util.List;

public interface PetService {
    Pet getById(Long id) throws IOException;

    List<Pet> getAllByUser(Long userId) throws IOException;

    Pet create(Pet pet) throws IOException;

    Pet update(Pet newPet) throws IOException;

    void delete(Long petId) throws IOException;
}
