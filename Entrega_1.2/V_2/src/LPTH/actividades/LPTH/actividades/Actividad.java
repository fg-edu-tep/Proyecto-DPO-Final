package LPTH.actividades;
import LPTH.usuarios.Resenia;
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
        protected ArrayList<Resenia> resenias;
        protected int nivelDificultad;

        public abstract void calificarActividad(); // revisar logica?? debiria calificarse automaticamente.    ???
        public abstract void notificarEstudiante(); // asume atributo como publico, mismo q recordar. Se emplea asi para identificar el tipo

        public abstract void empezarActividad(); // !! TODO Implementar o revisar

        public float calcularCalificacion() {
            return calificacion;
        }

        public String darRetroalimentacion() {
            return retroalimentacion;
        }

        public void setCompletada(boolean Status){
            this.esCompletada =  Status;
        }
        
        public boolean estaCompletada(){
            return esCompletada;
        }

        public ArrayList<Resenia> getResenas(){
            return resenias;
        }

        public int nivelDeDificultad(){
            return this.nivelDificultad;

}
		public String getNombre() {
			return this.nombre;
		}
		
		public String getDescripcion() {
			return this.descripcion;
		}
		public void agregarResenia(Resenia nuevaResenia) {
			resenias.add(nuevaResenia);
		}
	}
