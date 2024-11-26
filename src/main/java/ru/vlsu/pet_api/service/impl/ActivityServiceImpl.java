package ru.vlsu.pet_api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vlsu.pet_api.entity.Activity;
import ru.vlsu.pet_api.repository.ActivityRepository;
import ru.vlsu.pet_api.service.ActivityService;

import java.util.List;
import java.util.Optional;

@Service
public class ActivityServiceImpl implements ActivityService {
    @Autowired
    private ActivityRepository baseRepository;

    @Override
    public Activity getById(Long id) {
        Optional<Activity> activity = baseRepository.findById(id);
        return activity.orElse(null);
    }

    @Override
    public List<Activity> getAll() {
        return baseRepository.findAll();
    }
}
