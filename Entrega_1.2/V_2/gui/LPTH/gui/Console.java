package LPTH.gui;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random; // Para los Ids de Usuario
import java.util.Scanner;

import LPTH.Preguntas.Pregunta;
import LPTH.Preguntas.PreguntaAbierta;
import LPTH.Preguntas.PreguntaCerrada;
import LPTH.actividades.*;
import LPTH.modelo.*;
import LPTH.usuarios.Estudiante;
import LPTH.usuarios.Profesor;
import LPTH.usuarios.Usuario;
import LPTH.modelo.Sistema;


public class Console {
	private Sistema sistemaCentral;
	
	public Console() {
		sistemaCentral = new Sistema(); // Este debe ser la UNICA instancia de sistema a ser usada
	}
	
	
	/*Métodos de autenticación y creacion de usuario*/
	
	public void elegirCreacionUsuario() {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Elija el tipo de usuario a crear | P -> Profesor | E -> Estudiante");
		String selection = scanner.next();
		scanner.close();
		if(selection == "E") {
			Estudiante nuevoEstudiante = crearUsuarioEstudiante();
			System.out.print("Se ha creado el nuevo usuario:");
			System.out.print(nuevoEstudiante.getNombre());
			System.out.print("Con Identificador:");
			System.out.print(nuevoEstudiante.getIdUsuario());
			
		}
		else if (selection == "P") {
			Profesor nuevoProfesor = crearUsuarioProfesor();
			System.out.print("Se ha creado el nuevo usuario:");
			System.out.print(nuevoProfesor.getNombre());
			System.out.print("profesor de la materia:");
			System.out.print(nuevoProfesor.getMateria());
			System.out.print("Con Identificador:");
			System.out.print(nuevoProfesor.getIdUsuario());
		}
	}


	private Estudiante crearUsuarioEstudiante() {
		Scanner scanner = new Scanner(System.in);
		String tipo = "estudiante";
		System.out.print("Nombre del estudiante: ");
		String nombre = scanner.next();
		System.out.print("Email del estudiante: ");
		String email = scanner.next();
		System.out.print("Contraseña del estudiante: ");
		String pass = scanner.next();
		String materia = null;
		String fechaRegistro = (sistemaCentral.getCurrentDate()).toString();
		scanner.close();
		Estudiante nuevoEstudiante = (Estudiante)sistemaCentral.crearUsuario(tipo, nombre, email, pass, fechaRegistro, materia);
		return nuevoEstudiante;
	}


	private Profesor crearUsuarioProfesor() {
		Scanner scanner = new Scanner(System.in);
		String tipo = "profesor";
		System.out.print("Nombre del profesor: ");
		String nombre = scanner.next();
		System.out.print("Email del profesor: ");
		String email = scanner.next();
		System.out.print("Contraseña del profesor: ");
		String pass = scanner.next();
		System.out.print("Materia del profesor: ");
		String materia = scanner.next();
		String fechaRegistro = (sistemaCentral.getCurrentDate()).toString();
		scanner.close();
		Profesor nuevoEstudiante = (Profesor)sistemaCentral.crearUsuario(tipo, nombre, email, pass, fechaRegistro, materia);
		return nuevoEstudiante;		
	}
	
	private Usuario resetPassword(String Email){
		Usuario olvidadiso = sistemaCentral.grabUsuarioByEmail(Email);
		Scanner scanner = new Scanner(System.in);
		System.out.print("Ingrese su nuevo password: ");
		String newPassword = scanner.next();
		olvidadiso.setContrasenia(newPassword);
		scanner.close();
		return olvidadiso;
	}
	
	//backend, puede almacenarse en actividad?
	public Actividad seleccionarActividad(String nombre, ArrayList<Actividad> actividades) {
        //brutus cycle
        for (Actividad actividad: actividades) {
            if (actividad.getNombre().equals(nombre)){
            	return actividad;
            
            }
        } return null;
    }
	//act sin cmpletar, meter todo en responderActividad
	public void responderQuiz(Actividad actividadSeleccionada) {

	    if (actividadSeleccionada instanceof Quiz) {
	    	//metodo empezar
	        Quiz quiz = (Quiz) actividadSeleccionada; 
	        Scanner scanner = new Scanner(System.in);
	        
	        while (!quiz.esCompletada()) {
	            String enunciadoConOpciones = quiz.nextQuestion(""); 

	            System.out.println(enunciadoConOpciones);  //problema counter

	            System.out.print("Ingrese su respuesta: ");
	            String respuestaUsuario = scanner.nextLine();

	            quiz.nextQuestion(respuestaUsuario);
	        scanner.close();
	        }

	        double calificacion = quiz.calificarActividad();
	        System.out.println("Tu calificación final es: " + calificacion);
	        //terminarActividad estudiante.this.
	       
	    } else {
	        System.out.println("La actividad seleccionada no es un Quiz o no existe, porfavor seleccione otra opcion.");
	    } 
	}

