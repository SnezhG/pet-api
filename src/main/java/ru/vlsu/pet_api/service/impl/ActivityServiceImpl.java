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
    private ActivityRepository repository;

    @Override
    public Activity getById(Long id) {
        Optional<Activity> activity = repository.findById(id);
        return activity.orElse(null);
    }

    @Override
    public List<Activity> getAllByUser(Long id) {
        return repository.findAllByUser_Id(id);
    }

    @Override
    public Activity create(Activity activity) {
        return repository.save(activity);
    }

    @Override
    public Activity update(Activity newActivity) {
        Activity oldActivity = getById(newActivity.getId());
        oldActivity.setDateTime(newActivity.getDateTime());
        oldActivity.setDocument(newActivity.getDocument());
        oldActivity.setPet(newActivity.getPet());
        oldActivity.setPhoto(newActivity.getPhoto());
        oldActivity.setType(newActivity.getType());
        oldActivity.setDescription(newActivity.getDescription());
        return repository.save(oldActivity);
    }

    @Override
    public void delete(Long id) {
        Activity activity = getById(id);
        repository.delete(activity);
    }
}
