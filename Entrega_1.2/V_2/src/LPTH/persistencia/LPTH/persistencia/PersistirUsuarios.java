package LPTH.persistencia;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import LPTH.modelo.UserFactory;
import LPTH.usuarios.Profesor;
import LPTH.usuarios.Estudiante;

import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.time.Instant;
import java.util.LinkedList;

public class PersistirUsuarios {
    public static final String profesoresFilePath = "src/LPTH/persistencia/LPTH/persistencia/profesores.json";
    public static final String estudiantesFilePath = "src/LPTH/persistencia/LPTH/persistencia/estudiantes.json";

    public void salvarProfesores(LinkedList<Profesor> profesores) throws IOException {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(Instant.class, new InstantTypeAdapter())
                .create();
        String jsonString = gson.toJson(profesores);
        try (FileWriter writer = new FileWriter(profesoresFilePath)) {
            writer.write(jsonString);
        }
    }

    public LinkedList<Profesor> cargarProfesores() throws IOException {
        File archivoProfesores = new File(profesoresFilePath);
        if (!archivoProfesores.exists()) {
            System.out.println("El archivo de profesores no existe, creando uno nuevo...");
            archivoProfesores.getParentFile().mkdirs(); // Crea las carpetas necesarias si no existen
            archivoProfesores.createNewFile(); // Crea el archivo vacío en caso de no encontrar el otro
            return new LinkedList<>(); // Retorna una lista vacía ya que no hay profesores todavía
        }

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Instant.class, new InstantTypeAdapter())
                .create();

        try (FileReader reader = new FileReader(archivoProfesores)) {
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
        String jsonString = gson.toJson(estudiantes);
        try (FileWriter writer = new FileWriter(estudiantesFilePath)) {
            writer.write(jsonString);
        }
    }

    public LinkedList<Estudiante> cargarEstudiantes() throws IOException {
        File archivoEstudiantes = new File(estudiantesFilePath);
        if (!archivoEstudiantes.exists()) {
            System.out.println("El archivo de estudiantes no existe, creando uno nuevo...");
            archivoEstudiantes.getParentFile().mkdirs(); // Crea las carpetas necesarias si no existen
            archivoEstudiantes.createNewFile(); // Crea el archivo vacío
            return new LinkedList<>(); // Retorna una lista vacía ya que no hay estudiantes todavía
        }

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Instant.class, new InstantTypeAdapter())
                .create();

        try (FileReader reader = new FileReader(archivoEstudiantes)) {
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
