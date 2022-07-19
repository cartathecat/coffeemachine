package app.com.coffeemachine.controller;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import app.com.coffeemachine.service.CoffeeMachineService;

@Slf4j
@RestController
@RequestMapping(path="coffeemachine/v1")
public class BasicCoffeeMachineController {

	@Autowired
	private CoffeeMachineService coffeeMachineService;
	
	public BasicCoffeeMachineController() {
		log.info("BasicCoffeeMachineController");
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/onoff",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> onOff() {
		this.coffeeMachineService.PowerButton();
		return new ResponseEntity<>(coffeeMachineService.GetStatus(), HttpStatus.OK);
		
	}

	@RequestMapping(method = RequestMethod.GET, value = "/status", 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> status() {
		return new ResponseEntity<>(coffeeMachineService.GetStatus(), HttpStatus.OK);
		
	}
	
}
