package mainPackage;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ClientePessoa extends UnicastRemoteObject implements ClientePessoaInterface {

	private static final long serialVersionUID = 1L;
	private ServerInterface server;

	protected ClientePessoa(Registry referenciaServicoNomes) throws RemoteException {
		try {
			server = (ServerInterface)referenciaServicoNomes.lookup("ServerInterface");
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}

	public void notificarVaga(Vaga vaga) {
		System.out.println("Novo vaga...");
	}

	public ServerInterface getServer() {
		return server;
	}

	public void setServer(Server server) {
		this.server = server;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
