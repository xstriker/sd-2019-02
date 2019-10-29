package mainPackage;

import java.util.List;

public interface ServerInterface {

	public List<Curriculo> buscarCurriculos(String area);
	
	public List<Vaga> buscarVagas(String area, Double salarioMinimo);
	
	public void cadastrarCurriculo(Curriculo curriculo);
	
	public void cadastrarVaga(Vaga vaga);
	
	public void cadastrarInteresseCurriculo(String area, ClientePessoa cli);
	
	public void cadastrarInteresseVaga(String area, ClienteEmpresa cli);
}
