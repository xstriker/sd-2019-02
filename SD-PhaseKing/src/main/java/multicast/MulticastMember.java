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
import java.util.Collections;
import java.util.HashMap;
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
	private byte[] bufferIn;
	private MulticastMessage message;
	private HashMap<Long, String> keyMap;
	private HashMap<Long, Integer> msgMap;
	private Integer vValue;
	private Integer nOfMembers;
	private Integer fValue;
	private Integer majority;
	private Integer mult;

	public MulticastMember(Long id) throws InterruptedException, NoSuchAlgorithmException, InvalidKeyException {
		this.id = id;
		this.connIp = "228.5.6.7";
		this.groupId = 6789;
		this.exitMessage = "Exit Group";
		this.sc = new Scanner(System.in);
		this.vValue = new Random().nextInt(1);
		this.nOfMembers = 5;
		this.fValue = (int) Math.ceil(this.nOfMembers/4);
		this.majority = 0;
		this.mult = 0;
		this.generateKey();
		this.joinGroup();
		this.startReceive();
		this.startSend();
	}

	private void generateKey() throws NoSuchAlgorithmException, InvalidKeyException {
		this.publicKey = this.generateRandomString();
		Signature.getInstance(this.publicKey);
		SecureRandom secureRandom = new SecureRandom();
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("DSA");
		KeyPair keyPair = keyPairGenerator.generateKeyPair();
		this.signature.initSign(keyPair.getPrivate(), secureRandom);
	}
	
	private String generateRandomString() {
		byte[] array = new byte[7]; // length is bounded by 7
		new Random().nextBytes(array);
		return new String(array, Charset.forName("UTF-8"));
	}

	private void joinGroup() {
		try {
			this.group = InetAddress.getByName(this.connIp);
			this.socket = new MulticastSocket(this.groupId);
			this.socket.joinGroup(this.group);
			this.inGroup = true;
			this.sendMessage(this.publicKey, MulticastMessageType.KEY.getType());
		} catch (SocketException e) {
			System.out.println("Socket: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("IO: " + e.getMessage());
		}
	}

	private void startReceive() {
		@SuppressWarnings("unused")
		MulticastConnection conn = new MulticastConnection(this);
	}

	private void startSend() throws InterruptedException {
		while (this.inGroup) {
			this.sendMessage(this.getEntryMessage(), MulticastMessageType.MESSAGE.getType());
			TimeUnit.SECONDS.sleep(3);
		}
		this.socket.close();
	}

	private void sendMessage(String msg, String type) {
		try {
			if (msg.equals(exitMessage)) {
				this.socket.leaveGroup(this.group);
				this.inGroup = false;
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
		return new DatagramPacket(this.bufferOut, this.bufferOut.length, this.group, this.groupId);
	}
	
	private JSONObject setJsonValues(String msg, String type) {
		JSONObject json = new JSONObject();
		json.put(MulticastMessageFields.ID.getField(), this.id);
		json.put(MulticastMessageFields.MESSAGE.getField(), msg);
		json.put(MulticastMessageFields.IMKING.getField(), "");
		json.put(MulticastMessageFields.TYPE.getField(), type);
		return json;
	}

	private String getEntryMessage() {
		System.out.println("Insira a mensagem: ");
		return sc.nextLine();
	}
	
	public void receiveMessage() {
		try {
			this.bufferIn = new byte[1000];
			DatagramPacket messageIn = new DatagramPacket(bufferIn, bufferIn.length);
			this.getSocket().receive(messageIn);
			this.processDatagram(messageIn);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void processDatagram(DatagramPacket datagram) {
		String data = new String(datagram.getData());
		this.message = new MulticastMessage(new JSONObject(data));
		handleMessage();
	}
	
	private void handleMessage() {
		if(this.message.getType().equals(MulticastMessageType.KEY.getType())) {
			this.getNewKeyMessage();
		} else {
			this.getNewValueMessage();
		}
	}
	
	private void getNewKeyMessage() {
		if(!this.keyMap.containsKey(this.message.getId())) {
			this.keyMap.put(this.message.getId(), this.message.getMessage());
			this.sendMessage(this.publicKey, MulticastMessageType.KEY.getType());
			if(this.keyMap.keySet().size() == 5) {
				this.phaseKing();
			}
		} else {
			this.getNewValueMessage();
		}
	}
	
	private void getNewValueMessage() {
		try {
			Integer value = Integer.parseInt(this.message.getMessage());
			this.msgMap.put(this.message.getId(), value);
		} catch (Exception e) {
			System.out.println("Valor não numérico. Mensagem ignorada");
		}
	}
	
	private void phaseKing() {
		for(int phase = 1; phase < this.fValue+1; phase++) {
			this.round1();
			this.round2(phase);
		}
	}
	
	private void round1() {
		this.waitValues();
		this.countMajority();
	}
	
	private void round2(Integer phase) {
		if(phase.longValue() == this.id) {
			this.sendMessage(this.majority.toString(), MulticastMessageType.MESSAGE.getType());
		}
		try {
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Integer tieBreaker = this.msgMap.get(phase.longValue());
		this.vValue = this.mult > (this.nOfMembers/2 + this.fValue) ? this.majority : tieBreaker;
		if(phase == this.fValue + 1) {
			System.out.println("Consense value: " + vValue);
		}
	}
	
	private void waitValues() {
		while(this.msgMap.keySet().size() != this.getKeyMap().keySet().size());
	}
	
	private void countMajority() {
		Integer count0 = Collections.frequency(this.msgMap.values(), 0);
		Integer count1 = Collections.frequency(this.msgMap.values(), 1);
		this.majority = count0 > count1 ? 0 : 1;
		this.mult = count0 > count1 ? count0 : count1;
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

	public byte[] getBufferIn() {
		return bufferIn;
	}

	public void setBufferIn(byte[] bufferIn) {
		this.bufferIn = bufferIn;
	}

	public HashMap<Long, String> getKeyMap() {
		return keyMap;
	}

	public void setKeyMap(HashMap<Long, String> keyMap) {
		this.keyMap = keyMap;
	}

	public void setMessage(MulticastMessage message) {
		this.message = message;
	}
}