package app.com.breville;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import app.com.basic.BasicCoffeeMachine;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import app.com.coffeemachine.models.grindresponse.GrindResponse;

@Slf4j
@Component
public class BrevilleBaristaExpress extends BasicCoffeeMachine
						implements IBrevilleBaristaExpress {

	private final Map<UUID, GrindResponse> grindRequests = new HashMap<UUID, GrindResponse>();
				
	@Autowired
	private Hopper hopper;
	@Autowired
	private GrindWheel grindWheel;
	@Autowired
	private WaterTank waterTank;
	@Autowired
	private DoseWheel doseWheel;
	
	private CoffeeMachineOperation coffeeOperation;
	public CoffeeMachineOperation getCoffeeMachineOperation() {
		return this.coffeeOperation;
	}
	
	private boolean stopCoffeeGrinding;
	public void setStopCoffeeGrinding(boolean v) {
		this.stopCoffeeGrinding = v;
	}
	public boolean getStopCoffeeGrinding() {
		return this.stopCoffeeGrinding;
	}
	
	public Hopper Hopper() {
		return this.hopper;
	}
	public WaterTank WaterTank() {
		return this.waterTank;
	}
	public GrindWheel GrindWheel() {
		return this.grindWheel;
	}
	
	public BrevilleBaristaExpress() {
		log.info("BrevilleBaristaExpress");
		coffeeOperation = CoffeeMachineOperation.GRINDING_NOT_GRINDING;
	}

	@Override
	public GrindResponse GrindDose() {
		log.info("Grind dose");
		log.info("Water Level  : {} m/l" , this.waterTank.getWaterLevel());
		log.info("Hopper Level : {} grams", new BigDecimal(this.hopper.getBeanAmount()).setScale(2, RoundingMode.HALF_DOWN));
		log.info("Grind Wheel  : {}" , this.grindWheel.getGrindValue());
		log.info("Dose Wheel   : {}" , this.doseWheel.getDoseValue());
		
	/*
	 * Hopper 
	 * Level (grams)   GrindWheel              Dose
	 * 20              16 - course, 1 - fine   0.0 - low, 10.0 - high 
	 * 	
	 */
		setStopCoffeeGrinding(false);

		double gramsPerSecond = 0.2;
		
		coffeeOperation = CoffeeMachineOperation.GRINDING_STARTED;

		ScheduledExecutorService ex = Executors.newScheduledThreadPool(1);
		
		Runnable dispense = new Runnable() {
			int beanGrinderTimer = 8;
			
			@Override
			public void run() {
				log.info("Grinding beans ... " + beanGrinderTimer);
				beanGrinderTimer--;
				hopper.FillHopper(gramsPerSecond * -1);
				if ((beanGrinderTimer <= 0) || (hopper.getBeanAmount() <= 0) || (getStopCoffeeGrinding())) {
					if (getStopCoffeeGrinding()) {
						log.info("Coffee grinding requested to stop");
						coffeeOperation = CoffeeMachineOperation.GRINDING_INTERRUPTED;
					} else {
						coffeeOperation = CoffeeMachineOperation.GRINDING_COMPLETE;						
					}
					log.info("Coffee beans ground ...");
					ex.shutdown();
				}
			}
		};
		ex.scheduleAtFixedRate(dispense, 0, 1, TimeUnit.SECONDS);
		GrindResponse g = new GrindResponse(coffeeOperation);
		
		UUID uuid = UUID.randomUUID();
		String link = String.format("coffeebeans/%s", uuid.toString());
		g.setLocation(link);
		grindRequests.put(uuid, g);
		
		return g;
		
	}
	
	public HttpHeaders GetIdentifier() {
		HttpHeaders header = new HttpHeaders();
		return header;
	}


}
