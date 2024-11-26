package ru.vlsu.pet_api.mapper;

import org.mapstruct.Mapper;
import ru.vlsu.pet_api.dto.ActivityDTO;
import ru.vlsu.pet_api.entity.Activity;

import java.util.List;

@Mapper
public interface ActivityMapper {
    ActivityDTO acticityToActivityDTO(Activity activity);

    List<ActivityDTO> activityListToActivityDTO(List<Activity> activities);
}
