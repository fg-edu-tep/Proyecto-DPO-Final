package LPTH.usuarios;
import LPTH.modelo.Sistema;
import LPTH.modelo.learningPath;
import LPTH.actividades.Actividad;

public class Profesor extends Usuario{
    private String materia;
    private static final String tipo = "Profesor";
    
    public Profesor(Sistema sistemaCentral, int idUsuario, String nombre, String email, String contraseña, String fechaRegistro, String materia) {
    	super(sistemaCentral, idUsuario, nombre, email, contraseña, fechaRegistro, tipo);
        this.materia = materia;
    }


	public learningPath crearlearningPath(String titulo, String descripcion, String nivelDeDificultad, int duracion) {
		learningPath myNewLp = sistemaCentral.crearLearningPath(this, titulo, descripcion, nivelDeDificultad, duracion);
		return myNewLp;
    }

    public learningPath editarlearningPath(learningPath learningPath, String nuevoNombre, String nuevaDescripcion) {	
    	learningPath.editarTituloDesc(nuevoNombre, nuevaDescripcion);
    	return learningPath;
    }

    public Actividad crearyAgregarQuiz(learningPath learningPath, String tipo) { // Tiene que tener las entradas por cada tipo de <Actividad>
    	/* Crea un nuevo <Actividad> y lo agrega al LP - No se pudo completar debido a que no se completó
    	 * ni se pudo establecer la creación de preguntas individuales, lo cual hace casacada a los tipos de actividad.
    	 * 
    	 * Este se debe crear en base a un tipo de actividad particular (Quiz, examen, encuesta, etc)pues las actividades entre sí tiene
    	 *  atributos distintos.*/
    	
        return null;
    }

    public Actividad editarActividad(Actividad actividad, String nuevoTitulo, String nuevaDescripcion) {
    	actividad.setNombre(nuevoTitulo);
    	actividad.setDescripcion(nuevaDescripcion);
    	/*Sólo se pueden implementar estos pues son generales a la clase abstracta actividad, el editar preguntas, etc se debe a 
    	 * la misma razón de no haber podido definir la creación de preguntas particulares para una actividad (pues se requiere una dimensión n
    	 * con campos personalizados)*/ 
    	return actividad;
    }

    // Método para marcar una Actividad como obligatoria
    public void marcarActividadObligatoria(Actividad actividad) {
    	actividad.marcarObligatoria();
    }

    
  
    
}
