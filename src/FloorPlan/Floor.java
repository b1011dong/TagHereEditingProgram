package FloorPlan;

public class Floor {
	
	/** String for floor name */
	private String name;
	/** String for floor information */
	private String information;
	/** FloorPlan for floor's floor plan */
	private FloorPlan floorPlan;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getInformation() {
		return information;
	}
	public void setInformation(String information) {
		this.information = information;
	}
	public FloorPlan getFloorPlan() {
		return floorPlan;
	}
	public void setFloorPlan(FloorPlan floorPlan) {
		this.floorPlan = floorPlan;
	}
}
