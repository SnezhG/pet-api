package ru.vlsu.pet_api.service;

import java.io.IOException;

public interface PetFileService {
    String uploadFile(String fileBase64, String userId) throws IOException;

    String downloadFile(String fileUri) throws IOException;
}
