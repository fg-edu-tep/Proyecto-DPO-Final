package LPTH.actividades;
import java.util.ArrayList;
import java.util.Date;

import LPTH.usuarios.Resenia;

public class Tarea extends Actividad{

	 public Tarea (boolean obligatoria, int notaMinima, String nombre, Date fechaLimite, String descripcion, double calificacion, float rating, boolean esCompletada, ArrayList<Resenia> resenias, double nivelDificultad, boolean estaEmpezado) {
	    	super(obligatoria, notaMinima, nombre, fechaLimite, descripcion, calificacion, rating, esCompletada, resenias, nivelDificultad, estaEmpezado, "DAR UN OBJETIVO"); //TODO FIXED: Faltaba argumento String objetivo 
	 }
	
    @Override
    public double calificarActividad() {		
        if (esCompletada==true) {
        	return 5.0;
        }
        return 0.0;
    }

    @Override
    public String notificarEstudiante() {
        return("Debe realizar su Tarea!");

    }
    

}
