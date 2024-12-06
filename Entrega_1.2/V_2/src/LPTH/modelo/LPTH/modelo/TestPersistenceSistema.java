package LPTH.modelo;

import java.io.IOException;
import LPTH.exceptions.ExceptionNoPersistencia;
import LPTH.usuarios.Profesor;
import LPTH.usuarios.Estudiante;
import LPTH.persistencia.PersistirSistema;

public class TestPersistenceSistema {
    public static void main(String[] args) {
        // Create a Sistema instance
        Sistema sistema = new Sistema();

        // Create some LearningPaths
        Profesor profe = new Profesor(1, "John Doe", "john.doe@example.com", "password123", "2024-12-05", "Mathematics");
        sistema.crearLearningPath(profe, "Learning Path 1", "Description 1", "Beginner", 10);
        sistema.crearLearningPath(profe, "Learning Path 2", "Description 2", "Intermediate", 15);

        // Display created LearningPaths
        System.out.println("Learning Paths created:");
        sistema.getLearningPaths().forEach(lp -> {
            System.out.println("- " + lp.getTitulo() + " (" + lp.getnivelDeDificultad() + ")");
        });

        // Save the sistema
        System.out.println("\nSaving Sistema to JSON...");
        try {
            sistema.saveSistema();
            System.out.println("Sistema saved successfully.");
        } catch (ExceptionNoPersistencia e) {
            e.printStackTrace();
        }

        // Load the sistema
        System.out.println("\nLoading Sistema from JSON...");
        Sistema loadedSistema;
        try {
            loadedSistema = sistema.loadSistema();
            System.out.println("Sistema loaded successfully.");
            System.out.println("Number of Learning Paths loaded: " + loadedSistema.getLearningPaths().size());
            System.out.println("Learning Paths loaded:");
            loadedSistema.getLearningPaths().forEach(lp -> {
                System.out.println("- " + lp.getTitulo() + " (" + lp.getnivelDeDificultad() + ")");
            });
        } catch (ExceptionNoPersistencia e) {
            e.printStackTrace();
        }
    }
}
