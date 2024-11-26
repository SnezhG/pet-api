package ru.vlsu.pet_api.mapper;

import org.mapstruct.Mapper;
import ru.vlsu.pet_api.dto.ActivityDTO;
import ru.vlsu.pet_api.entity.Activity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ActivityMapper {
    ActivityDTO toDTO(Activity activity);

    Activity toEntity(ActivityDTO activityDTO);

    List<ActivityDTO> toDTOList(List<Activity> activities);
}
