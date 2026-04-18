package com.fuga.assignment.unit;

import com.fuga.assignment.client.SpotifyClient;
import com.fuga.assignment.client.SpotifyPublishRequest;
import com.fuga.assignment.entity.Album;
import com.fuga.assignment.entity.DestinationService;
import com.fuga.assignment.entity.PublishingResult;
import com.fuga.assignment.exception.FugaException;
import com.fuga.assignment.kafka.consumer.publishing.SpotifyPublisher;
import com.fuga.assignment.kafka.message.AlbumPublishMessage;
import com.fuga.assignment.repository.AlbumRepository;
import com.fuga.assignment.repository.PublishingResultRepository;
import com.fuga.assignment.service.SerializationHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SpotifyPublisherTest {

    @Mock
    private PublishingResultRepository publishingResultRepository;
    @Mock
    private AlbumRepository albumRepository;
    @Mock
    private SpotifyClient spotifyClient;
    @Mock
    private SerializationHelper serializationHelper;
    @Mock
    private Clock clock;

    @InjectMocks
    private SpotifyPublisher victim;

    @Test
    void shouldPublishAlbumSuccessfully() {
        String rawMessage = """
            {
                "albumId": 1
            }
        """;
        AlbumPublishMessage message = new AlbumPublishMessage(1L, DestinationService.SPOTIFY);
        Album album = Album.builder()
                .id(1L)
                .name("Album1")
                .description("Album1 Description")
                .build();

        when(serializationHelper.readValue(rawMessage, AlbumPublishMessage.class)).thenReturn(message);
        when(publishingResultRepository.existsByAlbumIdAndDestinationService(1L, DestinationService.SPOTIFY)).thenReturn(false);
        when(albumRepository.findById(1L)).thenReturn(Optional.of(album));
        when(spotifyClient.publish(new SpotifyPublishRequest("Album1", "Album1 Description")))
                .thenReturn(ResponseEntity.ok("Success"));
        when(clock.instant()).thenReturn(Instant.parse("2026-04-18T00:00:00Z"));
        when(clock.getZone()).thenReturn(ZoneOffset.UTC);

        victim.consume(rawMessage);

        ArgumentCaptor<PublishingResult> captor = ArgumentCaptor.forClass(PublishingResult.class);
        verify(publishingResultRepository).save(captor.capture());
        PublishingResult saved = captor.getValue();
        assertThat(saved.getAlbumId()).isEqualTo(1L);
        assertThat(saved.getDestinationService()).isEqualTo(DestinationService.SPOTIFY);
        assertThat(saved.getResult()).isEqualTo(PublishingResult.Result.SUCCESS);
    }

    @Test
    void shouldFailPublishingNonSpotifyAlbum() {
        String rawMessage = """
            {
                "albumId": 1
            }
        """;
        AlbumPublishMessage message = new AlbumPublishMessage(1L, null);

        when(serializationHelper.readValue(rawMessage, AlbumPublishMessage.class)).thenReturn(message);

        assertThatThrownBy(() -> victim.consume(rawMessage))
                .isInstanceOf(FugaException.class)
                .hasMessageContaining("The event has wrong destination=null, expected=SPOTIFY. AlbumId=1");

        verifyNoInteractions(albumRepository);
        verifyNoInteractions(spotifyClient);
        verify(publishingResultRepository, never()).save(any());
    }

    @Test
    void shouldFailPublishingNonExistAlbum() {
        String rawMessage = """
            {
                "albumId": 1
            }
        """;
        AlbumPublishMessage message = new AlbumPublishMessage(1L, DestinationService.SPOTIFY);

        when(serializationHelper.readValue(rawMessage, AlbumPublishMessage.class)).thenReturn(message);
        when(publishingResultRepository.existsByAlbumIdAndDestinationService(1L, DestinationService.SPOTIFY)).thenReturn(false);
        when(albumRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> victim.consume(rawMessage))
                .isInstanceOf(FugaException.class)
                .hasMessageContaining(FugaException.FugaExceptionCode.D002.getReason());

        verifyNoInteractions(spotifyClient);
        verify(publishingResultRepository, never()).save(any());
    }

    @Test
    void shouldSkipPublishingOnRepeatRequest() {
        String rawMessage = """
            {
                "albumId": 1
            }
        """;
        AlbumPublishMessage message = new AlbumPublishMessage(1L, DestinationService.SPOTIFY);

        when(serializationHelper.readValue(rawMessage, AlbumPublishMessage.class)).thenReturn(message);
        when(publishingResultRepository.existsByAlbumIdAndDestinationService(1L, DestinationService.SPOTIFY)).thenReturn(true);

        assertThatNoException().isThrownBy(() -> victim.consume(rawMessage));

        verifyNoInteractions(spotifyClient);
        verify(albumRepository, never()).findById(any());
        verify(publishingResultRepository, never()).save(any());
    }

    @Test
    void shouldFailWhenPublishingFailed() {
        String rawMessage = """
            {
                "albumId": 1
            }
        """;
        AlbumPublishMessage message = new AlbumPublishMessage(1L, DestinationService.SPOTIFY);
        Album album = Album.builder()
                .id(1L)
                .name("Album1")
                .description("Album1 Description")
                .build();

        when(serializationHelper.readValue(rawMessage, AlbumPublishMessage.class)).thenReturn(message);
        when(publishingResultRepository.existsByAlbumIdAndDestinationService(1L, DestinationService.SPOTIFY)).thenReturn(false);
        when(albumRepository.findById(1L)).thenReturn(Optional.of(album));
        when(spotifyClient.publish(new SpotifyPublishRequest("Album1", "Album1 Description")))
                .thenReturn(ResponseEntity.internalServerError().body("Something went wrong"));

        assertThatThrownBy(() -> victim.consume(rawMessage))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Publishing to Spotify failed. Error: Something went wrong, albumId=1");

        verify(publishingResultRepository, never()).save(any());
    }



}
