package ru.vlsu.pet_api.service;

import ru.vlsu.pet_api.entity.PetEvent;

import java.util.List;

public interface PetEventService {
    PetEvent getById(Long id);

    List<PetEvent> getAllByUser(Long id);

    PetEvent create(PetEvent petEvent);

    PetEvent update(PetEvent newPetEvent);

    void delete(Long id);
}
