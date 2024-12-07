package LPTH.usuarios;
import LPTH.modelo.Sistema;
import LPTH.modelo.LearningPath;
import LPTH.actividades.Actividad;
import LPTH.actividades.Quiz;
import java.util.ArrayList;

public class Profesor extends Usuario{
    private String materia;
    private static final String tipo = "Profesor";
    private ArrayList<Integer> lps = new ArrayList<Integer>();
    private Sistema sistemaCentral = null;
    
    //Todos los metodos que requieran learningPath usan metodo de sistema.getLP
    
    public Profesor(int idUsuario, String nombre, String email, String contraseña, String fechaRegistro, String materia) {
    	super(idUsuario, nombre, email, contraseña, fechaRegistro, tipo);
        this.materia = materia;
    }

	public void setSistema(Sistema sistemaCentral) {
		this.sistemaCentral = sistemaCentral;
	}
	
    public String getMateria() {
    	return this.materia;
    }
    
    public ArrayList<Integer> getLps(){
        return this.lps;
    }

    public void setLps(ArrayList<Integer> newlps){
        this.lps= newlps;
    }
    
    public void agregarLp(Integer lp) {
	    this.lps.add(lp);
	}
	
    public LearningPath editarlearningPath(LearningPath LearningPath, String nuevoNombre, String nuevaDescripcion) {	
    	LearningPath.editarTituloDesc(nuevoNombre, nuevaDescripcion);
    	return LearningPath;
    }

    public Actividad AgregarQuiz(LearningPath specificLP, Quiz ActividadNueva) {
    	/* Recibe un objeto actividad creada y lo agrega al LearningPath*/
    	specificLP.agregarActividad(ActividadNueva);
        return ActividadNueva;
    }

    public Actividad editarActividad(Actividad actividad, String nuevoTitulo, String nuevaDescripcion) {
    	/* Cambia el nombre y la descripción de una actividad*/
    	actividad.setNombre(nuevoTitulo);
    	actividad.setDescripcion(nuevaDescripcion);
    	return actividad;
    }

    // Método para marcar una Actividad como obligatoria
    public void marcarActividadObligatoria(Actividad actividad) {
    	actividad.marcarObligatoria();
    }

  
    
}
