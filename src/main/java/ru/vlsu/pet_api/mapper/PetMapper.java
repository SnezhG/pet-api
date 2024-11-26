package ru.vlsu.pet_api.mapper;

import org.mapstruct.Mapper;
import ru.vlsu.pet_api.dto.PetDTO;
import ru.vlsu.pet_api.entity.Pet;

import java.util.List;

@Mapper
public interface PetMapper {
    PetDTO petToPetDTO(Pet pet);

    List<PetDTO> petListToPetDTOList(List<Pet> pets);
}
