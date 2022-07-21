package app.com.coffeemachine.storge;

import java.io.File;

import app.com.coffeemachine.entities.JsonHopperCapacity;
import app.com.coffeemachine.entities.JsonWaterTank;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import app.com.breville.Hopper;
import app.com.breville.WaterTank;

@Slf4j
public class ObjectSerialDeSerial implements IObjectSerialDeSerial {

	public ObjectSerialDeSerial() {
		log.info("ObjectSerialDeSerial");
	}
	
	public void serialize(Object o) {
		log.info("Serialize Water Tanker object");
		String fileName = "";
		
		String clsName = o.getClass().getSimpleName();
		switch (clsName) {
		case "JsonWaterTank":
			log.info("Serialize Water Tanker object");
			fileName = "data/watertank.json";
			log.info("Tank " + ((JsonWaterTank) o).getCAPACITY());
			log.info("Tank " + ((JsonWaterTank) o).getWaterLevel());

			break;
		case "JsonHopperCapacity":
			log.info("Serialize Hopper object");
			fileName = "data/hopper.json";
			log.info("Hopper " + ((JsonHopperCapacity) o).getBeanAmount());
			break;
			
		default:
			break;
		}

		File classFile = null;
		try {
			classFile = new ClassPathResource(fileName).getFile();
			ObjectMapper objectMapper = new ObjectMapper();
			if (o.getClass() == JsonWaterTank.class) {
				objectMapper.writeValue(classFile, (JsonWaterTank)o);
			}
			if (o.getClass() == JsonHopperCapacity.class) {
				objectMapper.writeValue(classFile, (JsonHopperCapacity)o);
			}
			
		} catch (Exception e) {
			log.error("Error serialising : {s}", e.getMessage());
		}
		
	}
	
	public Object deSerialize(Class<?> clazz) {
		String fileName = "";
	
		String clsName = clazz.getSimpleName();
		switch (clsName) {
		case "JsonWaterTank":
			log.info("DeSerialize Water Tanker object");
			fileName = "data/watertank.json";
			break;
		case "JsonHopperCapacity":
			log.info("DeSerialize Hopper object");
			fileName = "data/hopper.json";
			break;
			
		default:
			break;
		}

		if (fileName.isEmpty()) {
			throw new IllegalArgumentException("clsName not set");
		}
		File classFile = null;
		Object o = null;
		try {
			classFile = new ClassPathResource(fileName).getFile();
			ObjectMapper objectMapper = new ObjectMapper();
			if (clazz == JsonWaterTank.class) {
				o = (JsonWaterTank) objectMapper.readValue(classFile, clazz);
			}
			if (clazz == JsonHopperCapacity.class) {
				o = (JsonHopperCapacity) objectMapper.readValue(classFile, clazz);
			}

		} catch (Exception e) {
			log.warn("Deserialing : {}",e.getMessage() );
		}
		
		return o;
	}
	
}
