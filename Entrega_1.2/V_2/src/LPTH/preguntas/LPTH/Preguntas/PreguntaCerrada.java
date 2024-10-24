package LPTH.Preguntas;

import java.util.ArrayList;

public class PreguntaCerrada extends Pregunta{
	public ArrayList<String> opciones;
	public String UserAnswer;
	public String opcionCorrecta;
	
	public PreguntaCerrada(String elEnunciado, ArrayList<String> Opciones, String opcionCorrecta) {
		super(elEnunciado);
		this.opciones = Opciones;
		this.opcionCorrecta = opcionCorrecta;
	}
	
	public ArrayList<String> getOpciones(){
		return this.opciones;
	}
	
	public void setOpciones(ArrayList<String> opciones) {
		this.opciones = opciones;
	}
	
	public void setNthOpcion(String enunOpcion, int indice) {
		/*Se le da el texto y el numero de opcion y lo cambia*/
		this.opciones.remove(indice);
		this.opciones.add(indice, enunOpcion);
	}
	
	public boolean checkCorrecta(String UserInput) {
		if (UserInput == this.opcionCorrecta) {
			return true;
		}
		else {
			return false;
		}
	}
}
