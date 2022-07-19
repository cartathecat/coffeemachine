package app.com.coffeemachine.kafka;

import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

public class Producer {

	private final static Logger log = LoggerFactory.getLogger(Producer.class);
	
	//@Autowired
	private KafkaTemplate<String,String> kafkaTemplate;
	
	@Autowired
	public Producer(KafkaTemplate<String, String> kafkaTemplate) {
		log.info("Kafka Producer ...");
		this.kafkaTemplate = kafkaTemplate;
	}

	// https://memorynotfound.com/spring-kafka-adding-custom-header-kafka-message-example/
	@Async
	public void sendToKafka(String topicName, String data) {
		log.info("Kafka send ... msg : " + data);

		UUID uuid = UUID.randomUUID();
		log.info("UUID : " + uuid);

		// https://www.youtube.com/watch?v=po9hvsaXmoQ
		// https://github.com/spring-cloud/spring-cloud-stream/issues/1948

		//ListenableFuture<SendResult<String, String>> future = this.kafkaTemplate.send(topicName,data);
		Message<String> msg = MessageBuilder
				.withPayload(data)
				.setHeader(KafkaHeaders.TOPIC,topicName)
//				.setHeader(KafkaHeaders.CORRELATION_ID, uuid)
				.build();
		ListenableFuture<SendResult<String, String>> future = this.kafkaTemplate.send(msg);

		future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

			@Override
			public void onSuccess(SendResult<String, String> result) {
				log.info("Message sent ... offset = [" + result.getRecordMetadata().offset() + "]" );

			}

			@Override
			public void onFailure(Throwable ex) {
				log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
				log.info("Unable to send message - error: " + ex.getMessage());
			}

		});
	}

}
