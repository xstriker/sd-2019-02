package com.sd2019.jobserver;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

@Path("/curriculum")
public class CurriculumService {

	public ArrayList<Curriculo> curriculos = new ArrayList<Curriculo>();

	@GET
	@Produces("application/json")
	public ArrayList<Curriculo> getCurriculos(@QueryParam("area") String area) {
		ArrayList<Curriculo> returnList = new ArrayList<Curriculo>();
		for (Curriculo curriculo : curriculos) {
			if (curriculo.getArea() == area) {
				returnList.add(curriculo);
			}
		}
		return returnList;
	}

	@POST
	@Consumes("application/json")
	@Produces("text/plain")
	public String newCurriculo(Curriculo curriculo) {
		curriculos.add(curriculo);
		return "Curriculo inserido com sucesso";
	}

	@PUT
	@Consumes("application/json")
	@Produces("text/plain")
	public String updateCurriculo(Curriculo curriculo) {
		for (Curriculo curriculoFor : curriculos) {
			if (curriculoFor.getContato() == curriculo.getContato()) {
				curriculoFor = curriculo;
			}
		}
		return "Curriculo atualizado com sucesso";
	}
}
