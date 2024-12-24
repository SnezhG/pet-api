package ru.vlsu.pet_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vlsu.pet_api.entity.PetUser;

import java.util.Optional;

@Repository
public interface PetUserRepository extends JpaRepository<PetUser, Long> {
    Optional<PetUser> findByEmail(String email);
}
