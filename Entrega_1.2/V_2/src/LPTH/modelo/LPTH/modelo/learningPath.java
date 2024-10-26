package LPTH.modelo;

import java.util.ArrayList;
import java.util.Date;

import LPTH.Preguntas.PreguntaCerrada;
import LPTH.actividades.Actividad;
import LPTH.actividades.Encuesta;
import LPTH.actividades.Quiz;
import LPTH.usuarios.Resenia;
import LPTH.usuarios.Usuario;

public class learningPath {
    private String profesorCreador;
    private String titulo;
    private int id;
    private String descripcion;
    private ArrayList<String> objetivos;
    private String nivelDeDificultad;
    private int duracion;
    private int rating;
    private Date fechaDeCreacion;
    private Date fechaDeModificacion;
    private String version;
    private float tasaDeExitoFracaso;
    private ArrayList<Actividad> actividades;

    
    public learningPath(String profesorCreador, String titulo, int id, String descripcion, ArrayList<String> objetivos,
                        String nivelDeDificultad, int duracion, int rating, Date fechaDeCreacion, Date fechaDeModificacion,
                        String version, float tasaDeExitoFracaso, ArrayList<Actividad> actividades) {
        this.profesorCreador = profesorCreador;
        this.titulo = titulo;
        this.id = id;
        this.descripcion = descripcion;
        this.objetivos = objetivos;
        this.nivelDeDificultad = nivelDeDificultad;
        this.duracion = duracion;
        this.rating = rating;
        this.fechaDeCreacion = fechaDeCreacion;
        this.fechaDeModificacion = fechaDeModificacion;
        this.version = version;
        this.tasaDeExitoFracaso = tasaDeExitoFracaso;
        this.actividades = actividades;
    }

    // 
    
    public int mostrarCalificacion() {
        return rating;
    }

    public Date getFechaModificacion() {
        return fechaDeModificacion;
    }

    public String getVersion() {
        return version;
    }

    public ArrayList<Actividad> getActividades() {
    	return actividades;
    }

    public String getProfesorCreador() {
        return profesorCreador;
    }


	public String getTitulo() {
		return this.titulo;
	}
	
	public String getDescripcion() {
		return this.descripcion;
	}
	
	public String crearActividad(Usuario usuario, Actividad actividad, int cantidadPreguntas, String actividadDeseada) { //Profesor usa crearActividad desde su propio metodo)
		ArrayList<preg>
		if (usuario.getTipotoLowerCase() == "Profesor") {
			if(actividadDeseada.toLowerCase()== "quiz") {
				crearQuiz()
			}
		}
		else {
			return "Para crear/modificar actividad(s) debe ser profesor")
		}
	}
	
	
	public Encuesta crearEncuesta() {
		
	}
	
	public Examen crearExamen() {
		
	}
	
	
    public Quiz crearQuiz() {
    	
    }
	public RecursoEducativo crearRecursoEd() {
		
	}
	
	public Tarea crearTarea() {
		
		
		
	}
	
	public void notificarEstudiante(conocer estudiante, conocer actividad) {
		
	}
	
    
}