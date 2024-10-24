package LPTH.actividades;
import java.util.ArrayList;
import java.util.Date;

public class Tarea extends Actividad{

	private boolean isDone;
	
	
    @Override
    public void calificarActividad() {
        // TODO Auto-generated method stub

    }

    @Override
    public String notificarEstudiante() {
        return("Debe realizar su Tarea!");

    }
    
    public void isitDone() {  //revisar q es, asume q es enviada
        if(esCompletada== true) {
            isDone= true;
        }
     }
}