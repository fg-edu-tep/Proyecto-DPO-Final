package LPTH.gui;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import LPTH.modelo.Sistema;
import LPTH.modelo.LearningPath;
import LPTH.usuarios.Estudiante;
import LPTH.usuarios.Profesor;
import LPTH.usuarios.Resenia;
import LPTH.usuarios.Usuario;
import LPTH.actividades.Actividad;
import LPTH.gui.*;



public class menu_profesor {
	private static Sistema sistemaCentral;
	private Profesor profesor;
	
	public menu_profesor(Sistema sistemaCentral, Profesor profesor) {
		menu_profesor.sistemaCentral = sistemaCentral; // EDado que solo hay una instancia de sistema debe ser estático
		this.profesor = profesor;
	}

    public void opcionesprofesor() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("¿Que desea hacer?");
        
        System.out.println("1. Crear un nuevo Learning Path");
        System.out.println("2. Ver Learning Paths existentes");
        System.out.println("3. Consultar learning Path de otro docente");
        System.out.println("4. Revisar tareas y exámenes enviados por estudiantes");
        String opcion = scanner.next();
        
         if (opcion.equals("1")) {
            opcionescrearLearningPath();
        } else if (opcion.equals("2")) {
            verLearningPaths();
        } else if (opcion.equals("3")) {
            ConsultarotrosLearningpath();
            System.out.println("Desea clonar alguno de estos Learning Paths? (S/N)");
            String respuesta = scanner.next();
            if (respuesta.equals("S")) {
                clonarLearningPath();
            } else {
                System.out.println("Hasta luego!");
            }
        } else if (opcion.equals("4")) {
            revisarTareasExamenes();
        } else {
            System.out.println("Opción inválida");
        scanner.close();    
        }
    }

  

    private void opcionescrearLearningPath() {
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
            this.profesor,
            titulo,
            descripcion,
            nivelDeDificultad,
            duracion
        );

        if (nuevoPath != null) {
            System.out.println("Learning Path creado exitosamente!");
            System.out.println("Título: " + nuevoPath.getTitulo());
            System.out.println("ID: " + nuevoPath.getID());
            System.out.println("Desearía agregar actividades ahora? (S/N)");
            String respuesta1 = scanner.next();
            if(respuesta1.equals("S")) {
            	crearActividad(nuevoPath);//PENDIENTE IMPLEMENTAR PARTE DE PABLO
				}
            
            System.out.println("¿Desea volver al menú principal? (S/N)");
            String respuesta = scanner.next();
            if (respuesta.equals("S")) {
                opcionesprofesor();
            } else {
                System.out.println("Hasta luego!");
                System.exit(5);
            }
            
        }
       scanner.close();
    }

    private void verLearningPaths() {
        Scanner scanner = new Scanner(System.in);
        ArrayList<LearningPath> paths = sistemaCentral.getLearningPaths();
        
        System.out.println("Learning Paths existentes:");
        System.out.println(paths);
        if (paths.isEmpty()) {
            System.out.println("No hay Learning Paths disponibles.");
            scanner.close();
            System.exit(5);;
        }
        
        System.out.println("¿Que desea hacer ahora");
        System.out.println("1. Editar Learning Path");
        System.out.println("2. Volver al menú principal");
        String respuesta = scanner.next();
        if (respuesta.equals("1")) {
            editarLearningPath();
        } else if (respuesta.equals("2")) {
            opcionesprofesor();
        } else {
            System.out.println("Opción inválida");
        }
        scanner.close();
    }

    private void editarLearningPath() {
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
            System.out.println("1. Editar actividades");
            System.out.println("2. Eliminar actividades");
            System.out.println(path.getActividades());
            String respuesta2 = scanner.next();
            if (respuesta2.equals("1")) {
            	 System.out.print("Escriba el nombre de la actividad que desea editar");
            	 String nombreActividad = scanner.next();
            	 Actividad modificada = seleccionarActividad(nombreActividad,path.getActividades()); //PENDIENTE IMPLEMENTAR PARTE DE PABLO
            	 modificarActividad(modificada);
//                System.out.println("¿Qué desea editar de la actividad?");
//                String actividad = scanner.next();
//                System.out.println("Ingrese el tipo de actividad a agregar entre Quiz -> Q, Tarea -> T, Examen -> E, Recurso Educativo -> R, Encuesta -> P");
//                String seleccion = scanner.next();
//                System.out.println("Es obligatoria? (true/false)");
//                boolean obligatoria = scanner.nextBoolean();
//                System.out.println("Ingrese el nombre: ");
//                scanner.nextLine(); // Limpiar buffer
//                String nombreact = scanner.nextLine();
//                System.out.println("Ingrese la fecha límite (en formato yyyy-MM-dd): ");
//                String fechaLimiteStr = scanner.nextLine();
//                Date fechaLimite = Date.valueOf(fechaLimiteStr); // Ajustar a un formato compatible
//                System.out.println("Ingrese la descripción: ");
//                String descripcion = scanner.nextLine();
//                double calificacion = 0.0;
//                float rating = 0;
//                boolean esCompletada = false;
//                ArrayList<Resenia> resenias = new ArrayList<>();
//                System.out.println("Ingrese el nivel de dificultad: ");
//                double nivelDificultad = scanner.nextDouble();
//                boolean estaEmpezado = false;
//                path.agregarActividad(obligatoria, nombreact, fechaLimite, descripcion,
//                    calificacion, rating, esCompletada, resenias, nivelDificultad, estaEmpezado);
//                if (seleccion.equals("Q")) {
//                	
//                } else if (seleccion.equals("T")) {
//                    // Código para Tarea
//                } else if (seleccion.equals("E")) {
//                    // Código para Examen
//                } else if (seleccion.equals("R")) {
//                    // Código para Recurso Educativo
//                } else if (seleccion.equals("P")) {
//                    // Código para Encuesta
//                } else {
//                  System.out.println("Opción inválida.");
//                  System.exit(0);
//                
            } else if (respuesta2.equals("2")) {
                System.out.println("Ingrese el nombre de la actividad a eliminar: ");
                scanner.nextLine(); // Limpiar buffer
                String nombreActividad = scanner.nextLine();
                Actividad seleccionada = seleccionarActividad(nombreActividad,path.getActividades());
                path.eliminarActividad(seleccionada); // Ajuste aquí
            } else {
                System.out.println("Opción inválida.");
                System.exit(0);
            }
        } else {
            System.out.println("Opción inválida.");
            System.exit(0);
        }
        System.out.println("Learning Path editado exitosamente.");
        System.out.println("¿Desea volver al menú principal? (S/N)");
        String respuesta3 = scanner.next();
        if (respuesta3.equals("S")) {
            opcionesprofesor();
        } else {
            System.out.println("Hasta luego!");
        }
        scanner.close();
    }

    

    private void ConsultarotrosLearningpath() {
        String nombreDocente = this.profesor.getNombre();
        ArrayList<String> otrosDocentes = new ArrayList<>();
        ArrayList<LearningPath> paths = sistemaCentral.getLearningPaths();

        for (LearningPath path : paths) {
            String profesorPath = path.getProfesorCreador();
            if (!profesorPath.equals(nombreDocente) && !otrosDocentes.contains(profesorPath)) {
                otrosDocentes.add(profesorPath);
            }
        }

        for (String otroDocente : otrosDocentes) {
            System.out.println("\nLearning Paths del profesor " + otroDocente + ":");
            for (LearningPath path : paths) {
                if (path.getProfesorCreador().equals(otroDocente)) {
                    System.out.println("- " + path.getTitulo() + " (ID: " + path.getID() + ")");
                    System.out.println("  Descripción: " + path.getDescripcion());
                    System.out.println("  Duración: " + path.getDuracion() + " horas");
                }
            }
        }

        if (otrosDocentes.isEmpty()) {
            System.out.println("No se encontraron Learning Paths de otros docentes en el sistema.");
        }
    }

    private void clonarLearningPath() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese el ID del Learning Path a clonar: ");
        String id = scanner.next();
        LearningPath path = sistemaCentral.getLearningPath(id);
        if (path == null) {
            System.out.println("Learning Path no encontrado.");
            System.exit(5);
        }
        
        LearningPath pathClonado = sistemaCentral.crearLearningPath(
            this.profesor,
            path.getTitulo(),
            path.getDescripcion(),
            path.getnivelDeDificultad(),
            path.getDuracion()
        );
        System.out.println("Learning Path clonado exitosamente: " + pathClonado.getTitulo());
        scanner.close();
    }

    private void revisarTareasExamenes() {
    	
    	
    	
     }
}