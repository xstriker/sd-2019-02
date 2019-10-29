package helloWorld;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServImpl extends UnicastRemoteObject implements InterfaceServ {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected ServImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	public void call(String msg, InterfaceCli reference) {
		reference.echo(msg);
	}

}