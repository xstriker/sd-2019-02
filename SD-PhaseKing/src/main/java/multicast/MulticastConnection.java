package multicast;

class MulticastConnection extends Thread {
	MulticastMember member;

	public MulticastConnection(MulticastMember member) {
		this.member = member;
		this.start();
	}

	public void run() {
		while (true) {
			this.member.receiveMessage();
		}
	}
}