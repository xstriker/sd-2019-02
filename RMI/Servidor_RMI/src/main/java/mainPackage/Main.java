package mainPackage;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Main {

	public static void main(String[] args) {
		try {
			// Crie um registro no serviço de nomes
			Registry referenciaServicoNomes = LocateRegistry.createRegistry(9999);
			// Instancie um novo server
			Server instance = new Server();
			// Cadastre o novo server no registro
			referenciaServicoNomes.rebind("ServerInterface", instance);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}
