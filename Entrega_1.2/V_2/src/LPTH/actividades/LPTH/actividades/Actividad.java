package LPTH.actividades;
import LPTH.usuarios.Resenia;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.Date;

public abstract class Actividad {

		//
	
        protected boolean obligatoria; 
        protected String nombre;
        protected Date fechaLimite;
        protected String descripcion;
        protected double calificacion;
        protected float rating; 
        protected boolean esCompletada; 
        protected ArrayList<Resenia> resenias; 
        protected double nivelDificultad;
        protected boolean estaEmpezado;


		//Se elimino objetivo y notaminima, notaminima no se espcifica, objetivos puede estar en descripcion.
        //notificar estudiante se realiza en L.P
        protected Temporal fechaEmpezada;
        protected Temporal fechaTerminada;
        protected int duracion;
        
        public Actividad (boolean obligatoria, String nombre, Date fechaLimite, String descripcion, double calificacion, float rating, boolean esCompletada, ArrayList<Resenia> resenias, double nivelDificultad, boolean estaEmpezado) {
			   this.obligatoria = obligatoria;
			   this.nombre = nombre;
			   this.fechaLimite = fechaLimite;
			   this.descripcion = descripcion;
			   this.calificacion = calificacion;
			   this.rating = rating;
			   this.esCompletada = esCompletada;
			   this.resenias = resenias != null ? resenias : new ArrayList<>(); // Manejo nulo
			   this.nivelDificultad = nivelDificultad;
			   this.estaEmpezado = estaEmpezado;
			   }
        
        public abstract double calificarActividad(); 
		
        public void marcarObligatoria() {
        	this.obligatoria = true;
        }

        public void setFechaLimite(Date nuevaFecha) {
        	this.fechaLimite= nuevaFecha;
        }
        
        //Permite al profe editar libremente la nota aunq el mkon se la tire/no la haga.
        public void setCalificacion(double nuevaCali) {
        	this.calificacion= nuevaCali;
        }
        
        public void setRating(float nuevoR) {
        	this.rating= nuevoR;
        }
        
        public void setDificultad(double nuevaDifi) {
        	this.nivelDificultad= nuevaDifi;
        }
        
		public void setNombre(String nombreNuevo) {
			this.nombre = nombreNuevo;
		}
		
		public void setDescripcion(String descripcionNueva) {
			this.descripcion = descripcionNueva;
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
        	this.fechaEmpezada = empezado;
            this.estaEmpezado = true;
        }

        public void terminarActividad() {
            Instant terminado = Instant.now(); 
        	this.fechaTerminada = terminado;
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
		
		public Date getDueDate() {
			return this.fechaLimite;
		}
		
        public double getNivelDeDificultad(){
            return this.nivelDificultad;
        }
        
        public float getRating() {
        	return this.rating;
        }
        
        public double getCalificacion() {
        	return this.calificacion;
        }
        
		
		public void agregarResenia(Resenia nuevaResenia) {
			resenias.add(nuevaResenia);
		}
		
	}
