package ru.vlsu.pet_api.mapper;

import org.mapstruct.Mapper;
import ru.vlsu.pet_api.dto.PetEventDTO;
import ru.vlsu.pet_api.entity.PetEvent;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PetEventMapper {
    PetEventDTO toDTO(PetEvent petEvent);

    PetEvent toEntity(PetEventDTO petEventDTO);

    List<PetEventDTO> toDTOList(List<PetEvent> petEvents);
}