	public void responderEncuesta(Actividad actividadSeleccionada) {

	    if (actividadSeleccionada instanceof Encuesta) {
	        Encuesta encuesta = (Encuesta) actividadSeleccionada; 
	        Scanner scanner = new Scanner(System.in);
	        
	        while (!encuesta.esCompletada()) {
	        	//falta asignar lo de estaempezada, usar estudiante
	            String enunciadoConOpciones = encuesta.nextQuestion(""); 

	            System.out.println(enunciadoConOpciones);

	            System.out.print("Ingrese su respuesta: ");
	            String respuestaUsuario = scanner.nextLine();

	            encuesta.nextQuestion(respuestaUsuario);
	        scanner.close();
	        }

	        double calificacion = encuesta.calificarActividad();
	        System.out.println("Tu calificación final es: " + calificacion);

	    } else {
	        System.out.println("La actividad seleccionada no es una Encuesta o no existe, porfavor seleccione otra opcion.");
	    } 
	} 
	
	public void responderExamen(Actividad actividadSeleccionada) {

	    if (actividadSeleccionada instanceof Examen) {
	    	Examen examen = (Examen) actividadSeleccionada; 
	        Scanner scanner = new Scanner(System.in);
	        
	        while (!examen.esCompletada()) {
	            String enunciadoConOpciones = examen.nextQuestion(""); 

	            System.out.println(enunciadoConOpciones);

	            System.out.print("Ingrese su respuesta: ");
	            String respuestaUsuario = scanner.nextLine();

	            examen.nextQuestion(respuestaUsuario);
	        scanner.close();
	        }

	        double calificacion = examen.calificarActividad();
	        System.out.println("Tu calificación final es: " + calificacion);

	    } else {
	        System.out.println("La actividad seleccionada no es un Examen o no existe, porfavor seleccione otra opcion.");
	    } 
	} 
	
	public void responderTarea(String nombre, ArrayList<Actividad> actividades) {

	    if (actividadSeleccionada instanceof Tarea) {
	    	Tarea tarea = (Tarea) actividadSeleccionada; 
	        Scanner scanner = new Scanner(System.in);
	        
	        System.out.print("Ingrese su respuesta: ");
	        String respuesta= scanner.nextLine();
	        tarea.completarTarea();
	        tarea.calificarActividad();
	        
	        double calificacion = tarea.calificarActividad();
	        System.out.println("Tu calificación final es: " + calificacion);
	        scanner.close();
	   }
	    else {
	        System.out.println("La actividad seleccionada no es una Tarea o no existe, porfavor seleccione otra opcion.");
	    }
	}
	
	public void responderRecursoEducativo(String nombre, ArrayList<Actividad> actividades) {
	    Actividad actividadSeleccionada = seleccionarActividad(nombre, actividades);

	    if (actividadSeleccionada instanceof RecursoEducativo) {
	    	RecursoEducativo recursoeducativo = (RecursoEducativo) actividadSeleccionada; 
	        Scanner scanner = new Scanner(System.in);
	        
	        System.out.print("Ingrese su respuesta: ");
	        String respuesta= scanner.nextLine();
	        recursoeducativo.completarRD();
	        recursoeducativo.calificarActividad();
	        
	        double calificacion = recursoeducativo.calificarActividad();
	        System.out.println("Tu calificación final es: " + calificacion);
	        scanner.close();
	   } 
	    else {
	        System.out.println("La actividad seleccionada no es un RecursoEducativo o no existe, porfavor seleccione otra opcion.");
	    }
	}
	
	public void evaluarActividadPA(String nombre, ArrayList<Actividad> actividades) {
		Actividad actividadSeleccionada = consolaEstudiante.seleccionarActividad(nombre, actividades);
		
        Scanner scanner = new Scanner(System.in);
		
		if (actividadSeleccionada instanceof Examen) {
	    	Examen examen = (Examen) actividadSeleccionada; 
	    	System.out.print("Revise las respuestas y determine si estan bien o mal" );
	    	System.out.print(examen.getPreguntas());
	    	System.out.print(examen.getRespuestas());
	    	String inputprofe= scanner.nextLine();
	    	examen.calificarActividad(inputprofe);
		}
	    
		else if (actividadSeleccionada instanceof Encuesta) {
	        Encuesta encuesta = (Encuesta) actividadSeleccionada;
	        Encuesta encuesta = (Encuesta) actividadSeleccionada; 
	    	System.out.print("Revise las respuestas y determine si estan bien o mal" );
	    	System.out.print(encuesta.getPreguntas());
	    	System.out.print(encuesta.getRespuestas());
	    	String inputprofe= scanner.nextLine();
	    	encuesta.calificarActividad(inputprofe);
	        
		} scanner.close();
	} 
	
	/*
	Crear l.p
	un lp necesita minimo 1 actividad,
	
	crearLp{
	}
	
	crearActividad(lp){} para asignarle las actividaades a ese lp.
	//ciclo brutus, obtiene
	necesitar tener forma de pillar lp (id)
	listaActividades.add(actividad)
	*/
	
}