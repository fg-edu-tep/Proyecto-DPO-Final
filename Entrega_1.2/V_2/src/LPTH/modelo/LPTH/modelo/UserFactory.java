package LPTH.modelo;

import java.io.IOException;
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
import LPTH.actividades.Actividad;
import LPTH.exceptions.ExceptionNoPersistencia;
import LPTH.exceptions.ExceptionUsuarioNoEncontrado;
import LPTH.modelo.*;
import LPTH.usuarios.Estudiante;
import LPTH.usuarios.Profesor;
import LPTH.usuarios.Resenia;
import LPTH.usuarios.Usuario;
import LPTH.persistencia.*;


public class UserFactory {
	private Sistema sistemaCentral = null;
	private LinkedList<Usuario> usuarios;
	private Map<String, Usuario> dbUsuarios; //Correo y Usuario
	private Map<String, String> logIns; //Correo y Password
    private Random rand = new Random();    

	
    public UserFactory() throws ExceptionNoPersistencia, IOException {
        this.usuarios = new LinkedList<Usuario>();
        this.dbUsuarios = new HashMap<String, Usuario>();
        this.logIns = new HashMap<String, String>();
        this.sistemaCentral = cargarSistema();  // Intentar cargar el sistema central si existe, si no, empezar uno vacío

    }
    
    // Intentar cargar el sistema central si existe, si no, empezar uno vacío
    public Sistema cargarSistema() {
        PersistirSistema fileMngr = new PersistirSistema();
        Sistema sistemaCentral;
        try {
            sistemaCentral = fileMngr.cargarSistema();
            if (sistemaCentral == null) {
                return new Sistema(); 
            }
        } catch (IOException e) {
            return new Sistema(); 
        }
        return sistemaCentral; // Por sus santos huevos regresa un sistema sin importar qué
    }
    
    public UserFactory loadUsuarios() throws ExceptionNoPersistencia {
    	/*Si existe permite sustituir el nuevo objeto de UserFactory por el cargado usando:
    	 *UserFactory factoria = new UserFactory();
		 *factoria = factoria.loadUsuarios(); 
		*/
        PersistirUsuarios fileMngr = new PersistirUsuarios();
        UserFactory usuariosSistema;
        try {
            usuariosSistema = fileMngr.cargarSistema();
            if (usuariosSistema == null) {
                throw new ExceptionNoPersistencia("Archivo de userFactory está vacío");
            }
        } catch (IOException e) {
            throw new ExceptionNoPersistencia("No hay un archivo de userFactory", e);
        }
        return usuariosSistema;
    }
    
    public void saveUsuarios() throws ExceptionNoPersistencia {
    	PersistirUsuarios fileMngr = new PersistirUsuarios(); 
    	try {
			fileMngr.salvarSistema(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    public Usuario crearUsuario(String tipo, String nombre, String email, String contrasenia, String fechaRegistro, String materia) {
    	int idUsuario = rand.nextInt(99999);
    	if (tipo == "profesor") {
        	Profesor nuevoUsuario = new Profesor(idUsuario,nombre,email,contrasenia,fechaRegistro, materia);
        	nuevoUsuario.setIdUsuario(idUsuario + nuevoUsuario.hashCode());
        	logIns.put(email, contrasenia);
        	dbUsuarios.put(email, nuevoUsuario);
        	usuarios.add(nuevoUsuario);
        	nuevoUsuario.setSistema(sistemaCentral); // Si existe el objeto UserFactory tiene un sistemaCenteral así esté vaío
        	nuevoUsuario.LogInAtt(); // Regresa con la sesión iniciada
        	return nuevoUsuario;
    	}
    	else {
        	Estudiante nuevoUsuario = new Estudiante(idUsuario,nombre,email,contrasenia,fechaRegistro);   // Sie se puede
        	nuevoUsuario.setIdUsuario(idUsuario + nuevoUsuario.hashCode());
        	logIns.put(email, contrasenia);
        	dbUsuarios.put(email, nuevoUsuario);
        	usuarios.add(nuevoUsuario);
        	nuevoUsuario.setSistema(sistemaCentral); // Si existe el objeto UserFactory tiene un sistemaCenteral así esté vaío
        	nuevoUsuario.LogInAtt(); // Regresa con la sesión iniciada
        	return nuevoUsuario;	
    		}
    	}
    
    
    public Usuario autenticarUsuario(String email, String contrasenia) throws ExceptionUsuarioNoEncontrado{
        Usuario usuario = grabUsuarioByEmail(email);
        if (usuario.equals(null)) {
        	throw new ExceptionUsuarioNoEncontrado();
        }
        if (usuario.getContrasenia().equals(contrasenia)) { 
        	usuario.LogInAtt();
        return usuario;
        }
        else {
        	return null;
        }
    }
 
    public LinkedList<Usuario> getUsuarios() {
        return usuarios;
    }
    
    public Usuario grabUsuarioByEmail(String email) throws ExceptionUsuarioNoEncontrado{
    	if (!dbUsuarios.containsKey(email))
    		throw new  ExceptionUsuarioNoEncontrado();
    	return dbUsuarios.get(email);
    }
}
