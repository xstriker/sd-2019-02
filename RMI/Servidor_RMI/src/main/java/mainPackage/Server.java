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
	
	// Map {'chave': 'area', 'valor':{'chave': 'contato', 'valor': 'curriculo'}
	private Map<String, Map<String, Curriculo>> mapCurriculos = new HashMap<String, Map<String,Curriculo>>();
	// Map {'chave': 'area', 'valor': {'chave': 'contato', 'valor': 'vaga'}}
	private Map<String, Map<String, Vaga>> mapVagas = new HashMap<String, Map<String,Vaga>>();
	// Map {'chave': 'area', 'valor': 'lista de pessoas interessadas em uma vaga'}
	private Map<String, List<ClientePessoaInterface>> registrosVagas = new HashMap<String, List<ClientePessoaInterface>>();
	// Map {'chave': 'area', 'valor': 'lista de empresas interessadas em um curriculo'}
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
		// Para cada vaga na area
		for (String key : mapVagas.get(area).keySet()) {
			Vaga vaga = mapVagas.get(area).get(key);
			// Se for maior que o salario minimo adicione a lista de vagas retornadas
			if(vaga.getSalario() >= salarioMinimo) {
				returnList.add(vaga);
			}
		}
		return returnList;
	}

	public void cadastrarCurriculo(Curriculo curriculo) {
		// Se a area nao existir, adicine uma nova area
		if(!mapCurriculos.containsKey(curriculo.getArea())) {
			mapCurriculos.put(curriculo.getArea(), new HashMap<String, Curriculo>());
		}
		// Adicione um curriculo na area
		mapCurriculos.get(curriculo.getArea()).put(curriculo.getContato(), curriculo);
		System.out.println("Curriculo Cadastrado");
		// Notifique interessados na area
		notificarClientesEmpresa(curriculo, curriculo.getArea());
	}

	public void cadastrarVaga(Vaga vaga) {
		// Se a area nao existir, adicione uma nova area
		if(!mapVagas.containsKey(vaga.getArea())) {
			mapVagas.put(vaga.getArea(), new HashMap<String, Vaga>());
		}
		// Adicione uma nova vaga
		mapVagas.get(vaga.getArea()).put(vaga.getNomeEmpresa(), vaga);
		System.out.println("Vaga Cadastrada");
		// Notifique interessados na area
		notificarClientesPessoa(vaga, vaga.getArea());
	}

	public void cadastrarInteresseVaga(String area, ClientePessoaInterface cli) {
		// Se a area nao existir, adicion uma nova area
		if(!registrosVagas.containsKey(area)) {
			registrosVagas.put(area, new ArrayList<ClientePessoaInterface>());
		}
		// Registre o interesse na area
		registrosVagas.get(area).add(cli);
		System.out.println("Interesse em vagas cadastrado");
	}

	public void cadastrarInteresseCurriculo(String area, ClienteEmpresaInterface cli) {
		// Se a area nao existir, adicion uma nova area
		if(!registrosCurriculo.containsKey(area)) {
			registrosCurriculo.put(area, new ArrayList<ClienteEmpresaInterface>());
		}
		// Registre o interesse na area
		registrosCurriculo.get(area).add(cli);
		System.out.println("Interesse em curriculos cadastrado");
	}
	
	public void notificarClientesPessoa(Vaga vaga, String area) {
		// Se a area existir
		if(registrosVagas.get(area) != null) {
			// Itere sobre todas as referencias
			for (ClientePessoaInterface referecia : registrosVagas.get(area)) {
				// Chamando o metodo notificaVaga dela
				try {
					referecia.notificarVaga(vaga);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void notificarClientesEmpresa(Curriculo curriculo, String area) {
		// Se a area existir
		if(registrosCurriculo.get(area) != null) {
			// Itere sobre as referencias
			for (ClienteEmpresaInterface referecia : registrosCurriculo.get(area)) {
				// Chamando o metodo notificaCurriculo dela
				try {
					referecia.notificarCurriculo(curriculo);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
