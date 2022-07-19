package app.com.coffeemachine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.kafka.core.KafkaTemplate;

import app.com.breville.BrevilleBaristaExpress;
import app.com.breville.DoseWheel;
import app.com.breville.Hopper;
import app.com.breville.GrindWheel;
import app.com.breville.WaterTank;
import app.com.coffeemachine.models.grindresponse.CoffeeMachineStatus;
import app.com.coffeemachine.kafka.Producer;
import app.com.coffeemachine.storge.ObjectSerialDeSerial;

@Configuration
public class AppConfig {

	@Autowired
	public KafkaTemplate<String, String> kafkaTemplate;
	
	@Bean
	public CoffeeMachineStatus status() {
		return new CoffeeMachineStatus();
	}
	
	// Breville Barista Express
	@Bean
	public BrevilleBaristaExpress brevilleBaristaExpress() {
		return new BrevilleBaristaExpress();
	}
	
	@Bean
	public GrindWheel grindWheel() {
		return new GrindWheel();
	}

	@Bean
	public DoseWheel doseWheel() {
		return new DoseWheel();
	}
	
	@Bean
	public ObjectSerialDeSerial objectSerialDeSerial() {
		return new ObjectSerialDeSerial();
	}

	@Bean
	@DependsOn("objectSerialDeSerial")
	public WaterTank waterTank() {
		WaterTank w = (WaterTank) objectSerialDeSerial().deSerialize(WaterTank.class);
		if (w != null) {
			return w;
		} else {
			return new WaterTank();
		}
	}
	
	@Bean
	@DependsOn("objectSerialDeSerial")
	public Hopper hopper() {
		Hopper h = (Hopper) objectSerialDeSerial().deSerialize(Hopper.class);
		if (h != null) {
			return h;
		} else {
			return new Hopper();
		}
	}
	
	@Bean
	public Producer producer() {
		return new Producer(kafkaTemplate);
	}
	
}
