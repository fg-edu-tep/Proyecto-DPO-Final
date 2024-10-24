package LPTH.actividades;

import java.util.ArrayList;
import java.util.Date;

public class RecursoEducativo extends Actividad {
    private String contenido;
    private String tipo;


    @Override
    public Double calificarActividad(boolean esCompletada) {		
        if (esCompletada==true) {
        	return 5.0;
        }
    }

    @Override
    public String notificarEstudiante() {
        return("Debe realizar su Examen!");

    }
    
    public void completarTarea() {
    	esCompletada = true;
    }
    
    public boolean getEstado() {
    	return esCompletada;
    }
}