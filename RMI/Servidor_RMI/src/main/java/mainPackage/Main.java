package mainPackage;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Main {

	public static void main(String[] args) {
		try {
			Registry referenciaServicoNomes = LocateRegistry.createRegistry(9999);
			Server instance = new Server();
			referenciaServicoNomes.rebind("ServerInterface", instance);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

}
