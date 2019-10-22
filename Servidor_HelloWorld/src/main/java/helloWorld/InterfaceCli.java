package helloWorld;

import java.rmi.Remote;

public interface InterfaceCli extends Remote {

	public void echo(String msg);
}
