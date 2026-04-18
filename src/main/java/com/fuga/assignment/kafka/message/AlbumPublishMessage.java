package com.fuga.assignment.kafka.message;

import com.fuga.assignment.entity.DestinationService;

public record AlbumPublishMessage(Long albumId, DestinationService destinationService) {
}
