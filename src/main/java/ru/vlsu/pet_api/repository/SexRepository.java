package ru.vlsu.pet_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vlsu.pet_api.entity.Sex;

@Repository
public interface SexRepository extends JpaRepository<Sex, Long> {
}
