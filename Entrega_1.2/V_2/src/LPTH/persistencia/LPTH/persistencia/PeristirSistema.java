package LPTH.persistencia;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import LPTH.modelo.Sistema;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;

public class PeristirSistema {
	private static final String filePath = "src/LPTH/persistencia/LPTH/persistencia/peristenciaSistema.json";
	
	
	public void salvarSistema(Sistema sistemaCentral) throws IOException {
	    Gson gson = new GsonBuilder().setPrettyPrinting().create();
	    try (FileWriter writer = new FileWriter(filePath)) {
	        gson.toJson(sistemaCentral, writer);
	    }
	}
public Sistema cargarSistema() throws IOException {
    Gson gson = new Gson();
    try (FileReader reader = new FileReader(filePath)) {
        return gson.fromJson(reader, Sistema.class);
    }
}

}