package FloorPlan;

import java.awt.Point;
import java.util.ArrayList;

public class Building {

	/** String for building name */
	private String name;
	/** Point for building position in map */
	private Point position;
	/** String for building information */
	private String information;
	/** Building's floors */
	private ArrayList<Floor> floors;
	
	public Building(String name) {
		setName(name);
		
		floors = new ArrayList<Floor>();
	}
	
	public Building(String name, Point position, String info) {
		setName(name);
		setPosition(position);
		setInformation(info);
		
		floors = new ArrayList<Floor>();
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Point getPosition() {
		return position;
	}
	public void setPosition(Point position) {
		this.position = position;
	}
	public String getInformation() {
		return information;
	}
	public void setInformation(String information) {
		this.information = information;
	}
	public ArrayList<Floor> getFloors() {
		return floors;
	}
	public void setFloors(ArrayList<Floor> floors) {
		this.floors = floors;
	}
}
