package mainPackage;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClienteEmpresaInterface extends Remote{

	public void notificarCurriculo(Curriculo curriculo) throws RemoteException;
}
