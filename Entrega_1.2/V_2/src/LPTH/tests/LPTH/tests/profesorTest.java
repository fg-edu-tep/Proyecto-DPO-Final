package LPTH.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import LPTH.actividades.Actividad;
import LPTH.actividades.Quiz;
import LPTH.actividades.Tarea;
import LPTH.exceptions.ExceptionEstudianteSinLp;
import LPTH.modelo.LearningPath;
import LPTH.modelo.Progreso;
import LPTH.modelo.Sistema;
import LPTH.Preguntas.Pregunta;
import LPTH.Preguntas.PreguntaAbierta;
import LPTH.usuarios.Estudiante;
import LPTH.usuarios.Profesor;
import LPTH.usuarios.Resenia;

public class profesorTest {
    Profesor jirafales;
    Sistema sistemaCentral = new Sistema();
    Date hoy = sistemaCentral.getCurrentDate();
    ArrayList<Resenia> resenias = new ArrayList<Resenia>();
    Tarea elQuije = new Tarea(true, "Unit tests", hoy, "Programa hasta sangrar", 2.5, (float) 3.11, false, resenias, 2.55, false, "Tarea");
    ArrayList<Pregunta> preguntas;
    ArrayList<Actividad> actividades = new ArrayList<Actividad>();

    @BeforeEach
    void setUp() throws Exception {
        jirafales = (Profesor) sistemaCentral.crearUsuario("profesor", "Jirafales", "Jirafales@unimandes.eu", "Chavito", "17_11_2024", "Geociencias");
        preguntas = new ArrayList<>();
        preguntas.add(new PreguntaAbierta("¿Qué es Java?"));
    }

    @AfterEach
    void tearDown() throws Exception {
    }

    @Test
    void testCrearLearningPath() {
        LearningPath nuevoLp = jirafales.crearlearningPath("Aprende Java", "Curso de Java desde cero", "Intermedio", 15);
        assertNotNull(nuevoLp, "El Learning Path no debería ser nulo");
        assertEquals("Aprende Java", nuevoLp.getTitulo(), "El título del Learning Path no coincide");
        assertEquals("Curso de Java desde cero", nuevoLp.getDescripcion(), "La descripción del Learning Path no coincide");
    }

    @Test
    void testAgregarLearningPath() {
        LearningPath nuevoLp = jirafales.crearlearningPath("Aprende Java", "Curso de Java desde cero", "Intermedio", 15);
        jirafales.agregarLp(nuevoLp);
        assertTrue(jirafales.getLps().contains(nuevoLp), "El Learning Path debería estar en la lista de Learning Paths del profesor");
    }

    @Test
    void testEditarLearningPath() {
        LearningPath nuevoLp = jirafales.crearlearningPath("Aprende Java", "Curso de Java desde cero", "Intermedio", 15);
        jirafales.editarlearningPath(nuevoLp, "Aprende Python", "Curso de Python desde cero");
        assertEquals("Aprende Python", nuevoLp.getTitulo(), "El título del Learning Path no se actualizó correctamente");
        assertEquals("Curso de Python desde cero", nuevoLp.getDescripcion(), "La descripción del Learning Path no se actualizó correctamente");
    }

    @Test
    void testAgregarQuiz() {
        LearningPath nuevoLp = jirafales.crearlearningPath("Aprende Java", "Curso de Java desde cero", "Intermedio", 15);
        Quiz nuevoQuiz = new Quiz(true, "Quiz de Java", hoy, "Evaluación inicial", 1.0, 5.0f, false, resenias, 2.0, false, "Quiz", preguntas);
        jirafales.AgregarQuiz(nuevoLp, nuevoQuiz);
        assertTrue(nuevoLp.getActividades().contains(nuevoQuiz), "El Quiz debería estar en la lista de actividades del Learning Path");
    }

    @Test
    void testEditarActividad() {
        LearningPath nuevoLp = jirafales.crearlearningPath("Aprende Java", "Curso de Java desde cero", "Intermedio", 15);
        Quiz nuevoQuiz = new Quiz(true, "Quiz de Java", hoy, "Evaluación inicial", 1.0, 5.0f, false, resenias, 2.0, false, "Quiz", preguntas);
        nuevoLp.agregarActividad(nuevoQuiz); // Eliminacion de errores
        jirafales.editarActividad(nuevoQuiz, "Quiz avanzado de Java", "Evaluación avanzada");
        assertEquals("Quiz avanzado de Java", nuevoQuiz.getNombre(), "El nombre de la actividad no se actualizó correctamente");
        assertEquals("Evaluación avanzada", nuevoQuiz.getDescripcion(), "La descripción de la actividad no se actualizó correctamente");
    }

    @Test
    void testMarcarActividadObligatoria() {
        LearningPath nuevoLp = jirafales.crearlearningPath("Aprende Java", "Curso de Java desde cero", "Intermedio", 15);
        Quiz nuevoQuiz = new Quiz(true, "Quiz de Java", hoy, "Evaluación inicial", 1.0, 5.0f, false, resenias, 2.0, false, "Quiz", preguntas);
        nuevoLp.agregarActividad(nuevoQuiz); // Eliminacion de errores
        jirafales.marcarActividadObligatoria(nuevoQuiz);
        assertTrue(nuevoQuiz.getObligatoria(), "La actividad debería estar marcada como obligatoria");
    }
}
