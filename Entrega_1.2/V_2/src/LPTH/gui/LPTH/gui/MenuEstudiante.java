package LPTH.gui;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import LPTH.modelo.LearningPath;
import LPTH.modelo.Sistema;
import LPTH.exceptions.ExceptionNoPersistencia;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class MenuEstudiante {

    private static Sistema sistema;

    // Método para asignar la instancia del sistema cargado
    public static void setSistema(Sistema sistemaCentral) {
        sistema = sistemaCentral;
    }

    public static void addContexts(HttpServer server) {
        // Context for the student menu
        server.createContext("/menu/student", exchange -> {
            String html = generarMenuEstudiante();
            enviarRespuesta(exchange, html);
        });

        // Context to handle viewing available LPs
        server.createContext("/menu/student/ver_lps", exchange -> {
            String html = generarVerLearningPaths();
            enviarRespuesta(exchange, html);
        });

        // Context for processing logout
        server.createContext("/menu/student/logout", exchange -> {
            if ("POST".equals(exchange.getRequestMethod())) {
                // Logic to handle logout (if necessary)
                redirigir(exchange, "/"); // Redirect to home page
            } else {
                exchange.sendResponseHeaders(405, -1); 
            }
        });
    }

    // Method to send an HTTP response
    private static void enviarRespuesta(HttpExchange exchange, String response) throws IOException {
        exchange.sendResponseHeaders(200, response.getBytes().length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }

    // Method to redirect the client to another route
    private static void redirigir(HttpExchange exchange, String ruta) throws IOException {
        exchange.getResponseHeaders().add("Location", ruta);
        exchange.sendResponseHeaders(302, -1); // HTTP 302 for redirection
        exchange.close();
    }

    // HTML: Student menu page
    private static String generarMenuEstudiante() {
        return """
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Menu Estudiante</title>
                <style>
                    body {
                        font-family: Arial, sans-serif;
                        display: flex;
                        justify-content: center;
                        align-items: center;
                        height: 100vh;
                        margin: 0;
                        background-color: #f5f5f5;
                    }

                    .container {
                        background: #fff;
                        padding: 20px;
                        border-radius: 8px;
                        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
                        text-align: center;
                        width: 300px;
                    }

                    h1 {
                        font-size: 24px;
                        margin-bottom: 20px;
                    }

                    button {
                        width: calc(100% - 20px);
                        padding: 10px;
                        background-color: #000;
                        color: #fff;
                        border: none;
                        border-radius: 4px;
                        font-size: 16px;
                        cursor: pointer;
                        margin-bottom: 10px;
                    }

                    button:hover {
                        background-color: #333;
                    }

                    .footer button {
                        background-color: #ccc;
                        color: #000;
                    }

                    .footer button:hover {
                        background-color: #aaa;
                    }
                </style>
            </head>
            <body>
                <div class="container">
                    <h1>Menu Estudiante</h1>
                    <p>¿Qué desea hacer?</p>
                    <form action="/menu/student/ver_lps" method="get">
                        <button type="submit">Ver LPs disponibles</button>
                    </form>
                    <div class="footer">
                        <form action="/menu/student/logout" method="post">
                            <button type="submit">Salir</button>
                        </form>
                    </div>
                </div>
            </body>
            </html>
        """;
    }

    // HTML: Page for viewing available LPs
    private static String generarVerLearningPaths() {
        StringBuilder html = new StringBuilder("""
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Learning Path</title>
                <style>
                    body {
                        font-family: Arial, sans-serif;
                        display: flex;
                        flex-direction: column;
                        justify-content: center;
                        align-items: center;
                        margin: 0;
                        background-color: #f5f5f5;
                        height: 100vh;
                    }

                    .container {
                        background-color: #fff;
                        padding: 20px;
                        border-radius: 8px;
                        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
                        width: 600px;
                        max-width: 90%;
                        text-align: center;
                    }

                    h1 {
                        font-size: 24px;
                        margin-bottom: 20px;
                    }

                    .lp-container {
                        background-color: #ddd;
                        padding: 10px;
                        border-radius: 8px;
                        border: 1px solid #000;
                        height: 300px;
                        overflow-y: auto; /* Adds scrollable behavior */
                    }

                    .lp-item {
                        display: flex;
                        justify-content: space-between;
                        align-items: center;
                        background-color: #b54d4d;
                        color: #fff;
                        padding: 10px;
                        border-radius: 8px;
                        margin-bottom: 10px;
                        box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
                    }

                    .lp-item .details {
                        text-align: left;
                    }

                    .lp-item .details p {
                        margin: 5px 0;
                        font-size: 14px;
                    }

                    .lp-item .description {
                        background-color: #eee;
                        width: 120px;
                        height: 80px;
                        display: flex;
                        align-items: center;
                        justify-content: center;
                        color: #000;
                        border-radius: 4px;
                        font-size: 12px;
                    }

                    .footer {
                        margin-top: 20px;
                        display: flex;
                        justify-content: space-between;
                    }

                    .footer button {
                        background-color: #ccc;
                        color: #000;
                        padding: 10px 20px;
                        border: none;
                        border-radius: 4px;
                        font-size: 16px;
                        cursor: pointer;
                    }

                    .footer button:hover {
                        background-color: #aaa;
                    }

                    .footer a {
                        text-decoration: none;
                        color: #333;
                    }

                    .footer a:hover {
                        text-decoration: underline;
                    }
                </style>
            </head>
            <body>
                <div class="container">
                    <h1>Learning Path</h1>
                    <div class="lp-container">
        """);

        try {
            List<LearningPath> learningPaths = sistema.getLearningPaths();
            if (learningPaths.isEmpty()) {
                html.append("<p>No hay Learning Paths disponibles.</p>");
            } else {
                for (LearningPath lp : learningPaths) {
                    html.append("""
                        <div class="lp-item">
                            <div class="details">
                                <p><b>Título LP:</b> %s</p>
                                <p><b>Profesor:</b> %s</p>
                                <p><b>Duración:</b> %d horas</p>
                            </div>
                            <div class="description">%s</div>
                        </div>
                    """.formatted(lp.getTitulo(), lp.getProfesorCreador(), lp.getDuracion(), lp.getDescripcion()));
                }
            }
        } catch (Exception e) {
            html.append("<p>Ocurrió un error al cargar los Learning Paths.</p>");
        }

        html.append("""
                    </div>
                    <div class="footer">
                        <a href="/menu/student">
                            <button>Regresar a Menu Estudiante</button>
                        </a>
                        <form action="/menu/student/logout" method="post" style="margin: 0;">
                            <button type="submit">Salir</button>
                        </form>
                    </div>
                </div>
            </body>
            </html>
        """);

        return html.toString();
    }
}
