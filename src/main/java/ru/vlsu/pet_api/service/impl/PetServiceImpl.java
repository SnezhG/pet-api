package ru.vlsu.pet_api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vlsu.pet_api.entity.Pet;
import ru.vlsu.pet_api.repository.PetRepository;
import ru.vlsu.pet_api.service.PetService;

import java.util.List;
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

    @Override
    public List<Pet> getAllByUser(Long userId) {
        return baseRepository.findAllByUser_Id(userId);
    }

    @Override
    public Pet create(Pet pet) {
        return baseRepository.save(pet);
    }

    @Override
    public Pet update(Pet newPet) {
        Optional<Pet> oldPet = baseRepository.findById(newPet.getId());
        if (oldPet.isPresent()) {
            Pet pet = oldPet.get();
            pet.setActivities(newPet.getActivities());
            pet.setHealth(newPet.getHealth());
            pet.setEvents(newPet.getEvents());
            pet.setBirthDate(newPet.getBirthDate());
            pet.setSpecies(newPet.getSpecies());
            pet.setBreed(newPet.getBreed());
            pet.setName(newPet.getName());
            pet.setWeight(newPet.getWeight());
            pet.setPhoto(newPet.getPhoto());
            return baseRepository.save(pet);
        }
        return null;
    }

    @Override
    public void delete(Long petId) {
        Pet pet = getById(petId);
        baseRepository.delete(pet);
    }
}
