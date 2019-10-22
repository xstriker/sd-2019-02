package helloWorld;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InterfaceServ extends Remote {

	public void call(String msg, InterfaceCli reference) throws RemoteException;
}
