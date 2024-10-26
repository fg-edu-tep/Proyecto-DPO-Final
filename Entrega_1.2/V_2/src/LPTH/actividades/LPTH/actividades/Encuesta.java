package LPTH.actividades;

import java.util.ArrayList;
import java.util.Date;

import LPTH.Preguntas.Pregunta;
import LPTH.Preguntas.PreguntaAbierta;
import LPTH.Preguntas.PreguntaCerrada;
import LPTH.usuarios.Resenia;


public class Encuesta extends Actividad {
	

    private ArrayList<PreguntaAbierta> preguntas;
    private int counterPregunta;
    
    public Encuesta (boolean obligatoria, int notaMinima, String nombre, Date fechaLimite, String descripcion, double calificacion, float rating, boolean esCompletada, ArrayList<Resenia> resenias, double nivelDificultad, boolean estaEmpezado, String objetivo) {
    	super(obligatoria, notaMinima, nombre, fechaLimite, descripcion, calificacion, rating, esCompletada, resenias, nivelDificultad, estaEmpezado, objetivo);
    	this.preguntas= new ArrayList<PreguntaAbierta>();
    	this.counterPregunta= 0;
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
        return "Debe realizar su Encuesta!";

    }

    public String nextQuestion() {
        if(estaEmpezado == true) {
        	if (counterPregunta < 4) {
            	String enunciadoPregunta = preguntas.get(counterPregunta).getEnunciado(); 
            	counterPregunta ++;
            	return (enunciadoPregunta);
                
            }
        }
        else if (counterPregunta== (preguntas.size())){
        	return("Ya completo su encuesta.");
        }
		return "Debe iniciar su encuesta";
    }
    


}