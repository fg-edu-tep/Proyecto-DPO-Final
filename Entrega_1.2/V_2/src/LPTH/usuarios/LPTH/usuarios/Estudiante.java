package LPTH.usuarios;

import java.util.ArrayList;
import java.util.List;

import LPTH.actividades.Actividad;
import LPTH.modelo.Progreso;
import LPTH.modelo.Sistema;
import LPTH.modelo.learningPath;

public class Estudiante extends Usuario{
    private Progreso progreso;
    private List<String> notificaciones;
    private String nombreLPActual;

    // Constructor
    public Estudiante(Sistema sistemaCentral, int idUsuario, String nombre, String email, String contraseña, String fechaRegistro, String tipo) {
    	super(sistemaCentral, idUsuario, nombre, email, contraseña, fechaRegistro, tipo);
        this.progreso = new Progreso();
        this.notificaciones = new ArrayList<>();
    }

    public void iniciarActividad(Actividad miActividad) {
    	miActividad.empezarActividad();	
    }

    public void cancelarActividad(Actividad miActividad) {
		miActividad.setCompletada(false);
    }
    
    public void peekLearningPath() {
        sistemaCentral.getLearningPath(nombreLPActual);
    }

    public void startLearningPath(learningPath selectedLp) {
    	nombreLPActual = selectedLp.getTitulo();
    }
    
    public void removeLearningPath() {
    	nombreLPActual = "None";
    }

    public ArrayList<Actividad> checkLearningPath(learningPath selectedLp) {
        return selectedLp.getActividades();
    }

    public String checkActivity(Actividad laActividad) {
        return laActividad.getDescripcion();
    }

    public List<String> getNotificaciones(){
    	return this.notificaciones;
    }

}
