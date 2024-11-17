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
import LPTH.exceptions.ExceptionUsuarioNoEncontrado;
import LPTH.modelo.*;
import LPTH.usuarios.Estudiante;
import LPTH.usuarios.Profesor;
import LPTH.usuarios.Resenia;
import LPTH.usuarios.Usuario;
import LPTH.persistencia.*;





public class Sistema {
    private Map<String, String> logIns; //Correo y Password
    private Map<String, LearningPath> LearningPaths; 
    private LinkedList<Usuario> usuarios;
    private Map<String, Usuario> dbUsuarios; //Correo y Usuario
    private Random rand = new Random();    


    public Sistema() {
        this.logIns = new HashMap<String, String>();
        this.LearningPaths = new HashMap<String, LearningPath>();
        this.usuarios = new LinkedList<Usuario>();
        this.dbUsuarios = new HashMap<String, Usuario>();
    }
    
    public Usuario crearUsuario(String tipo, String nombre, String email, String contrasenia, String fechaRegistro, String materia) {
    	int idUsuario = rand.nextInt(99999);
    	if (tipo == "profesor") {
        	Profesor nuevoUsuario = new Profesor(this, idUsuario,nombre,email,contrasenia,fechaRegistro, materia);
        	nuevoUsuario.setIdUsuario(idUsuario + nuevoUsuario.hashCode());
        	logIns.put(email, contrasenia);
        	dbUsuarios.put(email, nuevoUsuario);
        	usuarios.add(nuevoUsuario);
        	nuevoUsuario.LogInAtt(); // Regresa con la sesión iniciada
        	return nuevoUsuario;
    	}
    	else {
        	Estudiante nuevoUsuario = new Estudiante(this,idUsuario,nombre,email,contrasenia,fechaRegistro);   // Sie se puede
        	nuevoUsuario.setIdUsuario(idUsuario + nuevoUsuario.hashCode());
        	logIns.put(email, contrasenia);
        	dbUsuarios.put(email, nuevoUsuario);
        	usuarios.add(nuevoUsuario);
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
 
    
    public Usuario grabUsuarioByEmail(String email) throws ExceptionUsuarioNoEncontrado{
    	if (!dbUsuarios.containsKey(email))
    		throw new  ExceptionUsuarioNoEncontrado();
    	return dbUsuarios.get(email);
    }

    // Métodos de conexión:
    public LearningPath crearLearningPath(Profesor profe, String titulo, String descripcion,
    		String nivelDeDificultad, int duracion){
    	String profesorCreador = profe.getNombre();
    	int id = rand.nextInt(999999);
    	Date fechaDeCreacion = getCurrentDate();
    	Date fechaDeModificacion = getCurrentDate();
    	String version = "1";
    	int rating = 5;
    	int tasaDeExitoFracaso = 0;
    	ArrayList<Actividad> actividades = new ArrayList<Actividad>();
    	ArrayList<String> objetivos = new ArrayList<String>();
    	
    	
    	LearningPath originates = new LearningPath(profesorCreador, titulo, id, descripcion, objetivos,
                nivelDeDificultad, duracion, rating, fechaDeCreacion, fechaDeModificacion,
                version, tasaDeExitoFracaso,actividades);
    	this.LearningPaths.put(titulo, originates);
    	return originates;
    }
    
    public Date getCurrentDate() {
    	System.out.print("true");
    	Instant now = Instant.now(); 
    	Date today = Date.from(now);
    	return today;
    }
    
    public ArrayList<LearningPath> getLearningPaths() {
        return new ArrayList<>(LearningPaths.values()); // Retornar los valores en una lista
    }
    
    public LearningPath getLearningPath(String name) {
        return LearningPaths.get(name);
    }


    public LinkedList<Usuario> getUsuarios() {
        return usuarios;
    }

    public LearningPath recomendarLearningPath() {
    	ArrayList<LearningPath> Lp = getLearningPaths();
    	int recommend = rand.nextInt(Lp.size());
        return Lp.get(recommend);
    }
    
    
        
        
        
        
    
    
    
        	
    	

	



    
}
