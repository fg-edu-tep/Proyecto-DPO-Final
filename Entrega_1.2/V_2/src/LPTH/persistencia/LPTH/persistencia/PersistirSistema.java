package LPTH.persistencia;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.*;
import LPTH.modelo.Sistema;

import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.time.Instant;
import java.util.LinkedList;

public class PersistirSistema {
    private static final String sistemaFilePath = "Entrega_1.2\\V_2\\src\\LPTH\\persistencia\\LPTH\\persistencia\\persistenciaSistema.json";

    public void salvarSistema(Sistema sistema) throws IOException {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(Instant.class, new InstantTypeAdapter())
                .create();
        String jsonString = gson.toJson(sistema);
        System.out.println("JSON Output for Sistema: \n" + jsonString);
        try (FileWriter writer = new FileWriter(sistemaFilePath)) {
            writer.write(jsonString);
        } catch (IOException e) {
            throw e;
        }
    }

    public Sistema cargarSistema() throws IOException {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Instant.class, new InstantTypeAdapter())
                .create();
        System.out.println("Cargando sistema...2");
        try (FileReader reader = new FileReader(sistemaFilePath)) {
        	
            Sistema sistema = gson.fromJson(reader, Sistema.class);
            return sistema;
        } catch (IOException e) {
            throw e;
        }
    }
}
