package io.github.aryansh05.ticketing.event.service;

import io.github.aryansh05.ticketing.auth.security.AuthenticatedUser;
import io.github.aryansh05.ticketing.auth.security.UserPrincipal;
import io.github.aryansh05.ticketing.event.domain.entity.Event;
import io.github.aryansh05.ticketing.event.domain.entity.EventMedia;
import io.github.aryansh05.ticketing.event.domain.repository.EventMediaRepository;
import io.github.aryansh05.ticketing.event.domain.repository.EventRepository;
import io.github.aryansh05.ticketing.media.domain.entity.Media;
import io.github.aryansh05.ticketing.media.domain.entity.MediaRole;
import io.github.aryansh05.ticketing.media.domain.entity.MediaType;
import io.github.aryansh05.ticketing.media.domain.repository.MediaRepository;
import io.github.aryansh05.ticketing.media.dto.response.MediaResponse;
import io.github.aryansh05.ticketing.media.dto.response.StoredMedia;
import io.github.aryansh05.ticketing.media.service.CloudinaryStorageService;
import io.github.aryansh05.ticketing.shared.exception.OwnershipException;
import io.github.aryansh05.ticketing.shared.exception.ResourceAlreadyExistsException;
import io.github.aryansh05.ticketing.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventMediaService {

    private final MediaRepository mediaRepository;
    private final EventMediaRepository eventMediaRepository;
    private final EventRepository eventRepository;

    private final CloudinaryStorageService cloudinaryStorageService;

    private final AuthenticatedUser authenticatedUser;

    public MediaResponse uploadEventMedia(MultipartFile file, UUID eventId, MediaRole role, Integer displayOrder) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));
        UserPrincipal user = authenticatedUser.getAuthenticatedUser();

        if(!event.getUserId().equals(user.id())) throw new OwnershipException("You can only upload media to your own events");

        MediaType type = cloudinaryStorageService.determineMediaType(file);

        validateMediaRole(type, role);

        validateRoleAvailability(eventId, role);

        Integer display = displayOrder == null ? 0 : displayOrder;

        if(display < 0) throw new IllegalArgumentException("Invalid display number");

        String folder = "ticketing/events/" + eventId;

        StoredMedia storedMedia = cloudinaryStorageService.upload(file, folder, type);

        try{
            Media media = Media.builder()
                    .url(storedMedia.url())
                    .publicId(storedMedia.publicId())
                    .format(storedMedia.format())
                    .type(storedMedia.type())
                    .bytes(storedMedia.bytes())
                    .build();

            Media savedMedia =  mediaRepository.save(media);

            EventMedia eventMedia = EventMedia.builder()
                    .eventId(eventId)
                    .mediaId(savedMedia.getId())
                    .role(role)
                    .displayOrder(display)
                    .build();

            EventMedia savedEventMedia = eventMediaRepository.save(eventMedia);

            return new MediaResponse(
                    savedMedia.getId(),
                    savedMedia.getUrl(),
                    savedMedia.getFormat(),
                    savedMedia.getType(),
                    savedEventMedia.getRole(),
                    savedMedia.getBytes(),
                    savedEventMedia.getDisplayOrder()
            );
        } catch (Exception e) {
            try{
                cloudinaryStorageService.delete(
                        storedMedia.publicId(),
                        storedMedia.type()
                );
            } catch (Exception ex){
                throw new IllegalArgumentException("Unable to delete stored event media");
            }

            throw new ResourceNotFoundException("Failed to upload media");
        }
    }

    private void validateRoleAvailability(
            UUID eventId,
            MediaRole role
    ) {

        if (role == MediaRole.COVER
                || role == MediaRole.TRAILER) {

            boolean exists =
                    eventMediaRepository
                            .existsByEventIdAndRole(
                                    eventId,
                                    role
                            );

            if (exists) {

                throw new ResourceAlreadyExistsException(
                        "Event already has a " + role
                );
            }
        }
    }

    public List<MediaResponse> getEventMedia(UUID eventId) {

        if (!eventRepository.existsById(eventId)) {
            throw new ResourceNotFoundException(
                    "Event not found"
            );
        }

        return eventMediaRepository
                .findMediaByEventId(eventId);
    }


    private void validateMediaRole(
            MediaType type,
            MediaRole role
    ) {

        if (role == MediaRole.COVER
                && type != MediaType.IMAGE) {

            throw new IllegalArgumentException(
                    "Cover media must be an image"
            );
        }


        if (role == MediaRole.GALLERY
                && type != MediaType.IMAGE) {

            throw new IllegalArgumentException(
                    "Gallery media must be an image"
            );
        }


        if (role == MediaRole.TRAILER
                && type != MediaType.VIDEO) {

            throw new IllegalArgumentException(
                    "Trailer media must be a video"
            );
        }
    }
}