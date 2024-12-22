package ru.vlsu.pet_api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vlsu.pet_api.entity.Pet;
import ru.vlsu.pet_api.repository.PetRepository;
import ru.vlsu.pet_api.service.PetFileServiceImpl;
import ru.vlsu.pet_api.service.PetService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class PetServiceImpl implements PetService {
    @Autowired
    private PetRepository baseRepository;
    @Autowired
    private PetFileServiceImpl petFileService;

    @Override
    public Pet getById(Long id) throws IOException {
        Optional<Pet> pet = baseRepository.findById(id);
        if (pet.isPresent()) {
            Pet peresentPet = pet.get();
            if (peresentPet.getPhoto() != null) {
                String petPhoto = petFileService.downloadFile(peresentPet.getPhoto());
                peresentPet.setPhoto(petPhoto);
            }
            return peresentPet;
        }
        return null;
    }

    @Override
    public List<Pet> getAllByUser(Long userId) throws IOException {
        List<Pet> pets = baseRepository.findAllByUser_Id(userId);
        for (Pet pet : pets) {
            if (pet.getPhoto() != null) {
                String petPhoto = petFileService.downloadFile(pet.getPhoto());
                pet.setPhoto(petPhoto);
            }
        }
        return pets;
    }

    @Override
    public Pet create(Pet pet) throws IOException {
        String photoUri = petFileService.uploadFile(pet.getPhoto(), pet.getUser().getId().toString());
        pet.setPhoto(photoUri);
        return baseRepository.save(pet);
    }

    @Override
    public Pet update(Pet newPet) throws IOException {
        Optional<Pet> oldPet = baseRepository.findById(newPet.getId());
        if (oldPet.isPresent()) {
            Pet pet = oldPet.get();
            pet.setHealth(newPet.getHealth());
            pet.setPetEvents(newPet.getPetEvents());
            pet.setBirthDate(newPet.getBirthDate());
            pet.setSpecies(newPet.getSpecies());
            pet.setBreed(newPet.getBreed());
            pet.setSex(newPet.getSex());
            pet.setName(newPet.getName());
            pet.setWeight(newPet.getWeight());
            String photoUri = petFileService.uploadFile(newPet.getPhoto(), pet.getUser().getId().toString());
            pet.setPhoto(photoUri);
            return baseRepository.save(pet);
        }
        return null;
    }

    @Override
    public void delete(Long petId) throws IOException {
        Pet pet = getById(petId);
        baseRepository.delete(pet);
    }
}
