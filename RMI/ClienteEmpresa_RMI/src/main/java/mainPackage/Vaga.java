package mainPackage;

import java.io.Serializable;

public class Vaga implements Serializable{

	private String nomeEmpresa;
	private String area;
	private Double tempo;
	private Double salario;
	
	public Vaga(String nomeEmpresa, String area, Double tempo, Double salario) {
		super();
		this.nomeEmpresa = nomeEmpresa;
		this.area = area;
		this.tempo = tempo;
		this.salario = salario;
	}

	public Vaga() {
		// TODO Auto-generated constructor stub
	}

	public String getNomeEmpresa() {
		return nomeEmpresa;
	}
	public void setNomeEmpresa(String nomeEmpresa) {
		this.nomeEmpresa = nomeEmpresa;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public Double getTempo() {
		return tempo;
	}
	public void setTempo(Double tempo) {
		this.tempo = tempo;
	}
	public Double getSalario() {
		return salario;
	}
	public void setSalario(Double salario) {
		this.salario = salario;
	}
	
}
