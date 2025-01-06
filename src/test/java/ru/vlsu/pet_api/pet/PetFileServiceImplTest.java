package ru.vlsu.pet_api.pet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import ru.vlsu.pet_api.service.impl.PetFileServiceImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class PetFileServiceImplTest {

    @InjectMocks
    private PetFileServiceImpl petFileService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDownloadFile() throws IOException {
        // Arrange
        String fileUri = "123/123e4567-e89b-12d3-a456-426614174000.dat";
        Path filePath = Paths.get("files", fileUri);
        byte[] fileContent = "TestContent".getBytes();

        try (MockedStatic<Files> mockedFiles = mockStatic(Files.class)) {
            mockedFiles.when(() -> Files.exists(filePath)).thenReturn(true);
            mockedFiles.when(() -> Files.isReadable(filePath)).thenReturn(true);
            mockedFiles.when(() -> Files.readAllBytes(filePath)).thenReturn(fileContent);

            // Act
            String result = petFileService.downloadFile(fileUri);

            // Assert
            assertEquals(Base64.getEncoder().encodeToString(fileContent), result);
            mockedFiles.verify(() -> Files.exists(filePath), times(1));
            mockedFiles.verify(() -> Files.readAllBytes(filePath), times(1));
        }
    }

    @Test
    void testUploadFile_ThrowsIOException() {
        // Arrange
        String fileBase64 = Base64.getEncoder().encodeToString("TestContent".getBytes());
        String userId = "123";

        try (MockedStatic<Files> mockedFiles = mockStatic(Files.class)) {
            mockedFiles.when(() -> Files.createDirectories(any())).thenThrow(new IOException("Mocked IOException"));

            // Act & Assert
            RuntimeException exception = assertThrows(RuntimeException.class, () -> petFileService.uploadFile(fileBase64, userId));
            assertEquals("Error saving file: Mocked IOException", exception.getMessage());
        }
    }

    @Test
    void testDownloadFile_FileNotFound() {
        // Arrange
        String fileUri = "123/nonexistent.dat";
        Path filePath = Paths.get("files", fileUri);

        try (MockedStatic<Files> mockedFiles = mockStatic(Files.class)) {
            mockedFiles.when(() -> Files.exists(filePath)).thenReturn(false);

            // Act & Assert
            RuntimeException exception = assertThrows(RuntimeException.class, () -> petFileService.downloadFile(fileUri));
            assertEquals("File not found or not readable", exception.getMessage());
        }
    }
}
