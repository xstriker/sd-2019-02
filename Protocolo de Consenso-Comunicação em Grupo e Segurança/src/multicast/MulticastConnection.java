package multicast;

import java.io.EOFException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.MulticastSocket;

class MulticastConnection extends Thread {
	byte[] buffer;
	DatagramPacket messageIn;
	MulticastSocket connSocket;

	public MulticastConnection(MulticastSocket aClientSocket) {
		connSocket = aClientSocket;
	}

	public void run() {
		try {
			buffer = new byte[1000];
			messageIn = new DatagramPacket(buffer, buffer.length);
			connSocket.receive(messageIn);
			System.out.println("Received:" + new String(messageIn.getData()));

		} catch (EOFException e) {
			System.out.println("EOF:" + e.getMessage());
		} catch (IOException e) {
			System.out.println("readline:" + e.getMessage());
		} finally {
			connSocket.close();
		}
	}
}