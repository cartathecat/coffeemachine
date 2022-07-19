package app.com.breville;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class GrindWheel implements IGrindWheel {

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
