package ru.vlsu.pet_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vlsu.pet_api.entity.Pet;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
}
