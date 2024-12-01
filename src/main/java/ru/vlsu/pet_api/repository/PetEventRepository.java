package ru.vlsu.pet_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vlsu.pet_api.entity.PetEvent;

import java.util.List;

@Repository
public interface PetEventRepository extends JpaRepository<PetEvent, Long> {
    List<PetEvent> findAllByUser_Id(Long id);
}
