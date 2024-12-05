package LPTH.actividades;
import java.awt.geom.Arc2D.Double;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import LPTH.Preguntas.Pregunta;
import LPTH.Preguntas.PreguntaAbierta;
import LPTH.Preguntas.PreguntaCerrada;
import LPTH.Preguntas.PreguntaToF;
import LPTH.usuarios.Resenia;

public class Quiz extends Actividad{

    //Se removio crearPregunta Abierta
	
    private ArrayList<PreguntaCerrada> preguntas;
    private int counterPregunta;
    private int counterCorrecta;
    
    public Quiz (boolean obligatoria, String nombre, Instant fechaLimite, String descripcion, double calificacion, float rating, boolean esCompletada, ArrayList<Resenia> resenias, double nivelDificultad, boolean estaEmpezado, String tipo, ArrayList<PreguntaCerrada> preguntas) {
    	super(obligatoria, nombre, fechaLimite, descripcion, calificacion, rating, esCompletada, resenias, nivelDificultad, estaEmpezado, tipo);
    	this.preguntas= preguntas;
    	this.counterPregunta = 0;
    	this.counterCorrecta= 0;   
    }
    
    
    @Override
    public double calificarActividad(String input) { 
        if (esCompletada==true) {
	    	if (counterCorrecta != 0) {
		    	float calificacion= (counterCorrecta/ 4)*5 ; 
		        return calificacion;
	        }
        }
		return 0.0;
    }

    public void setPreguntas (ArrayList<PreguntaCerrada> nuevaspreguntas) {
    	this.preguntas= nuevaspreguntas;
    }
    
    public String nextQuestion(String inputUsuario) {
        if (!estaEmpezado) {
            return "Debe iniciar el quiz primero.";
        }

        if (counterPregunta < preguntas.size()) {
            if (!inputUsuario.isEmpty()) {
                preguntaCorrecta(inputUsuario); 
                counterPregunta++; 					//solo se aumenta si el input no esta vacio.
            }

            if (counterPregunta < preguntas.size()) {
                Pregunta preguntaActual = preguntas.get(counterPregunta);
                String enunciadoPregunta = preguntaActual.getEnunciado(); 
                String opcionesPregunta = "";

                if (preguntaActual instanceof PreguntaCerrada) {
                    PreguntaCerrada preguntaCerrada = (PreguntaCerrada) preguntaActual;
                    opcionesPregunta = "Opciones: " + String.join(", ", preguntaCerrada.getOpciones());
                } else if (preguntaActual instanceof PreguntaToF) {
                    opcionesPregunta = "Opciones: True / False";
                }

                return enunciadoPregunta + "\n" + opcionesPregunta + "\nIngrese su respuesta:";
            } else {
                esCompletada = true;
                return "Ya completó su quiz.";
            }
        }

        return "Ya completó su quiz.";
    }


    
    public void preguntaCorrecta(String inputUsuario) {
    	
    	if (preguntas.get(counterPregunta) instanceof PreguntaCerrada) {
    		((PreguntaCerrada) preguntas.get(counterPregunta)).checkCorrecta(inputUsuario);
    		counterCorrecta ++;

    	}
    	else if (preguntas.get(counterPregunta) instanceof PreguntaCerrada) {
    		// @Pablo revisar
    		//((PreguntaToF) preguntas.get(counterPregunta)).checkCorrecta(inputUsuario);
    		counterCorrecta ++;
    		}    	
    	}
}


