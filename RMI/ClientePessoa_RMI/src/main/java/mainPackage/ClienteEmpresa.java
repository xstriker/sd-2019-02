package mainPackage;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ClienteEmpresa extends UnicastRemoteObject implements ClienteEmpresaInterface {

	private static final long serialVersionUID = 1L;

	protected ClienteEmpresa(Registry referenciaServicoNomes) throws RemoteException {
		try {
			Server server = (Server)referenciaServicoNomes.lookup("ServerInterface");
			server.cadastrarInteresseVaga("area", this);
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}

	public void notificarCurriculo(Curriculo curriculo) {
		System.out.println("Novo currículo...");
	}
}
