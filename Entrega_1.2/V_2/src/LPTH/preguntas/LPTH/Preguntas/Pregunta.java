package LPTH.Preguntas;


// Se removio hints y su getter y setter.   
public abstract class Pregunta {
    private String enunciado;
    private boolean correcta = false;

    public Pregunta(String elEnunciado) {
    	this.enunciado = elEnunciado;
    }
    
    public void setEncunciado(String elEnunciado) {
    	this.enunciado = elEnunciado;
    }
    
    public String getEnunciado() {
    	return this.enunciado;
    }
    
    public void toggleCorrecta() {
    	this.correcta = !correcta;
    }
    
    
    //Añadido para poder corregir examen, profesor corrige abierta con setters ?? 8===)
    public boolean getCorrecta() {
    	return this.correcta;
    }
    
}
