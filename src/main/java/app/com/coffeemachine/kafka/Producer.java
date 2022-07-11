package app.com.coffeemachine.kafka;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

public class Producer {

	private final static Logger log = LoggerFactory.getLogger(Producer.class);
	
	//@Autowired
	private KafkaTemplate<String,String> template;
	
	@Autowired
	public Producer(KafkaTemplate<String, String> kafkaTemplate) {
		log.info("Kafka Producer ...");
		this.template = kafkaTemplate;
	}
	
	@Async
	public void sendToKafka(String topicName, String msg) {
		log.info("Kafka send ... msg : " + msg);
		
		/*
		final ProducerRecord<String, String> record = createRecord(msg);
		
		try {
			SendResult<String, String> sendRes = template.send(record).get(10, TimeUnit.SECONDS);
			RecordMetadata met = sendRes.getRecordMetadata();
			
			log.info("Message sent ... Partition: " + met.partition() + " offset : " + met.offset() );
			
			
		} catch (ExecutionException e) {
			log.info("Error Exception: " + e.getMessage());
			
		} catch (TimeoutException | InterruptedException e) {
			log.info("Error Timeout: " + e.getMessage());
			
		}
		*/
		// https://www.youtube.com/watch?v=po9hvsaXmoQ
		
		//ListenableFuture<SendResult<String, String>> future = 
		//               template.send("onoffstatus1",msg);
		try {
			ListenableFuture<SendResult<String, String>> future = 
					template.send(topicName,msg);

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
		} catch (Exception e) {
			log.info("Error ....");
		}
		//future = template.send("onoffstatus1",msg);

		/*
		try {
			future.get(5,TimeUnit.SECONDS);
			
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			log.error("Unable to send message - error: " + e.getMessage());
		}
		*/
	}

	private ProducerRecord<String, String> createRecord(String msg) {

		ProducerRecord<String,String> p = new ProducerRecord<String,String>("onoffstatus1", msg);
		return p;
		
	}
}
