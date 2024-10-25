package LPTH.usuarios;
public class Resenia {
    private String texto;
    private int rating;

    public Resenia(String texto, int rating) {
        if (texto == null || texto.isEmpty()) {
            throw new IllegalArgumentException("El texto de la resenia no puede estar vacío.");
        }
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("El rating debe estar entre 1 y 5.");
        }
        this.texto = texto;
        this.rating = rating;
    }

    // Methodos
    public void modificarTexto(String nuevoTexto) {
        if (nuevoTexto == null || nuevoTexto.isEmpty()) {
            System.out.println("El nuevo texto no puede estar vacío.");
            return;
        }
        this.texto = nuevoTexto;
        System.out.println("El texto de la resenia ha sido modificado.");
    }

    public int getRating() {
        return rating;
    }

    public String getTexto() {
        return texto;
    }
    public void setRating(int nuevoRating) {
        if (nuevoRating < 1 || nuevoRating > 5) {
            throw new IllegalArgumentException("El rating debe estar entre 1 y 5.");
        }
        this.rating = nuevoRating;
    }

    public void mostrarResenia() {
        System.out.println("Resenia: " + texto);
        System.out.println("Rating: " + rating + " estrellas");
    }
}
