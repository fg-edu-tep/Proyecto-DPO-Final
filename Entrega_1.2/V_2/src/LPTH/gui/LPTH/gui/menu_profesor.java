package LPTH.gui;

import java.lang.reflect.Array;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import LPTH.modelo.Sistema;
import LPTH.modelo.LearningPath;
import LPTH.usuarios.Estudiante;

import LPTH.usuarios.Profesor;
import LPTH.usuarios.Resenia;
import LPTH.usuarios.Usuario;
import LPTH.Preguntas.PreguntaAbierta;
import LPTH.Preguntas.PreguntaCerrada;
import LPTH.Preguntas.PreguntaToF;
import LPTH.actividades.Actividad;
import LPTH.actividades.Examen;
import LPTH.gui.*;



public class menu_profesor {
	private static Sistema sistemaCentral;
	private Profesor profesor;
	
	public menu_profesor(Sistema sistemaCentral, Profesor profesor) {
		menu_profesor.sistemaCentral = sistemaCentral; // EDado que solo hay una instancia de sistema debe ser estático
		this.profesor = profesor;
	}

	public void opcionesprofesor(Scanner scanner) {
	    boolean continuar = true;

	    while (continuar) {
	        System.out.println("¿Qué desea hacer?");
	        System.out.println("1. Crear un nuevo Learning Path");
	        System.out.println("2. Ver Learning Paths existentes");
	        System.out.println("3. Ver mis Learning Paths");
	        System.out.println("4. Consultar Learning Path de otro docente");
	        System.out.println("5. Revisar tareas y exámenes enviados por estudiantes");
	        System.out.println("6. Crear actividad");
	        System.out.println("7. Modificar Actividad");
	        System.out.println("8. Salir");

	        String opcion = scanner.nextLine(); // Leer opción en cada iteración.

	        if (opcion.equals("1")) {
	            opcionescrearLearningPath(scanner);
	        } else if (opcion.equals("2")) {
	            verLearningPaths(scanner); // Todos los LPs
	        } else if (opcion.equals("3")) {
	            verMisLps(scanner);
	        } else if (opcion.equals("4")) {
	            ConsultarotrosLearningpath();
	            System.out.println("¿Desea clonar alguno de estos Learning Paths? (S/N)");
	            String respuesta = scanner.nextLine();
	            if (respuesta.equalsIgnoreCase("S")) {
	                clonarLearningPath(scanner);
	            } else {
	                System.out.println("Hasta luego!");
	            }
	        } else if (opcion.equals("5")) {
	            ArrayList<LearningPath> lps = profesor.getLps();
	            System.out.println("Ingrese el título del LP a consultar:");
	            String nombrelp = scanner.nextLine();
	            LearningPath lpseleccionado = seleccionarLearningPathPorNombre(nombrelp, lps);
	            if (lpseleccionado != null) {
	                ArrayList<Actividad> actividades = lpseleccionado.getActividades();
	                revisarExamenes(actividades, scanner);
	            } else {
	                System.out.println("Learning Path no encontrado.");
	            }
	        } else if (opcion.equals("6")) {
	            ArrayList<LearningPath> lps = profesor.getLps();
	            System.out.println("Ingrese el título del LP a consultar:");
	            String nombrelp = scanner.nextLine();
	            LearningPath lpseleccionado = seleccionarLearningPathPorNombre(nombrelp, lps);
	            if (lpseleccionado != null) {
	                Actividad actividad = crearActividad(scanner, lpseleccionado);
	                lpseleccionado.agregarActividad(actividad);
	            } else {
	                System.out.println("Learning Path no encontrado.");
	            }
	        } else if (opcion.equals("7")) {
	            ArrayList<LearningPath> lps = profesor.getLps();
	            System.out.println("Ingrese el título del LP a consultar:");
	            String nombrelp = scanner.nextLine();
	            LearningPath lpseleccionado = seleccionarLearningPathPorNombre(nombrelp, lps);
	            if (lpseleccionado != null) {
	                ArrayList<Actividad> actividades = lpseleccionado.getActividades();
	                System.out.println("Ingrese el nombre de la actividad deseada:");
	                String nombre = scanner.nextLine();
	                Actividad actividad = seleccionarActividadPorNombre(nombre, actividades);
	                if (actividad != null) {
	                    modificarActividad(actividad, scanner);
	                } else {
	                    System.out.println("Actividad no encontrada.");
	                }
	            } else {
	                System.out.println("Learning Path no encontrado.");
	            }
	        } else if (opcion.equals("8")) {
	            continuar = false;
	        } else {
	            System.out.println("Opción inválida, por favor intente de nuevo.");
	        }
	    }
	}

	

  

