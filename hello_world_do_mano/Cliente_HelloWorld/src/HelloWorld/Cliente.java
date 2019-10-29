package HelloWorld;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Cliente {

	private static int port = 1099;
	
	public static void main(String args[]) {
		try {
			Registry registry = LocateRegistry.getRegistry(port);
			CliImpl impl = new CliImpl(registry);
		} catch (RemoteException error){
			System.out.println("RemoteException error: " + error.getMessage());
		} catch (NotBoundException error) {
			System.out.println("NotBoundException error: " + error.getMessage());
		}
	}
	
}
