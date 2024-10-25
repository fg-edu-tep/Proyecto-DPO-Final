package LPTH.actividades;

import java.awt.geom.Arc2D.Double;
import java.util.ArrayList;
import java.util.Date;
import LPTH.Preguntas.PreguntaAbierta;

public class Examen extends Actividad {

    private boolean isDone;
    private ArrayList<PreguntaAbierta> preguntas;
    private double calificacionMinima;
    
    
    }

    @Override
   //asume que el profesor retorna la cantidad correcta, misma logica q quiz
        

     public Double calificarActividad(int counterCorrecta, ArrayList<PreguntaAbierta> preguntas) {  // Por algun triple hpta motivo x.x es doble
            if (esCompletada==true) {
	        	if (counterCorrecta != 0) {
	    	    	float calificacion= (counterCorrecta/ (preguntas.size()))*5 ; 
	    	        return calificacion;
	            }
            else {
            	return 0.0;
            }
            }
      }

    @Override
    public String notificarEstudiante() {  // 
        return("Debe realizar su Examen!");
    }

    public String nextQuestion(ArrayList<PreguntaAbierta> preguntas, boolean esCompletada, boolean estaEmpezado, int counterPregunta) {

        if(estaEmpezado == true) {
        	if (counterPregunta < 4) {
            	String enunciadoPregunta = preguntas.get(counterPregunta).getEnunciado(); // reviasr getEneunciado en pregunta abierta
                return (enunciadoPregunta);
                counterPregunta ++;
            }
        }
        else if (counterPregunta== (preguntas.size())){
        	return("Ya completo su encuesta.");
        }
        else {
        	return("Debe iniciar la encuesta primero.");
        }
    }
    
    public void isitDone() {  //revisar q es, asume q es enviada
        if(esCompletada== true) {
            isDone= true;
        }
        }
    
}