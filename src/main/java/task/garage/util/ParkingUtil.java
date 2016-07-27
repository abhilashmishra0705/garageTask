package task.garage.util;

import task.garage.model.Type;

public class ParkingUtil {

	public static String getCharFromType(final Type type) {
		switch (type) {
		case CAR:
			return ParkingConstants.TYPE_CARS_CHAR;
		case MOTORCYCLE:
			return ParkingConstants.TYPE_MOTOR_CYCLE_CHAR;
		default:
			return ParkingConstants.TYPE_CARS_CHAR;
		}
	}
}
