package helloWorld;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Servidor {

	public static void main(String[] args) throws RemoteException {
		Registry referenciaServicoNomes = LocateRegistry.createRegistry(9999);
		ServImpl instance = new ServImpl();
		referenciaServicoNomes.rebind("InterfaceServ", instance);
	}

}
