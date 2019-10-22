package helloWorld;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Cliente {

	public static void main(String[] args) throws RemoteException, NotBoundException {
		
		Registry referenciaServicoNomes = LocateRegistry.getRegistry(9999);
		CliImpl cliImpl = new CliImpl(referenciaServicoNomes);
	}

}
