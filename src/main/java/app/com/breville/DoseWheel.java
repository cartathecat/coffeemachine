package app.com.breville;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class DoseWheel implements IDoseWheel {

	@Min(value=(long) 0.1, message="must be equal or greater than 0.1")
	@Max(value=(long) 10.0, message="must be equal or less than 10")
	private double doseValue;
	
	public DoseWheel() {
		log.debug("DoseWheel");
		this.doseValue = 1.0;
	}
	
	@Override
	public void setDoseValue(double v) {
		this.doseValue = v;
	}

	@Override
	public double getDoseValue() {
		return this.doseValue;
	}

}
