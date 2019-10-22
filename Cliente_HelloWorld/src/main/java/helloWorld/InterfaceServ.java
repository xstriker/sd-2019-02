package helloWorld;

import java.rmi.Remote;

public interface InterfaceServ extends Remote {

	public void call(String msg, InterfaceCli reference);
}
