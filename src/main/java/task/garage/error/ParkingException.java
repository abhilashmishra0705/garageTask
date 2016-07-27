package task.garage.error;

/**
 * This exception is thrown when the requested.
 */
public class ParkingException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ParkingException() {
		super();
	}

	public ParkingException(String message, Throwable cause) {
		super(message, cause);
	}

	public ParkingException(String message) {
		super(message);
	}

	public ParkingException(Throwable cause) {
		super(cause);
	}
}
