package app.com.basic;

//import app.com.basic.PowerStatus.POWERSTATUS;

public interface IBasicCoffeeMachinePower {

	public void PowerOnOff();
	public PowerStatus getPowerStatus();
	
	//public void DispenseHotWater(); - refactored to its own interface
	// Breville Barista Express machine
	
	// grind size
	// beam hopper
	
	//guage
	// grind amount
	// filter size - single/double
	// program ?
	// one cup
	// two cups
	
	// hot water spout
	// milk steamer
	
	// ground coffee dispenser
	// hot water disepenser
	
	
}
