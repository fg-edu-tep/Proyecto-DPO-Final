package LPTH.actividades;
import java.awt.geom.Arc2D.Double;
import java.util.ArrayList;
import java.util.Date;

import LPTH.Preguntas.PreguntaCerrada;

public class Quiz extends Actividad{

    private boolean isDone;
    private ArrayList<PreguntaCerrada> preguntas;
    private int counterPregunta;
    private int counterCorrecta;
    private double calificacionMinima; //encontrar uso

    @Override
    public Double calificarActividad(int counterCorrecta) {  // Por algun triple hpta motivo x.x es doble
        if (esCompletada==true) {
    	if (counterCorrecta != 0) {
	    	float calificacion= (counterCorrecta/ 4)*5 ; 
	        return calificacion;
        }
        else {
        	return 0.0;
        }
        }
    }

    @Override
    public String notificarEstudiante() {
        return "Debe realizar su quiz!";
    }

    public String nextQuestion(ArrayList<PreguntaCerrada> preguntas, boolean esCompletada, boolean quizEmpezado, int counterPregunta) {

        if(quizEmpezado == true) {
            if (counterPregunta < 4) {
            	String enunciadoPregunta = preguntas.get(counterPregunta).getEnunciado();
                String opcionesPregunta = preguntas.get(counterPregunta).getOpciones();
                return (enunciadoPregunta + " " + opcionesPregunta);
                counterPregunta ++;
            }
            else {
            	return ("Ya completo su quiz.");
                esCompletada = true;
            }
        }
        else {
        	return("Debe iniciar el quiz primero.");
        }

    }
    
    public void preguntaCorrecta(int counterPregunta, int counterCorrecta, String input, ArrayList<PreguntaCerrada> preguntas) {
    	boolean respuestaPregunta= preguntas[counterPregunta].checkCorrecta(input);
    	if (respuestaPregunta == true) {
    		counterCorrecta ++;
    	}
    }
    
    public void isitDone(boolean isDone) { 	//Es lo mismo q esCompletada?
        if(counterPregunta == 3) {
            isDone= true;
        }
    }

}