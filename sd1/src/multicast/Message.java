package multicast;

import org.json.simple.JSONObject;

public class Message {
	
	JSONObject json;
	private Long id;
	private String message;
	private Boolean imKing;
	private String type;
		
	public Message(Long id, String message, Boolean imKing, String type) {
		super();
		this.id = "id";
		this.message = "message";
		this.imKing = "imKing";
		this.type = "type";
		json = new JSONObject();
		json.put(this.id, id);
		json.put(this.message, message);
		json.put(this.imKing, imKing);
		json.put(this.type, type);
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
	public JSONObject getJson() {
		return json;
	}
	public void setJson(JSONObject json) {
		this.json = json;
	}
}