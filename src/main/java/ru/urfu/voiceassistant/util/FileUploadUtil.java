package ru.urfu.voiceassistant.util;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;

public class FileUploadUtil {

    public static String saveFile(String fileName, MultipartFile multipartFile) throws IOException {
        Path uploadDirectory = Paths.get("Files-Upload");

        String fileCode = RandomStringUtils.randomAlphanumeric(8);

        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadDirectory.resolve(fileCode + "-" + fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new IOException("Error saving uploaded file: " + fileName, e);
        }

        return fileCode;
    }

    public static void deleteFileByCode(String fileCode) throws IOException {
        Path uploadDirectory = Paths.get("Files-Upload");

        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(uploadDirectory, fileCode + "*")) {
            for (Path file : directoryStream) {
                Files.delete(file);
            }
        } catch (IOException e) {
            throw new IOException("Error deleting files with code: " + fileCode, e);
        }
    }
}
