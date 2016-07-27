package task.garage.model;

import org.springframework.stereotype.Component;

@Component
public class ParkingSpace {

	private String parkingSpaceId;
	private Type type;
	private boolean isEmpty;

	public String getSpaceId() {
		return parkingSpaceId;
	}

	public void setSpaceId(String spaceId) {
		this.parkingSpaceId = spaceId;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public boolean isEmpty() {
		return isEmpty;
	}

	public void setEmpty(boolean isEmpty) {
		this.isEmpty = isEmpty;
	}

}
