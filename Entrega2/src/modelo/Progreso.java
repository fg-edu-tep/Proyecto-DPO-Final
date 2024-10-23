package modelo;
import java.util.HashMap;
import java.util.Map;
import java.util.Date;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;    

public class Progreso {
	// Se elimino porcActividadesExitosas =\equiv successRate
    private Map<LearningPath, Date> inicioLearningPaths = new HashMap<>();
    private Map<LearningPath, Date> finLearningPaths = new HashMap<>();
    private Map<Actividad, Integer> tiempoDedicado = new HashMap<>();
    private boolean learningInProgress;
    private float successRate;
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");   

    // Getters:
    
   public Progreso {
	   this.learningInProgress = false;
	   this.successRate = 0;
   }
    
    public Date getInicioLearningPath(LearningPath QueryLearningPath) {
        Date startDate = inicioLearningPaths.get(QueryLearningPath);
    	return startDate;
    }

    public Date getFinLearningPath(LearningPath QueryLearningPath) {
        Date endDate = finLearningPaths.get(QueryLearningPath);
    	return endDate;
    }
    
    public int getTiempoDedicado(Actividad QueryActividad) {
    	int dedicacion = tiempoDedicado.get(QueryActividad);
    	return dedicacion;
    }

    public boolean isLearning() {
    	return this.learningInProgress;
    }
    public float getSuccessRate() {
        return this.successRate;
    }
    
    //Setters
    public void addStartDate(Date fecha, LearningPath theLearningPath) {
    	inicioLearningPaths.put(theLearningPath, fecha);
    }
    
    public void addStartDate(LearningPath theLearningPath) {
    	/*Si no se da una fecha por param, se elige la fecha de hoy*/
	   LocalDateTime now = LocalDateTime.now();
    	Date hoy = dtf.format(now);
    	addStartDate(theLearningPath, hoy);
    }
    
    public void addEndDate(Date fecha, LearningPath theLearningPath) {
    	finLearningPaths.put(theLearningPath, fecha);
    }
    
    public void addEndDate(LearningPath theLearningPath) {
    	/*Si no se da una fecha por param, se elige la fecha de hoy*/
    	LocalDateTime now = LocalDateTime.now();
    	Date hoy = dtf.format(now);
    	addEndDate(theLearningPath, hoy);
    }
    
    public boolean toggleLearning() {
	    this.learningInProgress = !learningInProgress;
	    return learningInProgress;
    }
    
    public float setSuccess(float newValue) {
    	/*Retorna el valor anterior del success rate*/
    	float prevSuccess = this.successRate;
    	this.successRate = newValue;
    	return prevSuccess;
	}
    
    
}
