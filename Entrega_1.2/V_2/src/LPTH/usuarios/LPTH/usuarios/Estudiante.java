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
    
    public learningPath peekLearningPath() {
    	/*Revisar el learning path acual (Retorna un learning Path)*/
        learningPath myLP = sistemaCentral.getLearningPath(nombreLPActual);
        return myLP;
    }
  
    public ArrayList<String> peekLearningPathInfo() {
    	/*Revisar info de un LearningPath actual [Titulo, descripción]*/
        learningPath myLP = peekLearningPath();
        ArrayList<String> attributos = new ArrayList<String>();
        attributos.add(myLP.getTitulo());
        attributos.add(myLP.getDescripcion());
        return attributos;
    }

    public void startLearningPath(learningPath selectedLp) {
    	/*Agrega el LP seleccionado y lo agrega a progreso*/
    	nombreLPActual = selectedLp.getTitulo();
    	Instant now = Instant.now();
    	Date actual = Date.from(now);
    	progreso.addStartDate(actual, selectedLp);
    	}
    
    public void removeLearningPath() {
    	/*Elimina el LP y lo quita de progreso*/
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

    public List<String>agregarNotificacion(String tituloActividad){
    	/*Learnign path usa este método para agregar notificaciones en rango*/
    	this.notificaciones.add(tituloActividad);
    	return this.notificaciones;
    }
    
    public List<String> getNotificaciones(){
    	/*Learning path es el responsable de obtener las notificaciones, 
    	 * este agrega a la lista*/ 
    	notificaciones.clear();
    	peekLearningPath().notificarEstudianteDue(this);
    	return this.notificaciones;
    }

}
