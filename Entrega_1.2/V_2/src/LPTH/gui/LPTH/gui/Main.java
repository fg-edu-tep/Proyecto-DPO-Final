package LPTH.gui;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Main {
    public static void main(String[] args) {
        try {
            // Crear el servidor en el puerto 8000
            HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

            // Agregar los contextos de la clase Interfaz
            Interfaz.addContexts(server);

            // Agregar los contextos de la clase MenuProfesor
            MenuProfesor.addContexts(server);
            
            MenuEstudiante.addContexts(server);

            // Iniciar el servidor
            server.start();
            System.out.println("Servidor iniciado en http://localhost:8000");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
