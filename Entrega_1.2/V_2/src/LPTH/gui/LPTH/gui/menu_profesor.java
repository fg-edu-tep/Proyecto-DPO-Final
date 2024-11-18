package LPTH.gui;

import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import LPTH.modelo.Sistema;
import LPTH.modelo.LearningPath;
import LPTH.usuarios.Profesor;
import LPTH.usuarios.Resenia;
import LPTH.usuarios.Usuario;
import LPTH.Preguntas.PreguntaAbierta;
import LPTH.Preguntas.PreguntaCerrada;
import LPTH.actividades.Actividad;
import LPTH.actividades.Examen;
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
        System.out.println("6. Crear actividad");
        System.out.println("7. Modificar Actividad");
        
        String opcion = scanner.next();
        
        if (opcion.equals("1")) {

        } else if (opcion.equals("2")) {
            opcionescrearLearningPath(profesor);
        } else if (opcion.equals("3")) {
            verLearningPaths(sistemaCentral);
        } else if (opcion.equals("4")) {

        } else if (opcion.equals("5")) {
        	ArrayList<Actividad> actividades = LearningPath.getActividades();  //mostrar actividades profesor funcion
        	revisarExamenes(actividades);
        } else if (opcion.equals("6")){
        	crearActividad();
    	} else if (opcion.equals("7")) {
    		ArrayList<Actividad> actividades = LearningPath.getActividades();  //mostrar actividades profesor funcion
    		Scanner scanner = new Scanner(System.in);
    		System.out.print("Ingrese el nombre de la actividad deseada: ");
    		String nombre= scanner.nextLine();
    		Actividad actividad = seleccionarActividadPorNombre(nombre, actividades);
    		modificarActividad(actividad);
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

		
	public void revisarExamenes(ArrayList<Actividad> actividades) {
		//Corrige todos los examenes.
		//obtiene lista con otro metodo
		ArrayList<Examen> actividadesfiltrada = new ArrayList<>();
		
		for (Actividad actividad: actividades) {
			if (actividad.getTipo().equals("examen")) {
				actividadesfiltrada.add((Examen) actividad);
			}
		    for (Examen examen : actividadesfiltrada) {
		        System.out.println("Revisando examen: " + examen.getNombre());

		        ArrayList<PreguntaAbierta> preguntas = examen.getPreguntas(); 

		        ArrayList<String> respuestas = examen.getRespuestas(); 
		        for (int i = 0; i < preguntas.size(); i++) {
		            PreguntaAbierta pregunta = preguntas.get(i);
		            String respuesta = respuestas.get(i);

		            System.out.println("Pregunta: " + pregunta.getEnunciado());
		            System.out.println("Respuesta del estudiante: " + respuesta);

			}
			Scanner scanner = new Scanner (System.in);
			System.out.println("Estan correctas (si/no)?");
			String correccion = scanner.nextLine();
			examen.calificarActividad(correccion);
			System.out.println("El estudiante saco: ");
			actividad.getCalificacion();
		    }
		}
		
	}
	
	
	public Actividad crearActividad() {
		//Todos los strings deben ser en minuscula.
		
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese la actividad deseada");
        String actividadDeseada= scanner.nextLine();
        
		System.out.print("¿Es obligatoria la actividad? (true/false): ");
        boolean obligatoria = scanner.nextBoolean();

        scanner.nextLine(); 
        System.out.print("Ingrese el nombre de la actividad: ");
        String nombre = scanner.nextLine();

        System.out.print("Ingrese la fecha límite (dd-MM-yyyy): ");
        String fechaLimiteStr = scanner.nextLine();
        Instant instant = getDateFromString(fechaLimiteStr);
        Date fechaLimite = (Date) Date.from(instant); //cast para q funcione
        
        System.out.print("Ingrese la descripción de la actividad: ");
        String descripcion = scanner.nextLine();

        System.out.print("Ingrese la calificación: ");
        double calificacion = scanner.nextDouble();

        System.out.print("Ingrese el rating (ejemplo: 4.5): ");
        int rating = scanner.nextInt();

        System.out.print("¿Está completada la actividad? (true/false): ");
        boolean esCompletada = scanner.nextBoolean();
		
        //invocar constructor de reseña y pedir sus respectivos parametros, revisar logica
        ArrayList<Resenia> resenias= new ArrayList<Resenia>();
        System.out.print("¿Desea crear reseña(s)?");
        String deseo= scanner.nextLine();
        
        if(deseo.toLowerCase().equals("si")) {
        	System.out.print("Ingrese la cantidad deseada: ");
        	int deseosinonimo = scanner.nextInt();
        	
        	for(int i= 0; i<deseosinonimo; i++) {
        	 System.out.print("Cree la reseña");
             String texto = scanner.nextLine();
             Resenia resenia= new Resenia(texto, rating);
             resenias.add(resenia);
        	}
        }
        
        System.out.print("Ingrese el nivel de dificultad: ");
        double nivelDificultad = scanner.nextDouble();

        System.out.print("¿Está empezada la actividad? (true/false): ");
        boolean estaEmpezado = scanner.nextBoolean();
        
        System.out.println("Ingrese el tipo de actividad: ");
        String tipo = scanner.nextLine();
        
        if(actividadDeseada.toLowerCase().equals("encuesta")) {
        	System.out.print("Ingrese la cantidad de preguntas que desea incluir: ");
        	int n = scanner.nextInt();
        	scanner.nextLine();
        	
        	ArrayList<PreguntaAbierta> preguntas= new ArrayList<PreguntaAbierta>();
        	
        	for(int i = 0; i<n; i++) {
        		System.out.print("Ingrese el enunciado de la pregunta " + (i + 1) + ": ");
                String enunciado = scanner.nextLine();
                PreguntaAbierta pregunta = new PreguntaAbierta(enunciado);
        		preguntas.add(pregunta);
        	}
        	return LearningPath.crearEncuesta (obligatoria, nombre, fechaLimite, descripcion, calificacion, rating, esCompletada, resenias, nivelDificultad, estaEmpezado, tipo, preguntas);
        }
        else if (actividadDeseada.toLowerCase().equals("examen")){
        	
        	System.out.print("Ingrese la cantidad de preguntas que desea incluir: ");
        	int n = scanner.nextInt();
        	scanner.nextLine();
        	
        	ArrayList<PreguntaAbierta> preguntas= new ArrayList<PreguntaAbierta>();
        	
        	
        	for(int i = 0; i<n; i++) {
        		System.out.print("Ingrese el enunciado de la pregunta " + (i + 1) + ": ");
                String enunciado = scanner.nextLine();
                PreguntaAbierta pregunta = new PreguntaAbierta(enunciado);
        		preguntas.add(pregunta);
        	}
        	
        	return LearningPath.crearExamen(obligatoria, nombre, fechaLimite, descripcion, calificacion, rating, esCompletada, resenias, nivelDificultad, estaEmpezado, tipo, preguntas);
        	
        }
        else if (actividadDeseada.toLowerCase().equals("quiz")){
        	System.out.print("Se crearan 4 preguntas de 4 opciones ");
            ArrayList<PreguntaCerrada> preguntas = new ArrayList<>();
            
            for (int i = 0; i < 4; i++) {
                System.out.print("Ingrese el enunciado: ");
                String enunciado = scanner.nextLine();

                ArrayList<String> opciones = new ArrayList<>();   
                for (int j = 1; j <= 4; j++) {
                    System.out.print("Ingrese la opción " + j + ": ");
                    String opcion= scanner.nextLine();
                    opciones.add(opcion);
                }

                System.out.print("Ingrese la opcion correcta (1-4): ");
                int opcionIndex = scanner.nextInt();
                String opcionCorrecta = opciones.get(opcionIndex +1);

                PreguntaCerrada pregunta = new PreguntaCerrada(enunciado, opciones, opcionCorrecta);
                preguntas.add(pregunta);

                System.out.println("Pregunta creada exitosamente.\n");
            }
        	
        	
        	for(int i= 0; i<4; i++) {
        		System.out.print("Ingrese el enunciado: ");
        		String enunciado= scanner.nextLine();
        		
        		System.out.print("Ahora ingrese las 4 opciones");
        		
        		
        	}
        	return LearningPath.crearQuiz(obligatoria, nombre, fechaLimite, descripcion, calificacion, rating, esCompletada, resenias, nivelDificultad, estaEmpezado,tipo, preguntas);
        	// de ultimas porque quiz suena como queers y esos son mks.
        	
        }
        
        else if (actividadDeseada.toLowerCase().equals("recursoeducativo")){
        	System.out.print("Ingrese el contenido y su tipo: ");
        	String contenido= scanner.nextLine();
        	String tipoC= scanner.nextLine();
        	
        	return LearningPath.crearRecursoEd(obligatoria, nombre, fechaLimite, descripcion, calificacion, rating, esCompletada, resenias, nivelDificultad, estaEmpezado, tipo,  contenido, tipoC);
        }
        
        else if (actividadDeseada.toLowerCase().equals("tarea")){
        	return LearningPath.crearTarea(obligatoria, nombre, fechaLimite, descripcion, calificacion, rating, esCompletada, resenias, nivelDificultad, estaEmpezado, tipo);
        } scanner.close();
    }
	
	public Actividad seleccionarActividadPorNombre(String nombre, ArrayList<Actividad> actividades) {
        //brutus cycle
        for (Actividad actividad: actividades) {
            if (actividad.getNombre().equals(nombre)){
            	return actividad;
            
            }
        } return null;
    }
	
	public void modificarActividad(Actividad actividad) {
		//necesita tomar actividad, usar nombre. salvajada por iteraccion

		
        Scanner scanner = new Scanner(System.in);
        boolean continuar = true;

        while (continuar) {
            System.out.println("\nSeleccione el atributo que desea modificar:");
            System.out.println("1. Nombre");
            System.out.println("2. Descripción");
            System.out.println("3. Fecha límite");
            System.out.println("4. Calificación");
            System.out.println("5. Rating");
            System.out.println("6. Nivel de dificultad");
            System.out.println("7. Estado de completada");
            System.out.println("8. Obligatoria");
            System.out.println("9. Tipo");
            System.out.println("10. Salir");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            if (opcion == 1) {
                System.out.print("Ingrese el nuevo nombre: ");
                String nombreNuevo = scanner.nextLine();
                actividad.setNombre(nombreNuevo);

            } else if (opcion == 2) {
                System.out.print("Ingrese la nueva descripción: ");
                String descripcionNueva = scanner.nextLine();
                actividad.setDescripcion(descripcionNueva);

            } else if (opcion == 3) {
            	 System.out.print("Ingrese la fecha límite (dd-MM-yyyy): ");
                 String fechaLimiteStr = scanner.nextLine();
                 Instant instant = getDateFromString(fechaLimiteStr);
                 Date fechaLimite = (Date) Date.from(instant);
                 actividad.setFechaLimite(fechaLimite);
                 
            } else if (opcion == 4) {
                System.out.print("Ingrese la nueva calificación: ");
                double nuevaCalificacion = scanner.nextDouble();
                actividad.setCalificacion(nuevaCalificacion);

            } else if (opcion == 5) {
                System.out.print("Ingrese el nuevo rating: ");
                float nuevoRating = scanner.nextFloat();
                actividad.setRating(nuevoRating);

            } else if (opcion == 6) {
                System.out.print("Ingrese el nuevo nivel de dificultad: ");
                double nuevaDificultad = scanner.nextDouble();
                actividad.setDificultad(nuevaDificultad);

            } else if (opcion == 7) {
                System.out.print("¿Está completada? (true/false): ");
                boolean completada = scanner.nextBoolean();
                actividad.setCompletada(completada);

            } else if (opcion == 8) {
                System.out.print("¿Es obligatoria? (true/false): ");
                boolean obligatoria = scanner.nextBoolean();
                actividad.marcarObligatoria();
            
            } else if (opcion == 9) {
                System.out.print("Ingrese el nuevo tipo: ");
                String tipo = scanner.nextLine();
                actividad.setTipo(tipo);    
                
            } else if (opcion == 10) {
                continuar = false;
                System.out.println("Saliendo de la modificación.");

            } else {
                System.out.println("Opción no válida. Intente de nuevo.");
            }

            if (continuar) {
                System.out.print("¿Desea modificar otro atributo? (si/no): ");
                String respuesta = scanner.next();
                continuar = respuesta.equalsIgnoreCase("si");
            }
        }

        scanner.close();
    }
	
	
    public static Instant getDateFromString(String string)
    {
        Instant timestamp = null;
 
        timestamp = Instant.parse(string);
 
        return timestamp;
    }
	
	
}
		
		
		
		
		
		
    		
		
        
    


	
	
	
	
	

