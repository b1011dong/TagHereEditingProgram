package FloorPlan;

import java.util.ArrayList;

import FloorPlanObject.FPObject;

public class FloorPlan {
	
	/** Floor plan objects (like line, tag etc) */
	private ArrayList<FPObject> objects;

	public FloorPlan() {
		objects = new ArrayList<FPObject>();
	}
	
	public ArrayList<FPObject> getObjects() {
		return objects;
	}

	public void setObjects(ArrayList<FPObject> objects) {
		this.objects = objects;
	}
}
