package LPTH.gui;
import java.util.Scanner;
import LPTH.modelo.Sistema;
import LPTH.usuarios.Estudiante;
import LPTH.usuarios.Profesor;
import LPTH.usuarios.Usuario;
	

public class menu_estudiante {
	
	public static void opcionesestudiante() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("¿Que desea hacer?");
		System.out.println("1. Explorar Learning Paths recomendados");
		System.out.println("2. Ver Learning Paths vistos");
		System.out.println("3. Ver Learning Paths inscritos");
		String opcion = scanner.next();
		
		if (opcion.equals("1")) {
			// pendiente learning paths recomendados para estudiantes
			
		} else if (opcion.equals("2")) {
			
		} else if (opcion.equals("3")) {
			// Ver Learning Paths inscritos
		} else {
			System.out.println("Opción inválida");
		}
		
		
		
	}
	
	
	
	
	
	

}
