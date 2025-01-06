package ru.vlsu.pet_api.pet_event;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.vlsu.pet_api.entity.PetEvent;
import ru.vlsu.pet_api.entity.PetEventType;
import ru.vlsu.pet_api.entity.PetEventsNotif;
import ru.vlsu.pet_api.repository.PetEventTypeRepository;
import ru.vlsu.pet_api.repository.PetEventsNotifRepository;
import ru.vlsu.pet_api.service.impl.PetEventsNotifServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PetEventsNotifServiceImplTest {

    @Mock
    private PetEventsNotifRepository petEventsNotifRepository;

    @Mock
    private PetEventTypeRepository petEventTypeRepository;

    @InjectMocks
    private PetEventsNotifServiceImpl petEventsNotifService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCancelPetEventsNotif() {
        // Arrange
        PetEventsNotif notif = new PetEventsNotif();
        notif.setId(1L);
        notif.setNotify(true);

        PetEvent petEvent = new PetEvent();
        petEvent.setPetEventsNotif(notif);

        when(petEventsNotifRepository.findById(1L)).thenReturn(Optional.of(notif));

        // Act
        petEventsNotifService.cancelPetEventsNotif(petEvent);

        // Assert
        assertFalse(notif.isNotify());
        verify(petEventsNotifRepository, never()).save(any()); // No save should occur
    }

    @Test
    void testUpdatePetEventNotif() {
        // Arrange
        PetEventsNotif notif = new PetEventsNotif();
        notif.setId(1L);

        PetEventType eventType = new PetEventType();
        eventType.setId(1L);
        eventType.setName("Updated Event");

        PetEvent oldEvent = new PetEvent();
        oldEvent.setPetEventsNotif(notif);

        PetEvent newEvent = new PetEvent();
        newEvent.setType(eventType);
        newEvent.setDescription("New Description");
        newEvent.setDate(null);
        newEvent.setUserToken("NewToken");

        when(petEventsNotifRepository.findById(1L)).thenReturn(Optional.of(notif));
        when(petEventTypeRepository.findById(1L)).thenReturn(Optional.of(eventType));

        // Act
        petEventsNotifService.updatePetEventNotif(oldEvent, newEvent);

        // Assert
        assertEquals("Updated Event", notif.getTitle());
        assertEquals("New Description", notif.getDescription());
        assertEquals("NewToken", notif.getUserToken());
        assertTrue(notif.isNotify());
    }

    @Test
    void testCreatePetEventNotif() {
        // Arrange
        PetEventType eventType = new PetEventType();
        eventType.setId(1L);
        eventType.setName("Test Event");

        PetEvent petEvent = new PetEvent();
        petEvent.setType(eventType);
        petEvent.setDescription("Test Description");
        petEvent.setDate(null);
        petEvent.setUserToken("TestToken");

        when(petEventTypeRepository.findById(1L)).thenReturn(Optional.of(eventType));

        // Act
        PetEventsNotif result = petEventsNotifService.createPetEventNotif(petEvent);

        // Assert
        assertNotNull(result);
        assertEquals("Test Event", result.getTitle());
        assertEquals("Test Description", result.getDescription());
        assertEquals("TestToken", result.getUserToken());
        assertTrue(result.isNotify());
    }

    @Test
    void testGetPetEventsNotifById_Found() {
        // Arrange
        PetEventsNotif notif = new PetEventsNotif();
        notif.setId(1L);

        when(petEventsNotifRepository.findById(1L)).thenReturn(Optional.of(notif));

        // Act
        PetEventsNotif result = petEventsNotifService.getPetEventsNotifById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testGetPetEventsNotifById_NotFound() {
        // Arrange
        when(petEventsNotifRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        PetEventsNotif result = petEventsNotifService.getPetEventsNotifById(1L);

        // Assert
        assertNull(result);
    }
}
