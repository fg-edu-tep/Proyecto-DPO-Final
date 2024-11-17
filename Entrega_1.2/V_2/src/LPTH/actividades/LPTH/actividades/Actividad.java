package LPTH.actividades;
import LPTH.usuarios.Resenia;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

public abstract class Actividad {

		//
	
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
        protected Date fechaEmpezada;
        protected Date fechaTerminada;
        protected int duracion;

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

        public void marcarObligatoria() {
        	this.obligatoria = true;
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

        public double nivelDeDificultad(){
            return this.nivelDificultad;
        }
        
        public void empezarActividad() {
            Instant empezado = Instant.now();
            Date fechaEmpezado = Date.from(empezado);
        	this.fechaEmpezada = fechaEmpezado;
            this.estaEmpezado = true;
        }

        public void terminarActividad() {
            Instant terminado = Instant.now(); 
            Date fechaTerminado = Date.from(terminado);
        	this.fechaTerminada = fechaTerminado;
            this.esCompletada = true;
        }
        
        public int getDuracionMinutos() {
            Duration duration = Duration.between(this.fechaEmpezada, this.fechaTerminada);
            int minutosDedicados = (int) duration.toMinutes(); // Convertir a horas la diferencia
            this.duracion = minutosDedicados;
            return minutosDedicados;
        }
        
        
		public String getNombre() {
			return this.nombre;
		}
		
		public String getDescripcion() {
			return this.descripcion;
		}
		
		public void setNombre(String nombreNuevo) {
			this.nombre = nombreNuevo;
		}
		
		public void setDescripcion(String descripcionNueva) {
			this.descripcion = descripcionNueva;
		}
		
		public Date getDueDate() {
			return this.fechaLimite;
		}
		
		public void agregarResenia(Resenia nuevaResenia) {
			resenias.add(nuevaResenia);
		}
		
		public String getObjetivo() {
			return this.objetivo;
		}
	}
