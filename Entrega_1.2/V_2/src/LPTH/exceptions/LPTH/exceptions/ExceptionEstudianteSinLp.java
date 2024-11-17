package LPTH.exceptions;

//Custom exception for "Usuario No Encontrado"
public class ExceptionEstudianteSinLp extends Exception {
	
 public ExceptionEstudianteSinLp() {
     super("El estudiante no tiene un learning path en este momento, debe empezar uno antes");
 }

 public ExceptionEstudianteSinLp(String message) {
     super(message); // Allows custom error messages
 }

 public ExceptionEstudianteSinLp(String message, Throwable cause) {
     super(message, cause); // Allows chaining exceptions
 }
}
