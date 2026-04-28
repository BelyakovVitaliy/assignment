package com.fuga.assignment.mapper;

import com.fuga.assignment.entity.Album;
import com.fuga.assignment.entity.Album.AlbumStatus;
import com.fuga.assignment.entity.Artist;
import com.fuga.assignment.entity.DestinationService;
import com.fuga.assignment.entity.Track;
import com.fuga.assignment.generated.model.AlbumDestinations;
import com.fuga.assignment.generated.model.AlbumResponse;
import com.fuga.assignment.generated.model.AlbumStatusEnum;
import com.fuga.assignment.generated.model.ArtistResponse;
import com.fuga.assignment.generated.model.CreateAlbumRequest;
import com.fuga.assignment.generated.model.CreateTrackRequest;
import com.fuga.assignment.generated.model.TrackResponse;
import com.fuga.assignment.generated.model.UpdateAlbumRequest;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-28T14:34:08+0200",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.10 (Eclipse Adoptium)"
)
@Component
public class AlbumMapperImpl implements AlbumMapper {

    @Override
    public Album toEntity(CreateAlbumRequest request, Artist artist) {
        if ( request == null && artist == null ) {
            return null;
        }

        Album.AlbumBuilder album = Album.builder();

        if ( request != null ) {
            album.name( request.getName() );
            album.tracks( createTrackRequestListToTrackList( request.getTracks() ) );
            album.platforms( albumDestinationsListToDestinationServiceList( request.getDestinations() ) );
            album.description( request.getDescription() );
            album.imageSrc( request.getImageSrc() );
        }
        album.artist( artist );
        album.status( AlbumStatus.NEW );

        Album albumResult = album.build();

        linkTracksToAlbum( albumResult );

        return albumResult;
    }

    @Override
    public Track toTrackEntity(CreateTrackRequest request) {
        if ( request == null ) {
            return null;
        }

        Track track = new Track();

        track.setName( request.getName() );
        track.setDescription( request.getDescription() );
        track.setSrc( request.getSrc() );

        return track;
    }

    @Override
    public void updateEntity(UpdateAlbumRequest request, Album album) {
        if ( request == null ) {
            return;
        }

        album.setName( request.getName() );
        album.setDescription( request.getDescription() );

        linkTracksToAlbum( album );
    }

    @Override
    public AlbumResponse toResponse(Album album) {
        if ( album == null ) {
            return null;
        }

        AlbumResponse albumResponse = new AlbumResponse();

        albumResponse.setDestinations( destinationServiceListToAlbumDestinationsList( album.getPlatforms() ) );
        albumResponse.setId( album.getId() );
        albumResponse.setName( album.getName() );
        albumResponse.setDescription( album.getDescription() );
        albumResponse.setStatus( toGeneratedStatus( album.getStatus() ) );
        albumResponse.setImageSrc( album.getImageSrc() );
        albumResponse.setArtist( toArtistResponse( album.getArtist() ) );
        albumResponse.setTracks( toTrackResponseList( album.getTracks() ) );

        return albumResponse;
    }

    @Override
    public ArtistResponse toArtistResponse(Artist artist) {
        if ( artist == null ) {
            return null;
        }

        ArtistResponse artistResponse = new ArtistResponse();

        artistResponse.setId( artist.getId() );
        artistResponse.setName( artist.getName() );
        artistResponse.setAlbumCount( artist.getAlbumCount() );

        return artistResponse;
    }

    @Override
    public TrackResponse toTrackResponse(Track track) {
        if ( track == null ) {
            return null;
        }

        TrackResponse trackResponse = new TrackResponse();

        trackResponse.setId( track.getId() );
        trackResponse.setName( track.getName() );
        trackResponse.setDescription( track.getDescription() );
        trackResponse.setSrc( track.getSrc() );

        return trackResponse;
    }

    @Override
    public List<TrackResponse> toTrackResponseList(List<Track> tracks) {
        if ( tracks == null ) {
            return null;
        }

        List<TrackResponse> list = new ArrayList<TrackResponse>( tracks.size() );
        for ( Track track : tracks ) {
            list.add( toTrackResponse( track ) );
        }

        return list;
    }

    @Override
    public AlbumStatusEnum toGeneratedStatus(Album.AlbumStatus status) {
        if ( status == null ) {
            return null;
        }

        AlbumStatusEnum albumStatusEnum;

        switch ( status ) {
            case NEW: albumStatusEnum = AlbumStatusEnum.NEW;
            break;
            case VALIDATED: albumStatusEnum = AlbumStatusEnum.VALIDATED;
            break;
            case PUBLISHED: albumStatusEnum = AlbumStatusEnum.PUBLISHED;
            break;
            case DELETED: albumStatusEnum = AlbumStatusEnum.DELETED;
            break;
            default: throw new IllegalArgumentException( "Unexpected enum constant: " + status );
        }

        return albumStatusEnum;
    }

    protected List<Track> createTrackRequestListToTrackList(List<CreateTrackRequest> list) {
        if ( list == null ) {
            return null;
        }

        List<Track> list1 = new ArrayList<Track>( list.size() );
        for ( CreateTrackRequest createTrackRequest : list ) {
            list1.add( toTrackEntity( createTrackRequest ) );
        }

        return list1;
    }

    protected DestinationService albumDestinationsToDestinationService(AlbumDestinations albumDestinations) {
        if ( albumDestinations == null ) {
            return null;
        }

        DestinationService destinationService;

        switch ( albumDestinations ) {
            case SPOTIFY: destinationService = DestinationService.SPOTIFY;
            break;
            default: throw new IllegalArgumentException( "Unexpected enum constant: " + albumDestinations );
        }

        return destinationService;
    }

    protected List<DestinationService> albumDestinationsListToDestinationServiceList(List<AlbumDestinations> list) {
        if ( list == null ) {
            return null;
        }

        List<DestinationService> list1 = new ArrayList<DestinationService>( list.size() );
        for ( AlbumDestinations albumDestinations : list ) {
            list1.add( albumDestinationsToDestinationService( albumDestinations ) );
        }

        return list1;
    }

    protected AlbumDestinations destinationServiceToAlbumDestinations(DestinationService destinationService) {
        if ( destinationService == null ) {
            return null;
        }

        AlbumDestinations albumDestinations;

        switch ( destinationService ) {
            case SPOTIFY: albumDestinations = AlbumDestinations.SPOTIFY;
            break;
            default: throw new IllegalArgumentException( "Unexpected enum constant: " + destinationService );
        }

        return albumDestinations;
    }

    protected List<AlbumDestinations> destinationServiceListToAlbumDestinationsList(List<DestinationService> list) {
        if ( list == null ) {
            return null;
        }

        List<AlbumDestinations> list1 = new ArrayList<AlbumDestinations>( list.size() );
        for ( DestinationService destinationService : list ) {
            list1.add( destinationServiceToAlbumDestinations( destinationService ) );
        }

        return list1;
    }
}
