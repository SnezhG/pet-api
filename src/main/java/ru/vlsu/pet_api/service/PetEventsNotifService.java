package ru.vlsu.pet_api.service;

import ru.vlsu.pet_api.entity.PetEvent;
import ru.vlsu.pet_api.entity.PetEventsNotif;

public interface PetEventsNotifService {
    PetEventsNotif getPetEventsNotifById(Long id);

    PetEventsNotif createPetEventNotif(PetEvent petEvent);

    void updatePetEventNotif(PetEvent oldPetEvent, PetEvent newPetEvent);

    void cancelPetEventsNotif(PetEvent petEvent);
}
