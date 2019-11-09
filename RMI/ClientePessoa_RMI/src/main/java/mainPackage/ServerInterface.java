package mainPackage;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ServerInterface extends Remote {

	public List<Curriculo> buscarCurriculos(String area) throws RemoteException;
	
	public List<Vaga> buscarVagas(String area, Double salarioMinimo) throws RemoteException;
	
	public void cadastrarCurriculo(Curriculo curriculo) throws RemoteException;
	
	public void cadastrarVaga(Vaga vaga) throws RemoteException;
	
	public void cadastrarInteresseCurriculo(String area, ClienteEmpresaInterface cli) throws RemoteException;
	
	public void cadastrarInteresseVaga(String area, ClientePessoaInterface cli) throws RemoteException;
}
