package LPTH.usuarios;
import LPTH.modelo.Sistema;
import LPTH.modelo.learningPath;
import LPTH.actividades.Actividad;

public class Profesor extends Usuario{
    private String materia;

    
    public Profesor(Sistema sistemaCentral, int idUsuario, String nombre, String email, String contraseña, String fechaRegistro, String tipo, String materia) {
    	super(sistemaCentral, idUsuario, nombre, email, contraseña, fechaRegistro, tipo);
        this.materia = materia;
    }


	public learningPath crearlearningPath(String nombre, String descripcion) {

    }

    public learningPath editarlearningPath(learningPath learningPath, String nuevoNombre, String nuevaDescripcion) {

        return learningPath;
    }

    public Actividad crearActividad(String titulo, String descripcion, boolean esObligatoria) {
        return nuevaActividad;
    }

    public Actividad editarActividad(Actividad actividad, String nuevoTitulo, String nuevaDescripcion) {

    }

    // Método para marcar una Actividad como obligatoria
    public void marcarActividadObligatoria(Actividad actividad) {
    }

    
  
    
}
