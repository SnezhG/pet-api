package ru.vlsu.pet_api.mapper;

import org.mapstruct.Mapper;
import ru.vlsu.pet_api.dto.BreedDTO;
import ru.vlsu.pet_api.dto.PetEventTypeDTO;
import ru.vlsu.pet_api.dto.SexDTO;
import ru.vlsu.pet_api.dto.SpeciesDTO;
import ru.vlsu.pet_api.entity.Breed;
import ru.vlsu.pet_api.entity.PetEventType;
import ru.vlsu.pet_api.entity.Sex;
import ru.vlsu.pet_api.entity.Species;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DictionaryMapper {
    List<BreedDTO> breedListToBreedDTOList(List<Breed> breeds);

    List<SpeciesDTO> speciesListToSpeciesDTOList(List<Species> species);

    List<PetEventTypeDTO> eventTypeListToEventTypeDTOList(List<PetEventType> petEventTypes);

    List<SexDTO> sexListTosexDTOList(List<Sex> sexes);
}
