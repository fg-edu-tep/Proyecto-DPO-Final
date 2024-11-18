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
    private ArrayList<String> respuestas;
    
    public Encuesta (boolean obligatoria, String nombre, Date fechaLimite, String descripcion, double calificacion, float rating, boolean esCompletada, ArrayList<Resenia> resenias, double nivelDificultad, boolean estaEmpezado, String tipo, ArrayList<PreguntaAbierta> preguntas) {
        super(obligatoria, nombre, fechaLimite, descripcion, calificacion, rating, esCompletada, resenias, nivelDificultad, estaEmpezado, tipo);
    	this.preguntas= preguntas;
    	this.counterPregunta= 0;
        this.respuestas= new ArrayList<>();
    }
    
    @Override
    public double calificarActividad(String input) { 
 
    	if (esCompletada==true && input.toLowerCase().equals("si")) { //saca 5
		    	double calificacion= 5.0; 
		        return calificacion;
	        
        }
    	else {
    		double calificacion= 0.0;
    		return calificacion;
    	}
    }	
    
    public void setPreguntas (ArrayList<PreguntaAbierta> nuevaspreguntas) {
    	this.preguntas= nuevaspreguntas;
    }

    public String nextQuestion(String inputUsuario) {
        if (!estaEmpezado) {
            return "Debe iniciar el quiz primero.";
        }

        if (counterPregunta < preguntas.size()) {
            if (!inputUsuario.isEmpty()) {
                respuestas.add(inputUsuario);
                counterPregunta++; 					//solo se aumenta si el input no esta vacio.
            }

            if (counterPregunta < preguntas.size()) {
                Pregunta preguntaActual = preguntas.get(counterPregunta);
                String enunciadoPregunta = preguntaActual.getEnunciado(); 

                return enunciadoPregunta + "\nIngrese su respuesta:";
            } else {
                esCompletada = true;
                return "Ya completó su Encuesta.";
            }
        }

        return "Ya completó su Encuesta.";
    }
    


}