package ru.vlsu.pet_api.service.impl;

import org.springframework.stereotype.Service;
import ru.vlsu.pet_api.service.PetFileService;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;

@Service
public class PetFileServiceImpl implements PetFileService {
    private static final String BASE_DIRECTORY = "files";

    @Override
    public String uploadFile(String fileBase64, String userId) throws IOException {
        try {
            // Декодируем Base64 в массив байтов
            byte[] fileBytes = Base64.getDecoder().decode(fileBase64);

            // Создаем директорию для пользователя, если её нет
            Path userDirectory = Paths.get(BASE_DIRECTORY, userId);
            Files.createDirectories(userDirectory);

            // Генерируем уникальное имя для файла
            String fileName = UUID.randomUUID().toString() + ".dat";
            Path filePath = userDirectory.resolve(fileName);

            // Сохраняем файл на диск
            try (FileOutputStream fos = new FileOutputStream(filePath.toFile())) {
                fos.write(fileBytes);
            }

            // Формируем URI файла относительно базовой директории
            String fileUri = userId + "/" + fileName;
            return fileUri;
        } catch (IOException e) {
            throw new RuntimeException("Error saving file: " + e.getMessage());
        }
    }

    @Override
    public String downloadFile(String fileUri) throws IOException {
        try {
            // Формируем полный путь к файлу
            Path filePath = Paths.get(BASE_DIRECTORY, fileUri);

            // Проверяем существование файла
            if (!Files.exists(filePath) || !Files.isReadable(filePath)) {
                throw new RuntimeException("File not found or not readable");
            }

            // Читаем файл и конвертируем в Base64
            byte[] fileBytes = Files.readAllBytes(filePath);
            String fileBase64 = Base64.getEncoder().encodeToString(fileBytes);

            return fileBase64;
        } catch (IOException e) {
            throw new RuntimeException("Error reading file: " + e.getMessage());
        }
    }

}
