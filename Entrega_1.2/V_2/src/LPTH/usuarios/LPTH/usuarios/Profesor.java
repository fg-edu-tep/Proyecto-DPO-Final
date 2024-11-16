package LPTH.usuarios;
import LPTH.modelo.Sistema;
import LPTH.modelo.learningPath;
import LPTH.actividades.Actividad;
import LPTH.actividades.Quiz;

public class Profesor extends Usuario{
    private String materia;
    private static final String tipo = "Profesor";
    
    public Profesor(Sistema sistemaCentral, int idUsuario, String nombre, String email, String contraseña, String fechaRegistro, String materia) {
    	super(sistemaCentral, idUsuario, nombre, email, contraseña, fechaRegistro, tipo);
        this.materia = materia;
    }

	public String getTipo() {
		return this.tipo;
	}

    public String getMateria() {
    	return this.materia;
    }
    
	public learningPath crearlearningPath(String titulo, String descripcion, String nivelDeDificultad, int duracion) {
		learningPath myNewLp = sistemaCentral.crearLearningPath(this, titulo, descripcion, nivelDeDificultad, duracion);
		return myNewLp;
    }

    public learningPath editarlearningPath(learningPath learningPath, String nuevoNombre, String nuevaDescripcion) {	
    	learningPath.editarTituloDesc(nuevoNombre, nuevaDescripcion);
    	return learningPath;
    }

    public Actividad AgregarQuiz(learningPath specificLP, Quiz ActividadNueva) {
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
