package ru.vlsu.pet_api.service;

import ru.vlsu.pet_api.entity.Activity;

import java.util.List;

public interface ActivityService {
    Activity getById(Long id);

    List<Activity> getAll();
}
