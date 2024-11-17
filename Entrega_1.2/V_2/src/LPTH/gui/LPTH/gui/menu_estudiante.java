package LPTH.gui;
import java.util.Scanner;

import LPTH.usuarios.Estudiante;
import LPTH.usuarios.Profesor;
import LPTH.usuarios.Usuario;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random; // Para los Ids de Usuario

import LPTH.Preguntas.PreguntaAbierta;
import LPTH.Preguntas.PreguntaCerrada;
import LPTH.actividades.*;
import LPTH.modelo.*;
import LPTH.modelo.Sistema.*;
	

public class menu_estudiante {
	private static Sistema sistemaCentral;
	private Estudiante estudiante;
	
	public menu_estudiante(Sistema sistemaCentral, Estudiante estudiante) {
		menu_estudiante.sistemaCentral = sistemaCentral; // EDado que solo hay una instancia de sistema debe ser estático
		this.estudiante = estudiante;
	}
	
	public void opcionesEstudiante() {
		boolean keepEstudianteinMenu = true;
		while (keepEstudianteinMenu) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("¿Que desea hacer?");
		System.out.println("1. Explorar Learning Paths recomendados");
		System.out.println("2. Ver Learning Paths vistos (Progreso)");
		System.out.println("3. Interactuar con learning Path actual");
		System.out.println("4. Eliminar Learning Paths inscrito");
		// TODO Hacer logout estudiante estudiante.logout() & Mandarlo al menu de inicio de sesion consola.inicioSension
		String opcion = scanner.next();
		
		if (opcion.equals("1")) {
			// pendiente learning paths recomendados para estudiantes
			LearningPath recomendado = sistemaCentral.recomendarLearningPath();
			System.out.println("Se le recomienda el learningPath: ");
			System.out.println(recomendado.getTitulo());
			System.out.println("Descripción: ");
			System.out.println(recomendado.getDescripcion());
			System.out.println("Desea empezar el learningPath? ");
			String seleccion = scanner.next();
			if (seleccion.equals("S")){
				startLearningPath(recomendado); 
			}
		} else if (opcion.equals("2")) {
			System.out.println("Información de su progreso : \n");
			System.out.print(estudiante.getProgreso().getCompleteProgress());
		} else if (opcion.equals("3")) {
			System.out.println("Su learningPath actual es: ");
			System.out.println(estudiante.getLearningPathActual());
			System.out.println("¿Desea ver interactuar con su learning path?  S/N");
			String interact = scanner.next();
			if (interact.equals("S")) {
				keepEstudianteinMenu = false; // Lo estamos mandando a otro menú
				opcionesInteractuarConLp();				
				}
		} else if (opcion.equals("4")) {
			System.out.println("Eliminara TODO su progreso en este learningPath. ¿Desea continuar? S/N");
			String delete = scanner.next();
			if (delete.equals("S")) {
				estudiante.removeLearningPath();
			}
		} else {
			System.out.println("Opción inválida");
		}
		scanner.close();
		
		}
	}
	
	public void opcionesInteractuarConLp() {
		boolean keepEstudianteinMenu = true;
		while (keepEstudianteinMenu) {
			Scanner scanner = new Scanner(System.in);
			System.out.println("¿Que desea hacer?");
			System.out.println("1. Ver detalles del LearningPath");
			System.out.println("2. Ver las actividades del LearningPath");
			System.out.println("3. Salir a menu principal estudiante");
			String opcion = scanner.next();
			if (opcion.equals("1")) {
				imprimirDetallesBasicosLp();
			}
			else if (opcion.equals("2")) {
				keepEstudianteinMenu = false; // Lo estamos mandando a otro menú
				imprimirActividadesLp();
			}
			else if (opcion.equals("3")) {
				keepEstudianteinMenu = false; // Lo estamos mandando a otro menú
				opcionesEstudiante();
			}
			
			
			
			scanner.close();
		}
		
	}
	


	// Flujo de consola divergente extenso:
	
	private void imprimirDetallesBasicosLp() {
		try {
			ArrayList<String> infoLp = estudiante.peekLearningPathInfo();
			System.out.println("El titulo de su Learning Path es: ");
			System.out.println(infoLp.getFirst());
			System.out.println("Y el LearningPath trata de: ");
			System.out.println(infoLp.getLast());
			
		}
		catch (Exception e){
			System.out.println(e); // Imprimir la excepcion
		}
		finally {
			opcionesInteractuarConLp(); // Regresar el usuario un menú
		}
	}
	
	private void imprimirActividadesLp() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Opciones: ");
		System.out.println("1. Sólo ver las actividades");
		System.out.println("2. Preguntar si quiero empezar en cada una de las actividades y empezr UNA");
		System.out.println("3. Empezar una actividad según el nombre");
		String actOpcn = scanner.next();
		try {
			
			LearningPath lpEstudiante = estudiante.peekLearningPath();

			if (actOpcn.equals("1")) {
			for (Actividad activity : lpEstudiante.getActividadesSinCompletar()) {
				System.out.println(activity.getNombre());
				}
			}
			if (actOpcn.equals("2")) {
				boolean yaSelecicionoActividad = false;
				for (Actividad activity : lpEstudiante.getActividadesSinCompletar()) {
					System.out.println("Actividad: ");
					System.out.println(activity.getNombre());
					System.out.println("¿Desea empezar la actividad ahora? S/N");
					String beggin = scanner.next();
					if ((beggin.equals("S")) && (!yaSelecicionoActividad)) {
						yaSelecicionoActividad = true;
						menuEmpezarActividad(activity);
					} else {
						if (yaSelecicionoActividad) {
							System.out.println(activity.getNombre());
						}
					}
				}
			}if (actOpcn.equals("3")) {
				// TODO Llamar a actividad de Pablo que recibe un String
			}
		} catch (Exception e) {
			System.out.println(e); // Imprimir la excepcion
			}
		
		finally {
			opcionesInteractuarConLp(); // Regresar el usuario un menú
			scanner.close();
		}
	}
	
	
	// Funciones Handler extensas
	public LearningPath startLearningPath(LearningPath newLearningPath) {
		LearningPath nuevoLp = null;
		estudiante.startLearningPath(newLearningPath);
		if (estudiante.getLearningPathActual().equals("None")) {
			estudiante.startLearningPath(newLearningPath);
			System.out.println("¡Ha empezado un nuevo learningPath!");
			System.out.println(newLearningPath.getTitulo());
			nuevoLp = newLearningPath;
			return nuevoLp;
		}
		else {
			System.out.println("¡Elimine el learning path anterior antes!");
			return nuevoLp;
		}
		
	}
	
	public void deleteLearningPath(LearningPath newLearningPath) {
		estudiante.removeLearningPath();
		System.out.println("¡Learning path eliminado!");
	}
	
	public void menuEmpezarActividad(Actividad activity) {
		// Establecer tipo:
		String tipoActividad;
		//tipoActividad = activity.getTipo(); // TODO Si esto no esta implementado debe implementarse
		/*
		 * Acá viene una serie de ifs' de manera que dependiendo del tipo de actividad hace el casting correcto\
		 * Pseudo Ejemplo:
		 * if (tipoActividad.equals("Quiz")){
		 * Quiz quizSeleccion = (Quiz)activity; // Esta linea hace el casting para acceder a todos los metodos implementados por QUIZ
		 * }
		 * ... Serie de Ifs
		 * Continucion ejemplo Quiz:
		 * =estudiante.iniciarActividad(quizSeleccion); ..... Lo que se necesite para mostrar al usuario en consol el quiz y que lo complete.
		 * ¿Quiz si se mrc cómo completdo un vez completo? -> Si no, implementr.
		 * */
		// TODO @PABLO Realizar esta implementación dependiendo del tipo y lo que necesite
	}
		
		


}
