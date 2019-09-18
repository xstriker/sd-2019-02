package multicast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.MulticastSocket;
import java.util.HashMap;

import org.json.JSONObject;

class MulticastConnection extends Thread {
	private byte[] buffer;
	private MulticastSocket connSocket;
	private Long id;
	private HashMap<Long, String> keyMap;
	private MulticastMessage message;

	public MulticastConnection(MulticastSocket aClientSocket, Long id) {
		connSocket = aClientSocket;
		this.id = id;
		this.keyMap = new HashMap<Long, String>();
		this.start();
	}
	
	private void receiveMessage() {
		try {
			this.buffer = new byte[1000];
			DatagramPacket messageIn = new DatagramPacket(buffer, buffer.length);
			connSocket.receive(messageIn);
			this.processDatagram(messageIn);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void processDatagram(DatagramPacket datagram) {
		String data = new String(datagram.getData());
		this.message = new MulticastMessage(new JSONObject(data));
		treatMessage();
	}
	
	private void treatMessage() {
		if(this.message.getType().equals(MulticastMessageType.KEY.getType())) {
			keyMap.put(this.message.getId(), this.message.getMessage());
		} else {
			
		}
	}

	public void run() {
		while (true) {
			this.receiveMessage();
		}
	}
}