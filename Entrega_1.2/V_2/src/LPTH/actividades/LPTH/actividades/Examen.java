package LPTH.actividades;

import java.awt.geom.Arc2D.Double;
import java.util.ArrayList;
import java.util.Date;
import LPTH.Preguntas.PreguntaAbierta;
import LPTH.usuarios.Resenia;

public class Examen extends Actividad {

    private ArrayList<PreguntaAbierta> preguntas;
    private double calificacionMinima;
    private int counterCorrecta;
    private int counterPregunta;
    
    public Examen (boolean obligatoria, int notaMinima, String nombre, Date fechaLimite, String descripcion, double calificacion, float rating, boolean esCompletada, ArrayList<Resenia> resenias, double nivelDificultad, boolean estaEmpezado, String objetivo) {
    	super(obligatoria, notaMinima, nombre, fechaLimite, descripcion, calificacion, rating, esCompletada, resenias, nivelDificultad, estaEmpezado, objetivo);
    	this.preguntas= new ArrayList<PreguntaAbierta>();
    	this.calificacionMinima= calificacionMinima;
    	this.counterCorrecta= 0;
    }

    @Override
     public double calificarActividad() {  
            if (esCompletada==true) {
            	return 5.0;
	            }
            }
            return 0.0;
     }    
   

    @Override
    public String notificarEstudiante() {   
        return("Debe realizar su Examen!");
    }

    public String nextQuestion(){

        if(estaEmpezado == true) {
        	if (counterPregunta < 4) {
            	String enunciadoPregunta = preguntas.get(counterPregunta).getEnunciado(); 
            	counterPregunta ++;
            	return (enunciadoPregunta);
                
            }
        }
        else if (counterPregunta== (preguntas.size())){
        	esCompletada= true;
        	return("Ya completo su Examen.");
        }
        return("Debe iniciar la Examen primero.");
    }
}