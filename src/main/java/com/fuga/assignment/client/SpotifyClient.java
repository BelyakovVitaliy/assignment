package com.fuga.assignment.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "spotify", url = "${clients.spotify.url}")
public interface SpotifyClient {

    @PostMapping("/api/v1/publish")
    ResponseEntity<String> publish(@RequestBody SpotifyPublishRequest request);
}
