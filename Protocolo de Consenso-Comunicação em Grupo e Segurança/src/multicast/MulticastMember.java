package multicast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.util.Scanner;

public class MulticastMember {
	
	private Long id;
	private MulticastSocket socket;
	private Integer groupId;
	private String connIp;
	private InetAddress group;
	private byte [] bufferOut;
	private Scanner sc;
	private DatagramPacket messageOut;
	private String exitMessage;
	
	public MulticastMember(Long id) {
		this.id = id;
		this.connIp = "228.5.6.7";
		this.groupId = 6789;
		this.exitMessage = "exitGroup";
		this.joinGroup();
	}
	
	public void joinGroup() {
		this.group = InetAddress.getByName(this.connIp);
		this.socket = new MulticastSocket(6789);
		this.socket.joinGroup(this.group);
	}
	
	public void startSend() {
		this.sc = new Scanner(System.in);
		while(true) {
			
		}
	}
	
	private byte [] getBytesMessage() {
		return sc.next().getBytes();
	}
	
	private void sendMessage() {
		bufferOut = this.getBytesMessage();
		if(bufferOut == (this.exitMessage).getBytes()) {
			this.messageOut = new DatagramPacket(bufferOut, bufferOut.length, this.group, this.groupId);
			socket.send(messageOut);
		} else {
			
		}
	}

	try {
		

		MulticastConnection conn = new MulticastConnection(s);
		
		byte[] m;
		Scanner sc = new Scanner(System.in);
		
		
		s.leaveGroup(group);
	} catch (SocketException e) {
		System.out.println("Socket: " + e.getMessage());
	} catch (IOException e) {
		System.out.println("IO: " + e.getMessage());
	} finally {
		if (s != null)
			s.close();
	}
}