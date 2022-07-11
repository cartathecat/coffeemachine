package app.com.coffeemachine.storge;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import app.com.breville.Hopper;
import app.com.breville.WaterTank;

public class ObjectSerialDeSerial {

	private final static Logger log = LoggerFactory.getLogger(ObjectSerialDeSerial.class);
	
	//@Autowired
	//public WaterTank waterTank;
	
	public ObjectSerialDeSerial() {
		log.info("ObjectSerialDeSerial");
	}
	
	public void serialize(Object o) {
		log.info("Serialize Water Tanker object");
		String fileName = "";
		
		String clsName = o.getClass().getSimpleName();
		switch (clsName) {
		case "WaterTank":
			log.info("Serialize Water Tanker object");
			fileName = "data/watertank.json";
			log.info("Tank " + ((WaterTank) o).getWaterLevel());
			break;
		case "Hopper":
			log.info("Serialize Hopper object");
			fileName = "data/hopper.json";
			log.info("Hopper " + ((Hopper) o).getBeanAmount());
			break;
			
		default:
			break;
		}

		File classFile = null;
		try {
			classFile = new ClassPathResource(fileName).getFile();
			ObjectMapper objectMapper = new ObjectMapper();
			if (o.getClass() == WaterTank.class) {
				objectMapper.writeValue(classFile, (WaterTank)o);
			}
			if (o.getClass() == Hopper.class) {
				objectMapper.writeValue(classFile, (Hopper)o);
			}
			
		} catch (Exception e) {
			System.out.println("Error serialising :" + fileName);
		}
		
	}
	
	public Object deSerialize(Class<?> clazz) {
		String fileName = "";
	
		String clsName = clazz.getSimpleName();
		switch (clsName) {
		case "WaterTank":
			log.info("DeSerialize Water Tanker object");
			fileName = "data/watertank.json";
			break;
		case "Hopper":
			log.info("DeSerialize Hopper object");
			fileName = "data/hopper.json";
			break;
			
		default:
			break;
		}

		File classFile = null;
		Object o = null;
		try {
			classFile = new ClassPathResource(fileName).getFile();
			ObjectMapper objectMapper = new ObjectMapper();
			if (clazz == WaterTank.class) {
				o = (WaterTank) objectMapper.readValue(classFile, clazz);
			}
			if (clazz == Hopper.class) {
				o = (Hopper) objectMapper.readValue(classFile, clazz);
			}

		} catch (Exception e) {
			
			System.out.println("Error deserialzing :" + clsName);
		}
		
		return o;
	}
	
}
