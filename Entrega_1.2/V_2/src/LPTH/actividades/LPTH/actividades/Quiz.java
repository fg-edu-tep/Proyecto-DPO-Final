package LPTH.actividades;
import java.awt.geom.Arc2D.Double;
import java.util.ArrayList;
import java.util.Date;

import LPTH.Preguntas.PreguntaCerrada;
import LPTH.usuarios.Resenia;

public class Quiz extends Actividad{

    private ArrayList<PreguntaCerrada> preguntas;
    private int counterPregunta;
    private int counterCorrecta;
    private double calificacionMinima; 
    
    public Quiz (boolean obligatoria, int notaMinima, String nombre, Date fechaLimite, String descripcion, double calificacion, float rating, boolean esCompletada, ArrayList<Resenia> resenias, double nivelDificultad, boolean estaEmpezado, String objetivo) {
    	super(obligatoria, notaMinima, nombre, fechaLimite, descripcion, calificacion, rating, esCompletada, resenias, nivelDificultad, estaEmpezado, objetivo);
    	this.counterPregunta = 0;
    	this.counterCorrecta= 0;
    	this.calificacionMinima= calificacionMinima;
    	this.preguntas= new ArrayList<PreguntaCerrada>();
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

    @Override
    public String notificarEstudiante() {
        return "Debe realizar su quiz"+ " "+ this.nombre;
    }

    public String nextQuestion(String inputUsuario) {

        if(estaEmpezado == true) {
            if (counterPregunta < 4) {
            	String enunciadoPregunta = preguntas.get(counterPregunta).getEnunciado();
                String opcionesPregunta = preguntas.get(counterPregunta).getOpciones();	
                preguntaCorrecta(inputUsuario);
                counterPregunta ++;
                return (enunciadoPregunta + " " + opcionesPregunta);                
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
    
    public void preguntaCorrecta(String inputUsuario) {
    	boolean respuestaPregunta= preguntas.get(counterCorrecta).checkCorrecta(inputUsuario);
    	if (respuestaPregunta == true) {
    		counterCorrecta ++;
    	}
    }


}