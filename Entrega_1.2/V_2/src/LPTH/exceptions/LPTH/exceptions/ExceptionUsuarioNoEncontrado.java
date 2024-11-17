package LPTH.exceptions;

//Custom exception for "Usuario No Encontrado"
public class ExceptionUsuarioNoEncontrado extends Exception {
	
 public ExceptionUsuarioNoEncontrado() {
     super("El usuario no existe o el LogIn fue equivocado");
 }

 public ExceptionUsuarioNoEncontrado(String message) {
     super(message); // Allows custom error messages
 }

 public ExceptionUsuarioNoEncontrado(String message, Throwable cause) {
     super(message, cause); // Allows chaining exceptions
 }
}
