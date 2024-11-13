package LPTH.actividades;

import java.util.ArrayList;
import java.util.Date;

import LPTH.usuarios.Resenia;

public class RecursoEducativo extends Actividad {
    private String contenido;
    private String tipo;

    public RecursoEducativo (boolean obligatoria, String nombre, Date fechaLimite, String descripcion, double calificacion, float rating, boolean esCompletada, ArrayList<Resenia> resenias, double nivelDificultad, boolean estaEmpezado, String contenido, String tipo) {
    	super(obligatoria, nombre, fechaLimite, descripcion, calificacion, rating, esCompletada, resenias, nivelDificultad, estaEmpezado);
    	this.contenido=contenido;
    	this.tipo=tipo;
    }

    @Override
    public double calificarActividad() {		
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

}