package multicast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Signature;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.json.JSONObject;

public class MulticastMember {

	private Long id;
	private MulticastSocket socket;
	private Integer groupId;
	private String connIp;
	private InetAddress group;
	private Boolean inGroup;
	private byte[] bufferOut;
	private Scanner sc;
	private String exitMessage;
	private Signature signature;
	private String publicKey;

	public MulticastMember(Long id) throws InterruptedException, NoSuchAlgorithmException, InvalidKeyException {
		this.id = id;
		this.connIp = "228.5.6.7";
		this.groupId = 6789;
		this.exitMessage = "Exit Group";
		this.sc = new Scanner(System.in);
		this.generateKey();
		this.joinGroup();
		this.startReceive();
		this.startSend();
	}

	private void generateKey() throws NoSuchAlgorithmException, InvalidKeyException {
		Signature.getInstance(this.generateRandomString());
		SecureRandom secureRandom = new SecureRandom();
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("DSA");
		KeyPair keyPair = keyPairGenerator.generateKeyPair();
		this.signature.initSign(keyPair.getPrivate(), secureRandom);
	}
	
	private String generateRandomString() {
		byte[] array = new byte[7]; // length is bounded by 7
		new Random().nextBytes(array);
		this.publicKey = new String(array, Charset.forName("UTF-8"));
		return this.publicKey;
	}

	public void joinGroup() {
		try {
			this.group = InetAddress.getByName(this.connIp);
			this.socket = new MulticastSocket(6789);
			this.socket.joinGroup(this.group);
			this.inGroup = true;
			this.sendMessage(this.publicKey, MulticastMessageType.KEY.getType());
		} catch (SocketException e) {
			System.out.println("Socket: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("IO: " + e.getMessage());
		}
	}

	public void startReceive() {
		@SuppressWarnings("unused")
		MulticastConnection conn = new MulticastConnection(this.socket, this.id);
	}

	public void startSend() throws InterruptedException {
		while (this.inGroup) {
			this.sendMessage(this.getMessage(), MulticastMessageType.MESSAGE.getType());
			TimeUnit.SECONDS.sleep(3);
		}
		this.socket.close();
	}

	public void sendMessage(String msg, String type) {
		try {
			if (msg.equals(exitMessage)) {
				this.socket.leaveGroup(this.group);
				this.inGroup = false;
				System.out.println(this.id + " exited group: " + this.groupId);
			} else {
				socket.send(this.makeMessage(msg, type));
			}
		} catch (SocketException e) {
			System.out.println("Socket: " + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IO: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private DatagramPacket makeMessage(String msg, String type) {
		JSONObject json = new JSONObject();
		json = this.setJsonValues(msg, type);
		this.bufferOut = json.toString().getBytes();
		return new DatagramPacket(this.bufferOut, this.bufferOut.length, group, 6789);
	}
	
	private JSONObject setJsonValues(String msg, String type) {
		JSONObject json = new JSONObject();
		json.put(MulticastMessageFields.ID.getField(), this.id);
		json.put(MulticastMessageFields.MESSAGE.getField(), msg);
		json.put(MulticastMessageFields.IMKING.getField(), "");
		json.put(MulticastMessageFields.TYPE.getField(), type);
		return json;
	}

	private String getMessage() {
		System.out.println("Insira a mensagem: ");
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

	public Boolean getInGroup() {
		return inGroup;
	}

	public void setInGroup(Boolean inGroup) {
		this.inGroup = inGroup;
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

	public Signature getSignature() {
		return signature;
	}

	public void setSignature(Signature signature) {
		this.signature = signature;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}
}