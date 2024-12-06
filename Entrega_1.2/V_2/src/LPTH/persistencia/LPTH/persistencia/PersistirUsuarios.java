package LPTH.persistencia;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import LPTH.modelo.UserFactory;
import LPTH.usuarios.Profesor;
import LPTH.usuarios.Estudiante;

import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.time.Instant;
import java.util.LinkedList;

public class PersistirUsuarios {
    private static final String profesoresFilePath = "src/LPTH/persistencia/LPTH/persistencia/profesores.json";
    private static final String estudiantesFilePath = "src/LPTH/persistencia/LPTH/persistencia/estudiantes.json";

    public void salvarProfesores(LinkedList<Profesor> profesores) throws IOException {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(Instant.class, new InstantTypeAdapter())
                .create();
        try (FileWriter writer = new FileWriter(profesoresFilePath)) {
            gson.toJson(profesores, writer);
        }
    }

    public LinkedList<Profesor> cargarProfesores() throws IOException {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Instant.class, new InstantTypeAdapter())
                .create();
        try (FileReader reader = new FileReader(profesoresFilePath)) {
            Profesor[] profesoresArray = gson.fromJson(reader, Profesor[].class);
            LinkedList<Profesor> profesoresList = new LinkedList<>();
            if (profesoresArray != null) {
                for (Profesor profesor : profesoresArray) {
                    profesoresList.add(profesor);
                }
            }
            return profesoresList;
        }
    }

    public void salvarEstudiantes(LinkedList<Estudiante> estudiantes) throws IOException {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(Instant.class, new InstantTypeAdapter())
                .create();
        try (FileWriter writer = new FileWriter(estudiantesFilePath)) {
            gson.toJson(estudiantes, writer);
        }
    }

    public LinkedList<Estudiante> cargarEstudiantes() throws IOException {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Instant.class, new InstantTypeAdapter())
                .create();
        try (FileReader reader = new FileReader(estudiantesFilePath)) {
            Estudiante[] estudiantesArray = gson.fromJson(reader, Estudiante[].class);
            LinkedList<Estudiante> estudiantesList = new LinkedList<>();
            if (estudiantesArray != null) {
                for (Estudiante estudiante : estudiantesArray) {
                    estudiantesList.add(estudiante);
                }
            }
            return estudiantesList;
        }
    }
}
