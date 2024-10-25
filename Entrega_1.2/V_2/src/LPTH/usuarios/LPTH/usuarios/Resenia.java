package LPTH.usuarios;

public class Resenia {
	
    private String texto;
    private int rating;

    public Resenia(String texto, int rating) {
        this.texto = texto;
        this.rating = setRating(rating);
    }

    public void modificarTexto(String nuevoTexto) {
    	this.texto = nuevoTexto;
    }
    
	public void modificarRating(int nuevoRating) {
		this.rating = setRating(nuevoRating);
	}

    public int getRating() {
        return rating;
    }

    public String getTexto() {
        return texto;
    }
    public int setRating(int nuevoRating) {
        if (nuevoRating < 1 || nuevoRating > 5) {
            throw new IllegalArgumentException("El rating debe estar entre 1 y 5."); // Esto hace sentido
        }
        this.rating = nuevoRating;
        return this.rating;
    }


}
