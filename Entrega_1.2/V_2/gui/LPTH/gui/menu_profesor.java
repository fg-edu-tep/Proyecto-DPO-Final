package LPTH.gui;

import java.util.Scanner;

public class menu_profesor {
	public static void opcionesprofesor() {
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
			// Ver Learning Paths vistos
		} else if (opcion.equals("3")) {
			// Ver Learning Paths inscritos
		} else {
			System.out.println("Opción inválida");
		}
		

    }
	}

