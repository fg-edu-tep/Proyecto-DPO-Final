package LPTH.gui;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class Interfaz {
    public static void iniciarServidor() {
        try {
            // Create the server on port 8000
            HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

            // Define the "/" endpoint
            server.createContext("/", exchange -> {
                String html = """
                    <html>
                    <head>
                        <title>Mi Proyecto</title>
                        <style>
                            body {
                                font-family: Arial, sans-serif;
                                margin: 0;
                                padding: 0;
                                background-color: #f4f4f9;
                                color: #333;
                                display: flex;
                                justify-content: center;
                                align-items: center;
                                height: 100vh;
                            }
                            .container {
                                text-align: center;
                                background: #fff;
                                padding: 20px;
                                border-radius: 8px;
                                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
                            }
                            h1 {
                                color: #4CAF50;
                            }
                            form {
                                margin-top: 20px;
                            }
                            label {
                                font-weight: bold;
                                display: block;
                                margin-bottom: 8px;
                            }
                            input {
                                padding: 10px;
                                width: 80%;
                                max-width: 300px;
                                margin-bottom: 20px;
                                border: 1px solid #ccc;
                                border-radius: 4px;
                            }
                            button {
                                padding: 10px 20px;
                                background-color: #4CAF50;
                                color: white;
                                border: none;
                                border-radius: 4px;
                                cursor: pointer;
                            }
                            button:hover {
                                background-color: #45a049;
                            }
                        </style>
                    </head>
                    <body>
                        <div class="container">
                            <h1>¡Bienvenido a mi Proyecto Java!</h1>
                            <form action="/procesar" method="post">
                                <label for="nombre">Nombre:</label>
                                <input type="text" id="nombre" name="nombre">
                                <button type="submit">Enviar</button>
                            </form>
                        </div>
                    </body>
                    </html>
                    """;
                exchange.sendResponseHeaders(200, html.getBytes().length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(html.getBytes());
                }
            });

            // Define the "/procesar" endpoint
            server.createContext("/procesar", exchange -> {
                if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
                    String response = "¡Datos recibidos!";
                    exchange.sendResponseHeaders(200, response.getBytes().length);
                    try (OutputStream os = exchange.getResponseBody()) {
                        os.write(response.getBytes());
                    }
                } else {
                    exchange.sendResponseHeaders(405, -1); // Method Not Allowed
                }
            });

            // Start the server
            server.start();
            System.out.println("Servidor iniciado en http://localhost:8000");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
