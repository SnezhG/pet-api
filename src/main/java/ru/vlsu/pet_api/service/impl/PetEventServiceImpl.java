package ru.vlsu.pet_api.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vlsu.pet_api.entity.PetEvent;
import ru.vlsu.pet_api.entity.PetEventsNotif;
import ru.vlsu.pet_api.entity.PetUser;
import ru.vlsu.pet_api.jwt.JwtService;
import ru.vlsu.pet_api.repository.PetEventRepository;
import ru.vlsu.pet_api.repository.PetUserRepository;
import ru.vlsu.pet_api.service.PetEventService;
import ru.vlsu.pet_api.service.PetEventsNotifService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class PetEventServiceImpl implements PetEventService {
    @Autowired
    private PetEventRepository repository;
    @Autowired
    private PetEventsNotifService petEventsNotifService;
    @Autowired
    private PetUserRepository petUserRepository;
    @Autowired
    private JwtService jwtService;

    @Override
    public PetEvent getById(Long id) {
        Optional<PetEvent> event = repository.findById(id);
        return event.orElse(null);
    }

    @Override
    public List<PetEvent> getAllByUser(HttpServletRequest request) {
        String petUserEmail = jwtService.extractUsername(request.getHeader("Authorization").substring(7));
        Optional<PetUser> petUser = petUserRepository.findByEmail(petUserEmail);
        if (petUser.isPresent()) {
            return repository.findAllByUser_Id(petUser.get().getId());
        }
        return null;
    }

    @Override
    public List<PetEvent> getAllOnWeekByUser(HttpServletRequest request) {
        String petUserEmail = jwtService.extractUsername(request.getHeader("Authorization").substring(7));
        Optional<PetUser> petUser = petUserRepository.findByEmail(petUserEmail);
        if (petUser.isPresent()) {
            // Получаем начало текущего дня (00:00:00)
            LocalDateTime startDateTime = LocalDate.now().atStartOfDay();

            // Получаем конец текущего дня через 6 дней (23:59:59)
            LocalDateTime endDateTime = LocalDate.now().plusDays(6).atTime(23, 59, 59);

            return repository.findAllByUser_IdAndDateBetween(petUser.get().getId(), startDateTime, endDateTime);
        }
        return null;
    }

    @Override
    public List<PetEvent> getAllByUserAndDate(LocalDate date, HttpServletRequest request) {
        String petUserEmail = jwtService.extractUsername(request.getHeader("Authorization").substring(7));
        Optional<PetUser> petUser = petUserRepository.findByEmail(petUserEmail);
        if (petUser.isPresent()) {
            LocalDateTime startDateTime = date.atStartOfDay();
            LocalDateTime endDateTime = date.atTime(23, 59, 59);
            return repository.findAllByUser_IdAndDateBetween(petUser.get().getId(), startDateTime, endDateTime);
        }
        return null;
    }

    @Override
    public Long create(PetEvent petEvent, HttpServletRequest request) {
        String petUserEmail = jwtService.extractUsername(request.getHeader("Authorization").substring(7));
        Optional<PetUser> petUser = petUserRepository.findByEmail(petUserEmail);
        if (petUser.isPresent()) {
            if (petEvent.isNotifEnabled()) {
                PetEventsNotif petEventsNotif = petEventsNotifService.createPetEventNotif(petEvent);
                petEvent.setPetEventsNotif(petEventsNotif);
            }
            petEvent.setUser(petUser.get());
            PetEvent petEventSaved = repository.save(petEvent);
            return petEventSaved.getId();
        }
        return null;
    }

    @Override
    public Long update(PetEvent newPetEvent) {
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
        oldPetEvent.setNotifEnabled(newPetEvent.isNotifEnabled());
        PetEvent petEventSaved = repository.save(oldPetEvent);
        return petEventSaved.getId();
    }

    @Override
    public void delete(Long id) {
        PetEvent petEvent = getById(id);
        repository.delete(petEvent);
    }

}
