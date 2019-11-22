package com.sd2019.jobserver;

import java.io.Serializable;

import org.json.simple.JSONObject;

public class Vaga implements Serializable {

	private String nomeEmpresa;
	private String contato;
	private String area;
	private Double tempo;
	private Double salario;

	public Vaga(String nomeEmpresa, String area, Double tempo, Double salario, String contato) {
		super();
		this.nomeEmpresa = nomeEmpresa;
		this.contato = contato;
		this.area = area;
		this.tempo = tempo;
		this.salario = salario;
	}
	
	public Vaga(JSONObject json) {
		this.nomeEmpresa = (String) json.get("nomeEmpresa");
		this.contato = (String) json.get("contato");
		this.area = (String) json.get("area");
		this.tempo = (Double) json.get("tempo");
		this.salario = (Double) json.get("salario");
	}

	public Vaga() {
		// TODO Auto-generated constructor stub
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject toJSON() {
		JSONObject json = new JSONObject();
		json.put("nomeEmpresa", this.nomeEmpresa);
		json.put("contato", this.contato);
		json.put("area", this.area);
		json.put("tempo", this.tempo);
		json.put("salario", this.salario);
		return json;
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

	public String getContato() {
		return contato;
	}

	public void setContato(String contato) {
		this.contato = contato;
	}

}
