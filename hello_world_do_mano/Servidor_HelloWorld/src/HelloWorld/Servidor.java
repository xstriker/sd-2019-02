package HelloWorld;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Servidor {

	private static int port = 5786;
	
	public static void main(String args[]) {
		try {
			Registry registry = LocateRegistry.createRegistry(port);
			ServImpl impl = new ServImpl();
			registry.rebind("ServImpl", impl);
		} catch (RemoteException error){
			System.out.println("RemoteException error: " + error.getMessage());
		}
	}
}
