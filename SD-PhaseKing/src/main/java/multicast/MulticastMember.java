package multicast;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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

import randonString.RandomString;

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
	private Boolean kingMessage;

	public MulticastMember(Long id) throws InterruptedException, NoSuchAlgorithmException, InvalidKeyException {
		this.id = id;
		this.connIp = "228.5.6.7";
		this.groupId = 6789;
		this.exitMessage = "Exit Group";
		this.sc = new Scanner(System.in);
		this.vValue = new Random().nextInt(1);
		this.nOfMembers = 5;
		this.fValue = 1;
		this.majority = 0;
		this.mult = 0;
		this.kingMessage = false;
		this.keyMap = new HashMap<Long, String>();
		this.msgMap = new HashMap<Long, Integer>();
		this.generateKey();
		this.joinGroup();
		this.startReceive();
		this.startSend();
	}

	private void generateKey() throws NoSuchAlgorithmException, InvalidKeyException {
		this.publicKey = RandomString.generate(10);
		this.keyMap.put(this.id, this.publicKey);
//		Signature.getInstance(this.publicKey);
//		SecureRandom secureRandom = new SecureRandom();
//		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("DSA");
//		KeyPair keyPair = keyPairGenerator.generateKeyPair();
//		this.signature.initSign(keyPair.getPrivate(), secureRandom);
	}

	private void joinGroup() {
		try {
			this.group = InetAddress.getByName(this.connIp);
			this.socket = new MulticastSocket(this.groupId);
			this.socket.joinGroup(this.group);
			this.inGroup = true;
			this.sendMessage(this.publicKey, MulticastMessageType.KEY.getType(), false);
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
		this.waitValues(0);
		while (this.inGroup) {
			this.sendMessage(this.getEntryMessage(), MulticastMessageType.MESSAGE.getType(), false);
			TimeUnit.SECONDS.sleep(3);
		}
		this.socket.close();
	}

	private void sendMessage(String msg, String type, Boolean imKing) {
		try {
			if (msg.equals(exitMessage)) {
				this.socket.leaveGroup(this.group);
				this.inGroup = false;
			} else {
				System.out.println(this.id + "sending +++++++++++++++++++++++");
				socket.send(this.makeMessage(msg, type, imKing));
			}
		} catch (SocketException e) {
			System.out.println("Socket: " + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IO: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private DatagramPacket makeMessage(String msg, String type, Boolean imKing) {
		try {
			JSONObject json = new JSONObject();
			json = this.setJsonValues(msg, type, imKing);
			this.bufferOut = json.toString().getBytes("utf-8");
			return new DatagramPacket(this.bufferOut, this.bufferOut.length, this.group, this.groupId);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private JSONObject setJsonValues(String msg, String type, Boolean imKing) {
		JSONObject json = new JSONObject();
		json.put(MulticastMessageFields.ID.getField(), this.id);
		json.put(MulticastMessageFields.MESSAGE.getField(), msg);
		json.put(MulticastMessageFields.IMKING.getField(), imKing);
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
	
	public void printReceivedMessage() {
		System.out.println("------------------- INIT ------------------------- \n"
				+ 		   "Received message from: " + this.message.getId() + "\n"
						+  "Message Type: " + this.message.getType()		+ "\n"
						+  "Message: " + this.message.getMessage()			+ "\n"
						+  "---------------------END------------------------");
	}
	
	private void processDatagram(DatagramPacket datagram) {
		String data = new String(datagram.getData());
		this.message = new MulticastMessage(new JSONObject(data));
		this.handleMessage();
	}
	
	private void handleMessage() {
		this.printReceivedMessage();
		if(this.message.getType().equals(MulticastMessageType.KEY.getType())) {
			this.getNewKeyMessage();
		} else {
			this.getNewValueMessage();
		}
	}
	
	private void getNewKeyMessage() {
		if(!this.keyMap.containsKey(this.message.getId())) {
			this.keyMap.put(this.message.getId(), this.message.getMessage());
			System.out.println("New member: " + this.id + " sending the public key");
			this.sendMessage(this.publicKey, MulticastMessageType.KEY.getType(), false);
		}
	}
	
	private void getNewValueMessage() {
		try {
			Integer value = Integer.parseInt(this.message.getMessage());
			this.msgMap.put(this.message.getId(), value);
			if(this.message.getImKing()) {
				this.kingMessage = true;
			}
		} catch (Exception e) {
			System.out.println("Valor não numérico. Mensagem ignorada");
		}
	}
	
	private void phaseKing() {
		for(int phase = 1; phase <= this.fValue+1; phase++) {
			this.round1();
			this.round2(phase);
		}
	}
	
	private void round1() {
		this.waitValues(1);
		this.countMajority();
	}
	
	private void round2(Integer phase) {
		if(phase.longValue() == this.id) {
			this.sendMessage(this.majority.toString(), MulticastMessageType.MESSAGE.getType(), true);
		}
		this.waitValues(2);
		Integer tieBreaker = this.msgMap.get(phase.longValue());
		this.vValue = this.mult > (this.nOfMembers/2 + this.fValue) ? this.majority : tieBreaker;
		this.kingMessage = false;
		if(phase == this.fValue + 1) {
			System.out.println("Consense value: " + vValue);
		}
	}
	
	private void waitValues(Integer round) {
		// Each case is a round number
		switch (round) {
			case 0:
				while(this.keyMap.keySet().size() != 5);
				break;
			case 1:
				while(this.msgMap.keySet().size() != this.getKeyMap().keySet().size());
				break;
			case 2:
				while(!this.kingMessage);
				break;
		}
	}
	
	private void countMajority() {
		Integer count0 = Collections.frequency(this.msgMap.values(), 0);
		Integer count1 = Collections.frequency(this.msgMap.values(), 1);
		this.majority = count0 > count1 ? 0 : 1;
		this.mult = this.majority == 0 ? count0 : count1;
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