package ru.vlsu.pet_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.vlsu.pet_api.entity.PetEvent;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PetEventRepository extends JpaRepository<PetEvent, Long> {
    @Query("SELECT pe FROM PetEvent pe WHERE pe.user.id = :userId AND pe.date BETWEEN :startDate AND :endDate")
    List<PetEvent> findAllByUser_IdAndDateBetween(@Param("userId") Long userId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    List<PetEvent> findAllByUser_Id(Long id);

    List<PetEvent> findAllByUser_IdAndDate(Long id, LocalDate date);
}
