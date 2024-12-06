package LPTH.exceptions;

//Custom exception for "Usuario No Encontrado"
public class ExceptionNoPersistencia extends Exception {
	
 public ExceptionNoPersistencia() {
     super("No existe o no se encuentra el archivo de persistencia");
 }

 public ExceptionNoPersistencia(String message) {
     super(message); // Allows custom error messages
 }

 public ExceptionNoPersistencia(String message, Throwable cause) {
     super(message, cause); // Allows chaining exceptions
 }
}