    private void opcionescrearLearningPath(Scanner scanner) {
        ////Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese el titulo del Learning Path: ");
        String titulo = scanner.nextLine();

        System.out.println("Ingrese la descripción del Learning Path: ");
        String descripcion = scanner.nextLine();

        System.out.println("Ingrese el nivel de dificultad del Learning Path (Básico/Intermedio/Avanzado): ");
        String nivelDeDificultad = scanner.nextLine();

        System.out.println("Ingrese la duración del Learning Path en horas: ");
        int duracion = scanner.nextInt();

        LearningPath nuevoPath = sistemaCentral.crearLearningPath(this.profesor,titulo,descripcion, nivelDeDificultad,duracion);
        
        if (nuevoPath != null) {
            System.out.println("Learning Path creado exitosamente!");
            System.out.println("Título: " + nuevoPath.getTitulo());
            System.out.println("ID: " + nuevoPath.getID());
            System.out.println("Desearía agregar actividades ahora? (S/N)");
            String respuesta1 = scanner.next();
            if(respuesta1.equals("S")) {
            	Actividad actividad= crearActividad(scanner,nuevoPath);//PENDIENTE IMPLEMENTAR PARTE DE PABLO
            	nuevoPath.agregarActividad(actividad);
            	
				}
            
            System.out.println("¿Desea volver al menú principal? (S/N)");
            String respuesta = scanner.next();
            if (respuesta.equals("S")) {
                opcionesprofesor(scanner);
            } else {
                System.out.println("Hasta luego!");
                System.exit(5);
            }
            
        }
        profesor.agregarLp(nuevoPath);
       //scanner.close();
    }

    private void verLearningPaths(Scanner scanner) {  //todos los lps
        ////Scanner scanner = new Scanner(System.in);
        ArrayList<LearningPath> paths = sistemaCentral.getLearningPaths();
        
        System.out.println("Learning Paths existentes:");
        System.out.println(paths);
        if (paths.isEmpty()) {
            System.out.println("No hay Learning Paths disponibles.");
            //scanner.close();
            System.exit(5);
        }
        
        System.out.println("¿Que desea hacer ahora");
        System.out.println("1. Editar Learning Path");
        System.out.println("2. Volver al menú principal");
        String respuesta = scanner.next();
        if (respuesta.equals("1")) {
            editarLearningPath(scanner);
        } else if (respuesta.equals("2")) {
            opcionesprofesor(scanner);
        } else {
            System.out.println("Opción inválida");
        }
        //scanner.close();
    }

    public void verMisLps(Scanner scanner){    //lps del profe
        ////Scanner scanner = new Scanner(System.in);
        ArrayList<LearningPath> paths = profesor.getLps();
        
        System.out.println("Learning Paths existentes:");
        System.out.println(paths);
        if (paths.isEmpty()) {
            System.out.println("No hay Learning Paths disponibles.");
            //scanner.close();
            System.exit(5);
        }
        
        System.out.println("¿Que desea hacer ahora");
        System.out.println("1. Editar Learning Path");
        System.out.println("2. Volver al menú principal");
        String respuesta = scanner.next();
        if (respuesta.equals("1")) {
            editarLearningPath(scanner);
        } else if (respuesta.equals("2")) {
            opcionesprofesor(scanner);
        } else {
            System.out.println("Opción inválida");
        }
        //scanner.close();
    }

    private void editarLearningPath(Scanner scanner) {
        ////Scanner scanner = new Scanner(System.in);
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
            	 Actividad modificada = seleccionarActividadPorNombre(nombreActividad,path.getActividades()); //PENDIENTE IMPLEMENTAR PARTE DE PABLO
            	 modificarActividad(modificada, scanner);
            } else if (respuesta2.equals("2")) {
                System.out.println("Ingrese el nombre de la actividad a eliminar: ");
                scanner.nextLine(); // Limpiar buffer
                String nombreActividad = scanner.nextLine();
                Actividad seleccionada = seleccionarActividadPorNombre(nombreActividad,path.getActividades());
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
            opcionesprofesor(scanner);
        } else {
            System.out.println("Hasta luego!");
        }
        //scanner.close();
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

