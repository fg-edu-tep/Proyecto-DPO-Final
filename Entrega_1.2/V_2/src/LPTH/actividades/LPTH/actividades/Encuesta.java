package LPTH.actividades;

import java.util.ArrayList;
import java.util.Date;

import LPTH.Preguntas.Pregunta;
import LPTH.Preguntas.PreguntaAbierta;


public class Encuesta extends Actividad {

    private ArrayList<PreguntaAbierta> preguntas;

    public Encuesta(boolean obligatoria, int notaMinima, String nombre, String descripcion, Date fechaLimite, boolean esCompletada, ArrayList<Resena> resenas, int nivelDificultad) {
        this.obligatoria = obligatoria;
        this.notaMinima = notaMinima;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaLimite= fechaLimite;
        this. 
        this.esCompletada= False;
        this.reseñas= reseñas;
        this.preguntasCerradas= preguntasCerradas; //revisar
        this.nivelDificultad= nivelDificultad;
    }

    @Override
    public void calificarActividad() {
        // TODO Auto-generated method stub

    }

    @Override
    public void notificarEstudiante() {
        // TODO Auto-generated method stub

    }



}