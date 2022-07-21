package app.com.coffeemachine.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JsonHopperCapacity {

    @JsonProperty("hopperCapacity")
    private double hopperCapacity = 250.0;

    @JsonProperty("beanAmount")
    private double beanAmount;

}
