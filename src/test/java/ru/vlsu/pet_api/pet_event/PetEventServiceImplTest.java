package ru.vlsu.pet_api.pet_event;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.vlsu.pet_api.entity.PetEvent;
import ru.vlsu.pet_api.entity.PetEventsNotif;
import ru.vlsu.pet_api.entity.PetUser;
import ru.vlsu.pet_api.jwt.JwtService;
import ru.vlsu.pet_api.repository.PetEventRepository;
import ru.vlsu.pet_api.repository.PetUserRepository;
import ru.vlsu.pet_api.service.PetEventsNotifService;
import ru.vlsu.pet_api.service.impl.PetEventServiceImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PetEventServiceImplTest {

    @Mock
    private PetEventRepository petEventRepository;

    @Mock
    private PetEventsNotifService petEventsNotifService;

    @Mock
    private PetUserRepository petUserRepository;

    @Mock
    private JwtService jwtService;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private PetEventServiceImpl petEventService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetById() {
        // Arrange
        PetEvent petEvent = new PetEvent();
        petEvent.setId(1L);
        when(petEventRepository.findById(1L)).thenReturn(Optional.of(petEvent));

        // Act
        PetEvent result = petEventService.getById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testGetById_NotFound() {
        // Arrange
        when(petEventRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        PetEvent result = petEventService.getById(1L);

        // Assert
        assertNull(result);
    }

    @Test
    void testGetAllByUser() {
        // Arrange
        PetUser user = new PetUser();
        user.setId(1L);
        when(jwtService.extractUsername(anyString())).thenReturn("test@example.com");
        when(request.getHeader("Authorization")).thenReturn("Bearer token");
        when(petUserRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));

        PetEvent event1 = new PetEvent();
        event1.setId(1L);
        PetEvent event2 = new PetEvent();
        event2.setId(2L);
        when(petEventRepository.findAllByUser_Id(1L)).thenReturn(Arrays.asList(event1, event2));

        // Act
        List<PetEvent> result = petEventService.getAllByUser(request);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void testGetAllOnWeekByUser() {
        // Arrange
        PetUser user = new PetUser();
        user.setId(1L);
        when(jwtService.extractUsername(anyString())).thenReturn("test@example.com");
        when(request.getHeader("Authorization")).thenReturn("Bearer token");
        when(petUserRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));

        LocalDateTime startDateTime = LocalDate.now().atStartOfDay();
        LocalDateTime endDateTime = LocalDate.now().plusDays(6).atTime(23, 59, 59);

        PetEvent event1 = new PetEvent();
        event1.setId(1L);
        PetEvent event2 = new PetEvent();
        event2.setId(2L);
        when(petEventRepository.findAllByUser_IdAndDateBetween(1L, startDateTime, endDateTime))
                .thenReturn(Arrays.asList(event1, event2));

        // Act
        List<PetEvent> result = petEventService.getAllOnWeekByUser(request);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void testCreate() {
        // Arrange
        PetUser user = new PetUser();
        user.setId(1L);
        PetEvent petEvent = new PetEvent();
        petEvent.setNotifEnabled(true);
        PetEventsNotif notif = new PetEventsNotif();
        when(jwtService.extractUsername(anyString())).thenReturn("test@example.com");
        when(request.getHeader("Authorization")).thenReturn("Bearer token");
        when(petUserRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(petEventsNotifService.createPetEventNotif(petEvent)).thenReturn(notif);
        when(petEventRepository.save(any(PetEvent.class))).thenAnswer(invocation -> {
            PetEvent savedEvent = invocation.getArgument(0);
            savedEvent.setId(1L);
            return savedEvent;
        });

        // Act
        Long result = petEventService.create(petEvent, request);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result);
        verify(petEventsNotifService).createPetEventNotif(petEvent);
    }

    @Test
    void testUpdate() {
        // Arrange
        PetEvent oldEvent = new PetEvent();
        oldEvent.setId(1L);
        oldEvent.setNotifEnabled(true);

        PetEvent newEvent = new PetEvent();
        newEvent.setId(1L);
        newEvent.setNotifEnabled(false);

        when(petEventRepository.findById(1L)).thenReturn(Optional.of(oldEvent));
        when(petEventRepository.save(any(PetEvent.class))).thenAnswer(invocation -> invocation.getArgument(0)); // Возвращаем сохраненный объект

        // Act
        Long result = petEventService.update(newEvent);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result);
        verify(petEventsNotifService).cancelPetEventsNotif(oldEvent);
        verify(petEventRepository).save(oldEvent);
    }


    @Test
    void testDelete() {
        // Arrange
        PetEvent petEvent = new PetEvent();
        petEvent.setId(1L);
        when(petEventRepository.findById(1L)).thenReturn(Optional.of(petEvent));

        // Act
        petEventService.delete(1L);

        // Assert
        verify(petEventRepository).delete(petEvent);
    }
}

