package com.fuga.assignment.generated.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.lang.Nullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * CreateTrackRequest
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-04-28T14:34:07.095591+02:00[Europe/Amsterdam]", comments = "Generator version: 7.11.0")
public class CreateTrackRequest {

  private String name;

  private @Nullable String description;

  private String src;

  public CreateTrackRequest() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public CreateTrackRequest(String name, String src) {
    this.name = name;
    this.src = src;
  }

  public CreateTrackRequest name(String name) {
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

  public CreateTrackRequest description(String description) {
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

  public CreateTrackRequest src(String src) {
    this.src = src;
    return this;
  }

  /**
   * Get src
   * @return src
   */
  @NotNull @Size(min = 1) 
  @Schema(name = "src", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("src")
  public String getSrc() {
    return src;
  }

  public void setSrc(String src) {
    this.src = src;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CreateTrackRequest createTrackRequest = (CreateTrackRequest) o;
    return Objects.equals(this.name, createTrackRequest.name) &&
        Objects.equals(this.description, createTrackRequest.description) &&
        Objects.equals(this.src, createTrackRequest.src);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, description, src);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreateTrackRequest {\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    src: ").append(toIndentedString(src)).append("\n");
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

