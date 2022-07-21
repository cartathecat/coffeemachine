package app.com.breville;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import app.com.coffeemachine.entities.JsonHopperCapacity;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class Hopper implements IHopper {

	@Autowired
	private GrindWheel grindWheel;

	private JsonHopperCapacity jsonHopperCapacity;

	public void setJsonHopperCapacity(JsonHopperCapacity h) {
		this.jsonHopperCapacity = h;
	}

	public JsonHopperCapacity getJsonHopperCapacity() {
		return this.jsonHopperCapacity;
	}

	// amount in grams
	//static final int HOPPERCAPACITY = 250;
	//private int hopperCapacity = 250;
	//private double beanAmount;

	public Hopper() {
		log.info("Hopper");
	//	setBeanAmount(0.0);
	}

	public double getHopperCapacity() {
		return this.jsonHopperCapacity.getHopperCapacity();
	}
	private void setHopperCapacity(double c) {
		this.jsonHopperCapacity.setHopperCapacity(c);
	}
	
	public double getBeanAmount() {
		return this.jsonHopperCapacity.getBeanAmount();
	}

	private void setBeanAmount(double v) {
		if (this.jsonHopperCapacity.getBeanAmount() + v > this.jsonHopperCapacity.getHopperCapacity()) {
			this.jsonHopperCapacity.setBeanAmount(this.jsonHopperCapacity.getHopperCapacity());
		} else {
			this.jsonHopperCapacity.setBeanAmount(this.jsonHopperCapacity.getBeanAmount() + v);
		}
	}	
	
	@Override
	public void FillHopper(double coffeeBeans) {
		log.info("Adjust hopper amount");
		setBeanAmount(coffeeBeans);
	}

	@Override
	public void EmptyHopper() {
		log.info("Empty hopper");
		setBeanAmount(0);
	}

}
