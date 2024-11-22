package LPTH.Preguntas;

import java.util.ArrayList;

public class PreguntaToF extends Pregunta{
    private boolean opcionCorrecta;
    private boolean correctaTF; //valor de si esta bien respondida

    public PreguntaToF(String enunciado, boolean opcionCorrecta){
        super(enunciado);
        this.correctaTF= false;
        this.opcionCorrecta=opcionCorrecta;
    }
    
    
    public boolean checkCorrecta(String UserInput) {
        boolean userInputBool = UserInput.equalsIgnoreCase("true");
        if (opcionCorrecta == userInputBool) {
            this.correctaTF = true;
            return true;
        } else {
            return false;
        }
    }

    public void setCorrectaTF(boolean input) {
        this.correctaTF= input;
    }

}
	



