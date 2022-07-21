package app.com.coffeemachine.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JsonWaterTank {

    @JsonProperty("capacity")
    private int CAPACITY = 2000;

    @JsonProperty("waterLevel")
    private int waterLevel;

}
