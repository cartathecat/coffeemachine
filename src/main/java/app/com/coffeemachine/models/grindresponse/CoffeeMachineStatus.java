package app.com.coffeemachine.models.grindresponse;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import app.com.basic.PowerStatus;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@JsonPropertyOrder({ "datetime", "status", "waterLevel", "beanLevel"})
public class CoffeeMachineStatus {

	private LocalDateTime timeStamp;
	private PowerStatus status;
	private CoffeeMachineWaterLevel waterLevel;
	private CoffeeMachineBeanLevel beanLevel;
	
	public CoffeeMachineStatus() {
		log.info("Creating Waterlevel object");
		this.waterLevel = new CoffeeMachineWaterLevel();
		this.beanLevel = new CoffeeMachineBeanLevel();

	}
	
	public CoffeeMachineStatus(PowerStatus s) {
		this.waterLevel = new CoffeeMachineWaterLevel();
		this.beanLevel = new CoffeeMachineBeanLevel();

		this.timeStamp = LocalDateTime.now();
		this.status = s;		
	}
	public CoffeeMachineStatus(LocalDateTime t, PowerStatus s) {
		this.waterLevel = new CoffeeMachineWaterLevel();
		this.beanLevel = new CoffeeMachineBeanLevel();

		this.timeStamp = t;
		this.status = s;		
	}

	public void setStatus(PowerStatus s) {
		this.timeStamp = LocalDateTime.now();
		this.status = s;
	}
	public void setStatus(LocalDateTime t, PowerStatus s) {
		this.timeStamp = t;
		this.status = s;
	}
	
	public PowerStatus getStatus() {
		return this.status;
	}
	
	@JsonProperty("datetime")
	public LocalDateTime getDateTime() {
		return this.timeStamp;
	}
	
	@Override
	public String toString() {
		return String.format("CoffeeMachineStatus [status=%s, timeStamp=%s, water=%s]",
				this.status, this.timeStamp, this.waterLevel);
	}

	// Water Level
	public void setWaterLevel(int w) {
		this.waterLevel.setWaterLevel(w);
	}

	public CoffeeMachineWaterLevel getWaterLevel() {
		return this.waterLevel;
	}

	// Bean Level
	public void setBeanLevel(double b) {
		this.beanLevel.setBeanLevel(b);
	}

	public CoffeeMachineBeanLevel getBeanLevel() {
		return this.beanLevel;
	}
}
