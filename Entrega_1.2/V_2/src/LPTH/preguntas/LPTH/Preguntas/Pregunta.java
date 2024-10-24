package LPTH.Preguntas;

public abstract class Pregunta {
    private String enunciado;
    private boolean correcta = false;
    private String hint;

    public Pregunta(String elEnunciado) {
    	this.enunciado = elEnunciado;
    }
    
    public void setEncunciado(String elEnunciado) {
    	this.enunciado = elEnunciado;
    }
    
    public String getEnunciado() {
    	return this.enunciado;
    }
    
    public String getHint() {
    	return this.hint;
    }

    public void toggleCorrecta() {
    	this.correcta = !correcta;
    }
    
    public void setHint(String hint) {
    	this.hint = hint;
    }
}
