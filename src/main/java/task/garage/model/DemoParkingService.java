package task.garage.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import task.garage.error.ParkingException;
import task.garage.util.ParkingUtil;

@Service
public class DemoParkingService implements ParkingService {
	private static final Logger logger = LoggerFactory
			.getLogger(DemoParkingService.class);
	private static Map<String, Vehicle> vehicleMap = new HashMap<String, Vehicle>();
	private static Map<String, ParkingSpace> spaceMap = new HashMap<String, ParkingSpace>();
	private static Queue<String> mSpaceQ = new LinkedList<String>();
	private static Queue<String> cSpaceQ = new LinkedList<String>();
	private static int highestLevel = 0;
	private static long COUNT = 0L;
//	private ParkingSpace pSpace;


	public DemoParkingService() {
	}

	/**
	 * {@link ParkingService#createParkingSpace(ParkingSpaceDetails)}
	 */
	@Override
	public List<ParkingSpace> createParkingSpace(
			final ParkingSpaceDetails parkingSpaceDetails) {
		List<ParkingSpace> lst = null;
		if (null != parkingSpaceDetails) {
			final List<LevelInformation> levelDetails = parkingSpaceDetails
					.getLevelDetails();

			if (null != levelDetails && !levelDetails.isEmpty()) {
				lst = new ArrayList<ParkingSpace>();
				for (final LevelInformation levelInfo : levelDetails) {
					final String level = levelInfo.getLevel();
					final Map<Type, String> typeInformation = levelInfo
							.getTypeInformation();

					for (final Entry<Type, String> entry : typeInformation
							.entrySet()) {
						lst.addAll(this.createSpace(level, entry.getKey()
								.toString(), entry.getValue()));
					}
				}
			}
		}

		return lst;
	}

	/**
	 * {@link ParkingService#vehicleCheckIn(String, Type)}
	 */
	@SuppressWarnings("static-access")
	@Override
	public ParkingSpace vehicleCheckIn(String id, Type type) {

		if (!this.vehicleMap.containsKey(id)) {

			Vehicle vehicle = new Vehicle();
			vehicle.setId(id);
			vehicle.setType(type);
			vehicle.setInGarage(true);
			Queue<String> qPerType = this.getQPerType(type);
			if (null != qPerType) {

				if (!qPerType.isEmpty()) {
					String parkingId = qPerType.poll();
					vehicle.setParkingSpaceId(parkingId);
					this.vehicleMap.put(id, vehicle);
					logger.debug("vehicle inserted");
					return this.spaceMap.get(parkingId);
				} else {
					throw new ParkingException(String.format(
							"Space Not Available for %s", type.toString()));
				}
			} else {
				throw new ParkingException(String.format(
						"%s Type is not supported ", type.toString()));
			}

		} else {
			throw new ParkingException(String.format(
					"Vehicle No %s Alread exists", id));
		}
	}

	/**
	 * {@link ParkingService#vehicleCheckOut(String)}
	 */
	@Override
	public Vehicle vehicleCheckOut(String id) {
		if (!this.vehicleMap.containsKey(id)) {
			throw new ParkingException(String.format(
					"Vehicle No %s doesnot exist", id));
		} else {
			Vehicle vehicle = this.vehicleMap.get(id);
			vehicle.setInGarage(false);
			Type type = vehicle.getType();
			Queue<Object> qPerType = this.getQPerType(type);
			if (null != qPerType) {
				qPerType.add(vehicle.getParkingSpaceId());
			} else {
				throw new ParkingException(String.format(
						"%s Type is not supported ", type.toString()));
			}
			vehicleMap.remove(id);
			logger.debug("vehicle Checked Out");
			return vehicle;
		}
	}

	/**
	 * {@link ParkingService#findVehicleLocationById(String)}
	 */
	@Override
	public ParkingSpace findVehicleLocationById(String id) {
		if (!this.vehicleMap.containsKey(id)) {
			throw new ParkingException(String.format(
					"Vehicle No %s doesnot exist", id));
		} else {
			Vehicle vehicle = this.vehicleMap.get(id);
			return spaceMap.get(vehicle.getParkingSpaceId());
		}
	}

	/**
	 * {@link ParkingService#findAllFreeParkingSpace()}
	 */
	@Override
	public List<ParkingSpace> findAllFreeParkingSpace() {
		List<ParkingSpace> freeParkingSpace = null;

		if (!cSpaceQ.isEmpty() || !mSpaceQ.isEmpty()) {
			freeParkingSpace = new ArrayList<ParkingSpace>();
		} else {
			throw new ParkingException("All Parking Spaces are Occupied.");
		}

		if (null != freeParkingSpace) {
			if (!cSpaceQ.isEmpty()) {
				Iterator<String> iterator = cSpaceQ.iterator();
				while (iterator.hasNext()) {
					String pId = iterator.next();
					freeParkingSpace.add(spaceMap.get(pId));
				}
			}

			if (!mSpaceQ.isEmpty()) {
				Iterator<String> iterator = mSpaceQ.iterator();
				while (iterator.hasNext()) {
					String pId = iterator.next();
					freeParkingSpace.add(spaceMap.get(pId));
				}
			}
		}
		return freeParkingSpace;
	}

	/**
	 * {@link ParkingService#findAllVehicle()}
	 */
	@Override
	public List<Vehicle> findAllVehicle() {
		return this.vehicleMap.values().stream().collect(Collectors.toList());
	}

	@Override
	@SuppressWarnings("static-access")
	public List<ParkingSpace> createSpace(final String levelStr,
			final String type, final String numOfSpaces) {
		return this.createSpace(levelStr, Type.getType(type), numOfSpaces);

	}

	@SuppressWarnings("static-access")
	private List<ParkingSpace> createSpace(String levelStr, Type type,
			String numOfSpaces) {
		final int level = Integer.parseInt(levelStr);
		final int noSpaces = Integer.parseInt(numOfSpaces);

		List<ParkingSpace> lst = new ArrayList<ParkingSpace>();
		for (int i = 1; i <= noSpaces; i++) {
			final ParkingSpace pSpace = new ParkingSpace();
			String typeChar = ParkingUtil.getCharFromType(type);
			final String spaceId = level + typeChar + this.COUNT;
			pSpace.setSpaceId(spaceId);
			pSpace.setType(type);
			pSpace.setEmpty(true);
			this.spaceMap.put(spaceId, pSpace);
			lst.add(pSpace);

			Queue<String> qPerType = this.getQPerType(pSpace);
			if (null != qPerType) {
				qPerType.add(pSpace.getSpaceId());
			}
			this.COUNT++;
			logger.debug(String.format("%s  vehicle Created", spaceId));
		}

		if (this.highestLevel < level) {
			this.highestLevel = level;
		}

		return lst;
	}

	private <T> Queue<T> getQPerType(final ParkingSpace pSpace) {
		final Type type = pSpace.getType();
		return this.getQPerType(type);
	}

	@SuppressWarnings("unchecked")
	private <T> Queue<T> getQPerType(final Type type) {
		switch (type) {
		case CAR:
			return (Queue<T>) cSpaceQ;
		case MOTORCYCLE:
			return (Queue<T>) mSpaceQ;
		}

		return null;
	}

	@Override
	public List<ParkingSpace> addParkingSpace(String numOflevel,
			String numCarType, String numMotorCycleType) {
		throw new ParkingException(
				"Right now adding parking spaces to existing parking space is not supported..");
	}

}
