package LPTH.actividades;
import java.awt.geom.Arc2D.Double;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import LPTH.Preguntas.PreguntaAbierta;
import LPTH.Preguntas.PreguntaCerrada;
import LPTH.usuarios.Resenia;

public class Quiz extends Actividad{

    //Se removio crearPregunta Abierta
	
    private ArrayList<PreguntaCerrada> preguntas;
    private int counterPregunta;
    private int counterCorrecta;
    
    public Quiz (boolean obligatoria, String nombre, Date fechaLimite, String descripcion, double calificacion, float rating, boolean esCompletada, ArrayList<Resenia> resenias, double nivelDificultad, boolean estaEmpezado, ArrayList<PreguntaCerrada> preguntas) {
    	super(obligatoria, nombre, fechaLimite, descripcion, calificacion, rating, esCompletada, resenias, nivelDificultad, estaEmpezado);
    	this.preguntas= preguntas;
    	this.counterPregunta = 0;
    	this.counterCorrecta= 0;   
    }
    
    
    @Override
    public double calificarActividad() { 
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

        if(estaEmpezado == true) {
            if (counterPregunta < 4) {
            	String enunciadoPregunta = preguntas.get(counterPregunta).getEnunciado();
                String opcionesPregunta = (preguntas.get(counterPregunta).getOpciones()).toString();	// TODO getOpciones regresa una lista
                preguntaCorrecta(inputUsuario);
                counterPregunta ++;
                return (enunciadoPregunta + " " + opcionesPregunta);                
            }
            else {
            	esCompletada = true;
            	return ("Ya completo su quiz.");
            }
        }
        else {
        	return("Debe iniciar el quiz primero.");
        }

    }
    
    public void preguntaCorrecta(String inputUsuario) {
    	boolean respuestaPregunta= preguntas.get(counterCorrecta).checkCorrecta(inputUsuario);
    	if (respuestaPregunta == true) {
    		counterCorrecta ++;
    	}
    }


}