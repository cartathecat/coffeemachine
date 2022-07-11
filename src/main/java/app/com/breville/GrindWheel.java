package app.com.breville;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GrindWheel implements IGrindWheel {

	private final static Logger log = LoggerFactory.getLogger(GrindWheel.class);
	
	@Min(value=1, message="must be equal or greater than 1")
	@Max(value=16, message="must be equal or less than 16")
	private double grindWheelValue;
			
	public GrindWheel() {
		this.grindWheelValue = 7.5; 
	}
	
	@Override
	public double getGrindValue() {
		return this.grindWheelValue;
	}

	@Override
	public void setGrindValue(double v) {
		this.grindWheelValue = v;
	}
	
}
