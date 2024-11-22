package LPTH.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import LPTH.modelo.Sistema;
import LPTH.usuarios.Estudiante;
import LPTH.usuarios.Profesor;
import LPTH.modelo.LearningPath;

public class SistemaTest {
    Sistema sistemaCentral;
    Date hoy;

    @BeforeEach
    void setUp() {
        sistemaCentral = new Sistema();
        hoy = sistemaCentral.getCurrentDate();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testCrearUsuarioProfesor() {
        Profesor profesor = (Profesor) sistemaCentral.crearUsuario("profesor", "Jirafales", "jirafales@unimandes.edu", "1234", "17_11_2024", "Matemáticas");
        assertNotNull(profesor, "El profesor debería haber sido creado");
        assertEquals("Jirafales", profesor.getNombre(), "El nombre del profesor no coincide");
    }

    @Test
    void testCrearUsuarioEstudiante() {
        Estudiante estudiante = (Estudiante) sistemaCentral.crearUsuario("estudiante", "Chavo", "chavo@unimandes.edu", "1234", "17_11_2024", "");
        assertNotNull(estudiante, "El estudiante debería haber sido creado");
        assertEquals("Chavo", estudiante.getNombre(), "El nombre del estudiante no coincide");
    }

    @Test
    void testAutenticarUsuario() {
        sistemaCentral.crearUsuario("profesor", "Jirafales", "jirafales@unimandes.edu", "1234", "17_11_2024", "Matemáticas");
        try {
            Profesor profesor = (Profesor) sistemaCentral.autenticarUsuario("jirafales@unimandes.edu", "1234");
            assertNotNull(profesor, "El profesor debería haber sido autenticado");
        } catch (Exception e) {
            assertTrue(false, "No se debería haber lanzado una excepción");
        }
    }

    @Test
    void testCrearLearningPath() {
        Profesor profesor = (Profesor) sistemaCentral.crearUsuario("profesor", "Jirafales", "jirafales@unimandes.edu", "1234", "17_11_2024", "Matemáticas");
        LearningPath learningPath = sistemaCentral.crearLearningPath(profesor, "Aprender Java", "Curso básico de Java", "Fácil", 15);
        assertNotNull(learningPath, "El Learning Path debería haber sido creado");
        assertEquals("Aprender Java", learningPath.getTitulo(), "El título del Learning Path no coincide");
    }

    @Test
    void testRecomendarLearningPath() {
        Profesor profesor = (Profesor) sistemaCentral.crearUsuario("profesor", "Jirafales", "jirafales@unimandes.edu", "1234", "17_11_2024", "Matemáticas");
        sistemaCentral.crearLearningPath(profesor, "Aprender Java", "Curso básico de Java", "Fácil", 15);
        sistemaCentral.crearLearningPath(profesor, "Aprender Python", "Curso básico de Python", "Fácil", 10);
        LearningPath recomendado = sistemaCentral.recomendarLearningPath();
        assertNotNull(recomendado, "El Learning Path recomendado no debería ser nulo");
    }

    @Test
    void testGetUsuarios() {
        sistemaCentral.crearUsuario("profesor", "Jirafales", "jirafales@unimandes.edu", "1234", "17_11_2024", "Matemáticas");
        sistemaCentral.crearUsuario("estudiante", "Chavo", "chavo@unimandes.edu", "1234", "17_11_2024", "");
        LinkedList<?> usuarios = sistemaCentral.getUsuarios();
        assertEquals(2, usuarios.size(), "Debería haber dos usuarios en el sistema");
    }
}
