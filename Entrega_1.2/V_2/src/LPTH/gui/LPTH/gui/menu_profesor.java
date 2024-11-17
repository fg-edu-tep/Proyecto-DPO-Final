package LPTH.gui;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import LPTH.modelo.Sistema;
import LPTH.modelo.learningPath;
import LPTH.usuarios.Profesor;
import LPTH.usuarios.Usuario;
import LPTH.gui.*;

public class menu_profesor {
	private static Sistema sistemaCentral;
	private Profesor profesor;

	public menu_profesor(Sistema sistemaCentral, Profesor profesor) {
		menu_profesor.sistemaCentral = sistemaCentral; // Dado que solo hay una instancia de sistema debe ser estático
		this.profesor = profesor;
	}

   
    public static void opcionesprofesor(Profesor profesor) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("¿Que desea hacer?");
        System.out.println("1. Consultar reseñas de actividades");
        System.out.println("2. Crear un nuevo Learning Path");
        System.out.println("3. Ver Learning Paths existentes");
        System.out.println("4. Consultar learning Path de otro docente");
        System.out.println("5. Revisar tareas y exámenes enviados por estudiantes");
        String opcion = scanner.next();
        
        if (opcion.equals("1")) {

        } else if (opcion.equals("2")) {
            opcionescrearLearningPath(profesor);
        } else if (opcion.equals("3")) {
            verLearningPaths(sistemaCentral);
        } else if (opcion.equals("4")) {

        } else if (opcion.equals("5")) {

        } else {
            System.out.println("Opción inválida");
        }
    }
    
	public static void consultarResenasActividades(Profesor profesor) {
	// Método para consultar las reseñas de las actividades de los estudiantes
	
	}
    
    
    
    
    

    public static void opcionescrearLearningPath(Profesor profesor) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Ingrese el titulo del Learning Path: ");
        String titulo = scanner.nextLine();

        System.out.println("Ingrese la descripción del Learning Path: ");
        String descripcion = scanner.nextLine();

        System.out.println("Ingrese el nivel de dificultad del Learning Path (Básico/Intermedio/Avanzado): ");
        String nivelDeDificultad = scanner.nextLine();

        System.out.println("Ingrese la duración del Learning Path en horas: ");
        int duracion = scanner.nextInt();

        learningPath nuevoPath = sistemaCentral.crearLearningPath(
            profesor, // Usa el objeto pasado como argumento NO OLVIDAR
            titulo,
            descripcion,
            nivelDeDificultad,
            duracion
        );

        System.out.println("Learning Path creado exitosamente: " + nuevoPath.getTitulo());
    
        if (nuevoPath != null) {
            System.out.println("Learning Path creado exitosamente!");
            System.out.println("Título: " + nuevoPath.getTitulo());
            System.out.println("ID: " + nuevoPath.getID());
            System.out.println("¿Desea volver al menú principal? (S/N)");
            String respuesta = scanner.next();
            if (respuesta.equals("S")) {
                opcionesprofesor(profesor);
                }
            else {
                System.out.println("Hasta luego!");
            }
        	
        }
        scanner.close();
    }
        
	public static void verLearningPaths(Sistema sistemaCentral) {
		// Método para ver los Learning Paths existentes
		ArrayList<learningPath> paths = sistemaCentral.getLearningPaths();
		
		System.out.println("Learning Paths existentes:");
		System.out.println(paths);//usar libreria como en EDA para imprimir
		if (paths.isEmpty()) {
	        System.out.println("No hay Learning Paths disponibles."); 
		}	
		
		
	}

	
	
	public static void ConsultarotrosLearningpath(Profesor profesor, Sistema sistemaCentral) {
	    String nombreDocente = profesor.getNombre();
	    ArrayList<Profesor> otrosDocentes = new ArrayList<>();
	    
	    ArrayList<learningPath> paths = sistemaCentral.getLearningPaths();
	    
	    for (learningPath path : paths) {
	        /// profesorCreador = path.getProfesorCreador();
	        if (profesorCreador.getNombre().equals(nombreDocente) && 
	           // otrosDocentes.contains(profesorCreador)) {
	            otrosDocentes.add(profesorCreador);
	        }
	    }

	    if (otrosDocentes.isEmpty()) {
	        System.out.println("No hay otros docentes con Learning Paths.");
	        return;
	    }
	    
	    
	        
	        
	     
		
	    }
	}

	public static void revisarTareasExamenes(Profesor profesor) {
		
		
		
		
		// Método para revisar tareas y exámenes enviados por estudiantes
		// Se puede hacer uso de los métodos de la clase Profesor
	}



}
		
		
		
		
		
		
    		
		
        
    


	
	
	
	
	