    private void clonarLearningPath(Scanner scanner) {
        ////Scanner scanner = new Scanner(System.in);
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
        profesor.agregarLp(pathClonado);
        //scanner.close();
    }



		
    public void revisarExamenes(ArrayList<Actividad> actividades, Scanner scanner) {
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
        //Scanner scanner = new Scanner (System.in);
        System.out.println("Estan correctas (si/no)?");
        String correccion = scanner.nextLine();
        examen.calificarActividad(correccion);
        System.out.println("El estudiante saco: ");
        actividad.getCalificacion();
        //scanner.close();
        }
    }
    
}


    public Actividad crearActividad(Scanner scanner, LearningPath lp) {
        //Todos los strings deben ser en minuscula.
        
        ////Scanner scanner = new Scanner(System.in);
    	
    	System.out.print("Selecciono crear actividad, ahora se le pediran los parametros" );
    	
    	System.out.print("\nIngrese el tipo de la actividad");
    	System.out.print("\nOpciones: encuesta, examen, quiz, recursoeducativo, tarea: ");
        String actividadDeseada= scanner.next();
    	
        System.out.print("¿Es obligatoria la actividad? (true/false): ");
        boolean obligatoria = scanner.nextBoolean();

        scanner.nextLine(); 
        System.out.print("Ingrese el nombre de la actividad: ");
        String nombre = scanner.nextLine();

        
        System.out.print("Ingrese la fecha límite (dd-MM-yyyy): ");		//no funciona
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
        System.out.print("¿Desea crear reseña(s)?" );
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
            return lp.crearEncuesta (obligatoria, nombre, fechaLimite, descripcion, calificacion, rating, esCompletada, resenias, nivelDificultad, estaEmpezado, actividadDeseada, preguntas);
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
            
            return lp.crearExamen(obligatoria, nombre, fechaLimite, descripcion, calificacion, rating, esCompletada, resenias, nivelDificultad, estaEmpezado, actividadDeseada, preguntas);
            
        }
        /*
        else if (actividadDeseada.toLowerCase().equals("quiz")){
        	System.out.print("Que tipo de preguntas desea usar (PreguntaToF/PreguntaCerrada)");
        	String tipopregunta= scanner.nextLine();
        	
        	if(tipopregunta.toLowerCase().equals("PreguntaCerrada")) {      	
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
	            return lp.crearQuiz(obligatoria, nombre, fechaLimite, descripcion, calificacion, rating, esCompletada, resenias, nivelDificultad, estaEmpezado,tipo, preguntas);
	            // de ultimas porque quiz suena como queers y esos son mks.
        	}
        	
        	else if (tipopregunta.toLowerCase().equals("PreguntaToF")) {      	
        		System.out.print("Ingrese la cantidad de preguntas que desea incluir: ");
                int n = scanner.nextInt();
                scanner.nextLine();
                
                ArrayList<PreguntaToF> preguntasT= new ArrayList<PreguntaToF>();
                
                for(int i = 0; i<n; i++) {
                    System.out.print("Ingrese el enunciado de la pregunta " + (i + 1) + ": ");
                    String enunciado = scanner.nextLine();
                    System.out.print("Ingrese si es(true/false)");
                    boolean respuestaC= scanner.nextBoolean();
                    PreguntaToF preguntaT = new PreguntaToF(enunciado, respuestaC);
                    preguntasT.add(preguntaT);
                	}
                return lp.crearQuiz(obligatoria, nombre, fechaLimite, descripcion, calificacion, rating, esCompletada, resenias, nivelDificultad, estaEmpezado,tipo, preguntasT);
	            }
        }
        */
        else if (actividadDeseada.toLowerCase().equals("recursoeducativo")){
            System.out.print("Ingrese el contenido y su tipo: ");
            String contenido= scanner.nextLine();
            String tipoC= scanner.nextLine();
            
            return lp.crearRecursoEd(obligatoria, nombre, fechaLimite, descripcion, calificacion, rating, esCompletada, resenias, nivelDificultad, estaEmpezado, actividadDeseada,  contenido, tipoC);
        }
        
        else if (actividadDeseada.toLowerCase().equals("tarea")){
            return lp.crearTarea(obligatoria, nombre, fechaLimite, descripcion, calificacion, rating, esCompletada, resenias, nivelDificultad, estaEmpezado, actividadDeseada);
        } return null; //scanner.close();
    }


    //Metodos de busqueda
    public Actividad seleccionarActividadPorNombre(String nombre, ArrayList<Actividad> actividades) {
        //brutus cycle
        for (Actividad actividad: actividades) {
            if (actividad.getNombre().equals(nombre)){
                return actividad;
            
            }
        } return null;
    }
    
    public LearningPath seleccionarLearningPathPorNombre(String nombre, ArrayList<LearningPath> lps){
                //brutus cycle
                for (LearningPath lp: lps) {
                    if (lp.getTitulo().equals(nombre)){
                        return lp;
                    
                    }
                } return null;
    }
    
    public void modificarActividad(Actividad actividad, Scanner scanner) {
        //necesita tomar actividad, usar nombre. salvajada por iteraccion

        
        ////Scanner scanner = new Scanner(System.in);
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
                String obligatoria= scanner.nextLine();
                if(obligatoria.equals("true")){
                	actividad.marcarObligatoria();
                }
                
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

        //scanner.close();
    }




    public static Instant getDateFromString(String string) {
        // Definir el formato personalizado
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        // Parsear la cadena a LocalDate
        LocalDate localDate = LocalDate.parse(string, formatter);
        // Convertir LocalDate a Instant
        return localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
    }

}

