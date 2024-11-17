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
	
	public static void opcionesestudiante() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("¿Que desea hacer?");
		System.out.println("1. Explorar Learning Paths recomendados");
		System.out.println("2. Ver Learning Paths vistos");
		System.out.println("3. Ver Learning Paths inscritos");
		String opcion = scanner.next();
		
		if (opcion.equals("1")) {
			// pendiente learning paths recomendados para estudiantes
			learningPath recomendado = sistemaCentral.recomendarLearningPath();
			System.out.println("Se le recomienda el learningPath: ");
			System.out.println(recomendado.getTitulo());
			System.out.println("Descripción: ");
			System.out.println(recomendado.getDescripcion());
			System.out.println("Desea empezar el learningPath? ");
		} else if (opcion.equals("2")) {
			
		} else if (opcion.equals("3")) {
			// Ver Learning Paths inscritos
		} else {
			System.out.println("Opción inválida");
		}
		
	}
	
	public learningPath startLearningPath() {
		
	}
		
		
	
	
	
	
	
	
	

}
