package ru.vlsu.pet_api.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vlsu.pet_api.entity.Pet;
import ru.vlsu.pet_api.entity.PetUser;
import ru.vlsu.pet_api.jwt.JwtService;
import ru.vlsu.pet_api.repository.PetRepository;
import ru.vlsu.pet_api.repository.PetUserRepository;
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
    @Autowired
    private PetUserRepository petUserRepository;
    @Autowired
    private JwtService jwtService;

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
    public List<Pet> getAllByUser(HttpServletRequest request) throws IOException {
        String petUserEmail = jwtService.extractUsername(request.getHeader("Authorization").substring(7));
        Optional<PetUser> petUser = petUserRepository.findByEmail(petUserEmail);
        if (petUser.isPresent()) {
            List<Pet> pets = baseRepository.findAllByUser_Id(petUser.get().getId());
            for (Pet pet : pets) {
                if (pet.getPhoto() != null) {
                    String petPhoto = petFileService.downloadFile(pet.getPhoto());
                    pet.setPhoto(petPhoto);
                }
            }
            return pets;
        }
        return null;
    }

    @Override
    public Long create(Pet pet, HttpServletRequest request) throws IOException {
        String petUserEmail = jwtService.extractUsername(request.getHeader("Authorization").substring(7));
        Optional<PetUser> petUser = petUserRepository.findByEmail(petUserEmail);
        if (petUser.isPresent()) {
            PetUser user = petUser.get();
            pet.setUser(user);
            String photoUri = petFileService.uploadFile(pet.getPhoto(), user.getId().toString());
            pet.setPhoto(photoUri);
        }
        Pet savedPet = baseRepository.save(pet);
        return savedPet.getId();
    }

    @Override
    public Long update(Pet newPet) throws IOException {
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
            Pet savedPet = baseRepository.save(pet);
            return savedPet.getId();
        }
        return null;
    }

    @Override
    public void delete(Long petId) throws IOException {
        Pet pet = getById(petId);
        baseRepository.delete(pet);
    }
}
