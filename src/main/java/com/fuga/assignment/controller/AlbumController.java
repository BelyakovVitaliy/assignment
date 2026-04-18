package com.fuga.assignment.controller;

import com.fuga.assignment.generated.api.AlbumsApi;
import com.fuga.assignment.generated.model.AlbumResponse;
import com.fuga.assignment.generated.model.CreateAlbumRequest;
import com.fuga.assignment.generated.model.UpdateAlbumRequest;
import com.fuga.assignment.service.AlbumService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AlbumController implements AlbumsApi {

    private final AlbumService albumService;

    @Override
    public ResponseEntity<AlbumResponse> createAlbum(CreateAlbumRequest createAlbumRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(albumService.createAlbum(createAlbumRequest));
    }

    @Override
    public ResponseEntity<List<AlbumResponse>> getAllAlbums() {
        return ResponseEntity.ok(albumService.getAllAlbums());
    }

    @Override
    public ResponseEntity<AlbumResponse> getAlbumById(Long id) {
        return ResponseEntity.ok(albumService.getAlbum(id));
    }

    @Override
    public ResponseEntity<AlbumResponse> updateAlbum(Long id, UpdateAlbumRequest updateAlbumRequest) {
        return ResponseEntity.ok(albumService.updateAlbum(id, updateAlbumRequest));
    }

    @Override
    public ResponseEntity<Void> deleteAlbum(Long id) {
        albumService.deleteAlbum(id);
        return ResponseEntity.noContent().build();
    }
}
