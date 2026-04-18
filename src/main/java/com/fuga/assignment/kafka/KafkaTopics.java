package com.fuga.assignment.kafka;

import lombok.Getter;

import java.util.List;

@Getter
public class KafkaTopics {


	public static final String TOPIC_VALIDATE_GENRE = "validate.genre";
	public static final String TOPIC_VALIDATE_CONTENT = "validate.content";
	public static final String TOPIC_VALIDATE_IMAGE = "validate.image";
	public static final List<String> VALIDATION_TOPICS = List.of(
			TOPIC_VALIDATE_GENRE,
			TOPIC_VALIDATE_CONTENT,
			TOPIC_VALIDATE_IMAGE
	);

	public static final String TOPIC_PUBLISH_SPOTIFY = "publish.spotify";
	public static final String TOPIC_PUBLISH_YT_MUSIC = "publish.ytmusic";
	public static final String TOPIC_ALBUM_PUBLISHED = "published";
}
