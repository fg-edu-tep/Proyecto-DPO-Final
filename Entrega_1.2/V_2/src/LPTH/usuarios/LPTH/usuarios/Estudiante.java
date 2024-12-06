package LPTH.usuarios;

import java.util.ArrayList;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.time.Duration;
import java.util.List;

import LPTH.actividades.Actividad;
import LPTH.exceptions.ExceptionEstudianteSinLp;
import LPTH.modelo.Progreso;
import LPTH.modelo.Sistema;
import LPTH.modelo.LearningPath;

public class Estudiante extends Usuario{
    private Progreso progreso;
    private List<String> notificaciones;
    private String nombreLPActual = "None";
    private int idNuevoLP = 000;
    private static final String tipo = "Estudiante";
    private Sistema sistemaCentral = null;


    // Constructor
    public Estudiante(int idUsuario, String nombre, String email, String contraseña, String fechaRegistro) {
    	super(idUsuario, nombre, email, contraseña, fechaRegistro, tipo);
        this.progreso = new Progreso();
        this.notificaciones = new ArrayList<>();
    }

	public void setSistema(Sistema sistemaCentral) {
		this.sistemaCentral = sistemaCentral;
	}
	
    public void iniciarActividad(Actividad miActividad) {
        miActividad.empezarActividad(); 
    }

    public void cancelarActividad(Actividad miActividad) {
		miActividad.setCompletada(false);
    }
    
    public LearningPath peekLearningPath() throws ExceptionEstudianteSinLp {
    	/*Revisar el learning path acual (Retorna un learning Path)*/
    	LearningPath myLP = null;
    	if (idNuevoLP == 000){
    		throw new ExceptionEstudianteSinLp();
    	}
    	else {
        myLP = sistemaCentral.getLearningPath(idNuevoLP);
    	}
        return myLP;
    }
  
    public ArrayList<String> peekLearningPathInfo() throws ExceptionEstudianteSinLp{
    	/*Revisar info de un LearningPath actual [Titulo, descripción]*/
        LearningPath myLP = peekLearningPath();
        ArrayList<String> attributos = new ArrayList<String>();
        attributos.add(myLP.getTitulo());
        attributos.add(myLP.getDescripcion());
        return attributos;
    }

    public void startLearningPath(LearningPath selectedLp) {
    	/*Agrega el LP seleccionado y lo agrega a progreso*/
    	nombreLPActual = selectedLp.getTitulo();
    	idNuevoLP = selectedLp.getID(); // TODO Arregrar toda la lógica de acceso para que se haga con el DI
    	Instant now = Instant.now();
    	Date actual = Date.from(now);
    	progreso.addStartDate(actual, selectedLp);
    	}
    
    public void endLearningPath(LearningPath selectedLp) {
    	/*Agrega el LP seleccionado y lo agrega a progreso*/
    	nombreLPActual = selectedLp.getTitulo();
    	Instant now = Instant.now();
    	Date actual = Date.from(now);
    	progreso.addEndDate(actual, selectedLp);
    	}
    
	public void terminarActividad(Actividad myActividad) {
		this.progreso.agregarTiempoActividad(myActividad);
	}

    
    public void removeLearningPath() {
    	/*Elimina el LP y lo quita de progreso*/
    	LearningPath mylearningPath;
		try {
			mylearningPath = peekLearningPath();
	    	progreso.removeStartDate(mylearningPath);
	    	nombreLPActual = "None";
		} catch (ExceptionEstudianteSinLp e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public Progreso getProgreso() {
    	return this.progreso;
    }
    
    public String getLearningPathActual() {
    	return this.nombreLPActual;
    }

    public ArrayList<Actividad> checkLearningPath(LearningPath selectedLp) {
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
    
    public List<String> getNotificaciones() throws ExceptionEstudianteSinLp{
    	/*Learning path es el responsable de obtener las notificaciones, 
    	 * este agrega a la lista*/ 
    	notificaciones.clear();
    	peekLearningPath().notificarEstudianteDue(this);
    	return this.notificaciones;
    }

}
