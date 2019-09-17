package multicast;

public enum MulticastMessageType {
	 MESSAGE("Message"),KEY("Key");
	 
    public String messageType;

	MulticastMessageType(String type) {
    	this.messageType = type;
    }
	
	public String getType(){
        return this.messageType;
    }
}
