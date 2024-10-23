package LPTH.actividades;
import LPTH.actividades.Resenia;
import java.util.ArrayList;
import java.util.Date;

public abstract class Actividad {

        protected boolean obligatoria;
        public final int notaMinima= 3;
        protected String nombre;
        protected Date fechaLimite;
        protected String descripcion;
        protected float calificacion;
        protected float rating;
        protected String retroalimentacion;
        protected boolean esCompletada;
        protected ArrayList<Resenia> reseñas;
        protected int nivelDificultad;

        public abstract void calificarActividad(); // revisar logica?? debiria calificarse automaticamente.    ???
        public abstract void notificarEstudiante(); // asume atributo como publico, mismo q recordar. Se emplea asi para identificar el tipo

        // siguiendo la logica del taller 3, los metodos heredados no se añaden

        public float calcularCalificacion() {
            return calificacion;
        }

        public String darRetroalimentacion() {
            return retroalimentacion;
        }

        public boolean estaCompletada(){
            return esCompletada;
        }

        public ArrayList<Resenia> getResenas(){
            return resenas;
        }

        public int nivelDeDificultad(){
            return this.nivelDificultad;

}
