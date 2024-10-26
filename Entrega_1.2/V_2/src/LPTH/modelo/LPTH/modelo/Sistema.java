package LPTH.modelo;

import java.util.Scanner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random; // Para los Ids de Usuario

import LPTH.Preguntas.PreguntaAbierta;
import LPTH.Preguntas.PreguntaCerrada;
import LPTH.actividades.Actividad;
import LPTH.modelo.learningPath;
import LPTH.usuarios.Estudiante;
import LPTH.usuarios.Profesor;
import LPTH.usuarios.Usuario;



public class Sistema {
    private Map<String, String> logIns; //Correo y Password
    private Map<String, learningPath> learningPaths; 
    private LinkedList<Usuario> usuarios;
    private Random rand = new Random();


    public Sistema() {
        this.logIns = new HashMap<String, String>();
        this.learningPaths = new HashMap<String, learningPath>();
        this.usuarios = new LinkedList<Usuario>();
    }
    
    public Object crearUsaurio(String tipo, String nombre, String email, String contrasenia, String fechaRegistro, String materia) {
    	int idUsuario = rand.nextInt(99999);
    	if (tipo == "profesor") {
        	Profesor nuevoUsuario = new Profesor(this, idUsuario,nombre,email,contrasenia,fechaRegistro,tipo, materia);
        	return nuevoUsuario;
    	}
    	else {
        	Estudiante nuevoUsuario = new Estudiante(this,idUsuario,nombre,email,contrasenia,fechaRegistro,tipo);   //TODO revisar si el sistema se puede auto-mandar
        	return nuevoUsuario;	
    		}
    	}
    
    public boolean autenticarUsuario(String email, String contraseña) {
        return logIns.containsKey(email) && logIns.get(email).equals(contraseña);
    }

    // Métodos de conexión:
    public ArrayList<learningPath> getLearningPaths() {
        return new ArrayList<>(learningPaths.values()); // Retornar los valores en una lista
    }
    
    public learningPath getLearningPath(String name) {
        return learningPaths.get(name);
    }


    public LinkedList<Usuario> getUsuarios() {
        return usuarios;
    }

    public learningPath recomendarLearningPath() {
    	ArrayList<learningPath> Lp = getLearningPaths();
    	int recommend = rand.nextInt(Lp.size());
        return Lp.get(recommend);
    }
    


    public ArrayList<PreguntaCerrada> crearPreguntasCerradas() {
        ArrayList<PreguntaCerrada> preguntas = new ArrayList<>();
        
        Scanner input = new Scanner(System.in);

        for (int i = 1; i <= 4; i++) {
            System.out.print("Ingrese el enunciado de la pregunta " + i + ": ");
            String enunciado = input.nextLine();

            ArrayList<String> opciones = new ArrayList<>();

            for (int j = 1; j <= 4; j++) {
                System.out.print("Ingrese la opcion " + j + ": ");
                String opcion = input.nextLine();
                opciones.add(opcion);
            }

            System.out.println("Opciones : ");
            for (int j = 0; j < opciones.size(); j++) {
                System.out.println((j + 1) + ". " + opciones.get(j));
            }

            System.out.print("Seleccione el número de la opcion correcta (1-4): ");
            int opcionCorrectaI = Integer.parseInt(input.nextLine()) - 1;

            String opcionCorrecta = opciones.get(opcionCorrectaI);

            preguntas.add(new PreguntaCerrada(enunciado, opciones, opcionCorrecta));
        }
        input.close();

        return preguntas; 
    }

    public ArrayList<PreguntaAbierta> crearPreguntasAbiertas() {
        ArrayList<PreguntaAbierta> preguntas = new ArrayList<>();
        Scanner input = new Scanner(System.in);

        System.out.print("Ingrese la cantidad de preguntas que desea: ");
        int cantidadPreguntas = Integer.parseInt(input.nextLine());

        for (int i = 1; i <= cantidadPreguntas; i++) {
            System.out.print("Ingrese el enunciado de la pregunta " + i + ": ");
            String enunciado = input.nextLine();

            preguntas.add(new PreguntaAbierta(enunciado));
        }
        input.close();
        return preguntas; 

    }
    	
    	
   // Persistencia:
    
    public void cargarLearningPath() {
    	// TODO Esto es de persistencia
    	}
    
    public void cargarSistema() {
    	// TODO Esto es de persistencia
    }
    public void cargarUsuarios() {
    	// TODO Esto es de persistencia
    }

    public void salvarLearningPath() {
        /// TODO Esto es de persistencia
    }

    public void salvarUsuarios() {
    	// TODO Esto es de persistencia
    }

    public void salvarSistema() {
        /// TODO Esto es de persistencia
    }



    
}
