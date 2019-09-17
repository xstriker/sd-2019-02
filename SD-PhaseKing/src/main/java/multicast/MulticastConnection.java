package multicast;

import java.io.EOFException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.MulticastSocket;

class MulticastConnection extends Thread {
	private byte[] buffer;
	private DatagramPacket messageIn;
	private MulticastSocket connSocket;
	private Long id;

	public MulticastConnection(MulticastSocket aClientSocket, Long id) {
		connSocket = aClientSocket;
		this.id = id;
		this.start();
	}

	public void run() {
		try {
			while (true) {
				if (!connSocket.isClosed()) {
					buffer = new byte[1000];
					messageIn = new DatagramPacket(buffer, buffer.length);
					connSocket.receive(messageIn);
					System.out.println(this.id + " Received | " + new String(messageIn.getData()));
				} else {
					break;
				}
			}
		} catch (EOFException e) {
			System.out.println("EOF:" + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("readline:" + e.getMessage());
			e.printStackTrace();
		}
	}
}