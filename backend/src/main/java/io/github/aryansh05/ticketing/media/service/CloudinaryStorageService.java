package io.github.aryansh05.ticketing.media.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import io.github.aryansh05.ticketing.media.domain.entity.MediaType;
import io.github.aryansh05.ticketing.media.dto.response.StoredMedia;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryStorageService {

    private final Cloudinary cloudinary;

    public StoredMedia upload(MultipartFile file, String folder, MediaType type) {
        String fileType = switch (type) {
            case IMAGE -> "image";
            case VIDEO -> "video";
        };

        try {
            Map<?, ?> res;
            Map<String, Object> options = ObjectUtils.asMap(
                    "resource_type", fileType,
                    "folder", folder
            );

            if (type == MediaType.VIDEO) {
                res = cloudinary.uploader().uploadLarge(file.getBytes(), options);
            } else {
                res = cloudinary.uploader().upload(file.getBytes(), options);
            }
            return new StoredMedia(
                    res.get("secure_url").toString(),
                    res.get("public_id").toString(),
                    res.get("format").toString(),
                    type,
                    ((Number) res.get("bytes")).longValue()
                    );
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to upload file");
        }
    }

    public void delete(String publicId, MediaType mediaType) {
        String resourceType = switch (mediaType) {
            case IMAGE -> "image";
            case VIDEO -> "video";
        };

        try {
            cloudinary.uploader().destroy(
                    publicId,
                    ObjectUtils.asMap(
                            "resource_type", resourceType
                    )
            );
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to delete media");
        }
    }

    public MediaType determineMediaType(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Media file is required");
        }

        String contentType = file.getContentType();

        if (contentType != null) {

            if (contentType.startsWith("image/")) {
                return MediaType.IMAGE;
            }

            if (contentType.startsWith("video/")) {
                return MediaType.VIDEO;
            }
        }

        String filename = file.getOriginalFilename();

        if (filename == null || !filename.contains(".")) {
            throw new IllegalArgumentException(
                    "Unable to determine media type"
            );
        }

        String extension = filename
                .substring(filename.lastIndexOf('.') + 1)
                .toLowerCase();

        return switch (extension) {
            case "jpg", "jpeg", "png", "webp", "gif" -> MediaType.IMAGE;

            case "mp4", "webm", "mov" -> MediaType.VIDEO;

            default -> throw new IllegalArgumentException("Only images and videos are allowed");
        };
    }
}