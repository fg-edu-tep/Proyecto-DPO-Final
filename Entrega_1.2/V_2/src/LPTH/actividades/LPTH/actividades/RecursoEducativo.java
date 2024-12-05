package LPTH.actividades;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

import LPTH.usuarios.Resenia;

public class RecursoEducativo extends Actividad {
    private String contenido;
    private String tipoC;

    public RecursoEducativo (boolean obligatoria, String nombre, Instant fechaLimite, String descripcion, double calificacion, float rating, boolean esCompletada, ArrayList<Resenia> resenias, double nivelDificultad, boolean estaEmpezado, String tipo, String contenido, String tipoC) {
    	super(obligatoria, nombre, fechaLimite, descripcion, calificacion, rating, esCompletada, resenias, nivelDificultad, estaEmpezado, tipo);
    	this.contenido=contenido;
    	this.tipoC=tipoC;
    }

    @Override
    public double calificarActividad(String input) {		
        if (esCompletada==true) {
        	return 5.0;
        }
        return 0.0;
    }
    
    public void completarTarea() {
    	esCompletada = true;
    }
    
    public boolean getEstado() {
    	return this.esCompletada;
    }
    
    public String getTipo() {
        return this.tipo;
    }

    public String getContenido() {
        return this.contenido;
    }
    
    public boolean completarRD() {
    	return this.esCompletada= true;
    }
}