package app.com.breville;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

public class WaterTank implements IWaterTank {

	private final static Logger log = LoggerFactory.getLogger(WaterTank.class);
	
	// m/l
	@JsonProperty("capacity")
	private int CAPACITY = 2000;
	@JsonProperty("waterLevel")
	private int waterLevel;
	
	public WaterTank() {
		log.info("WaterTank");
		this.waterLevel = 0;		
	}
	
	@Override
	public void fillWaterTank(int w) {
		log.info("Fill water tank");
		this.waterLevel += w;
	}

	@Override
	public void emptyWaterTank() {
		log.info("Empty tank");
	}

	public int getCapacity() {
		log.info("Get Capacity Level");
		return this.CAPACITY;
	}
	private void setCapacity(int c) {
		this.CAPACITY = c;
	}
	
	@Override
	public int getWaterLevel() {
		log.info("Get Water Level");
		return this.waterLevel;
	}
	private void setWaterLevel(int w) {
		this.waterLevel = w;
	}
	
	@Override
	public String toString() {
		return String.format("WaterTank [Capacity=%s, level=%s]", CAPACITY, this.waterLevel);
	}
	
}
