package LPTH.gui;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import LPTH.modelo.Sistema;
import LPTH.modelo.LearningPath;
import LPTH.usuarios.Profesor;
import LPTH.usuarios.*;
import LPTH.actividades.Actividad;
import LPTH.gui.*;

public class menu_profesor {
	private static Sistema sistemaCentral;
	private Profesor profesor;
	

	public menu_profesor(Sistema sistemaCentral, Profesor profesor) {
		menu_profesor.sistemaCentral = sistemaCentral; // Dado que solo hay una instancia de sistema debe ser estático
		this.profesor = profesor;
		
	}

   
    public static void opcionesprofesor(Profesor profesor,Sistema sistemaCentral) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("¿Que desea hacer?");
        System.out.println("1. Crear un nuevo Learning Path");
        System.out.println("2. Ver Learning Paths existentes");
        System.out.println("3. Consultar learning Path de otro docente");
        System.out.println("4. Revisar tareas y exámenes enviados por estudiantes");
        String opcion = scanner.next();
        
        if (opcion.equals("1")) {
            opcionescrearLearningPath(profesor);
        } else if (opcion.equals("2")) {
            verLearningPaths(sistemaCentral);
        } else if (opcion.equals("3")) {
        	ConsultarotrosLearningpath(profesor, sistemaCentral);
        	System.out.println("Desea clonar alguno de estos Learning Paths? (S/N)");
        	String respuesta = scanner.next();
        	if (respuesta.equals("S")) {
        	clonarLearningPath(profesor, sistemaCentral);
        	}
			else {
				System.out.println("Hasta luego!");
			}

        } else if (opcion.equals("4")) {
        	//revisarTareasExamenes(profesor);

        } else {
            System.out.println("Opción inválida");
        }
    }
    
	
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

        LearningPath nuevoPath = sistemaCentral.crearLearningPath(
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
        
	public static void verLearningPaths(Profesor profesor,Sistema sistemaCentral) {
		// Método para ver los Learning Paths existentes
		ArrayList<LearningPath> paths = sistemaCentral.getLearningPaths();
		
		System.out.println("Learning Paths existentes:");
		System.out.println(paths);//usar libreria como en EDA para imprimir
		
		
		
		
		
		if (paths.isEmpty()) {
	        System.out.println("No hay Learning Paths disponibles."); 
	        System.exit(0);
		}	
		System.out.println("¿Que desea hacer ahora");
		System.out.println("1. Editar Learning Path");
		System.out.println("2. Volver al menú principal");
		String respuesta = scanner.next();
		if (respuesta.equals("1")) {
            editarLearningPath(profesor);
        } else if (respuesta.equals("2")) {
            opcionesprofesor(profesor);
        } else {
            System.out.println("Opción inválida");
            
        }
			
		
		
		
	}

	
	
	private static void editarLearningPath(Profesor profesor) {
		// Método para editar un Learning Path existente
		Scanner scanner = new Scanner(System.in);
		System.out.println("Ingrese el ID del Learning Path a editar: ");
		String nombre = scanner.next();
		LearningPath path = sistemaCentral.getLearningPath(nombre);
		if (path == null) {
			System.out.println("Learning Path no encontrado.");
			System.exit(0);
		}
		System.out.println("¿Qué desea editar?");
		System.out.println("1. Título");
		System.out.println("2. Descripción");
		System.out.println("3. Nivel de dificultad");
		System.out.println("4. Duración");
		System.out.println("5. Actividades");
		String respuesta = scanner.next();
		if (respuesta.equals("1")) {
			System.out.println("Ingrese el nuevo título: ");
			String nuevoTitulo = scanner.nextLine();
			path.setTitulo(nuevoTitulo);
		} else if (respuesta.equals("2")) {
			System.out.println("Ingrese la nueva descripción: ");
			String nuevaDescripcion = scanner.nextLine();
			path.setDescripcion(nuevaDescripcion);
		} else if (respuesta.equals("3")) {
			System.out.println("Ingrese el nuevo nivel de dificultad: ");
			String nuevoNivel = scanner.nextLine();
			path.setnivelDeDificultad(nuevoNivel);
		} else if (respuesta.equals("4")) {
			System.out.println("Ingrese la nueva duración: ");
			int nuevaDuracion = scanner.nextInt();
			path.setDuracion(nuevaDuracion);
		} else if (respuesta.equals("5")) {
			System.out.println("¿Qué desea hacer?");
			System.out.println("1. Agregar actividad");
			System.out.println("2. Eliminar actividad");
			System.out.println(path.getActividades());
			String respuesta2 = scanner.next();
			if (respuesta2.equals("1")) {
				System.out.println("Ingrese el tipo de actividad a agregar entre Quiz -> Q, Tarea -> T, Examen -> E, Recurso Educativo -> R; Encuesta -> P");
			    String seleccion = scanner.next();
				if (seleccion.equals("Q")) {
					Actividad actividad = path.agregarActividad("Quiz"); // preguntar como va A PABLO como FUNIONA EL agregarActividad
					path.agregarActividad(actividad);
				} else if (seleccion.equals("T")) {
					Actividad actividad = path.agregarActividad("Tarea");
					path.agregarActividad(actividad);
				} else if (seleccion.equals("E")) {
					Actividad actividad = path.agregarActividad("Examen");
					path.agregarActividad(actividad);
				} else if (seleccion.equals("R")) {
					Actividad actividad = path.agregarActividad("Recurso Educativo");
					path.agregarActividad(actividad);
				} else if (seleccion.equals("P")) {
					Actividad actividad = path.agregarActividad("Encuesta");
					path.agregarActividad(actividad);
				} else {
					System.out.println("Opción inválida.");
					System.exit(0);
				}
				
				System.out.println("Ingrese el nombre de la actividad a agregar: ");
				String nombreActividad = scanner.nextLine();
				Actividad actividad = path.agregarActividad(null); // entra como parametro el tipo de actividad quiz, tarea, etc
				path.agregarActividad(actividad);
			} else if (respuesta2.equals("2")) {
				System.out.println("Ingrese el nombre de la actividad a eliminar: ");
				String nombreActividad = scanner.nextLine();
				Actividad actividad = new Actividad(nombreActividad);
				path.eliminarActividad(actividad);
			
			
		} else {
			System.out.println("Opción inválida.");
			System.exit(0);
		}
		System.out.println("Learning Path editado exitosamente.");
		System.out.println("¿Desea volver al menú principal? (S/N)");
		String respuesta2 = scanner.next();
		if (respuesta2.equals("S")) {
			opcionesprofesor(profesor);
		} else {
			System.out.println("Hasta luego!");
		}
		scanner.close();
	}
		
	}


	public static void ConsultarotrosLearningpath(Profesor profesor, Sistema sistemaCentral) {
	    String nombreDocente = profesor.getNombre();
	    ArrayList<Profesor> otrosDocentes = new ArrayList<>();
	    
	    ArrayList<LearningPath> paths = sistemaCentral.getLearningPaths();
	    
	    for (LearningPath path : paths) {
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

	public static void revisarTareasExamenes(Profesor profesor) {
		
		
		
		
		// Método para revisar tareas y exámenes enviados por estudiantes
		// Se puede hacer uso de los métodos de la clase Profesor
	}



}
		
		
		
		
		
		
    		
		
        
    


	
	
	
	
	

