package LPTH.modelo;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
    private Map<Integer, LearningPath> learningPaths;

    public Sistema() {
        this.learningPaths = new HashMap<Integer, LearningPath>(); 
    }

    // Métodos de conexión:
    public LearningPath crearLearningPath(Profesor profe, String titulo, String descripcion,
            String nivelDeDificultad, int duracion) {
    	
			String profesorCreador = profe.getNombre();
			int id = (int) (Math.random() * 999999); // Generar ID único
			
			Instant fechaDeCreacion = Instant.now();
			
			Instant fechaDeModificacion = Instant.now();
			
			String version = "1.0";
			
			int rating = 5;
			
			float tasaDeExitoFracaso = 0;
			ArrayList<Actividad> actividades = new ArrayList<>();
			
			ArrayList<String> objetivos = new ArrayList<>();
			objetivos.add("Entender los conceptos básicos de " + titulo);
			objetivos.add(descripcion);
			
			LearningPath learningPath = new LearningPath(
			profesorCreador, titulo, id, descripcion, objetivos,
			nivelDeDificultad, duracion, rating, fechaDeCreacion,
			fechaDeModificacion, version, tasaDeExitoFracaso, actividades
			);
			
			System.out.println("!!!Añadiendo LearningPath al sistema...");
			this.learningPaths.put(id, learningPath);
			
			// Verificar si fue añadido
			if (this.learningPaths.containsKey(id)) {
			System.out.println("!!!LearningPath añadido correctamente: " + id);
			} else {
			System.out.println("Error: No se pudo añadir el LearningPath al sistema.");
			}
			
			return learningPath;
    		}



    public Instant getCurrentInstant() {
        return Instant.now();
    }

    public ArrayList<LearningPath> getLearningPaths() {
        return new ArrayList<>(learningPaths.values()); // Retornar los valores en una lista
    }

    public LearningPath getLearningPath(Integer ID) {
        return learningPaths.get(ID);
    }

    public LearningPath recomendarLearningPath() throws ExceptionNoPersistencia {
        ArrayList<LearningPath> lp = getLearningPaths();
        if (lp.isEmpty()) {
            throw new ExceptionNoPersistencia("No hay learningPaths para recomendar");
        }
        int recommend = (int) (Math.random() * lp.size());
        return lp.get(recommend);
    }
    
    //Extra LP por id, lista de usuario tiene lp con Codigo Integer
    public LearningPath getLP(Integer id) {
    	LearningPath lp= learningPaths.get(id); 
    	return lp;
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