package HelloWorld;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServImpl extends UnicastRemoteObject implements InterfaceServ {

	public ServImpl() throws RemoteException { }

	public void chamar(String qualquer, InterfaceCli cliente) throws RemoteException {
		cliente.echo(qualquer);
	}
	
}
