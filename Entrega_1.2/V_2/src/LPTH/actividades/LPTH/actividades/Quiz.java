package LPTH.actividades;
import java.awt.geom.Arc2D.Double;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

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
    	this.preguntas= crearPreguntasCerradas();
    
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

    public ArrayList<PreguntaCerrada> crearPreguntasCerradas() {
        ArrayList<PreguntaCerrada> preguntas = new ArrayList<>();
        
        Scanner input = new Scanner(System.in);

        for (int i = 1; i <= 4; i++) {
            System.out.print("Ingrese el enunciado de la pregunta " + i + ": ");
            String enunciado = input.nextLine();

            ArrayList<String> opciones = new ArrayList<>();

            for (int j = 1; j <= 4; j++) {
                System.out.print("Ingrese la opcion " + j + ": ");
                String opcion = input.nextLine();
                opciones.add(opcion);
            }

            System.out.println("Opciones : ");
            for (int j = 0; j < opciones.size(); j++) {
                System.out.println((j + 1) + ". " + opciones.get(j));
            }

            System.out.print("Seleccione el número de la opcion correcta (1-4): ");
            int opcionCorrectaI = Integer.parseInt(input.nextLine()) - 1;

            String opcionCorrecta = opciones.get(opcionCorrectaI);

            preguntas.add(new PreguntaCerrada(enunciado, opciones, opcionCorrecta));
        }
        input.close();

        return preguntas; 
    }

}