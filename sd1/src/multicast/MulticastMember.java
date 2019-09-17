package multicast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class MulticastMember {
	
	private Long id;
	private MulticastSocket socket;
	private Integer groupId;
	private String connIp;
	private InetAddress group;
	private Boolean inGroup;
	private byte [] bufferOut;
	private Scanner sc;
	private String exitMessage;
	private Signature signature;
	
	public MulticastMember(Long id, String ) throws InterruptedException {
		this.id = id;
		this.connIp = "228.5.6.7";
		this.groupId = 6789;
		this.exitMessage = "Exit Group";
		this.sc = new Scanner(System.in);
		this.signature = Signature.getInstance(this.generateKey());
		this.joinGroup();
		this.startReceive();
		this.startSend();
	}
	
	private String generateKey() {
		byte[] array = new byte[7]; // length is bounded by 7
	    new Random().nextBytes(array);
	    String key = String(array, Charset.forName("UTF-8"));
	    SecureRandom secureRandom = new SecureRandom();
	    KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("DSA");
	    KeyPair keyPair = keyPairGenerator.generateKeyPair();
	    signature.initSign(keyPair.getPrivate(), secureRandom);
	}
	
	public void joinGroup() {
		try {
			this.group = InetAddress.getByName(this.connIp);
			this.socket = new MulticastSocket(6789);
			this.socket.joinGroup(this.group);
			this.inGroup = true;
		} catch(SocketException e) {
			System.out.println("Socket: " + e.getMessage()); 
		} catch (IOException e) {
			System.out.println("IO: " + e.getMessage());
		}
	}
	
	public void startReceive() {
		MulticastConnection conn = new MulticastConnection(this.socket, this.id);
	}
	
	public void startSend() throws InterruptedException {
		while(this.inGroup) {
			this.sendMessage();
			TimeUnit.SECONDS.sleep(3);
		}
		this.socket.close();
	}
	
	public void sendMessage() {
		String msg;
		try {
			System.out.println("Insira a mensagem: ");
			msg = this.getBytesMessage();
			if(msg.equals(exitMessage)) {
				this.socket.leaveGroup(this.group);
				this.inGroup = false;
				System.out.println(this.id + " exited group: " + this.groupId);
			} else {
				socket.send(this.makeMessage(msg));
			}
		} catch(SocketException e) {
			System.out.println("Socket: " + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IO: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public DatagramPacket makeMessage(String msg) {
		msg = this.id + " says: " + msg;
		this.bufferOut = msg.getBytes();
		return new DatagramPacket(this.bufferOut, this.bufferOut.length, group, 6789);
	}
	
	private String getBytesMessage() {
		return sc.nextLine();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public MulticastSocket getSocket() {
		return socket;
	}

	public void setSocket(MulticastSocket socket) {
		this.socket = socket;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public String getConnIp() {
		return connIp;
	}

	public void setConnIp(String connIp) {
		this.connIp = connIp;
	}

	public InetAddress getGroup() {
		return group;
	}

	public void setGroup(InetAddress group) {
		this.group = group;
	}

	public byte[] getBufferOut() {
		return bufferOut;
	}

	public void setBufferOut(byte[] bufferOut) {
		this.bufferOut = bufferOut;
	}

	public Scanner getSc() {
		return sc;
	}

	public void setSc(Scanner sc) {
		this.sc = sc;
	}

	public String getExitMessage() {
		return exitMessage;
	}

	public void setExitMessage(String exitMessage) {
		this.exitMessage = exitMessage;
	}
}