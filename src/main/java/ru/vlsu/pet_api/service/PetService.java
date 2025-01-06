package ru.vlsu.pet_api.service;

import jakarta.servlet.http.HttpServletRequest;
import ru.vlsu.pet_api.entity.Pet;

import java.io.IOException;
import java.util.List;

public interface PetService {
    Pet getById(Long id) throws IOException;

    List<Pet> getAllByUser(HttpServletRequest request) throws IOException;

    Long create(Pet pet, HttpServletRequest request) throws IOException;

    Long update(Pet newPet) throws IOException;

    void delete(Long petId) throws IOException;
}
