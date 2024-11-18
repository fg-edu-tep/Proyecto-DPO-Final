package LPTH.modelo;

import java.util.ArrayList;
import java.util.Date;

import LPTH.Preguntas.PreguntaAbierta;
import LPTH.Preguntas.PreguntaCerrada;
import LPTH.actividades.Actividad;
import LPTH.actividades.Encuesta;
import LPTH.actividades.Examen;
import LPTH.actividades.Quiz;
import LPTH.actividades.RecursoEducativo;
import LPTH.actividades.Tarea;
import LPTH.usuarios.Estudiante;
import LPTH.usuarios.Resenia;
import LPTH.usuarios.Usuario;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Calendar;  

import java.util.List;
import java.util.Random;


public class LearningPath {
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

    
    public LearningPath(String profesorCreador, String titulo, int id, String descripcion, ArrayList<String> objetivos,
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
        if (!actividades.isEmpty()) {
        	this.actividades = actividades;
        }
        
    }

    // Getters basicos
    public int mostrarCalificacion() {
        return this.rating;
    }
    
    public int getDuracion() {
        return this.duracion;
    }
    
    public int getID() {
        return this.id;
    }

    public Date getFechaModificacion() {
        return this.fechaDeModificacion;
    }
 
    public Date getFechaCreacionn() {
        return this.fechaDeCreacion;
    }

    public String getVersion() {
        return this.version;
    }

    public ArrayList<Actividad> getActividades() {
    	return this.actividades;
    }

    public ArrayList<String> getObjetivos() {
    	return this.objetivos;
    }
    
    public String getProfesorCreador() {
        return this.profesorCreador;
    }

	public String getTitulo() {
		return this.titulo;
	}
	
	public String getnivelDeDificultad() {
		return this.nivelDeDificultad;
	}
	
	public String getDescripcion() {
		return this.descripcion;
	}
	
    
	public float getTasa(){
		return this.tasaDeExitoFracaso;
	}
	
	
	// setters basicos
	public void setRating(int rating) {
	    this.rating = rating;
	}

	public void setFechaDeModificacion(Date fechaDeModificacion) {
	    this.fechaDeModificacion = fechaDeModificacion;
	}

	public void setVersion(String version) {
	    this.version = version;
	}

	public void setActividades(ArrayList<Actividad> actividades) {
	    this.actividades = actividades;
	}
	
	public void agregarActividad(Actividad actividadNueva) {
	    this.actividades.add(actividadNueva);
	}
	
	public void eliminarActividad(Actividad actividadQuitar) {
	    this.actividades.remove(actividadQuitar);
	}

	public void setProfesorCreador(String profesorCreador) {
	    this.profesorCreador = profesorCreador;
	}

	public void setTitulo(String titulo) {
	    this.titulo = titulo;
	}

	public void setDescripcion(String descripcion) {
	    this.descripcion = descripcion;
	}

	public void setnivelDeDificultad(String nivelDeDificultad) {
		this.nivelDeDificultad = nivelDeDificultad;
	}
	
	public void setDuracion(int duracion) {
        this.duracion = duracion;
    }
	
	
	
	
	
	// Métodos complejos de profesor

	public void editarTituloDesc(String titulo, String Descripcion) {
		setTitulo(titulo);
		setDescripcion(descripcion);
	}
	// Métodos complejos de estudiante
	public void notificarEstudianteDue(Estudiante ElEstudiante){
		ArrayList<Actividad> sinCompletar = getActividadesSinCompletar();
    	Instant now = Instant.now(); 
    	Date today = Date.from(now);
    	Calendar cal = Calendar.getInstance(); //Usar cal
    	cal.setTime(today); 
    	cal.add(Calendar.DAY_OF_MONTH, 2); //Avanzar dos dias
    	Date future = cal.getTime(); // EStablecer dos dias en el futuro cómo el futuro
		for (Actividad subAct : sinCompletar) {
			if(((subAct.getDueDate()).before(today)) || ((subAct.getDueDate()).before(future))) { // Si es hoy o en el futur
				ElEstudiante.agregarNotificacion(subAct.getNombre()); // Agregar notificación
			}
		}
	}
	

	
	public ArrayList<Actividad> getActividadesSinCompletar(){
		ArrayList<Actividad> ActividadesNoComp = new ArrayList<Actividad>();
		for (Actividad actividad : actividades) {
			if (!actividad.estaCompletada()) {
				ActividadesNoComp.add(actividad);
				
			}
		}
		return ActividadesNoComp;
	}
	
