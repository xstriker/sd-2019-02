package multicast;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		Scanner sc = new Scanner(System.in);
		System.out.println("Insert ID: ");
		Long id = sc.nextLong();
		MulticastMember m1 = new MulticastMember(id);
	}
}