package app.com.coffeemachine;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.TopicConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableAsync
public class MyProducerFactory {

	@Value("${spring.kafka.properties.bootstrap.servers}")
	private String BOOTSTRAPSERVER;

	private final static Logger log = LoggerFactory.getLogger(MyProducerFactory.class);
	
	public MyProducerFactory() {
		log.info("ProducerFactory ...");
	}

	@Value("${spring.kafka.properties.sasl.jaas.config}")
	private String JAAS_CONFIG;

	@Bean
	public NewTopic vendcoffee() {
		return TopicBuilder.name("vendcoffee").partitions(6).replicas(3)
				.config(TopicConfig.RETENTION_MS_CONFIG, "3600000")
				.build();
	}
	@Bean
	public NewTopic onoffstatus() {
		return TopicBuilder.name("onoffstatus").partitions(6).replicas(3)
				.config(TopicConfig.RETENTION_MS_CONFIG, "3600000")
				.build();
	}

	@Bean("kafkaproducer")
	public ProducerFactory<String,String> producerFactory() {
		log.info("KafkaProducer ...");
		return new DefaultKafkaProducerFactory<>(createProperties());
		
	}
	
	@Bean
	public KafkaTemplate<String, String> kafkaTemplate() {
		return new KafkaTemplate<>(producerFactory());
	}

	public Map<String,Object> createProperties() {
		log.info("Kafka Properties Bean...");

		Map<String, Object> props = new HashMap<>();

		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAPSERVER);
		props.put(ProducerConfig.ACKS_CONFIG, "all");   // all = -1
		props.put("min.insync.replicas", "2"); 
		
		props.put(ProducerConfig.RETRIES_CONFIG,0);
		props.put("retry.backoff.ms", "5000"); // wait 1 second between each re-try
		props.put("request.timeout.ms", "10000"); // wait for 10 seconds
		props.put("socket.connection.setup.timeout.max.ms", "10000"); // wait for 10 seconds

		
//		props.put("delivery.timeout.ms", "150000"); // wait for 10 seconds
		
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		
		//props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
		//props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
		props.put("sasl.mechanism", "PLAIN");

		props.put("sasl.jaas.config", this.JAAS_CONFIG);
		props.put("security.protocol", "SASL_SSL");
		
		//props.put("delivery.timeout.ms",30000); 
		//props.put("metadata.max.idle.ms", 10000);

		return props;
	}
	
	/*
	 * # Required connection configs for Kafka producer, consumer, and admin
spring.kafka.properties.sasl.mechanism=PLAIN
spring.kafka.properties.bootstrap.servers=pkc-41wq6.eu-west-2.aws.confluent.cloud:9092
spring.kafka.properties.sasl.jaas.config=org.apache.kafka.common.security.plain.PlainLoginModule   required username='H2SGSSOYRCUYIR6Z'   password='Sm7MkbMRi2Z6tvBVHx2K1ji/2ns1GCbOQOLZlVOHgnAqnELrob+pzezILuHP3yZL';
spring.kafka.properties.security.protocol=SASL_SSL

# Best practice for higher availability in Apache Kafka clients prior to 3.0
spring.kafka.properties.session.timeout.ms=45000

# Required connection configs for Confluent Cloud Schema Registry
spring.kafka.properties.basic.auth.credentials.source=USER_INFO
spring.kafka.properties.basic.auth.user.info={{ SR_API_KEY }}:{{ SR_API_SECRET }}
spring.kafka.properties.schema.registry.url=https://{{ SR_ENDPOINT }}

	 * 
	 * 
	 */
}
