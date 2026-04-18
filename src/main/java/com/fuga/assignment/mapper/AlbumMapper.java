package com.fuga.assignment.mapper;

import com.fuga.assignment.entity.Album;
import com.fuga.assignment.entity.Artist;
import com.fuga.assignment.entity.Track;
import com.fuga.assignment.generated.model.*;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", imports = {Album.AlbumStatus.class})
public interface AlbumMapper {

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "status", expression = "java(AlbumStatus.NEW)")
	@Mapping(target = "artist", source = "artist")
	@Mapping(target = "name", source = "request.name")
	@Mapping(target = "tracks", source = "request.tracks")
	@Mapping(target = "platforms", source = "request.destinations")
	Album toEntity(CreateAlbumRequest request, Artist artist);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "album", ignore = true)
	Track toTrackEntity(CreateTrackRequest request);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "status", ignore = true)
	@Mapping(target = "imageSrc", ignore = true)
	@Mapping(target = "artist", ignore = true)
	@Mapping(target = "tracks", ignore = true)
	void updateEntity(UpdateAlbumRequest request, @MappingTarget Album album);

	@AfterMapping
    default void linkTracksToAlbum(@MappingTarget Album album) {
        if (album.getTracks() != null) {
            album.getTracks().forEach(track -> track.setAlbum(album));
        }
    }



	@Mapping(target = "destinations", source = "album.platforms")
	AlbumResponse toResponse(Album album);

	ArtistResponse toArtistResponse(Artist artist);

	TrackResponse toTrackResponse(Track track);

	List<TrackResponse> toTrackResponseList(List<Track> tracks);

	com.fuga.assignment.generated.model.AlbumStatusEnum toGeneratedStatus(Album.AlbumStatus status);
}
