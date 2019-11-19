package com.sd2019.jobserver;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

@Path("/job_oportunity")
public class VagaService {

	public ArrayList<Vaga> vagas = new ArrayList<Vaga>();

	@GET
	@Produces("application/json")
	public ArrayList<Vaga> getCurriculos(@QueryParam("area") String area, @QueryParam("salary") Double salary) {
		ArrayList<Vaga> returnList = new ArrayList<Vaga>();
		for (Vaga vaga : vagas) {
			if (vaga.getSalario() >= salary && vaga.getArea() == area) {
				returnList.add(vaga);
			}
		}
		return returnList;
	}

	@POST
	@Consumes("application/json")
	@Produces("text/plain")
	public String newCurriculo(Vaga vaga) {
		vagas.add(vaga);
		return "Vaga inserida com sucesso";
	}

	@PUT
	@Consumes("application/json")
	@Produces("text/plain")
	public String updateCurriculo(Vaga vaga) {
		for (Vaga vagaFor : vagas) {
			if (vaga.getContato() == vagaFor.getContato()) {
				vagaFor = vaga;
			}
		}
		return "Vaga atualizada com sucesso";
	}
}
