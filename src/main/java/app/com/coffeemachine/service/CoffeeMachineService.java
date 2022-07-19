package app.com.coffeemachine.service;

import app.com.coffeemachine.entities.Status;
import app.com.coffeemachine.repository.OnOffRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import app.com.basic.PowerStatus;
import app.com.breville.BrevilleBaristaExpress;
import app.com.coffeemachine.models.grindresponse.CoffeeMachineStatus;
import app.com.coffeemachine.models.grindresponse.GrindResponse;
import app.com.coffeemachine.storge.ObjectSerialDeSerial;
import app.com.coffeemachine.kafka.*;

@Slf4j
@Service
public class CoffeeMachineService {

	@Autowired
	private BrevilleBaristaExpress coffeeMachine;
	
	@Autowired
	private CoffeeMachineStatus status;
	
	@Autowired
	private ObjectSerialDeSerial objectSerialDeSerial;
	
	@Autowired
	private Producer producer;

	@Autowired
	private OnOffRepository repository;

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
	public CoffeeMachineStatus GetStatus() {
	//	CoffeeMachineStatus status = new CoffeeMachineStatus();
		status.setStatus(coffeeMachine.getPowerStatus());
		status.setWaterLevel(coffeeMachine.WaterTank().getWaterLevel());
		status.setBeanLevel(coffeeMachine.Hopper().getBeanAmount());

		Status s = new Status();
		s.setLocalDateTime(status.getDateTime());
		s.setStatus(coffeeMachine.getPowerStatus().toString());
		repository.save(s);

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
		return (coffeeMachine.GrindDose());
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
		return (coffeeMachine.GetIdentifier());
	}



}
