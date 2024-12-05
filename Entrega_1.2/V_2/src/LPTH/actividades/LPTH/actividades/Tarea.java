package LPTH.actividades;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

import LPTH.usuarios.Resenia;

public class Tarea extends Actividad{

	 public Tarea (boolean obligatoria, String nombre, Instant fechaLimite, String descripcion, double calificacion, float rating, boolean esCompletada, ArrayList<Resenia> resenias, double nivelDificultad, boolean estaEmpezado, String tipo) {
	    	super(obligatoria, nombre, fechaLimite, descripcion, calificacion, rating, esCompletada, resenias, nivelDificultad, estaEmpezado, tipo); 
	 }
	
    @Override
    public double calificarActividad(String input) {		
        if (esCompletada==true) {
        	return 5.0;
        }
        return 0.0;
    }
    
    public boolean completarTarea() {
    	return this.esCompletada= true;
    }
}
//Test