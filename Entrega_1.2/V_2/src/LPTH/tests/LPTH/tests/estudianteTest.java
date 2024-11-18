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

import LPTH.actividades.Tarea;
import LPTH.exceptions.ExceptionEstudianteSinLp;
import LPTH.modelo.LearningPath;
import LPTH.modelo.Progreso;
import LPTH.modelo.Sistema;
import LPTH.usuarios.Estudiante;
import LPTH.usuarios.Profesor;
import LPTH.usuarios.Resenia;

public class estudianteTest {
    Estudiante myEstudiante;
    Sistema sistemaCentral = new Sistema();
    Date hoy = sistemaCentral.getCurrentDate();
    ArrayList<Resenia> resenias = new ArrayList<Resenia>();
    Tarea elQuije = new Tarea(true, "Unit tests", hoy, "Programa hasta sangrar", 2.5, (float) 3.11, false, resenias, 2.55, false, "Tarea");
    Profesor jirafales = (Profesor) sistemaCentral.crearUsuario("profesor", "Jirafales", "Jirafales@unimandes.eu", "Chavito", "17_11_2024", "Geociencias");
    LearningPath eda = sistemaCentral.crearLearningPath(jirafales, "Aprende a no esperar", "La impaciencia es buena", "Meh", 10);

    @BeforeEach
    void setUp() throws Exception {
        myEstudiante = new Estudiante(sistemaCentral, 1, "usuarioPrueba", "pruebon@pruebita.eu", "Probeta", "17_11_2024");
    }

    @AfterEach
    void tearDown() throws Exception {
    }

    @Test
    void testStartAndRemoveLearningPath() {
        myEstudiante.startLearningPath(eda);
        assertEquals("Aprende a no esperar", myEstudiante.getLearningPathActual(), "Error al iniciar el Learning Path");

        myEstudiante.removeLearningPath();
        assertEquals("None", myEstudiante.getLearningPathActual(), "El Learning Path debería haber sido eliminado");
    }

    @Test
    void testPeekLearningPathInfoWithoutLearningPath() {
        assertThrows(ExceptionEstudianteSinLp.class, () -> {
            myEstudiante.peekLearningPathInfo();
        }, "Se esperaba una ExceptionEstudianteSinLp cuando no hay un Learning Path activo");
    }

    @Test
    void testPeekLearningPathInfo() throws ExceptionEstudianteSinLp {
        myEstudiante.startLearningPath(eda);
        ArrayList<String> lpInfo = myEstudiante.peekLearningPathInfo();
        assertNotNull(lpInfo, "La información del Learning Path no debería ser nula");
        assertEquals("Aprende a no esperar", lpInfo.get(0), "El título del Learning Path no coincide");
        assertEquals("La impaciencia es buena", lpInfo.get(1), "La descripción del Learning Path no coincide");
    }

    @Test
    void testIniciarYTerminarActividad() {
        myEstudiante.iniciarActividad(elQuije);
        assertTrue(elQuije.estaCompletada(), "La actividad debería marcarse como iniciada");

        myEstudiante.terminarActividad(elQuije);
        Progreso progreso = myEstudiante.getProgreso();
        assertNotNull(progreso, "El progreso no debería ser nulo después de completar la actividad");
    }

    @Test
    void testGetNotificaciones() throws ExceptionEstudianteSinLp {
        myEstudiante.startLearningPath(eda);
        List<String> notificaciones = myEstudiante.getNotificaciones();
        assertNotNull(notificaciones, "Las notificaciones no deberían ser nulas");
        assertTrue(notificaciones.isEmpty(), "Las notificaciones deberían estar vacías inicialmente");

        // Simulate adding a notification
        myEstudiante.agregarNotificacion("New activity available");
        notificaciones = myEstudiante.getNotificaciones();
        assertEquals(1, notificaciones.size(), "Se esperaba una notificación");
        assertEquals("New activity available", notificaciones.get(0), "El contenido de la notificación no coincide");
    }

    @Test
    void testEndLearningPath() {
        myEstudiante.startLearningPath(eda);
        myEstudiante.endLearningPath(eda);
        Progreso progreso = myEstudiante.getProgreso();
        assertNotNull(progreso, "El progreso no debería ser nulo después de finalizar el Learning Path");
    }

    @Test
    void testCheckLearningPath() {
        myEstudiante.startLearningPath(eda);
        ArrayList<LPTH.actividades.Actividad> actividadesSinCompletar = myEstudiante.checkLearningPath(eda);
        assertNotNull(actividadesSinCompletar, "Las actividades sin completar no deberían ser nulas");
        assertEquals(eda.getActividadesSinCompletar().size(), actividadesSinCompletar.size(), "No coincide el número de actividades sin completar");
    }
}
