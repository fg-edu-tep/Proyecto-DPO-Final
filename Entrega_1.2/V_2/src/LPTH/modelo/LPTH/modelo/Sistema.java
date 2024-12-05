package LPTH.modelo;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import LPTH.Preguntas.PreguntaAbierta;
import LPTH.Preguntas.PreguntaCerrada;
import LPTH.actividades.Actividad;
import LPTH.exceptions.ExceptionNoPersistencia;
import LPTH.exceptions.ExceptionUsuarioNoEncontrado;
import LPTH.modelo.*;
import LPTH.usuarios.Estudiante;
import LPTH.usuarios.Profesor;
import LPTH.usuarios.Resenia;
import LPTH.usuarios.Usuario;
import LPTH.persistencia.*;

public class Sistema {
    private Map<String, LearningPath> learningPaths;
    private Random rand = new Random();

    public Sistema() {
        this.learningPaths = new HashMap<String, LearningPath>();
    }

    // Métodos de conexión:
    public LearningPath crearLearningPath(Profesor profe, String titulo, String descripcion,
                                          String nivelDeDificultad, int duracion) {
        String profesorCreador = profe.getNombre();
        int id = rand.nextInt(999999);
        Instant fechaDeCreacion = getCurrentInstant();
        Instant fechaDeModificacion = getCurrentInstant();
        String version = "1";
        int rating = 5;
        int tasaDeExitoFracaso = 0;
        ArrayList<Actividad> actividades = new ArrayList<>();
        ArrayList<String> objetivos = new ArrayList<>();

        LearningPath originates = new LearningPath(profesorCreador, titulo, id, descripcion, objetivos,
                nivelDeDificultad, duracion, rating, fechaDeCreacion, fechaDeModificacion,
                version, tasaDeExitoFracaso, actividades);
        this.learningPaths.put(titulo, originates);
        return originates;
    }

    public Instant getCurrentInstant() {
        return Instant.now();
    }

    public ArrayList<LearningPath> getLearningPaths() {
        return new ArrayList<>(learningPaths.values()); // Retornar los valores en una lista
    }

    public LearningPath getLearningPath(String name) {
        return learningPaths.get(name);
    }

    public LearningPath recomendarLearningPath() throws ExceptionNoPersistencia {
        ArrayList<LearningPath> lp = getLearningPaths();
        if (lp.isEmpty()) {
            throw new ExceptionNoPersistencia("No learning paths available for recommendation.");
        }
        int recommend = rand.nextInt(lp.size());
        return lp.get(recommend);
    }

    // Persistencia

    public Sistema loadSistema() throws ExceptionNoPersistencia {
        /* Cargar el sistema desde un Json usando Gson, ruta predeterminada */
        PersistirSistema fileMngr = new PersistirSistema();
        Sistema sistemaCentral;
        try {
            sistemaCentral = fileMngr.cargarSistema();
            if (sistemaCentral == null) {
                throw new ExceptionNoPersistencia("Archivo de persistir sistema vacío");
            }
        } catch (IOException e) {
            throw new ExceptionNoPersistencia("No hay un archivo de sistemaCentral", e);
        }
        return sistemaCentral;
    }

    public void saveSistema() throws ExceptionNoPersistencia {
        /* Guardar el sistema a Json Usando Gson, ruta predeterminada */
        PersistirSistema fileMngr = new PersistirSistema();
        try {
            fileMngr.salvarSistema(this);
        } catch (IOException e) {
            throw new ExceptionNoPersistencia("Error al guardar el sistema.", e);
        }
    }
}
