package app.com.coffeemachine.models.grindresponse;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import app.com.breville.CoffeeMachineOperation;

@JsonPropertyOrder({"status", "datetime", "location", "groundAmount"})
public class GrindResponse {

	private LocalDateTime timeStamp;
	private CoffeeMachineOperation status;
	private int groundAmount;
	private String location;

	public GrindResponse() {
		super();
		this.timeStamp = LocalDateTime.now();
	}

	public GrindResponse(CoffeeMachineOperation s) {
		super();
		this.timeStamp = LocalDateTime.now();
		this.status = s;
		this.groundAmount = 0;
	}

	public GrindResponse(LocalDateTime t,CoffeeMachineOperation s) {
		super();
		this.status = s;
		this.timeStamp = t;
		this.groundAmount = 0;
	}

	public CoffeeMachineOperation getStatus() {
		return this.status;
	}

	public void setMessage(CoffeeMachineOperation s) {
		this.status = s;
	}

	public int getGroundAmount() {
		return this.groundAmount;
	}

	public void setGroundAmount(int v) {
		this.groundAmount = v;
	}
	
	@JsonProperty("datetime")
	public LocalDateTime getDateTime() {
		return this.timeStamp;
	}
	
	public String getLocation() {
		return this.location;
	}
	public void setLocation(String l) {
		this.location = l;
	}
	
	@Override
	public String toString() {
		return String.format("Response [status=%s]", this.status);
	}
	
}
