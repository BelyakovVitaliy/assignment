package com.fuga.assignment.service;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
@Slf4j
public class SerializationHelper {

	private final ObjectMapper objectMapper;


	public String serializeAsString(Object value) {
		try {
			return objectMapper.writeValueAsString(value);
		} catch (JacksonException e) {
			log.error("Failed to serialize object", e);
			throw new RuntimeException("Serialization failed");
		}
	}

	public <T> T readValue(String message, Class<T> tClass) {
		try {
			return objectMapper.readValue(message, tClass);
		} catch (JsonProcessingException e) {
			log.error("Failed to deserialize message", e);
			throw new RuntimeException("Deserialization failed");
		}
	}

}
