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

@Path("/curriculum")
public class CurriculumService {

	private String folderPath = "\\curriculos";

	@GET
	@Produces("application/json")
	public ArrayList<Curriculo> getCurriculos(@QueryParam("area") String area) {
		return null;
	}

	@POST
	@Consumes("application/json")
	@Produces("text/plain")
	public String newCurriculo(Curriculo curriculo) {
		try {
			this.newFile(curriculo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Curriculo asdaspdj com sucesso";
	}

	@PUT
	@Consumes("application/json")
	@Produces("text/plain")
	public String updateCurriculo(Curriculo curriculo) {
		try {
			this.deleteFile(returnCurriculoPath(curriculo, "json"));
			this.newFile(curriculo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Curriculo atualizado com sucesso";
	}
	
	private String returnCurriculoPath(Curriculo curriculo, String ext) {
		return this.folderPath + curriculo.getArea() + "-" + curriculo.getContato() + "." + ext;
	}
	
	private void newFile(Curriculo curriculo) {
		try {
			JSONObject curriculoJson = curriculo.toJSON();
			FileWriter file = new FileWriter(returnCurriculoPath(curriculo, "json"));
			file.write(curriculoJson.toJSONString());
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
