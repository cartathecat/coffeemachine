package app.com.coffeemachine.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import app.com.coffeemachine.models.grindresponse.GrindResponse;
import app.com.coffeemachine.service.CoffeeMachineService;

@Slf4j
@RestController
@RequestMapping(path="coffeemachine/v1")
public class BrevilleCoffeeMachineController {

	@Autowired
	private CoffeeMachineService coffeeMachineService;
	
	public BrevilleCoffeeMachineController() {
		log.info("CoffeeMachineController");
	}

	/**
	 * Add coffee beans to the hopper, if there is one
	 * 
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/addcoffeebeans/{coffeeBeans}", 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> addCoffeeBeans(@PathVariable int coffeeBeans) {
		this.coffeeMachineService.FillHopper(coffeeBeans);
		return new ResponseEntity<>("addCoffeeBeans", HttpStatus.OK);
		
	}

	/**
	 * Add water to the water reservoir, if there is one
	 * 
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/addwater/{water}", 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> addWater(@PathVariable int water) {
		this.coffeeMachineService.FillWaterTank(water);
		return new ResponseEntity<>("addWater", HttpStatus.OK);
		
	}

	@RequestMapping(method = RequestMethod.GET, value = "/grinddose", 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> grinddose() {
		GrindResponse g = this.coffeeMachineService.GrindDose();
		//HttpHeaders headers = this.coffeeMachineService.GetIdentifier();
		//headers.add("Location", "/coffeebeans/12345");

		return new ResponseEntity<>(g, HttpStatus.ACCEPTED);
		
	}
	
}
