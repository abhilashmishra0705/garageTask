package task.garage.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "total", "parkingSlots" })
public class CreateParkingSpaceResponse {

	protected long total;
	protected List<ParkingSpace> parkingSlots;

	public CreateParkingSpaceResponse(final long total,
			final List<ParkingSpace> list) {
		this.total = total;
		this.parkingSlots = list;
	}

	@JsonProperty("total")
	public long getTotal() {
		return total;
	}

	@JsonProperty("total")
	public void setGrid(final long total) {
		this.total = total;
	}

	@JsonProperty("parkingSlots")
	public List<ParkingSpace> getParkingSlots() {
		return this.parkingSlots;
	}

	@JsonProperty("parkingSlots")
	public void setParkingSlots(List<ParkingSpace> slots) {
		parkingSlots = slots;
	}

}
