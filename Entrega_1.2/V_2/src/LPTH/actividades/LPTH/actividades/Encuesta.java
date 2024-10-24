package LPTH.actividades;

import java.util.ArrayList;
import java.util.Date;

import LPTH.Preguntas.Pregunta;
import LPTH.Preguntas.PreguntaAbierta;
import LPTH.Preguntas.PreguntaCerrada;


public class Encuesta extends Actividad {
	
	private boolean isDone;
    private ArrayList<PreguntaAbierta> preguntas;
    private int counterPregunta;

    @Override
    public Double calificarActividad(boolean esCompletada) {		//Asume q realizar encuesta da 5
        if (esCompletada==true) {
        	return 5.0;
        }

    }

    @Override
    public String notificarEstudiante() {
        return "Debe realizar su Encuesta!";

    }

    public String nextQuestion(ArrayList<PreguntaAbierta> preguntas, boolean esCompletada, boolean estaEmpezado, int counterPregunta) {

        if(estaEmpezado == true) {
        	if (counterPregunta < 4) {
            	String enunciadoPregunta = preguntas.get(counterPregunta).getEnunciado(); // reviasr getEneunciado en pregunta abierta, y sus parametros
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
    
    public void isitDone() {  //revisar q es
        if(esCompletada== true) {
            isDone= true;
        }
        }
        
    public ArrayList<String> getResults(ArrayList<PreguntaAbierta> preguntas, int counterPregunta){  //reviasr q es esto, asumo q es una lista de resultados del estudiante????
    	ArrayList<String> respuestas = new ArrayList<>();
    	for (int i: preguntas) {
    		 String enunciado= preguntas[counterPregunta].getResults(); //q retonar la res del usuario?
    		 repuestas.add(enunciado);
    	}
    	return respuestas;
    }

}