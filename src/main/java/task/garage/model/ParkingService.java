package task.garage.model;

import java.util.List;

/**
 * This interface declares the methods that provides CRUD operations for
 * {@link task.garage.model.ParkingSpace} objects.
 */
public interface ParkingService {

	/**
	 * Creates parkingSpace entries.
	 * 
	 * @param parkingSpaceDetails
	 *            The details of the to be created parkingSpace entry.
	 * @return The details of the created parkingSpace entry.
	 */
	List<ParkingSpace> createParkingSpace(ParkingSpaceDetails parkingSpace);

	/**
	 * 
	 * @param level
	 * @param type
	 * @param num
	 * @return
	 */
	List<ParkingSpace> createSpace(final String level, final String type,
			final String num);

	/**
	 * 
	 * @param level
	 * @param type
	 * @param num
	 * @return
	 */
	List<ParkingSpace> addParkingSpace(final String numOflevel,
			final String numCarType, final String numMotorCycleType);

	/**
	 * Assigns a PAkring space for this vehicle
	 * 
	 * @param id
	 *            vehicle License no
	 * @param type
	 *            Type of Vehicle
	 * @return returns the Assigned Parking space
	 */
	ParkingSpace vehicleCheckIn(final String id, final Type type);

	/**
	 * Detaches the PAkring space of this vehicle
	 * 
	 * @param id
	 *            vehicle License no
	 * @return returns the Checked out Vehicle
	 */
	Vehicle vehicleCheckOut(final String id);

	/**
	 * Finds all parkingSpace entries.
	 * 
	 * @return The information of all parkingSpace entries.
	 */
	List<ParkingSpace> findAllFreeParkingSpace();

	/**
	 * Finds all vehicle entries.
	 * 
	 * @return The information of all vehicle entries.
	 */
	List<Vehicle> findAllVehicle();

	/**
	 * Finds a Vehicle Location by it's Id.
	 * 
	 * @param id
	 *            The id of the requested vehcile entry.
	 * @return The information of the assigned parking space.
	 * @throws VehicleNotFoundException
	 *             if no Vehicle entry is found.
	 */
	ParkingSpace findVehicleLocationById(String id);
}
