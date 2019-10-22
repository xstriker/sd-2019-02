package helloWorld;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InterfaceCli extends Remote {

	public void echo(String msg) throws RemoteException;
}
