package com.fuga.assignment.generated.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fuga.assignment.generated.model.AlbumDestinations;
import com.fuga.assignment.generated.model.CreateTrackRequest;
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
 * CreateAlbumRequest
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-04-28T14:34:07.095591+02:00[Europe/Amsterdam]", comments = "Generator version: 7.11.0")
public class CreateAlbumRequest {

  private String name;

  private @Nullable String description;

  private String imageSrc;

  private Long artistId;

  @Valid
  private List<@Valid CreateTrackRequest> tracks = new ArrayList<>();

  @Valid
  private List<AlbumDestinations> destinations = new ArrayList<>();

  public CreateAlbumRequest() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public CreateAlbumRequest(String name, String imageSrc, Long artistId, List<@Valid CreateTrackRequest> tracks, List<AlbumDestinations> destinations) {
    this.name = name;
    this.imageSrc = imageSrc;
    this.artistId = artistId;
    this.tracks = tracks;
    this.destinations = destinations;
  }

  public CreateAlbumRequest name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   * @return name
   */
  @NotNull @Size(min = 1) 
  @Schema(name = "name", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public CreateAlbumRequest description(String description) {
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

  public CreateAlbumRequest imageSrc(String imageSrc) {
    this.imageSrc = imageSrc;
    return this;
  }

  /**
   * Get imageSrc
   * @return imageSrc
   */
  @NotNull @Size(min = 1) 
  @Schema(name = "imageSrc", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("imageSrc")
  public String getImageSrc() {
    return imageSrc;
  }

  public void setImageSrc(String imageSrc) {
    this.imageSrc = imageSrc;
  }

  public CreateAlbumRequest artistId(Long artistId) {
    this.artistId = artistId;
    return this;
  }

  /**
   * Get artistId
   * @return artistId
   */
  @NotNull 
  @Schema(name = "artistId", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("artistId")
  public Long getArtistId() {
    return artistId;
  }

  public void setArtistId(Long artistId) {
    this.artistId = artistId;
  }

  public CreateAlbumRequest tracks(List<@Valid CreateTrackRequest> tracks) {
    this.tracks = tracks;
    return this;
  }

  public CreateAlbumRequest addTracksItem(CreateTrackRequest tracksItem) {
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
  @NotNull @Valid @Size(min = 1) 
  @Schema(name = "tracks", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("tracks")
  public List<@Valid CreateTrackRequest> getTracks() {
    return tracks;
  }

  public void setTracks(List<@Valid CreateTrackRequest> tracks) {
    this.tracks = tracks;
  }

  public CreateAlbumRequest destinations(List<AlbumDestinations> destinations) {
    this.destinations = destinations;
    return this;
  }

  public CreateAlbumRequest addDestinationsItem(AlbumDestinations destinationsItem) {
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
  @NotNull @Valid @Size(min = 1) 
  @Schema(name = "destinations", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("destinations")
  public List<AlbumDestinations> getDestinations() {
    return destinations;
  }

  public void setDestinations(List<AlbumDestinations> destinations) {
    this.destinations = destinations;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CreateAlbumRequest createAlbumRequest = (CreateAlbumRequest) o;
    return Objects.equals(this.name, createAlbumRequest.name) &&
        Objects.equals(this.description, createAlbumRequest.description) &&
        Objects.equals(this.imageSrc, createAlbumRequest.imageSrc) &&
        Objects.equals(this.artistId, createAlbumRequest.artistId) &&
        Objects.equals(this.tracks, createAlbumRequest.tracks) &&
        Objects.equals(this.destinations, createAlbumRequest.destinations);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, description, imageSrc, artistId, tracks, destinations);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreateAlbumRequest {\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    imageSrc: ").append(toIndentedString(imageSrc)).append("\n");
    sb.append("    artistId: ").append(toIndentedString(artistId)).append("\n");
    sb.append("    tracks: ").append(toIndentedString(tracks)).append("\n");
    sb.append("    destinations: ").append(toIndentedString(destinations)).append("\n");
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

