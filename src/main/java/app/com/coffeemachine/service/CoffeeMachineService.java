package app.com.coffeemachine.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.admin.Admin;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.DescribeTopicsResult;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.clients.admin.TopicDescription;
import org.apache.kafka.common.errors.TimeoutException;
import org.apache.kafka.common.errors.UnknownTopicOrPartitionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import app.com.basic.PowerStatus;
//import app.com.basic.PowerStatus.POWERSTATUS;
import app.com.breville.BrevilleBaristaExpress;
import app.com.coffeemachine.entities.CoffeeMachineStatus;
import app.com.coffeemachine.entities.GrindResponse;
import app.com.coffeemachine.storge.ObjectSerialDeSerial;
import app.com.coffeemachine.kafka.*;

@Service
public class CoffeeMachineService {

	private final static Logger log = LoggerFactory.getLogger(CoffeeMachineService.class);
	
	@Autowired
	private AdminClient admin;
	
	@Autowired
	private BrevilleBaristaExpress coffeeMachine;
	
	@Autowired
	private CoffeeMachineStatus status;
	
	@Autowired
	private ObjectSerialDeSerial objectSerialDeSerial;
	
	@Autowired
	private Producer producer;
	
	public CoffeeMachineService() {
		log.info("CoffeeMachineService");
	}

	/*
	 * Switch ON / OFF a coffee machine
	 */
	
	
	public void PowerButton() {
		log.info("PowerButton");
		String topicName = "onoffstatus1";
		
		coffeeMachine.PowerOnOff();
		coffeeMachine.setStopCoffeeGrinding(true);
		
		boolean topicExist = doesTopicExist(topicName);
		//if (!topicExist) {
		//	log.info("Topic '{}' does not exist, unable to send to kafka", topicName);
		//} else {		
			String msg = "{status:" + coffeeMachine.getPowerStatus().toString() + "}";
			log.info("STATUS : " + msg);
			producer.sendToKafka(topicName, msg);
		//}
	}
	
	
	// Check if topic exists
	
	private boolean doesTopicExist(String topicName) {		
		ListTopicsResult topics = admin.listTopics();
		//List<String> topic = new ArrayList<String>(Arrays.asList(topicName));
		List<String> topic = Collections.singletonList(topicName);
		
		DescribeTopicsResult topicDesc = admin.describeTopics(topic);
		TopicDescription topicDescription = null;
		try {
			//topicDescription = admin.describeTopics(Collections.singleton(topicName))
			//		.values().get(topicName).get();
			
			topicDescription = topicDesc.values().get(topicName).get();
			//topicDescription = 
			//		admin.describeTopics(Collections.singletonList(topicName)).values()
			//		.get(topicName).get(5,TimeUnit.SECONDS);
			log.info("Here ..." + topicDescription.name());
		} catch (ExecutionException | InterruptedException e) {
			if (! (e.getCause() instanceof UnknownTopicOrPartitionException)) {
				e.printStackTrace();
				try {
					throw e;
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			log.info("Topic {} does not exist", topicName);
			return false;
		}
			/**
		} catch (TimeoutException e) {
			log.info("Timeout1 error : " + e.getMessage());
			return false;
			
		} catch (InterruptedException | ExecutionException e) {
			log.info("Error : " + e.getMessage());
			return false;
		} catch (java.util.concurrent.TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		if (topicDescription == null) {
			return false;
		}
		return true;
	}
	
	
	/*
	 * Return the power status of a coffee machine
	 */
	public CoffeeMachineStatus GetStatus() {
	//	CoffeeMachineStatus status = new CoffeeMachineStatus();
		status.setStatus(coffeeMachine.getPowerStatus());
		status.setWaterLevel(coffeeMachine.WaterTank().getWaterLevel());
		status.setBeanLevel(coffeeMachine.Hopper().getBeanAmount());
		return status;
	}
	
	/*
	 * Grind a coffee dose
	 */
	public GrindResponse GrindDose() {
		log.info("Grind coffee");
		if (coffeeMachine.getPowerStatus() != PowerStatus.ON) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Coffee machine is not switched on");			
		}
		GrindResponse g = coffeeMachine.GrindDose();
		return g;
	}
	
	public void FillHopper(int coffeeBeans) {
		log.info("Fill bean hopper");
		coffeeMachine.Hopper().FillHopper(coffeeBeans);
		objectSerialDeSerial.serialize(coffeeMachine.Hopper());

	}

	public void FillWaterTank(int water) {
		log.info("Fill water tank");
		coffeeMachine.WaterTank().fillWaterTank(water);
		objectSerialDeSerial.serialize(coffeeMachine.WaterTank());
	}

	public HttpHeaders GetIdentifier() {
		HttpHeaders headers = coffeeMachine.GetIdentifier();
		return headers;
	}



}
