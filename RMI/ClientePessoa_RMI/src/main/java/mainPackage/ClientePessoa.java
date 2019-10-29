package mainPackage;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ClientePessoa extends UnicastRemoteObject implements ClientePessoaInterface {

	private static final long serialVersionUID = 1L;

	protected ClientePessoa(Registry referenciaServicoNomes) throws RemoteException {
		try {
			Server serverInstance = (Server)referenciaServicoNomes.lookup("InterfaceServ");
			serverInstance.cadastrarInteresseCurriculo("area", this);
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}

	public void notificarVaga(Vaga vaga) {
		System.out.println("Novo vaga...");
	}

}
