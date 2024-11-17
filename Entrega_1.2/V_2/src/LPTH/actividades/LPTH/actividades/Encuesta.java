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
    
    public Encuesta (boolean obligatoria, String nombre, Date fechaLimite, String descripcion, double calificacion, float rating, boolean esCompletada, ArrayList<Resenia> resenias, double nivelDificultad, boolean estaEmpezado,ArrayList<PreguntaAbierta> preguntas) {
    	super(obligatoria, nombre, fechaLimite, descripcion, calificacion, rating, esCompletada, resenias, nivelDificultad, estaEmpezado);
    	this.preguntas= preguntas;
    	this.counterPregunta= 0;
    }
    
    @Override
    public double calificarActividad() {		
        if (esCompletada==true) {
        	return 5.0;
        }
        return 0.0;
    }
    
    public void setPreguntas (ArrayList<PreguntaAbierta> nuevaspreguntas) {
    	this.preguntas= nuevaspreguntas;
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
        	esCompletada= true;
        	return("Ya completo su encuesta.");
        }
		return "Debe iniciar su encuesta";
    }
    


}