package LPTH.usuarios;

import java.util.ArrayList;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
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
    	Instant now = Instant.now();
    	Date actual = Date.from(now);
    	progreso.addStartDate(actual, selectedLp);
    	}
    
    public void removeLearningPath() {
    	learningPath mylearningPath = sistemaCentral.getLearningPath(nombreLPActual);
    	progreso.removeStartDate(mylearningPath);
    	nombreLPActual = "None";
    }

    public ArrayList<Actividad> checkLearningPath(learningPath selectedLp) {
    	/*Retorna las actividades que no se han completado*/
        return selectedLp.getActividadesSinCompletar();
    }

    public String checkActivity(Actividad laActividad) {
    	/*Permite inspeccionar la descripción de una actividad*/
        return laActividad.getDescripcion();
    }

    public List<String> getNotificaciones(){
    	
    	return this.notificaciones;
    }

}
