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
import LPTH.modelo.Sistema;


public class Console {
	private Sistema sistemaCentral;
	
	public Console() {
		sistemaCentral = new Sistema(); // Este debe ser la UNICA instancia de sistema a ser usada
	}
	
	
	/*Métodos de autenticación y creacion de usuario*/
	
	public void elegirCreacionUsuario() {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Elija el tipo de usuario a crear | P -> Profesor | E -> Estudiante");
		String selection = scanner.next();
		scanner.close();
		if(selection == "E") {
			Estudiante nuevoEstudiante = crearUsuarioEstudiante();
			System.out.print("Se ha creado el nuevo usuario:");
			System.out.print(nuevoEstudiante.getNombre());
			System.out.print("Con Identificador:");
			System.out.print(nuevoEstudiante.getIdUsuario());
			
		}
		else if (selection == "P") {
			Profesor nuevoProfesor = crearUsuarioProfesor();
			System.out.print("Se ha creado el nuevo usuario:");
			System.out.print(nuevoProfesor.getNombre());
			System.out.print("profesor de la materia:");
			System.out.print(nuevoProfesor.getMateria());
			System.out.print("Con Identificador:");
			System.out.print(nuevoProfesor.getIdUsuario());
		}
	}


	private Estudiante crearUsuarioEstudiante() {
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
		scanner.close();
		Estudiante nuevoEstudiante = (Estudiante)sistemaCentral.crearUsuario(tipo, nombre, email, pass, fechaRegistro, materia);
		return nuevoEstudiante;
	}


	private Profesor crearUsuarioProfesor() {
		Scanner scanner = new Scanner(System.in);
		String tipo = "profesor";
		System.out.print("Nombre del profesor: ");
		String nombre = scanner.next();
		System.out.print("Email del profesor: ");
		String email = scanner.next();
		System.out.print("Contraseña del profesor: ");
		String pass = scanner.next();
		System.out.print("Materia del profesor: ");
		String materia = scanner.next();
		String fechaRegistro = (sistemaCentral.getCurrentDate()).toString();
		scanner.close();
		Profesor nuevoEstudiante = (Profesor)sistemaCentral.crearUsuario(tipo, nombre, email, pass, fechaRegistro, materia);
		return nuevoEstudiante;		
	}
	
	private Usuario resetPassword(String Email){
		Usuario olvidadiso = sistemaCentral.grabUsuarioByEmail(Email);
		Scanner scanner = new Scanner(System.in);
		System.out.print("Ingrese su nuevo password: ");
		String newPassword = scanner.next();
		olvidadiso.setContrasenia(newPassword);
		scanner.close();
		return olvidadiso;
	}


	private Usuario realizarLogIn() {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Ingrese Usuario: ");
		String usurio = scanner.next();
		System.out.print("Ingrese su contraseña: ");
		String password = scanner.next();
		scanner.close();
		return sistemaCentral.realizarLogin(usurio, password);
	}
}
