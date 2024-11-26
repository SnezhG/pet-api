package ru.vlsu.pet_api.mapper;

import org.mapstruct.Mapper;
import ru.vlsu.pet_api.dto.ActivityTypeDTO;
import ru.vlsu.pet_api.dto.BreedDTO;
import ru.vlsu.pet_api.dto.EventTypeDTO;
import ru.vlsu.pet_api.dto.SpeciesDTO;
import ru.vlsu.pet_api.entity.ActivityType;
import ru.vlsu.pet_api.entity.Breed;
import ru.vlsu.pet_api.entity.EventType;
import ru.vlsu.pet_api.entity.Species;

import java.util.List;

@Mapper
public interface DictionaryMapper {
    List<BreedDTO> breedListToBreedDTOList(List<Breed> breeds);

    List<SpeciesDTO> speciesListToSpeciesDTOList(List<Species> species);

    List<ActivityTypeDTO> activityTypeListToActivityTypeDTOList(List<ActivityType> activityTypes);

    List<EventTypeDTO> eventTypeListToEventTypeDTOList(List<EventType> eventTypes);
}
