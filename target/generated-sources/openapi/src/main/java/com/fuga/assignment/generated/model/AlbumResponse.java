package com.fuga.assignment.generated.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fuga.assignment.generated.model.AlbumDestinations;
import com.fuga.assignment.generated.model.AlbumStatusEnum;
import com.fuga.assignment.generated.model.ArtistResponse;
import com.fuga.assignment.generated.model.TrackResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.lang.Nullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * AlbumResponse
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-04-28T14:34:07.095591+02:00[Europe/Amsterdam]", comments = "Generator version: 7.11.0")
public class AlbumResponse {

  private @Nullable Long id;

  private @Nullable String name;

  private @Nullable String description;

  private @Nullable AlbumStatusEnum status;

  @Valid
  private List<AlbumDestinations> destinations = new ArrayList<>();

  private @Nullable String imageSrc;

  private @Nullable ArtistResponse artist;

  @Valid
  private List<@Valid TrackResponse> tracks = new ArrayList<>();

  public AlbumResponse id(Long id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
   */
  
  @Schema(name = "id", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("id")
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public AlbumResponse name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   * @return name
   */
  
  @Schema(name = "name", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public AlbumResponse description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Get description
   * @return description
   */
  
  @Schema(name = "description", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("description")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public AlbumResponse status(AlbumStatusEnum status) {
    this.status = status;
    return this;
  }

  /**
   * Get status
   * @return status
   */
  @Valid 
  @Schema(name = "status", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("status")
  public AlbumStatusEnum getStatus() {
    return status;
  }

  public void setStatus(AlbumStatusEnum status) {
    this.status = status;
  }

  public AlbumResponse destinations(List<AlbumDestinations> destinations) {
    this.destinations = destinations;
    return this;
  }

  public AlbumResponse addDestinationsItem(AlbumDestinations destinationsItem) {
    if (this.destinations == null) {
      this.destinations = new ArrayList<>();
    }
    this.destinations.add(destinationsItem);
    return this;
  }

  /**
   * Get destinations
   * @return destinations
   */
  @Valid 
  @Schema(name = "destinations", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("destinations")
  public List<AlbumDestinations> getDestinations() {
    return destinations;
  }

  public void setDestinations(List<AlbumDestinations> destinations) {
    this.destinations = destinations;
  }

  public AlbumResponse imageSrc(String imageSrc) {
    this.imageSrc = imageSrc;
    return this;
  }

  /**
   * Get imageSrc
   * @return imageSrc
   */
  
  @Schema(name = "imageSrc", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("imageSrc")
  public String getImageSrc() {
    return imageSrc;
  }

  public void setImageSrc(String imageSrc) {
    this.imageSrc = imageSrc;
  }

  public AlbumResponse artist(ArtistResponse artist) {
    this.artist = artist;
    return this;
  }

  /**
   * Get artist
   * @return artist
   */
  @Valid 
  @Schema(name = "artist", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("artist")
  public ArtistResponse getArtist() {
    return artist;
  }

  public void setArtist(ArtistResponse artist) {
    this.artist = artist;
  }

  public AlbumResponse tracks(List<@Valid TrackResponse> tracks) {
    this.tracks = tracks;
    return this;
  }

  public AlbumResponse addTracksItem(TrackResponse tracksItem) {
    if (this.tracks == null) {
      this.tracks = new ArrayList<>();
    }
    this.tracks.add(tracksItem);
    return this;
  }

  /**
   * Get tracks
   * @return tracks
   */
  @Valid 
  @Schema(name = "tracks", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("tracks")
  public List<@Valid TrackResponse> getTracks() {
    return tracks;
  }

  public void setTracks(List<@Valid TrackResponse> tracks) {
    this.tracks = tracks;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AlbumResponse albumResponse = (AlbumResponse) o;
    return Objects.equals(this.id, albumResponse.id) &&
        Objects.equals(this.name, albumResponse.name) &&
        Objects.equals(this.description, albumResponse.description) &&
        Objects.equals(this.status, albumResponse.status) &&
        Objects.equals(this.destinations, albumResponse.destinations) &&
        Objects.equals(this.imageSrc, albumResponse.imageSrc) &&
        Objects.equals(this.artist, albumResponse.artist) &&
        Objects.equals(this.tracks, albumResponse.tracks);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, description, status, destinations, imageSrc, artist, tracks);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AlbumResponse {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    destinations: ").append(toIndentedString(destinations)).append("\n");
    sb.append("    imageSrc: ").append(toIndentedString(imageSrc)).append("\n");
    sb.append("    artist: ").append(toIndentedString(artist)).append("\n");
    sb.append("    tracks: ").append(toIndentedString(tracks)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

