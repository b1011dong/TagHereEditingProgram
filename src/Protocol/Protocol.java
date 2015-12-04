package Protocol;

import java.util.ArrayList;

import FloorPlanObject.FPObject;

/**
 * Protocol class for communicate between server
 * @author DongKyu
 * @since 11.07.15
 */
public class Protocol {
	private PType type;
	private Object data;
	
	public Protocol(PType procKind, Object data) {
		this.type = procKind;
		this.data = data;
	}

	public PType getType() {
		return type;
	}

	public void setType(PType type) {
		this.type = type;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}