package task.garage.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import task.garage.error.ParkingException;
import task.garage.model.CreateParkingSpaceResponse;
import task.garage.model.ParkingService;
import task.garage.model.ParkingSpace;
import task.garage.model.Type;
import task.garage.model.Vehicle;

@RestController
@RequestMapping("/garage")
public final class GarageController {

	private final ParkingService service;
	private static final Logger logger = LoggerFactory
			.getLogger(GarageController.class);
	
	@Autowired
	public GarageController(ParkingService service) {
		this.service = service;
	}
	
	@RequestMapping("/equalParkingSpaces")
	 public CreateParkingSpaceResponse createEqualSpaces(
	   @RequestParam(value = "levels", required = true) int levels,
	   @RequestParam(value = "mSpaces", required = true) int mSpaces,
	   @RequestParam(value = "cSpaces", required = true) int cSpaces) {
	  List<ParkingSpace> lst = new ArrayList<ParkingSpace>();
	  for (int i = 1; i <= levels; i++) {
	   if (mSpaces > 0) {
	    lst.addAll(this.service.createSpace(String.valueOf(i),
	      Type.MOTORCYCLE.toString(), String.valueOf(mSpaces)));
	   }
	   if (cSpaces > 0) {
	    lst.addAll(this.service.createSpace(String.valueOf(i),
	      Type.CAR.toString(), String.valueOf(cSpaces)));
	   }
	  }

	  return new CreateParkingSpaceResponse(lst.size(), lst);
	 }

	@RequestMapping("/vehicleCheckIn")
	public ParkingSpace vehicleIn(
			@RequestParam(value = "type", required = true) String vType,
			@RequestParam(value = "id", required = true) String vId) {

		return this.service.vehicleCheckIn(vId, Type.getType(vType));
	}

	@RequestMapping("/vehicleCheckOut")
	public Vehicle vehicleOut(
			@RequestParam(value = "id", required = true) String vId) {

		return this.service.vehicleCheckOut(vId);
	}

	@RequestMapping("/vehiclePresent")
	public ParkingSpace locateVehicle(
			@RequestParam(value = "id", required = true) String vId)
			throws Exception {
		return this.service.findVehicleLocationById(vId);
	}

	@RequestMapping(value = "/freeSpaces/", method = RequestMethod.GET)
	public List<ParkingSpace> calculateFreeSpaces() {
		return this.service.findAllFreeParkingSpace();
	}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	public ParkingException handleException(ParkingException ex) {
		logger.error("Handling error with message: {}", ex.getMessage());
		return ex;
	}
}
