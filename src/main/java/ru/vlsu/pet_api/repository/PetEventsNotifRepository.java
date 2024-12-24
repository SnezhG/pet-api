package ru.vlsu.pet_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vlsu.pet_api.entity.PetEventsNotif;

@Repository
public interface PetEventsNotifRepository extends JpaRepository<PetEventsNotif, Long> {
}
