package ru.vlsu.pet_api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vlsu.pet_api.repository.PetRepository;
import ru.vlsu.pet_api.service.PetService;

@Service
public class PetServiceImpl extends PetService {
    @Autowired
    private PetRepository baseRepository;
}
