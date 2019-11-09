package mainPackage;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientePessoaInterface extends Remote{

	public void notificarVaga(Vaga vaga) throws RemoteException;
}
