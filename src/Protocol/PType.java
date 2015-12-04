package Protocol;

/**
 * For distinguish protocol types
 * @author DongKyu
 * @since 11.07.15
 */
public enum PType {
	REQUEST_LOGIN(0),
	REPLY_LOGIN(1),
	OBJECT_RECEIVED(2);

	private int value;
	
	private PType() {}
	
	private PType(int v) {
		value = v;
	}
	
	public int getInt() {
		return value;
	}
}
