package ru.vlsu.pet_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vlsu.pet_api.entity.Breed;

import java.util.List;

@Repository
public interface BreedRepository extends JpaRepository<Breed, Long> {
    List<Breed> findAllBySpecies_Id(Long id);
}
