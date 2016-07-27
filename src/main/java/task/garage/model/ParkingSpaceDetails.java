package task.garage.model;

import java.util.List;

/*
 * This contains the information of a list of parking space object entries
 */
public class ParkingSpaceDetails {

	private List<LevelInformation> levelDetails;

	/**
	 * @return the levelDetails
	 */
	public List<LevelInformation> getLevelDetails() {
		return levelDetails;
	}

	/**
	 * @param levelDetails
	 *            the levelDetails to set
	 */
	public void setLevelDetails(List<LevelInformation> levelDetails) {
		this.levelDetails = levelDetails;
	}

}
