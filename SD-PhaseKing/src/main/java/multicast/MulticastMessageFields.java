package multicast;

public enum MulticastMessageFields {
	ID("id"), MESSAGE("message"), IMKING("imKing"), TYPE("type");
	
	public String messageField;

	MulticastMessageFields(String field) {
    	this.messageField = field;
    }
	
	public String getField(){
        return this.messageField;
    }
}
