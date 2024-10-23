package modelo;

import java.util.ArrayList;
import java.util.Date;

import modelo.Actividades.Actividad;

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
        this.actividades = actividades;
    }

    
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
    
}