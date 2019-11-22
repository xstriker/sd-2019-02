package com.sd2019.jobserver;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.json.simple.JSONObject;

@Path("/job_opportunity")
public class VagaService {
	
	private String folderPath = "\\vagas";

	@GET
	@Produces("application/json")
	public ArrayList<Vaga> getCurriculos(@QueryParam("area") String area, @QueryParam("salary") Double salary) {
		return null;
	}

	@POST
	@Consumes("application/json")
	@Produces("text/plain")
	public String newCurriculo(Vaga vaga) {
		try {
			this.newFile(vaga);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Vaga inserida com sucesso";
	}

	@PUT
	@Consumes("application/json")
	@Produces("text/plain")
	public String updateCurriculo(Vaga vaga) {
		try {
			this.deleteFile(returnVagaPath(vaga, "json"));
			this.newFile(vaga);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Vaga atualizada com sucesso";
	}
	
	private String returnVagaPath(Vaga vaga, String ext) {
		return this.folderPath + vaga.getArea() + "-" + vaga.getContato() + "." + ext;
	}
	
	private void newFile(Vaga vaga) {
		try {
			JSONObject vagaJson = vaga.toJSON();
			FileWriter file = new FileWriter(returnVagaPath(vaga, "json"));
			file.write(vagaJson.toJSONString());
			file.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void deleteFile(String path) {
		File file = new File(path);
		if (file.delete()) {
			System.out.println("File deleted successfully");
		} else {
			System.out.println("Failed to delete the file");
		}
	}
}
