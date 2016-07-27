package task.garage.model;

public class Vehicle {

	private String id;
	private Type type;
	private String parkingSpaceId;

	public String getParkingSpaceId() {
		return parkingSpaceId;
	}

	public void setParkingSpaceId(String parkingSpaceId) {
		this.parkingSpaceId = parkingSpaceId;
	}

	private boolean inGarage;

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isInGarage() {
		return inGarage;
	}

	public void setInGarage(boolean inGarage) {
		this.inGarage = inGarage;
	}

}
