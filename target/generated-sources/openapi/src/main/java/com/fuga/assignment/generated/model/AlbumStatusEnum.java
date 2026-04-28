package com.fuga.assignment.generated.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonValue;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Gets or Sets AlbumStatusEnum
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-04-28T14:34:07.095591+02:00[Europe/Amsterdam]", comments = "Generator version: 7.11.0")
public enum AlbumStatusEnum {
  
  NEW("NEW"),
  
  VALIDATED("VALIDATED"),
  
  PUBLISHED("PUBLISHED"),
  
  DELETED("DELETED");

  private String value;

  AlbumStatusEnum(String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return value;
  }

  @Override
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static AlbumStatusEnum fromValue(String value) {
    for (AlbumStatusEnum b : AlbumStatusEnum.values()) {
      if (b.value.equals(value)) {
        return b;
      }
    }
    throw new IllegalArgumentException("Unexpected value '" + value + "'");
  }
}

