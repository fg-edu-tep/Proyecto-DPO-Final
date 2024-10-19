package modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import modelo.Usuarios.Usuario; 

public class Sistema {
    private Map<String, String> logIns; 
    private List<LearningPath> learningPaths; 
    private LinkedList<Usuario> usuarios; 

    public Sistema() {
        this.logIns = new HashMap<>();
        this.learningPaths = new ArrayList<>();
        this.usuarios = new LinkedList<>();
    }

    public boolean autenticarUsuario(String email, String contraseña) {
        return logIns.containsKey(email) && logIns.get(email).equals(contraseña);
    }

    public void cargarLearningPath() {
        // Implementación para cargar los LearningPaths (puede ser desde un archivo o una base de datos)
    }

    public void salvarLearningPath() {
        // Implementación para guardar los LearningPaths (puede ser en un archivo o una base de datos)
    }

    // Métodos para cargar y salvar Usuarios
    public void cargarUsuarios() {
        // Implementación para cargar usuarios (puede ser desde un archivo o una base de datos)
    }

    public void salvarUsuarios() {
        // Implementación para guardar usuarios (puede ser en un archivo o una base de datos)
    }

    public void cargarSistema() {
        // Implementación para cargar todos los datos del sistema
    }

    public void salvarSistema() {
        // Implementación para guardar todos los datos del sistema
    }

    public List<LearningPath> getLearningPaths() {
        return learningPaths;
    }

    public LinkedList<Usuario> getUsuarios() {
        return usuarios;
    }

    public LearningPath recomendarLearningPath() {
        // Implementación para recomendar un LearningPath según algún criterio (por ejemplo, rating o nivel de dificultad)
        if (!learningPaths.isEmpty()) {
            return learningPaths.get(0); // Ejemplo simple que devuelve el primer LearningPath
        }
        return null;
    }

    
}
