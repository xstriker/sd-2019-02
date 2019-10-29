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

	private Map<String, Map<String, Curriculo>> mapCurriculos;
	private Map<String, Map<String, Vaga>> mapVagas;
	private Map<String, List<ClientePessoa>> registrosVagas;
	private Map<String, List<ClienteEmpresa>> registrosCurriculo;
	
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
		notificarClientesEmpresa(curriculo, curriculo.getArea());
	}

	public void cadastrarVaga(Vaga vaga) {
		if(!mapVagas.containsKey(vaga.getArea())) {
			mapVagas.put(vaga.getArea(), new HashMap<String, Vaga>());
		}
		mapVagas.get(vaga.getArea()).put(vaga.getNomeEmpresa(), vaga);
		notificarClientesPessoa(vaga, vaga.getArea());
	}

	public void cadastrarInteresseCurriculo(String area, ClientePessoa cli) {
		if(!registrosVagas.containsKey(area)) {
			registrosVagas.put(area, new ArrayList<ClientePessoa>());
		}
		registrosVagas.get(area).add(cli);
	}

	public void cadastrarInteresseVaga(String area, ClienteEmpresa cli) {
		if(!registrosCurriculo.containsKey(area)) {
			registrosCurriculo.put(area, new ArrayList<ClienteEmpresa>());
		}
		registrosCurriculo.get(area).add(cli);
	}
	
	public void notificarClientesPessoa(Vaga vaga, String area) {
		for (ClientePessoa referecia : registrosVagas.get(area)) {
			referecia.notificarVaga(vaga);
		}
	}
	
	public void notificarClientesEmpresa(Curriculo curriculo, String area) {
		for (ClienteEmpresa referecia : registrosCurriculo.get(area)) {
			referecia.notificarCurriculo(curriculo);
		}
	}
}
