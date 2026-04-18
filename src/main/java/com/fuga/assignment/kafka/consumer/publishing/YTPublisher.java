package com.fuga.assignment.kafka.consumer.publishing;

import com.fuga.assignment.kafka.KafkaTopics;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class YTPublisher {

	@KafkaListener(topics = KafkaTopics.TOPIC_PUBLISH_YT_MUSIC, groupId = "ytmusic-publisher-group")
	public void consume(String message) {
		throw new UnsupportedOperationException("Publishing to YT_MUSIC is not yet supported");
	}
}
