package ru.vlsu.pet_api.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vlsu.pet_api.entity.PetEvent;
import ru.vlsu.pet_api.entity.PetEventsNotif;
import ru.vlsu.pet_api.repository.PetEventRepository;
import ru.vlsu.pet_api.service.PetEventService;
import ru.vlsu.pet_api.service.PetEventsNotifService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class PetEventServiceImpl implements PetEventService {
    @Autowired
    private PetEventRepository repository;
    @Autowired
    private PetEventsNotifService petEventsNotifService;

    @Override
    public PetEvent getById(Long id) {
        Optional<PetEvent> event = repository.findById(id);
        return event.orElse(null);
    }

    @Override
    public List<PetEvent> getAllByUser(Long id) {
        return repository.findAllByUser_Id(id);
    }

    @Override
    public List<PetEvent> getAllOnWeekByUser(Long id) {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(6);
        return repository.findAllByUser_IdAndDateBetween(id, startDate, endDate);
    }

    @Override
    public List<PetEvent> getAllByUserAndDate(Long id, LocalDate date) {
        return repository.findAllByUser_IdAndDate(id, date);
    }

    @Override
    public PetEvent create(PetEvent petEvent) {
        if (petEvent.isNotifEnabled()) {
            PetEventsNotif petEventsNotif = petEventsNotifService.createPetEventNotif(petEvent);
            petEvent.setPetEventsNotif(petEventsNotif);
        }
        return repository.save(petEvent);
    }

    @Override
    public PetEvent update(PetEvent newPetEvent) {
        PetEvent oldPetEvent = getById(newPetEvent.getId());
        oldPetEvent.setDate(newPetEvent.getDate());
        oldPetEvent.setType(newPetEvent.getType());
        oldPetEvent.setPet(newPetEvent.getPet());
        oldPetEvent.setDescription(newPetEvent.getDescription());
        if (newPetEvent.isNotifEnabled() && oldPetEvent.isNotifEnabled()) {
            petEventsNotifService.updatePetEventNotif(oldPetEvent, newPetEvent);
        } else if (!newPetEvent.isNotifEnabled() && oldPetEvent.isNotifEnabled()) {
            petEventsNotifService.cancelPetEventsNotif(oldPetEvent);
        } else if (newPetEvent.isNotifEnabled() && oldPetEvent.getPetEventsNotif() == null) {
            PetEventsNotif petEventsNotif = petEventsNotifService.createPetEventNotif(newPetEvent);
            oldPetEvent.setPetEventsNotif(petEventsNotif);
        } else if (newPetEvent.isNotifEnabled()) {
            petEventsNotifService.updatePetEventNotif(oldPetEvent, newPetEvent);
        }
        return repository.save(oldPetEvent);
    }

    @Override
    public void delete(Long id) {
        PetEvent petEvent = getById(id);
        repository.delete(petEvent);
    }

}
