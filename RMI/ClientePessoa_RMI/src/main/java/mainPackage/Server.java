package mainPackage;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Server extends UnicastRemoteObject implements ServerInterface{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected Server() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	private Map<String, Map<String, Curriculo>> mapCurriculos = new HashMap<String, Map<String,Curriculo>>();
	private Map<String, Map<String, Vaga>> mapVagas = new HashMap<String, Map<String,Vaga>>();
	private Map<String, List<ClientePessoaInterface>> registrosVagas = new HashMap<String, List<ClientePessoaInterface>>();
	private Map<String, List<ClienteEmpresaInterface>> registrosCurriculo = new HashMap<String, List<ClienteEmpresaInterface>>();
	
	public List<Curriculo> buscarCurriculos(String area) {
		List<Curriculo> returnList = new ArrayList<Curriculo>();
		for (String key : mapCurriculos.get(area).keySet()) {
			returnList.add(mapCurriculos.get(area).get(key));
		}
		return returnList;
	}

	public List<Vaga> buscarVagas(String area, Double salarioMinimo) {
		List<Vaga> returnList = new ArrayList<Vaga>();
		for (String key : mapVagas.get(area).keySet()) {
			Vaga vaga = mapVagas.get(area).get(key);
			if(vaga.getSalario() >= salarioMinimo) {
				returnList.add(vaga);
			}
		}
		return returnList;
	}

	public void cadastrarCurriculo(Curriculo curriculo) {
		if(!mapCurriculos.containsKey(curriculo.getArea())) {
			mapCurriculos.put(curriculo.getArea(), new HashMap<String, Curriculo>());
		}
		mapCurriculos.get(curriculo.getArea()).put(curriculo.getContato(), curriculo);
		System.out.println("Curriculo Cadastrado");
		notificarClientesEmpresa(curriculo, curriculo.getArea());
	}

	public void cadastrarVaga(Vaga vaga) {
		if(!mapVagas.containsKey(vaga.getArea())) {
			mapVagas.put(vaga.getArea(), new HashMap<String, Vaga>());
		}
		mapVagas.get(vaga.getArea()).put(vaga.getNomeEmpresa(), vaga);
		System.out.println("Vaga Cadastrada");
		notificarClientesPessoa(vaga, vaga.getArea());
	}

	public void cadastrarInteresseVaga(String area, ClientePessoaInterface cli) {
		if(!registrosVagas.containsKey(area)) {
			registrosVagas.put(area, new ArrayList<ClientePessoaInterface>());
		}
		registrosVagas.get(area).add(cli);
		System.out.println("Interesse em vagas cadastrado");
	}

	public void cadastrarInteresseCurriculo(String area, ClienteEmpresaInterface cli) {
		if(!registrosCurriculo.containsKey(area)) {
			registrosCurriculo.put(area, new ArrayList<ClienteEmpresaInterface>());
		}
		registrosCurriculo.get(area).add(cli);
		System.out.println("Interesse em curriculos cadastrado");
	}
	
	public void notificarClientesPessoa(Vaga vaga, String area) {
		if(registrosVagas.get(area) != null) {
			for (ClientePessoaInterface referecia : registrosVagas.get(area)) {
				try {
					referecia.notificarVaga(vaga);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void notificarClientesEmpresa(Curriculo curriculo, String area) {
		if(registrosCurriculo.get(area) != null) {
			for (ClienteEmpresaInterface referecia : registrosCurriculo.get(area)) {
				try {
					referecia.notificarCurriculo(curriculo);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
