package server;

public interface ServerInterface {

	public void buscarCurriculos(String area);
	
	public void buscarVagas(String area, Double salarioMinimo);
	
	public void cadastrarCurriculo(Curriculo curriculo);
	
	public void cadastrarVaga(Vaga vaga);
}
