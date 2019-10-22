package HelloWorld;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InterfaceServ extends Remote {
	public void chamar(String qualquer, InterfaceCli cliente) throws RemoteException;
}
