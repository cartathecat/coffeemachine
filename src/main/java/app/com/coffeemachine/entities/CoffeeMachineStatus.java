package app.com.coffeemachine.entities;

import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import app.com.basic.PowerStatus;
//import app.com.basic.PowerStatus.POWERSTATUS;
import app.com.breville.WaterTank;

@EntityScan
@JsonPropertyOrder({ "datetime", "status", "waterLevel", "beanLevel"})
public class CoffeeMachineStatus {

	private LocalDateTime timeStamp;
	private PowerStatus status;
	private WaterLevel waterLevel;
	private BeanLevel beanLevel;
	
	public CoffeeMachineStatus() {
		System.out.println("Creating Waterlevel object");
		this.waterLevel = new WaterLevel();
		this.beanLevel = new BeanLevel();

	}
	
	public CoffeeMachineStatus(PowerStatus s) {
		this.waterLevel = new WaterLevel();
		this.beanLevel = new BeanLevel();

		this.timeStamp = LocalDateTime.now();
		this.status = s;		
	}
	public CoffeeMachineStatus(LocalDateTime t, PowerStatus s) {
		this.waterLevel = new WaterLevel();
		this.beanLevel = new BeanLevel();

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
		return String.format("CoffeeMachineStatus [status=%s, timeStamp=%s, water=%s]", this.status, this.timeStamp, this.waterLevel);
	}

	// Water Level
	public void setWaterLevel(int w) {
		this.waterLevel.setWaterLevel(w);
	}
	public WaterLevel getWaterLevel() {
		return this.waterLevel;
	}

	// Bean Level
	public void setBeanLevel(double b) {
		this.beanLevel.setBeanLevel(b);
	}
	public BeanLevel getBeanLevel() {
		return this.beanLevel;
	}
		
}

// Water Level
class WaterLevel {
	
	private int waterLevel;
	private String unit;
	
	WaterLevel() {
		this.unit = "Litres";
	}
	
	public String getUnit() {
		return this.unit;
	}
	
	public void setWaterLevel(int w) {
		this.waterLevel = w;
	}
	public int getWaterLevel() {
		return this.waterLevel;
	}
}

// Beans
class BeanLevel {
	
	private double beanLevel;
	private String unit;
	
	BeanLevel() {
		this.unit = "Grams";
	}
	
	public String getUnit() {
		return this.unit;
	}
	
	public void setBeanLevel(double b) {
		this.beanLevel = b;
	}
	public double getBeanLevel() {
		return this.beanLevel;
	}
	
}