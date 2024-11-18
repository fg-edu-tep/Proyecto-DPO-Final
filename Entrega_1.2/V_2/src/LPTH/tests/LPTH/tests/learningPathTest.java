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

public class learningPathTest {
    Profesor jirafales;
    Sistema sistemaCentral = new Sistema();
    Date hoy = sistemaCentral.getCurrentDate();
    ArrayList<Resenia> resenias = new ArrayList<Resenia>();
    Tarea elQuije = new Tarea(true, "Unit tests", hoy, "Programa hasta sangrar", 2.5, (float) 3.11, false, resenias, 2.55, false, "Tarea");
    ArrayList<Pregunta> preguntas;
    ArrayList<Actividad> actividades = new ArrayList<Actividad>();
    LearningPath learningPath;

    @BeforeEach
    void setUp() throws Exception {
        jirafales = (Profesor) sistemaCentral.crearUsuario("profesor", "Jirafales", "Jirafales@unimandes.eu", "Chavito", "17_11_2024", "Geociencias");
        preguntas = new ArrayList<>();
        preguntas.add(new PreguntaAbierta("¿Qué es Java?"));
        actividades.add(elQuije);
        learningPath = new LearningPath(jirafales.getNombre(), "Aprende Java", 1, "Curso para aprender Java desde cero", new ArrayList<>(), "Intermedio", 10, 5, hoy, hoy, "1.0", 0.8f, actividades);
    }

    @AfterEach
    void tearDown() throws Exception {
    }

    @Test
    void testAgregarActividad() {
        Tarea nuevaTarea = new Tarea(true, "Polimorfismo en Java", hoy, "Aprender sobre polimorfismo", 3.0, (float) 4.0, false, resenias, 3.0, false, "Tarea");
        learningPath.agregarActividad(nuevaTarea);
        assertTrue(learningPath.getActividades().contains(nuevaTarea), "La actividad no se agregó correctamente al Learning Path");
    }

    @Test
    void testEliminarActividad() {
        learningPath.eliminarActividad(elQuije);
        assertTrue(!learningPath.getActividades().contains(elQuije), "La actividad no se eliminó correctamente del Learning Path");
    }

    @Test
    void testNotificarEstudianteDue() {
        Estudiante estudiante = new Estudiante(sistemaCentral, 1, "Estudiante Prueba", "estudiante@unimandes.edu", "1234", "17_11_2024");
        learningPath.notificarEstudianteDue(estudiante);
        List<String> notificaciones;
        try {
            notificaciones = estudiante.getNotificaciones();
        } catch (ExceptionEstudianteSinLp e) {
            throw new RuntimeException("No se pudo obtener las notificaciones del estudiante porque no tiene un Learning Path activo.", e);
        }
        assertNotNull(notificaciones, "Las notificaciones deberían existir aunque estén vacías");
        assertTrue(notificaciones.isEmpty(), "No debería haber notificaciones ya que no hay actividades próximas a vencer");
    }

    @Test
    void testRecomendarActividad() {
        Actividad actividadRecomendada = learningPath.recomendarActividad();
        assertNotNull(actividadRecomendada, "La actividad recomendada no debería ser nula");
        assertTrue(learningPath.getActividades().contains(actividadRecomendada), "La actividad recomendada debería pertenecer al Learning Path");
    }

    @Test
    void testEditarTituloDescripcion() {
        learningPath.editarTituloDesc("Aprende Python", "Curso para aprender Python desde cero");
        assertEquals("Aprende Python", learningPath.getTitulo(), "El título no se actualizó correctamente");
        assertEquals("Curso para aprender Python desde cero", learningPath.getDescripcion(), "La descripción no se actualizó correctamente");
    }
}
