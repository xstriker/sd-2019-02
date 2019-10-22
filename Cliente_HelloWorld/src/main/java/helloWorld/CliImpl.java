package helloWorld;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class CliImpl extends UnicastRemoteObject implements InterfaceCli {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private InterfaceServ reference;

	protected CliImpl(Registry referenciaServicoNomes) throws RemoteException, NotBoundException {
		Remote remote = referenciaServicoNomes.lookup("InterfaceServ");
		((ServImpl) remote).call("Hello World", this);
	}

	public void echo(String msg) {
		System.out.println(msg);
	}

}
