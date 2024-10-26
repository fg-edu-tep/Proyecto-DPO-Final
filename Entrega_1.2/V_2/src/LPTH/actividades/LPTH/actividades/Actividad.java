package LPTH.actividades;
import LPTH.usuarios.Resenia;
import java.util.ArrayList;
import java.util.Date;

public abstract class Actividad {

		//revisar q es send
		//revisar atributos externos invocados en metodos
		//revisar parametros de metodos invocados.
		//revisar logica detras de resena, posible cambio en uml
		// crear super/constructor
		// isdone == esCompletada
	
        protected boolean obligatoria; 
        protected int notaMinima;
        protected String nombre;
        protected Date fechaLimite;
        protected String descripcion;
        protected double calificacion;
        protected float rating; 
        protected boolean esCompletada; 
        protected ArrayList<Resenia> resenias; 
        protected double nivelDificultad;
        protected boolean estaEmpezado;
        protected String objetivo;

        public Actividad (boolean obligatoria, int notaMinima, String nombre, Date fechaLimite, String descripcion, double calificacion, float rating, boolean esCompletada, ArrayList<Resenia> resenias, double nivelDificultad, boolean estaEmpezado, String objetivo) {
			   this.obligatoria = obligatoria;
			   this.notaMinima = notaMinima;
			   this.nombre = nombre;
			   this.fechaLimite = fechaLimite;
			   this.descripcion = descripcion;
			   this.calificacion = calificacion;
			   this.rating = rating;
			   this.esCompletada = esCompletada;
			   this.resenias = resenias != null ? resenias : new ArrayList<>(); // Manejo nulo
			   this.nivelDificultad = nivelDificultad;
			   this.estaEmpezado = estaEmpezado;
			   this.objetivo= objetivo;
			   }
        
        public abstract double calificarActividad(); 
        public abstract String notificarEstudiante(); // eliminar y meter en learning path

        public void setCompletada(boolean Status){
            this.esCompletada =  Status;
        }
        
        public boolean estaCompletada(){
            return esCompletada;
        }

        public ArrayList<Resenia> getResenas(){
            return resenias;
        }

        public double nivelDeDificultad(){
            return this.nivelDificultad;
        }

        public boolean empezarActividad(boolean quizEmpezado) {
            return estaEmpezado= true;
        }
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
		
		public String getObjetivo() {
			return this.objetivo;
		}
	}
