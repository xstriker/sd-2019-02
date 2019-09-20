package SD_PhaseKing.SD_PhaseKing;

import java.util.Scanner;

import multicast.MulticastMember;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	try {
    		Scanner sc = new Scanner(System.in);
    		System.out.println("pID?");
            MulticastMember member = new MulticastMember(sc.nextLong());

		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
