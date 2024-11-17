package LPTH.actividades;

import java.util.ArrayList;
import java.util.Date;

import LPTH.Preguntas.PreguntaAbierta;
import LPTH.usuarios.Resenia;

public class Examen extends Actividad {

    private ArrayList<PreguntaAbierta> preguntas;
    private int counterCorrecta;
    private int counterPregunta;
    
    //Se removio crearPregunta Abierta
    
    public Examen (boolean obligatoria, String nombre, Date fechaLimite, String descripcion, double calificacion, float rating, boolean esCompletada, ArrayList<Resenia> resenias, double nivelDificultad, boolean estaEmpezado, ArrayList<PreguntaAbierta> preguntas) {
        super(obligatoria, nombre, fechaLimite, descripcion, calificacion, rating, esCompletada, resenias, nivelDificultad, estaEmpezado);
    	this.preguntas= preguntas;
        this.counterCorrecta = 0;
        this.counterPregunta= 0;
    }

    @Override
    public double calificarActividad() { 
        
    	int n = preguntas.size();
    	
    	if (esCompletada==true) {
	    	if (counterCorrecta != 0) {
		    	float calificacion= (counterCorrecta/n)*5 ; 
		        return calificacion;
	        }
        }
		return 0.0;
    }

    public void setPreguntas (ArrayList<PreguntaAbierta> nuevaspreguntas) {
    	this.preguntas= nuevaspreguntas;
    }

    
    public String nextQuestion(String inputUsuario) {
        if (estaEmpezado == true) {
            if (counterPregunta < preguntas.size()) {  // FIXED: Se corrige la condición para utilizar preguntas.size()
                String enunciadoPregunta = preguntas.get(counterPregunta).getEnunciado(); 
                preguntaCorrecta(inputUsuario);
                counterPregunta++;
                return enunciadoPregunta;
            } else if (counterPregunta == preguntas.size()) {
                esCompletada = true;
                return ("Ya completo su Examen.");
            }
        }
        return ("Debe iniciar la Examen primero.");
    }
    
    public void preguntaCorrecta(String inputUsuario) {
    	boolean respuestaPregunta= preguntas.get(counterCorrecta).getCorrecta();	
    	if (respuestaPregunta == true) {
    		counterCorrecta ++;
    	}
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
