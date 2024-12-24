package ru.vlsu.pet_api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vlsu.pet_api.entity.PetEvent;
import ru.vlsu.pet_api.entity.PetEventType;
import ru.vlsu.pet_api.entity.PetEventsNotif;
import ru.vlsu.pet_api.repository.PetEventTypeRepository;
import ru.vlsu.pet_api.repository.PetEventsNotifRepository;
import ru.vlsu.pet_api.service.PetEventsNotifService;

import java.util.Optional;

@Service
public class PetEventsNotifServiceImpl implements PetEventsNotifService {
    @Autowired
    private PetEventsNotifRepository repository;
    @Autowired
    private PetEventTypeRepository petEventTypeRepository;

    @Override
    public void cancelPetEventsNotif(PetEvent petEvent) {
        PetEventsNotif petEventsNotif = getPetEventsNotifById(petEvent.getPetEventsNotif().getId());
        petEventsNotif.setNotify(false);
        petEvent.setPetEventsNotif(petEventsNotif);
    }

    @Override
    public void updatePetEventNotif(PetEvent oldPetEvent, PetEvent newPetEvent) {
        PetEventsNotif oldPetEventNotif = getPetEventsNotifById(oldPetEvent.getPetEventsNotif().getId());
        Optional<PetEventType> petEventType = petEventTypeRepository.findById(newPetEvent.getType().getId());
        petEventType.ifPresent(eventType -> oldPetEventNotif.setTitle(eventType.getName()));
        oldPetEventNotif.setDescription(newPetEvent.getDescription());
        oldPetEventNotif.setTime(newPetEvent.getDate());
        oldPetEventNotif.setUserToken(newPetEvent.getUserToken());
        oldPetEventNotif.setNotify(true);
    }

    @Override
    public PetEventsNotif createPetEventNotif(PetEvent petEvent) {
        PetEventsNotif petEventsNotif = new PetEventsNotif();
        petEventsNotif.setPetEvent(petEvent);
        Optional<PetEventType> petEventType = petEventTypeRepository.findById(petEvent.getType().getId());
        petEventType.ifPresent(eventType -> petEventsNotif.setTitle(eventType.getName()));
        petEventsNotif.setDescription(petEvent.getDescription());
        petEventsNotif.setTime(petEvent.getDate());
        petEventsNotif.setUserToken(petEvent.getUserToken());
        petEventsNotif.setNotify(true);
        return petEventsNotif;
    }

    @Override
    public PetEventsNotif getPetEventsNotifById(Long id) {
        Optional<PetEventsNotif> petEventsNotif = repository.findById(id);
        return petEventsNotif.orElse(null);
    }
}
