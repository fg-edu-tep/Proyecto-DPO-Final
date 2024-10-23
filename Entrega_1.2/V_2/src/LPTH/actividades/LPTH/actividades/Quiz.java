package LPTH.actividades;
import java.util.ArrayList;
import java.util.Date;

import LPTH.Preguntas.PreguntaCerrada;

public class Quiz extends Actividad{

    private boolean quizEmpezando;
    private ArrayList<PreguntaCerrada> preguntas;
    private int counter; 


    @Override
    public void calificarActividad() {
        // TODO Auto-generated method stub

    }

    @Override
    public String notificarEstudiante() {
        return "Debe realizar su quiz!";
    }

    public boolean empezarQuiz(boolean quizEmpezado) {
        return quizEmpezado= true;
    }

    public void nextQuestion(ArrayList<PreguntaCerrada> preguntas, boolean esCompletada, boolean quizEmpezado) {

        if(quizEmpezado == true) {
            if (counter < 4) {
                System.out.println(preguntas[counter]);
                counter ++;
            }
            else {
                System.out.println("Ya completo su quiz");
                esCompletada = true;
            }
        }
        else {
            System.out.println("Debe iniciar el Quiz primero");
        }

    }

    public void isitDone(boolean isDone) { //gettersenconsola
        if(counter == 3) {
            isDone= true;
        }
    }

}