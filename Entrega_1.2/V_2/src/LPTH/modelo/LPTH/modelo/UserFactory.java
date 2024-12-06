package LPTH.modelo;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
    private LinkedList<Profesor> profesores;
    private LinkedList<Estudiante> estudiantes;
    private Map<String, Usuario> dbUsuarios; //Correo y Usuario
    private Map<String, String> logIns; //Correo y Password
    
    public UserFactory() throws ExceptionNoPersistencia, IOException {
        this.profesores = new LinkedList<Profesor>();
        this.estudiantes = new LinkedList<Estudiante>();
        this.dbUsuarios = new HashMap<String, Usuario>();
        this.logIns = new HashMap<String, String>();
        this.sistemaCentral = cargarSistema();  // Intentar cargar el sistema central si existe, si no, empezar uno vacío
    }
    
    // Intentar cargar el sistema central si existe, si no, empezar uno vacío
    public Sistema cargarSistema() {
        PersistirSistema fileMngr = new PersistirSistema();
        Sistema sistemaCentral;
        try {
        	System.out.println("Cargando sistema...");
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
        PersistirUsuarios fileMngr = new PersistirUsuarios();
        try {
        	System.out.println("Cargando usuarios...3");
            this.profesores = fileMngr.cargarProfesores();
            this.estudiantes = fileMngr.cargarEstudiantes();
        	System.out.println("Cargando usuarios...4");
            for (Profesor profesor : profesores) {
                dbUsuarios.put(profesor.getEmail(), profesor);
                logIns.put(profesor.getEmail(), profesor.getContrasenia());
            }
            for (Estudiante estudiante : estudiantes) {
                dbUsuarios.put(estudiante.getEmail(), estudiante);
                logIns.put(estudiante.getEmail(), estudiante.getContrasenia());
            }
        } catch (IOException e) {
            throw new ExceptionNoPersistencia("No hay un archivo de usuarios", e);
        }
        return this;
    }
    
    public void saveUsuarios() throws ExceptionNoPersistencia {
        PersistirUsuarios fileMngr = new PersistirUsuarios(); 
        try {
            fileMngr.salvarProfesores(profesores);
            fileMngr.salvarEstudiantes(estudiantes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Usuario crearUsuario(String tipo, String nombre, String email, String contrasenia, String materia) {
        int idUsuario = (int) (Math.random() * 99999);
        String fechaRegistro = LocalDate.now().toString();

        if (tipo.equals("profesor")) {
            Profesor nuevoUsuario = new Profesor(idUsuario, nombre, email, contrasenia, fechaRegistro, materia);
            nuevoUsuario.setIdUsuario(idUsuario + nuevoUsuario.hashCode());
            logIns.put(email, contrasenia);
            dbUsuarios.put(email, nuevoUsuario);
            profesores.add(nuevoUsuario);
            nuevoUsuario.setSistema(sistemaCentral); // Si existe el objeto UserFactory tiene un sistemaCentral así esté vacío
            nuevoUsuario.LogInAtt(); // Regresa con la sesión iniciada
            return nuevoUsuario;
        } else {
            Estudiante nuevoUsuario = new Estudiante(idUsuario, nombre, email, contrasenia, fechaRegistro);   // Si se puede
            nuevoUsuario.setIdUsuario(idUsuario + nuevoUsuario.hashCode());
            logIns.put(email, contrasenia);
            dbUsuarios.put(email, nuevoUsuario);
            estudiantes.add(nuevoUsuario);
            nuevoUsuario.setSistema(sistemaCentral); // Si existe el objeto UserFactory tiene un sistemaCentral así esté vacío
            nuevoUsuario.LogInAtt(); // Regresa con la sesión iniciada
            return nuevoUsuario;    
        }
    }
    
    public Usuario autenticarUsuario(String email, String contrasenia) throws ExceptionUsuarioNoEncontrado {
        Usuario usuario = grabUsuarioByEmail(email);
        if (usuario == null) {
            throw new ExceptionUsuarioNoEncontrado();
        }
        if (usuario.getContrasenia().equals(contrasenia)) { 
            usuario.LogInAtt();
            return usuario;
        } else {
            return null;
        }
    }
 
    public LinkedList<Usuario> getUsuarios() {
        LinkedList<Usuario> usuarios = new LinkedList<>();
        usuarios.addAll(profesores);
        usuarios.addAll(estudiantes);
        return usuarios;
    }
    
    public Usuario grabUsuarioByEmail(String email) throws ExceptionUsuarioNoEncontrado {
        if (!dbUsuarios.containsKey(email))
            throw new ExceptionUsuarioNoEncontrado();
        return dbUsuarios.get(email);
    }
}
