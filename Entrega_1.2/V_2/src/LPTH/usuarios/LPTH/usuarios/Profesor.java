package LPTH.usuarios;

import LPTH.actividades.Actividad;

public class Profesor extends Usuario{
    private String materia;

    
    public Profesor(int idUsuario, String nombre, String email, String contraseña, String fechaRegistro, String tipo, String materia) {
        super(idUsuario, nombre, email, contraseña, fechaRegistro, tipo);
        this.materia = materia;
    }

    public LearningPath crearLearningPath(String nombre, String descripcion) {
        if (nombre == null || nombre.isEmpty()) {
            System.out.println("El nombre del Learning Path no puede estar vacío.");
            return null;
        }
        if (descripcion == null || descripcion.isEmpty()) {
            System.out.println("La descripción del Learning Path no puede estar vacía.");
            return null;
        }

        LearningPath nuevoLearningPath = new LearningPath(nombre, descripcion);
        System.out.println("Learning Path creado exitosamente: " + nombre);
        return nuevoLearningPath;
    }

    // Método para editar un Learning Path existente
    public LearningPath editarLearningPath(LearningPath learningPath, String nuevoNombre, String nuevaDescripcion) {
        if (learningPath == null) {
            System.out.println("El Learning Path no puede ser nulo.");
            return null;
        }
        if (nuevoNombre != null && !nuevoNombre.isEmpty()) {
            learningPath.setNombre(nuevoNombre);
        }
        if (nuevaDescripcion != null && !nuevaDescripcion.isEmpty()) {
            learningPath.setDescripcion(nuevaDescripcion);
        }
        System.out.println("Learning Path editado exitosamente.");
        return learningPath;
    }

    // Método para crear una nueva Actividad
    public Actividad crearActividad(String titulo, String descripcion, boolean esObligatoria) {
        if (titulo == null || titulo.isEmpty()) {
            System.out.println("El título de la actividad no puede estar vacío.");
            return null;
        }
        if (descripcion == null || descripcion.isEmpty()) {
            System.out.println("La descripción de la actividad no puede estar vacía.");
            return null;
        }

        Actividad nuevaActividad = new Actividad(titulo, descripcion, esObligatoria);
        System.out.println("Actividad creada exitosamente: " + titulo);
        return nuevaActividad;
    }

    // Método para editar una Actividad existente
    public Actividad editarActividad(Actividad actividad, String nuevoTitulo, String nuevaDescripcion) {
        if (actividad == null) {
            System.out.println("La actividad no puede ser nula.");
            return null;
        }
        if (nuevoTitulo != null && !nuevoTitulo.isEmpty()) {
            actividad.setTitulo(nuevoTitulo);
        }
        if (nuevaDescripcion != null && !nuevaDescripcion.isEmpty()) {
            actividad.setDescripcion(nuevaDescripcion);
        }
        System.out.println("Actividad editada exitosamente.");
        return actividad;
    }

    // Método para marcar una Actividad como obligatoria
    public void marcarActividadObligatoria(Actividad actividad) {
        if (actividad == null) {
            System.out.println("La actividad no puede ser nula.");
            return;
        }
        actividad.setEsObligatoria(true);
        System.out.println("La actividad ha sido marcada como obligatoria.");
    }

    // Overridden methods from Usuario
    
    @Override
    public void crearResenia(String texto, int rating, Actividad actividad) {
        if (actividad == null) {
            System.out.println("La actividad no puede ser nula.");
            return;
        }
        if (rating < 1 || rating > 5) {
            System.out.println("La calificacion debe estar entre 1 y 5.");
            return;
        }
        Resenia nuevaResenia = new Resenia(texto, rating);
        actividad.agregarResenia(nuevaResenia);
        agregarResenia(nuevaResenia);
        System.out.println("Resenia creada exitosamente para la actividad: " + actividad.getNombre());
    }

    @Override
    public void modificarRating(Resenia resenia, int nuevoRating) {
        if (resenia == null) {
            System.out.println("La resenia no puede ser nula.");
            return;
        }
        if (nuevoRating < 1 || nuevoRating > 5) {
            System.out.println("El nuevo rating debe estar entre 1 y 5.");
            return;
        }
        resenia.modificarRating(nuevoRating);
        System.out.println("El rating de la resenia ha sido modificado a: " + nuevoRating);
    }

    @Override
    public void realizarLogIn(String email, String contrasenia) {
        if (email == null || email.isEmpty()) {
            System.out.println("El correo electrónico no puede estar vacío.");
            return;
        }
    
        if (contrasenia == null || contrasenia.isEmpty()) {
            System.out.println("La contraseña no puede estar vacía.");
            return;
        }
        boolean autenticado = autenticarUsuario(email, contrasenia);
    
        if (autenticado) {
            System.out.println("LogIn exitoso. Bienvenido, " + getNombre() + "!");
        } else {
            System.out.println("Error de LogIn. Verifique sus credenciales.");
        }
    }
    
TODO // Verificar método para autenticar simple
    private boolean autenticarUsuario(String email, String contrasenia) {
        return email.equals(this.getEmail()) && contrasenia.equals(this.getContrasenia());
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }
    
}
