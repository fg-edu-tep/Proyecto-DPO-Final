package LPTH.modelo;

import java.util.ArrayList;
import java.util.Date;
import LPTH.actividades.Actividad;
import LPTH.usuarios.Estudiante;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Calendar;  

import java.util.List;


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
	
}