	// seguro el error es relacionado con super.
	public Encuesta crearEncuesta(boolean obligatoria, String nombre, Date fechaLimite, String descripcion, double calificacion, float rating, boolean esCompletada, ArrayList<Resenia> resenias, double nivelDificultad, boolean estaEmpezado, String tipo, ArrayList<PreguntaAbierta> preguntas) {
		Encuesta encuesta= new Encuesta(obligatoria, nombre, fechaLimite, descripcion, calificacion, rating, esCompletada, resenias, nivelDificultad, estaEmpezado, tipo, preguntas);
		return encuesta;
	}
	
	// seguro el error es relacionado con super.
	public Examen crearExamen(boolean obligatoria, String nombre, Date fechaLimite, String descripcion, double calificacion, float rating, boolean esCompletada, ArrayList<Resenia> resenias, double nivelDificultad, boolean estaEmpezado,String tipo, ArrayList<PreguntaAbierta> preguntas) {
	    Examen examen = new Examen(obligatoria, nombre, fechaLimite, descripcion, calificacion, rating, esCompletada, resenias, nivelDificultad, estaEmpezado, tipo, preguntas);
	    return examen; 
	}
	
	// seguro el error es relacionado con super.
	public Quiz crearQuiz( boolean obligatoria, String nombre, Date fechaLimite, String descripcion, double calificacion, float rating, boolean esCompletada, ArrayList<Resenia> resenias, double nivelDificultad, boolean estaEmpezado, String tipo, ArrayList<PreguntaCerrada> preguntas) {
	    Quiz quiz = new Quiz(obligatoria, nombre, fechaLimite, descripcion, calificacion, rating, esCompletada, resenias, nivelDificultad, estaEmpezado, tipo, preguntas);
	    return quiz;
	}

	
    public RecursoEducativo crearRecursoEd( boolean obligatoria, String nombre, Date fechaLimite, String descripcion, double calificacion, float rating, boolean esCompletada, ArrayList<Resenia> resenias, double nivelDificultad, boolean estaEmpezado, String tipo, String contenido, String tipoC) {
    	RecursoEducativo recursoeducativo = new RecursoEducativo(obligatoria, nombre, fechaLimite, descripcion, calificacion, rating, esCompletada, resenias, nivelDificultad, estaEmpezado,tipo,contenido, tipoC);
	    return recursoeducativo;
	}

	public Tarea crearTarea( boolean obligatoria, String nombre, Date fechaLimite, String descripcion, double calificacion, float rating, boolean esCompletada, ArrayList<Resenia> resenias, double nivelDificultad, boolean estaEmpezado, String tipo) {
		Tarea tarea= new Tarea(obligatoria, nombre, fechaLimite, descripcion, calificacion, rating, esCompletada, resenias, nivelDificultad, estaEmpezado, tipo);
		return tarea;
	}
	
	
	//Por simplicidad recomienda una actividad al estudiante de manera aleatoria.
	//En consola, usar este metodo antes de permitir al estudiante escoger actividad.
	public Actividad recomendarActividad(){
		
		if (actividades.isEmpty()) {
            return null;
        }
		
		else {
		Random random = new Random();
        int indiceAleatorio = random.nextInt(actividades.size());
        Actividad recActividad = actividades.get(indiceAleatorio);
		
		return recActividad;}
	}
}
