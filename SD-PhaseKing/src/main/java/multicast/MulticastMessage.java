package multicast;

import org.json.JSONObject;

public class MulticastMessage {

	private JSONObject json;
	private Long id;
	private String message;
	private Boolean imKing;
	private String type;

	public MulticastMessage(JSONObject json) {
		this.id = json.getLong(MulticastMessageFields.ID.getField());
		this.message = json.getString(MulticastMessageFields.MESSAGE.getField());
		this.imKing = json.getBoolean(MulticastMessageFields.IMKING.getField());
		this.type = json.getString(MulticastMessageFields.TYPE.getField());
		this.message.getBytes();
		this.json = json;
	}

	public JSONObject getJson() {
		return json;
	}

	public void setJson(JSONObject json) {
		this.json = json;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Boolean getImKing() {
		return imKing;
	}

	public void setImKing(Boolean imKing) {
		this.imKing = imKing;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}