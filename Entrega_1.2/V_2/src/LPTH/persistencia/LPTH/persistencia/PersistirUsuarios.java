package LPTH.persistencia;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import LPTH.modelo.Sistema;
import LPTH.modelo.UserFactory;

import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;

public class PersistirUsuarios {
	private static final String filePath = "src/LPTH/persistencia/LPTH/persistencia/persistenciaUsuarios.json";
	
	
	public void salvarSistema(UserFactory userFactory) throws IOException {
	    Gson gson = new GsonBuilder().setPrettyPrinting().create();
	    try (FileWriter writer = new FileWriter(filePath)) {
	        gson.toJson(userFactory, writer);
	    }
	}
public UserFactory cargarSistema() throws IOException {
    Gson gson = new Gson();
    try (FileReader reader = new FileReader(filePath)) {
        return gson.fromJson(reader, UserFactory.class);
    }
}

}