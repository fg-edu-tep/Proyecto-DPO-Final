package LPTH.actividades;

import java.util.ArrayList;
import java.util.Date;

public class RecursoEducativo extends Actividad {
    private String contenido;
    private String tipo;

    public RecursoEducativo(boolean obligatoria, float notaMinima, String nombre, String descripcion, Date fechaLimite, boolean esCompletada,  list<Reseña> reseñas,int nivelDificultad , String contenido, String tipo) {
        this.obligatoria = obligatoria;
        this.notaMinima = notaMinima;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaLimite= fechaLimite= Date;
        this.esCompletada = false;
        this.resenas= resenas;
        this.nivelDificultad= nivelDificultad;
        this.contenido = contenido;
        this.tipo = tipo;
    }

    @Override
    public void calificarActividad() {
        // TODO Auto-generated method stub

    }

    @Override
    public String notificarEstudiante() {
        return("Debe realizar su Examen!");

    }

}