package mainPackage;

import java.io.Serializable;

public class Curriculo implements Serializable{

	private String nome;
	private String contato;
	private String area;
	private Double tempo;
	private Double salario;
	
	public Curriculo(String nome, String contato, String area, Double tempo, Double salario) {
		super();
		this.nome = nome;
		this.contato = contato;
		this.area = area;
		this.tempo = tempo;
		this.salario = salario;
	}

	public Curriculo() {
		// TODO Auto-generated constructor stub
	}

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getContato() {
		return contato;
	}
	public void setContato(String contato) {
		this.contato = contato;
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
