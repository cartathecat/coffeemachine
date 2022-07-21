package app.com.breville;

import app.com.coffeemachine.entities.JsonWaterTank;
import lombok.extern.slf4j.Slf4j;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class WaterTank implements IWaterTank {

	//@Autowired
	public JsonWaterTank jsonWaterTank;

	public void setJsonWaterTank(JsonWaterTank w) {
		this.jsonWaterTank = w;
	}
	public JsonWaterTank getJsonWaterTank() {
		return this.jsonWaterTank;
	}

	public WaterTank() {
		log.info("WaterTank");
	}
	
	@Override
	public void fillWaterTank(int w) {
		log.info("Fill water tank");
	//	this.waterLevel += w;
		this.jsonWaterTank.setWaterLevel(this.jsonWaterTank.getWaterLevel() + w);
	}

	@Override
	public void emptyWaterTank() {
		log.info("Empty tank");
	}

	public int getCapacity() {
		log.info("Get Capacity Level");
	//	return this.CAPACITY;
		return this.jsonWaterTank.getCAPACITY();
	}
	private void setCapacity(int c) {

	//	this.CAPACITY = c;
		this.jsonWaterTank.setCAPACITY(c);
	}
	
	@Override
	public int getWaterLevel() {
		log.info("Get Water Level");
	//	return this.waterLevel;
		return this.jsonWaterTank.getWaterLevel();
	}
	private void setWaterLevel(int w) {
	//	this.waterLevel = w;
		this.jsonWaterTank.setWaterLevel(w);
	}
	
	@Override
	public String toString() {

		return String.format("WaterTank [Capacity=%s, level=%s]",
				this.jsonWaterTank.getCAPACITY(),
				this.jsonWaterTank.getWaterLevel());
	}
	
}
