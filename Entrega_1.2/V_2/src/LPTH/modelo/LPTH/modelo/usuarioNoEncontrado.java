package LPTH.modelo;

//Custom exception for "Usuario No Encontrado"
public class usuarioNoEncontrado extends Exception {
	
 public usuarioNoEncontrado() {
     super("El usuario no existe o el LogIn fue equivocado");
 }

 public usuarioNoEncontrado(String message) {
     super(message); // Allows custom error messages
 }

 public usuarioNoEncontrado(String message, Throwable cause) {
     super(message, cause); // Allows chaining exceptions
 }
}
