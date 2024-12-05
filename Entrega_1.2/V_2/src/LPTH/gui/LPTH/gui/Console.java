package LPTH.gui;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random; // Para los Ids de Usuario
import java.util.Scanner;

import LPTH.Preguntas.Pregunta;
import LPTH.Preguntas.PreguntaAbierta;
import LPTH.Preguntas.PreguntaCerrada;
import LPTH.actividades.*;
import LPTH.modelo.*;
import LPTH.usuarios.Estudiante;
import LPTH.usuarios.Profesor;
import LPTH.usuarios.Usuario;
import LPTH.modelo.Sistema.*;
import LPTH.persistencia.PersistirSistema;


public class Console {
	
	
	private static Sistema sistemaCentral = new Sistema();
	
	public Console() {
		sistemaCentral = new Sistema(); // Este debe ser la UNICA instancia de sistema a ser usada
	}
	
	
	/*Métodos de autenticación y creacion de usuario*/
	
	public static Usuario IngresoUsuario(Scanner scanner) {
	    Usuario elUsuario = null;
	    
	    while (elUsuario == null) {
	        System.out.println("Ingrese su email: ");
	        String email = scanner.next();
	        System.out.println("Ingrese su contraseña: ");
	        String contrasenia = scanner.next();

	        try {
	            elUsuario = sistemaCentral.autenticarUsuario(email, contrasenia);
	            if (elUsuario.checkLogIn()) {
	                System.out.println("Ha ingresado correctamente");
	           
	                String tipoDeUsuario = elUsuario.getTipo();
	                System.out.println(tipoDeUsuario);

	                if (tipoDeUsuario.equals("Profesor")) {
	                    Profesor elProfesor = (Profesor) elUsuario;
	                    menu_profesor menuDelProfesor= new menu_profesor(sistemaCentral, elProfesor);
	                    menuDelProfesor.opcionesprofesor(scanner);
	                } else if (tipoDeUsuario.equals("Estudiante")) {
	                    Estudiante elEstudiante = (Estudiante) elUsuario;
	                    menu_estudiante menuDelEstudiante = new menu_estudiante(sistemaCentral, elEstudiante, scanner);
	                    menuDelEstudiante.opcionesEstudiante(scanner);
	                }
	                return elUsuario; 
	            }
	        } catch (Exception e) {
	            System.out.println("No se ha podido autenticar. ¿Desea intentar de nuevo? | S -> Sí | N -> No");
	            String retry = scanner.next();
	            if (retry.equalsIgnoreCase("N")) {
	                System.out.println("Regresando al menú principal...");
	                return null; 
	            }
	        }
	    }
	    //scanner.close();
	    return elUsuario;
	}

	
	public static void elegirCreacionUsuario(Scanner scanner ) {
		System.out.print("Elija el tipo de usuario a crear | P -> Profesor | E -> Estudiante");
		String selection = scanner.next();
		System.out.print(selection);
		//scanner.close();	
		
		if(selection.equals("E")) {
			Estudiante nuevoEstudiante = crearUsuarioEstudiante(scanner);
			System.out.println("Se ha creado el nuevo usuario:");
			System.out.println(nuevoEstudiante.getNombre());
			System.out.println("Con Identificador:");
			System.out.println(nuevoEstudiante.getIdUsuario());
			System.out.println("Desea ingresar a su cuenta? | S -> Sí | N -> No");			
			String  ingresoUsuario = scanner.nextLine();
			if (ingresoUsuario.equals("S")) {
				IngresoUsuario(scanner);
			}
			
			//scanner.close();	
		}
		else if (selection.equals("P")) {
			Profesor nuevoProfesor = crearUsuarioProfesor(scanner);
			System.out.println("Se ha creado el nuevo usuario:");
			System.out.println(nuevoProfesor.getNombre());
			System.out.println("profesor de la materia: ");
			System.out.println(nuevoProfesor.getMateria());
			System.out.println("Con Identificador:");
			System.out.println(nuevoProfesor.getIdUsuario());
			String  ingresoUsuario2 = scanner.next();
			if (ingresoUsuario2.equals("S")) {
				IngresoUsuario(scanner);
			}
			
		}
	}

	/* DEPRECTAED, USE NEW UserFACTORY
	
	private static Estudiante crearUsuarioEstudiante(Scanner scanner ) {
		String tipo = "estudiante";
		System.out.print("Nombre del estudiante: ");
		String nombre = scanner.nextLine();
		System.out.print("Email del estudiante: ");
		String email = scanner.nextLine();
		System.out.print("Contraseña del estudiante: ");
		String pass = scanner.nextLine();
		String materia = null;
		String fechaRegistro = (sistemaCentral.getCurrentDate()).toString();
		////scanner.close();
		Estudiante nuevoEstudiante = (Estudiante)sistemaCentral.crearUsuario(tipo, nombre, email, pass, fechaRegistro, materia);
		//scanner.close();
		return nuevoEstudiante;
	}


	private static Profesor crearUsuarioProfesor(Scanner scanner ) {
		System.out.print("Nombre del profesor: ");
		String tipo = "profesor";
		String nombre = scanner.next();
		System.out.print("Email del profesor: ");
		String email = scanner.next();
		System.out.print("Contraseña del profesor: hoal ");
		String pass = scanner.next();
		System.out.print("Materia del profesor: ");
		String materia = scanner.next();
		String fechaRegistro = (sistemaCentral.getCurrentDate()).toString();
		//scanner.close();
		Profesor nuevoProfesor = (Profesor)sistemaCentral.crearUsuario(tipo, nombre, email, pass, fechaRegistro, materia);
		return  nuevoProfesor;		
	}
	
	private static Usuario resetPassword(Scanner scanner, String Email){
		Usuario elUsuario = null;
		while(elUsuario == null)
		try {
		Usuario olvidadiso = sistemaCentral.grabUsuarioByEmail(Email);
		System.out.print("Ingrese su nuevo password: ");
		String newPassword = scanner.next();
		olvidadiso.setContrasenia(newPassword);
		//scanner.close();
		return olvidadiso;
		}
		catch (Exception e){
			System.out.println("Email no encontrado");
			return elUsuario;
		}
		return elUsuario;
	}

	*/
	
	public static void main (String[] args) {
		Scanner scannerUnico = new Scanner(System.in);
		System.out.println("Bienvenido");
		System.out.println("¿Tiene cuenta? | S -> Sí | N -> No");
		String  tiene_cuenta = scannerUnico.nextLine();
		if(tiene_cuenta.equals("S")){
			boolean persistLogIn = true;
			while (persistLogIn) {
			Usuario user = IngresoUsuario(scannerUnico);
			if (user.equals(null)){
				IngresoUsuario(scannerUnico);
			}
			else {
				persistLogIn = false;
				}
			}
		}
		else if (tiene_cuenta.equals("N")) {
			System.out.println("¿Desea crear una cuenta? | S -> Sí | N -> No");
			String crear_cuenta = scannerUnico.nextLine();
			if (crear_cuenta.equals("S")) {
				elegirCreacionUsuario(scannerUnico);
			} else if( crear_cuenta.equals("N")) {
			    System.out.println("Hasta luego");
				System.exit(0);
            }
		}
	}
}