package LPTH.persistencia;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import LPTH.modelo.Sistema;

import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.time.Instant;
import java.util.LinkedList;

public class PersistirSistema {
    private static final String sistemaFilePath = "src/LPTH/persistencia/LPTH/persistencia/sistema.json";

    public void salvarSistema(Sistema sistema) throws IOException {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(Instant.class, new InstantTypeAdapter())
                .create();
        String jsonString = gson.toJson(sistema);
        System.out.println("JSON Output for Sistema: \n" + jsonString);
        try (FileWriter writer = new FileWriter(sistemaFilePath)) {
            writer.write(jsonString);
            System.out.println("Sistema saved successfully to: " + sistemaFilePath);
        } catch (IOException e) {
            System.out.println("Failed to save Sistema to: " + sistemaFilePath);
            throw e;
        }
    }

    public Sistema cargarSistema() throws IOException {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Instant.class, new InstantTypeAdapter())
                .create();
        System.out.println("Attempting to load Sistema from: " + sistemaFilePath);
        try (FileReader reader = new FileReader(sistemaFilePath)) {
            Sistema sistema = gson.fromJson(reader, Sistema.class);
            System.out.println("Sistema loaded successfully from: " + sistemaFilePath);
            return sistema;
        } catch (IOException e) {
            System.out.println("Failed to load Sistema from: " + sistemaFilePath);
            throw e;
        }
    }
}
