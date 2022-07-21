package app.com.coffeemachine.service;

import app.com.basic.PowerStatus;
import app.com.breville.BrevilleBaristaExpress;
import app.com.coffeemachine.entities.Status;
import app.com.coffeemachine.kafka.Producer;
import app.com.coffeemachine.models.grindresponse.CoffeeMachineStatus;
import app.com.coffeemachine.models.grindresponse.GrindResponse;
//import app.com.coffeemachine.repositories.status.StoreStatus;
import app.com.coffeemachine.storge.ObjectSerialDeSerial;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;

@Slf4j
@Service
public class CoffeeMachineService {

	@Autowired
	private BrevilleBaristaExpress coffeeMachine;
	
	@Autowired
	private CoffeeMachineStatus coffeeMachineStatus;
	
	@Autowired
	private ObjectSerialDeSerial objectSerialDeSerial;
	
	@Autowired
	private Producer producer;

//	@Autowired
//	private StoreStatus storeStatus;

	public CoffeeMachineService() {
		log.info("CoffeeMachineService");
	}

	/*
	 * Switch ON / OFF a coffee machine
	 */
	public void PowerButton() {
		log.info("PowerButton");
		final String topicName = "onoffstatus";
		
		coffeeMachine.PowerOnOff();
		coffeeMachine.setStopCoffeeGrinding(true);

	//	boolean topicExist = doesTopicExist(topicName);
		//if (!topicExist) {
		//	log.info("Topic '{}' does not exist, unable to send to kafka", topicName);
		//} else {		
		String msg = "{status:" + coffeeMachine.getPowerStatus().toString() + "}";
		log.info("STATUS : " + msg);

		producer.sendToKafka(topicName, msg);
		//}
	}
	
	
	// Check if topic exists
	/*
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
		//if (topicDescription == null) {
		//	return false;
		//}
		//return true;
	//}

	
	/*
	 * Return the power status of a coffee machine
	 */
	public CoffeeMachineStatus GetStatus()
	{
	//	CoffeeMachineStatus status = new CoffeeMachineStatus();
		coffeeMachineStatus.setStatus(coffeeMachine.getPowerStatus());
		coffeeMachineStatus.setWaterLevel(coffeeMachine.WaterTank().getWaterLevel());
		coffeeMachineStatus.setBeanLevel(coffeeMachine.Hopper().getBeanAmount());

		Status s = new Status();
		s.setId(1);
		s.setStatus("ON");
		System.out.println("Status " + s.toString());

		System.out.println("Calling endpoint ...");
		String uri = "https://d130de26-e070-47d0-81cf-9d92f12536f7.mock.pstmn.io/db/v1/status";
		RestTemplate restTemplate = new RestTemplate();
		//HttpHeaders headers = new HttpHeaders();
		//headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		ResponseEntity<String> resp = restTemplate.getForEntity(uri, String.class);


		//String resp = restTemplate.getForObject(uri, String.class);
		//ResponseEntity<Status> resp = restTemplate.getForEntity(uri, Status.class);

		System.out.println("Response :" + resp.toString());
		ObjectMapper mapper = new ObjectMapper();
		JsonNode root = null;
		try {
			root = mapper.readTree(resp.getBody());
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
		System.out.println("JsonNode :" + root.toString());

		Status jsonStatus = restTemplate.getForObject(uri, Status.class);
		System.out.println("Status :" + jsonStatus.toString());



		/*
		Status s = new Status();
		s.setLocalDateTime(coffeeMachineStatus.getDateTime());		// Get the timestamp from the coffeemachinestatus object
		s.setStatus(coffeeMachine.getPowerStatus().toString());		// Get the power status from the coffeemachine
		storeStatus.StoreStatus(s);	// Store the data if connected to a database
		*/

		return coffeeMachineStatus;
	}
	
	/*
	 * Grind a coffee dose
	 */
	public GrindResponse GrindDose() {
		log.info("Grind coffee");
		if (coffeeMachine.getPowerStatus() != PowerStatus.ON) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Coffee machine is not switched on");			
		}
		return (coffeeMachine.GrindDose());
	}
	
	public void FillHopper(double coffeeBeans) {
		log.info("Fill bean hopper");
		coffeeMachine.Hopper().FillHopper(coffeeBeans);
		objectSerialDeSerial.serialize(coffeeMachine.Hopper().getJsonHopperCapacity());
	}

	public void FillWaterTank(int water) {
		log.info("Fill water tank");
		coffeeMachine.WaterTank().fillWaterTank(water);
		objectSerialDeSerial.serialize(coffeeMachine.WaterTank().getJsonWaterTank());
	}

	public HttpHeaders GetIdentifier() {
		return (coffeeMachine.GetIdentifier());
	}



}
