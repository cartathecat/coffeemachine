package app.com.basic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//import app.com.basic.PowerStatus.POWERSTATUS;
import app.com.breville.Hopper;

@Component
public class BasicCoffeeMachine implements IBasicCoffeeMachine {

	private final static Logger log = LoggerFactory.getLogger(BasicCoffeeMachine.class);
	
	private PowerStatus powerStatus;

	public BasicCoffeeMachine() {
		log.info("BasicCoffeeMachine");
		this.powerStatus = PowerStatus.OFF;
	}

	private void setPowerStatus(PowerStatus s) {
		this.powerStatus = s;
	}

	@Override
	public void PowerOnOff() {
		if (this.getPowerStatus() == powerStatus.OFF) {
			setPowerStatus(powerStatus.ON);
		} else {
			setPowerStatus(powerStatus.OFF);
		}
	}
	
	@Override
	public PowerStatus getPowerStatus() {
		return this.powerStatus;
	}
	
	@Override
	public void DispenseHotWater() {
		log.info("Dispense Hot Water");
	}

}
