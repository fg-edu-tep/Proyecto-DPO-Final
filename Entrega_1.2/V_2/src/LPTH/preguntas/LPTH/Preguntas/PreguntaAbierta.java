package LPTH.Preguntas;

public class PreguntaAbierta extends Pregunta{
	private String respuestaUsuario;
	
	public PreguntaAbierta(String elEnunciado) {
		super(elEnunciado);
	}
	
	public void responderUsuario(String responde) {
		this.respuestaUsuario = responde;
	}
	
	public String getRespuesta() {
		return this.respuestaUsuario;
	}
	
}
