package app.com.breville;

import java.io.IOException;

public interface IWaterTank {

	public void fillWaterTank(int w);

	public void emptyWaterTank();

	public int getWaterLevel();
	
}
