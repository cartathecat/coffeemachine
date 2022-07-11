package app.com.coffeemachine;

import java.util.HashMap;
import java.util.Map;

import ch.qos.logback.core.net.SyslogOutputStream;
import org.apache.kafka.clients.admin.Admin;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAsync
public class MyProducerFactory {

	final private static String BOOTSTRAPSERVER = "pkc-41wq6.eu-west-2.aws.confluent.cloud:9092";

	private final static Logger log = LoggerFactory.getLogger(MyProducerFactory.class);
	
	public MyProducerFactory() {
		log.info("ProducerFactory ...");
	}

	// Env values
	private String KAFKA_USER;
	private String KAFKA_PASS;

	@Value("${spring.kafka.properties.sasl.jaas.config}")
	private String JAAS_CONFIG;

	@Bean("kafkaproducer")
	public ProducerFactory<String,String> producerFactory() {
		log.info("KafkaProducer ...");
		return new DefaultKafkaProducerFactory<>(createProperties());
		
	}
	
	
	/*
	@Bean
	public AdminClient admin() {
		log.info("Admin  Bean...");
		
		Map<String, Object> props = new HashMap<>();
		
		//props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAPSERVER);
		props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAPSERVER);
//		props.put(AdminClientConfig.REQUEST_TIMEOUT_MS_CONFIG, 5000);
		props.put(AdminClientConfig.RETRIES_CONFIG,1);

//		props.put(AdminClientConfig.DEFAULT_API_TIMEOUT_MS_CONFIG, 5000);
//		props.put(AdminClientConfig.CLIENT_ID_CONFIG, "adminclient-99");
		
		AdminClient c = AdminClient.create(props);
		return c;
	}
	*/

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

		// Replace the properties with env values
		this.JAAS_CONFIG.replace("KAFKA_USER", System.getenv("KAFKA_USER"));
		this.JAAS_CONFIG.replace("KAFKA_PASS", System.getenv("KAFKA_PASS"));

		System.out.print("JAAS_CONFIG : " + this.JAAS_CONFIG);
		//props.put("sasl.jaas.config", "org.apache.kafka.common.security.plain.PlainLoginModule required username='H2SGSSOYRCUYIR6Z'   password='Sm7MkbMRi2Z6tvBVHx2K1ji/2ns1GCbOQOLZlVOHgnAqnELrob+pzezILuHP3yZL';");
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
