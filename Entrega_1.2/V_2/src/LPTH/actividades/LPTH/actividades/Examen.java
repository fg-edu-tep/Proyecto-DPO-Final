package LPTH.actividades;

import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import LPTH.Preguntas.PreguntaAbierta;
import LPTH.usuarios.Resenia;

public class Examen extends Actividad {

    private ArrayList<PreguntaAbierta> preguntas;
    private double calificacionMinima;
    private int counterCorrecta;
    private int counterPregunta;
    
    public Examen (boolean obligatoria, int notaMinima, String nombre, Date fechaLimite, String descripcion, double calificacion, float rating, boolean esCompletada, ArrayList<Resenia> resenias, double nivelDificultad, boolean estaEmpezado, String objetivo) {
        super(obligatoria, notaMinima, nombre, fechaLimite, descripcion, calificacion, rating, esCompletada, resenias, nivelDificultad, estaEmpezado, objetivo);
        this.preguntas = crearPreguntasAbiertas();
        this.calificacionMinima = notaMinima;  // FIXED: Se inicializa correctamente calificacionMinima
        this.counterCorrecta = 0;
    }

    @Override
    public double calificarActividad() {  
        if (esCompletada == true) {
            return 5.0;
        }
        return 0.0;
    }    

    @Override
    public String notificarEstudiante() {   
        return ("Debe realizar su Examen!");
    }

    public String nextQuestion() {
        if (estaEmpezado == true) {
            if (counterPregunta < preguntas.size()) {  // FIXED: Se corrige la condición para utilizar preguntas.size()
                String enunciadoPregunta = preguntas.get(counterPregunta).getEnunciado(); 
                counterPregunta++;
                return enunciadoPregunta;
            } else if (counterPregunta == preguntas.size()) {
                esCompletada = true;
                return ("Ya completo su Examen.");
            }
        }
        return ("Debe iniciar la Examen primero.");
    }
    
    public ArrayList<PreguntaAbierta> crearPreguntasAbiertas() {
        ArrayList<PreguntaAbierta> preguntas = new ArrayList<>();
        Scanner input = new Scanner(System.in);

        System.out.print("Ingrese la cantidad de preguntas que desea: ");
        int cantidadPreguntas = Integer.parseInt(input.nextLine());

        for (int i = 1; i <= cantidadPreguntas; i++) {
            System.out.print("Ingrese el enunciado de la pregunta " + i + ": ");
            String enunciado = input.nextLine();

            preguntas.add(new PreguntaAbierta(enunciado));
        }
        input.close();  // TODO: // Cerrar el scanner aquí puede causar problemas si se usa más adelante, revisar el flujo general del programa
        return preguntas; 
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
