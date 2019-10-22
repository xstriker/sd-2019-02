package HelloWorld;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class CliImpl extends UnicastRemoteObject implements InterfaceCli {

	public CliImpl(Registry registry) throws RemoteException, NotBoundException {
		Remote remote = registry.lookup("ServImpl");
		((InterfaceServ) remote).chamar("Hello World", this);
	}

	public void echo(String qualquer) throws RemoteException {
		System.out.println(qualquer);
	}
	
}
