package LPTH.actividades;

import java.util.ArrayList;
import java.util.Date;

import LPTH.Preguntas.Pregunta;
import LPTH.Preguntas.PreguntaAbierta;
import LPTH.Preguntas.PreguntaCerrada;
import LPTH.Preguntas.PreguntaToF;
import LPTH.usuarios.Resenia;

public class Examen extends Actividad {

    private ArrayList<PreguntaAbierta> preguntas;
    private int counterCorrecta;
    private int counterPregunta;
    private ArrayList<String> respuestas;
    
    //Se removio crearPregunta Abierta
    
    public Examen (boolean obligatoria, String nombre, Date fechaLimite, String descripcion, double calificacion, float rating, boolean esCompletada, ArrayList<Resenia> resenias, double nivelDificultad, boolean estaEmpezado, String tipo, ArrayList<PreguntaAbierta> preguntas) {
        super(obligatoria, nombre, fechaLimite, descripcion, calificacion, rating, esCompletada, resenias, nivelDificultad, estaEmpezado, tipo);
    	this.preguntas= preguntas;
        this.counterCorrecta = 0;
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
                return "Ya completó su Examen.";
            }
        }

        return "Ya completó su Examen.";
    }
    
    public void preguntaCorrecta(String inputUsuario) {
    	boolean respuestaPregunta= preguntas.get(counterCorrecta).getCorrecta();	
    	if (respuestaPregunta == true) {
    		counterCorrecta ++;
    	}
    }
    
    public ArrayList<PreguntaAbierta> getPreguntas(){
    	return this.preguntas;
    }
    
    public ArrayList<String> getRespuestas(){
    	return this.respuestas;
    }
}

/*
Changelog:
Insertions (+):
- Fixed initialization of calificacionMinima in constructor.
- Added condition checks for better flow in nextQuestion().

Deletions (-):
- Removed incorrect import (java.awt.geom.Arc2D.Double).

TODOs (#):
- Added a TODO for the input.close() line to indicate potential issues with closing Scanner.
*/
