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
        protected float calificacion;
        protected float rating;
        protected String retroalimentacion;
        protected boolean esCompletada;
        protected ArrayList<Resenia> resenias;
        protected int nivelDificultad;
        protected boolean estaEmpezado;

        public abstract Double calificarActividad(); 
        public abstract String notificarEstudiante(); 


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
	}
