package app.com.basic;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BasicCoffeeMachine implements IBasicCoffeeMachinePower,IBasicCoffeeMachineWater {

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
		if (this.getPowerStatus() == PowerStatus.OFF) {
			setPowerStatus(PowerStatus.ON);
		} else {
			setPowerStatus(PowerStatus.OFF);
		}
	}
	
	@Override
	public PowerStatus getPowerStatus() {
		return this.powerStatus;
	}
	
	@Override
	public void DispenseWater() {
		log.info("Dispense Hot Water");
	}

}
