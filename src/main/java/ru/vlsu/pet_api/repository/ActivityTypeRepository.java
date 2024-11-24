package ru.vlsu.pet_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vlsu.pet_api.entity.ActivityType;

@Repository
public interface ActivityTypeRepository extends JpaRepository<ActivityType, Long> {
}
