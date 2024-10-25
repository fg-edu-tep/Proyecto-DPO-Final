package LPTH.usuarios;
import LPTH.modelo.learningPath;
import LPTH.actividades.Actividad;
import LPTH.usuarios.Resenia;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class Usuario {
    private int idUsuario;
    private String nombre;
    private String email;
    private String contraseña;
    private String fechaRegistro;
    private String tipo;
    private ArrayList<Resenia> resenias;
    private ArrayList<learningPath> learningPaths;

    public Usuario(int idUsuario, String nombre, String email, String contraseña, String fechaRegistro, String tipo) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.email = email;
        this.contraseña = contraseña;
        this.fechaRegistro = fechaRegistro;
        this.tipo = tipo;
        this.resenias = new ArrayList<>();
        this.learningPaths = new ArrayList<>();
    }

    public abstract void crearReseña(String texto, int rating, Actividad actividad);
    public abstract void modificarRating(Resenia resenia, int nuevoRating);
    public abstract void realizarLogIn(String email, String contrasenia);

    // Methodos

    @Override
    public ArrayList<learningPath> checkLearningPaths() {
        if (learningPaths.isEmpty()) {
            System.out.println("No hay Learning Paths asociados a este usuario.");
        } else {
            System.out.println("Learning Paths asociados:");
            for (learningPath lp : learningPaths) {
                System.out.println("- " + lp.getTitulo());
            }
        }
        return learningPaths;
    }

    public void resetPassword() {
    try (Scanner scanner = new Scanner(System.in)) {
        System.out.println("Por favor, ingrese su nueva contraseña:");
        String nuevaContrasenia = scanner.nextLine();
        if (nuevaContrasenia == null || nuevaContrasenia.isEmpty()) {
            System.out.println("La nueva contraseña no puede estar vacía.");
            return;
        }
        if (nuevaContrasenia.length() < 8) {
            System.out.println("La nueva contraseña debe tener al menos 8 caracteres.");
            return;
        }
        setContrasenia(nuevaContrasenia);
        System.out.println("La contraseña ha sido restablecida con éxito.");
    }
}


    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasenia() {
        return contraseña;
    }

    public void setContrasenia(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public void agregarResenia(Resenia resenia) {
        this.resenias.add(resenia);
    }
    public ArrayList<Resenia> getResenias() {
        return resenias;
    }
}