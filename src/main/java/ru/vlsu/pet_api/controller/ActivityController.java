package ru.vlsu.pet_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vlsu.pet_api.dto.ActivityDTO;
import ru.vlsu.pet_api.entity.Activity;
import ru.vlsu.pet_api.mapper.ActivityMapper;
import ru.vlsu.pet_api.service.ActivityService;

import java.util.List;

@RestController
@RequestMapping("/api/activity")
public class ActivityController {
    @Autowired
    private ActivityService service;
    @Autowired
    private ActivityMapper mapper;

    @GetMapping("/{id}")
    public ActivityDTO getById(@PathVariable Long id) {
        return mapper.toDTO(service.getById(id));
    }

    @GetMapping("/by-user/{id}")
    public List<ActivityDTO> getAllByUser(@PathVariable Long id) {
        return mapper.toDTOList(service.getAllByUser(id));
    }

    @PostMapping("/add")
    public Activity create(@RequestBody ActivityDTO activityDTO) {
        Activity activity = mapper.toEntity(activityDTO);
        return service.create(activity);
    }

    @PostMapping("/edit")
    public Activity update(@RequestBody ActivityDTO activityDTO) {
        Activity activity = mapper.toEntity(activityDTO);
        return service.update(activity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
