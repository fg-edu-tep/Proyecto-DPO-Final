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

import LPTH.Preguntas.PreguntaAbierta;
import LPTH.Preguntas.PreguntaCerrada;
import LPTH.actividades.*;
import LPTH.modelo.*;
import LPTH.usuarios.Estudiante;
import LPTH.usuarios.Profesor;
import LPTH.usuarios.Usuario;
import LPTH.modelo.Sistema.*;


public class Console {
	
	
	private static Sistema sistemaCentral = new Sistema();
	
	public Console() {
		sistemaCentral = new Sistema(); // Este debe ser la UNICA instancia de sistema a ser usada
	}
	
	
	/*Métodos de autenticación y creacion de usuario*/
	
	public static boolean IngresoUsuario() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Ingrese su email: ");
		String email = scanner.next();
		System.out.println("Ingrese su contraseña: ");
		String contrasenia = scanner.next();
		boolean estaautenticado = sistemaCentral.autenticarUsuario(email, contrasenia);
		if (estaautenticado) {
			System.out.println("Ha ingresado correctamente");
			TipodeUsuario();
			if (TipodeUsuario().equals("profesor")) {
				menu_profesor.opcionesprofesor();
			} else if (TipodeUsuario().equals("estudiante")) {
				menu_estudiante.opcionesestudiante();
			}
		} else {
			System.out.println("No se ha podido autenticar");
		}
		return estaautenticado;
	}
	
	public static void elegirCreacionUsuario() {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Elija el tipo de usuario a crear | P -> Profesor | E -> Estudiante");
		String selection = scanner.next();
		//scanner.close();
		System.out.print(selection);
		
		if(selection.equals("E")) {
			Estudiante nuevoEstudiante = crearUsuarioEstudiante();
			System.out.println("Se ha creado el nuevo usuario:");
			System.out.println(nuevoEstudiante.getNombre());
			System.out.println("Con Identificador:");
			System.out.println(nuevoEstudiante.getIdUsuario());
			System.out.println("Desea ingresar a su cuenta? | S -> Sí | N -> No");
			String  ingresoUsuario = scanner.next();
			if (ingresoUsuario.equals("S")) {
				IngresoUsuario();
			}
			
				
		}
		else if (selection.equals("P")) {
			Profesor nuevoProfesor = crearUsuarioProfesor();
			System.out.println("Se ha creado el nuevo usuario:");
			System.out.println(nuevoProfesor.getNombre());
			System.out.println("profesor de la materia:");
			System.out.println(nuevoProfesor.getMateria());
			System.out.println("Con Identificador:");
			System.out.println(nuevoProfesor.getIdUsuario());
		}
	}


	private static Estudiante crearUsuarioEstudiante() {
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
		//scanner.close();
		Estudiante nuevoEstudiante = (Estudiante)sistemaCentral.crearUsuario(tipo, nombre, email, pass, fechaRegistro, materia);
		return nuevoEstudiante;
	}


	private static Profesor crearUsuarioProfesor() {
		System.out.print("Nombre del profesor: ");
		Scanner scanner = new Scanner(System.in);
		String tipo = "profesor";
		String nombre = scanner.next();
		System.out.print("Email del profesor: ");
		String email = scanner.next();
		System.out.print("Contraseña del profesor: ");
		String pass = scanner.next();
		System.out.print("Materia del profesor: ");
		String materia = scanner.next();
		String fechaRegistro = (sistemaCentral.getCurrentDate()).toString();
		scanner.close();
		Profesor nuevoProfesor = (Profesor)sistemaCentral.crearUsuario(tipo, nombre, email, pass, fechaRegistro, materia);
		return  nuevoProfesor;		
	}
	
	private static Usuario resetPassword(String Email){
		Usuario olvidadiso = sistemaCentral.grabUsuarioByEmail(Email);
		Scanner scanner = new Scanner(System.in);
		System.out.print("Ingrese su nuevo password: ");
		String newPassword = scanner.next();
		olvidadiso.setContrasenia(newPassword);
		scanner.close();
		return olvidadiso;
	}
	public static String TipodeUsuario() {
		 String tipo = sistemaCentral.esProfesorOEstudiante();		
		return tipo;
	}

	
	
	
	
	
	
	
	public static void main (String[] args) {
		
	
		
		System.out.println("Bienvenido");
		Scanner scanner = new Scanner(System.in);
		System.out.println("¿Tiene cuenta? | S -> Sí | N -> No");
		String  tiene_cuenta = scanner.next();
		if(tiene_cuenta.equals("S")){
			IngresoUsuario();
		}
		else if (tiene_cuenta.equals("N")) {
			System.out.println("¿Desea crear una cuenta? | S -> Sí | N -> No");
	        String crear_cuenta = scanner.next();
			if (crear_cuenta.equals("S")) {
				elegirCreacionUsuario();
			} else if( crear_cuenta.equals("N")) {
			    System.out.println("Hasta luego");
				System.exit(0);
            }
		}
		
		scanner.close();//pedinete cerrar el scanner
		
		
			
		}
	
}
