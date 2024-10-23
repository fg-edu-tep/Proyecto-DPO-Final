package LPTH.modelo;

import java.util.HashMap;
import java.util.Map;
import java.util.Date;  
import LPTH.actividades.Actividad;
import LPTH.modelo.learningPath;

public class Progreso {
	// Se elimino porcActividadesExitosas =\equiv successRate
    private Map<learningPath, Date> inicioLearningPaths = new HashMap<>();
    private Map<learningPath, Date> finLearningPaths = new HashMap<>();
    private Map<Actividad, Integer> tiempoDedicado = new HashMap<>();
    private boolean learningInProgress;
    private float successRate;

    // Getters:
    
   public Progreso (){
	   this.learningInProgress = false;
	   this.successRate = 0;
   }
    
    public Date getInicioLearningPath(learningPath QueryLearningPath) {
        Date startDate = inicioLearningPaths.get(QueryLearningPath);
    	return startDate;
    }

    public Date getFinLearningPath(learningPath QueryLearningPath) {
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
    public void addStartDate(Date fecha, learningPath theLearningPath) {
    	inicioLearningPaths.put(theLearningPath, fecha);
    }
    

    
    public void addEndDate(Date fecha, learningPath theLearningPath) {
    	finLearningPaths.put(theLearningPath, fecha);
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
