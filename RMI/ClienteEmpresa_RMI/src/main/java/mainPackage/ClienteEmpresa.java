package mainPackage;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ClienteEmpresa extends UnicastRemoteObject implements ClienteEmpresaInterface {

	private static final long serialVersionUID = 1L;
	private ServerInterface server;

	// Construtor do cliente recebe a referencia do servico de nomes e procura pelo server	
	protected ClienteEmpresa(Registry referenciaServicoNomes) throws RemoteException {
		try {
			server = (ServerInterface)referenciaServicoNomes.lookup("ServerInterface");
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}

	// Notifica novo objeto cadastrado em topico de interesse	
	public void notificarCurriculo(Curriculo curriculo) {
		System.out.println("Novo currículo de " + curriculo.getNome());
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
