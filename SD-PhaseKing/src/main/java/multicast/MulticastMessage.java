package multicast;

import org.json.JSONObject;

public class MulticastMessage {

	JSONObject json;
	private Long id;
	private String message;
	private Boolean imKing;
	private String type;

	public MulticastMessage(JSONObject json) {
		this.id = json.getLong("id");
		this.message = json.getString("message");
		this.imKing = json.getBoolean("imKing");
		this.type = json.getString("type");
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