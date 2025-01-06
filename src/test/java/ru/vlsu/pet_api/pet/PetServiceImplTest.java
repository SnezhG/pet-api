package ru.vlsu.pet_api.pet;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.vlsu.pet_api.entity.Pet;
import ru.vlsu.pet_api.entity.PetUser;
import ru.vlsu.pet_api.jwt.JwtService;
import ru.vlsu.pet_api.repository.PetRepository;
import ru.vlsu.pet_api.repository.PetUserRepository;
import ru.vlsu.pet_api.service.impl.PetFileServiceImpl;
import ru.vlsu.pet_api.service.impl.PetServiceImpl;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PetServiceImplTest {

    @Mock
    private PetRepository petRepository;

    @Mock
    private PetFileServiceImpl petFileService;

    @Mock
    private PetUserRepository petUserRepository;

    @Mock
    private JwtService jwtService;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private PetServiceImpl petService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetById_WithPhoto() throws IOException {
        // Arrange
        Pet pet = new Pet();
        pet.setId(1L);
        pet.setPhoto("photo-uri");
        when(petRepository.findById(1L)).thenReturn(Optional.of(pet));
        when(petFileService.downloadFile("photo-uri")).thenReturn("photo-content");

        // Act
        Pet result = petService.getById(1L);

        // Assert
        assertNotNull(result);
        assertEquals("photo-content", result.getPhoto());
        verify(petFileService).downloadFile("photo-uri");
    }

    @Test
    void testGetById_NotFound() throws IOException {
        // Arrange
        when(petRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        Pet result = petService.getById(1L);

        // Assert
        assertNull(result);
    }

    @Test
    void testGetAllByUser_WithPhotos() throws IOException {
        // Arrange
        PetUser user = new PetUser();
        user.setId(1L);
        when(jwtService.extractUsername(anyString())).thenReturn("test@example.com");
        when(request.getHeader("Authorization")).thenReturn("Bearer token");
        when(petUserRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));

        Pet pet1 = new Pet();
        pet1.setPhoto("photo1");
        Pet pet2 = new Pet();
        pet2.setPhoto("photo2");
        List<Pet> pets = Arrays.asList(pet1, pet2);
        when(petRepository.findAllByUser_Id(1L)).thenReturn(pets);
        when(petFileService.downloadFile("photo1")).thenReturn("content1");
        when(petFileService.downloadFile("photo2")).thenReturn("content2");

        // Act
        List<Pet> result = petService.getAllByUser(request);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("content1", result.get(0).getPhoto());
        assertEquals("content2", result.get(1).getPhoto());
    }

    @Test
    void testCreate() throws IOException {
        // Arrange
        Pet pet = new Pet();
        pet.setPhoto("photo-content");
        PetUser user = new PetUser();
        user.setId(1L);
        when(jwtService.extractUsername(anyString())).thenReturn("test@example.com");
        when(request.getHeader("Authorization")).thenReturn("Bearer token");
        when(petUserRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(petFileService.uploadFile("photo-content", "1")).thenReturn("photo-uri");
        when(petRepository.save(any(Pet.class))).thenAnswer(invocation -> {
            Pet savedPet = invocation.getArgument(0);
            savedPet.setId(1L);
            return savedPet;
        });

        // Act
        Long result = petService.create(pet, request);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result);
        assertEquals("photo-uri", pet.getPhoto());
    }

    @Test
    void testUpdate() throws IOException {
        // Arrange
        Pet oldPet = new Pet();
        oldPet.setId(1L);
        PetUser user = new PetUser();
        user.setId(1L);
        oldPet.setUser(user);
        when(petRepository.findById(1L)).thenReturn(Optional.of(oldPet));
        when(petFileService.uploadFile(anyString(), anyString())).thenReturn("new-photo-uri");
        when(petRepository.save(any(Pet.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Pet newPet = new Pet();
        newPet.setId(1L);
        newPet.setPhoto("new-photo-content");

        // Act
        Long result = petService.update(newPet);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result);
        assertEquals("new-photo-uri", oldPet.getPhoto());
    }

    @Test
    void testDelete() throws IOException {
        // Arrange
        Pet pet = new Pet();
        pet.setId(1L);
        when(petRepository.findById(1L)).thenReturn(Optional.of(pet));

        // Act
        petService.delete(1L);

        // Assert
        verify(petRepository).delete(pet);
    }
}
