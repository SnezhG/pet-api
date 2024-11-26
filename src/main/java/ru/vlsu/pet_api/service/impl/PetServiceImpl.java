package ru.vlsu.pet_api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vlsu.pet_api.entity.Pet;
import ru.vlsu.pet_api.repository.PetRepository;
import ru.vlsu.pet_api.service.PetService;

import java.util.Optional;

@Service
public class PetServiceImpl implements PetService {
    @Autowired
    private PetRepository baseRepository;

    @Override
    public Pet getById(Long id) {
        Optional<Pet> pet = baseRepository.findById(id);
        return pet.orElse(null);
    }
}
