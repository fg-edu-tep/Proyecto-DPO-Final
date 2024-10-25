package LPTH.usuarios;

import java.util.ArrayList;
import java.util.List;

import LPTH.actividades.Actividad;
import LPTH.modelo.Progreso;

public class Estudiante extends Usuario{
    private Progreso progreso;
    private List<String> notificaciones;

    // Constructor
    public Estudiante(int idUsuario, String nombre, String email, String contraseña, String fechaRegistro, String tipo) {
        super(idUsuario, nombre, email, contraseña, fechaRegistro, tipo);
        this.progreso = new Progreso(); // Assuming Progreso is a separate class that needs to be defined
        this.notificaciones = new ArrayList<>();
    }

    // Methods specific to Estudiante
    public Actividad iniciarActividad() {
        // Implementation for starting an activity
        return new Actividad(); // Placeholder, assuming Actividad is a separate class that needs to be defined
    }

    public void cancelarActividad() {
        // Implementation for canceling an activity
    }

    public Actividad terminarActividad() {
        // Implementation for ending an activity
        return new Actividad(); // Placeholder
    }

    public void peekLearningPath() {
        // Implementation for checking current learning path
    }

    public void startLearningPath() {
        // Implementation for starting a learning path
    }

    public LearningPath checkLearningPath() {
        // Implementation for checking a learning path
        return new LearningPath(); // Placeholder, assuming LearningPath is a separate class
    }

    public Actividad checkActivity() {
        // Implementation for checking an activity
        return new Actividad(); // Placeholder
    }

    public void printNotificaciones() {
        // Implementation for printing notifications
        for (String notification : notificaciones) {
            System.out.println(notification);
        }
    }

    // Overridden methods from Usuario
    @Override
    public void crearReseña(String texto, int rating, Actividad actividad) {
        if (actividad == null) {
            System.out.println("La actividad no puede ser nula.");
            return;
        }
        if (rating < 1 || rating > 5) {
            System.out.println("La calificación debe estar entre 1 y 5.");
            return;
        }
        Resenia nuevaReseña = new Resenia(texto, rating);
        actividad.agregarResenia(nuevaReseña);
        System.out.println("Reseña creada exitosamente para la actividad: " + actividad.getTitulo());
    }

    @Override
    public void realizarLogIn(String email, String contrasenia) {
        if (email == null || email.isEmpty()) {
            System.out.println("El correo electrónico no puede estar vacío.");
            return;
        }
        if (contrasenia == null || contrasenia.isEmpty()) {
            System.out.println("La contraseña no puede estar vacía.");
            return;
        }
        boolean autenticado = autenticarUsuario(email, contrasenia);
    
        if (autenticado) {
            System.out.println("LogIn exitoso. Bienvenido, " + getNombre() + "!");
            cargarProgreso();
        } else {
            System.out.println("Error de LogIn. Verifique sus credenciales.");
        }
    }
    private boolean autenticarUsuario(String email, String contrasenia) {
        return email.equals(this.getEmail()) && contrasenia.equals(this.getContrasenia());
    }
    
    private void cargarProgreso() {
        System.out.println("Cargando el progreso del estudiante...");
        //  Implementacion para recuperar el progreso real del estudiante
    }

    // Getters and Setters
    public Progreso getProgreso() {
        return progreso;
    }

    public void setProgreso(Progreso progreso) {
        this.progreso = progreso;
    }

    public List<String> getNotificaciones() {
        return notificaciones;
    }

    public void setNotificaciones(List<String> notificaciones) {
        this.notificaciones = notificaciones;
    }
    
}
