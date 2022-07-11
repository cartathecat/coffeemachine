package app.com.breville;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.beans.factory.annotation.Autowired;

//@EntityScan
public class Hopper implements IHopper {

	private final static Logger log = LoggerFactory.getLogger(Hopper.class);

	@Autowired
	private GrindWheel grindWheel;
	
	// amount in grams
	//static final int HOPPERCAPACITY = 250;
	private int hopperCapacity = 250;
	private double beanAmount;

	public Hopper() {
		log.info("Hopper");
		setBeanAmount(0.0);
	}

	public int getHopperCapacity() {
		return this.hopperCapacity;
	}
	private void setHopperCapacity(int c) {
		this.hopperCapacity = c;
	}
	 
	
	public double getBeanAmount() {
		return this.beanAmount;
	}
	private void setBeanAmount(double v) {
		if (this.beanAmount + v > hopperCapacity) {
			this.beanAmount = hopperCapacity;
		} else {
			this.beanAmount += v;
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
