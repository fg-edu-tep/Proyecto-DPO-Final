package LPTH.persistencia;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import LPTH.usuarios.Usuario;
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Base_de_datos {
    private static final String RUTA_ARCHIVO = "persistencia/usuarios.json";

    // Método para guardar un usuario en el archivo JSON
    public static void guardarUsuario(Usuario usuario) {
        Gson gson = new Gson();
        Map<String, Usuario> usuarios = cargarUsuarios();  // Cargar el mapa actual de usuarios

        // Agregar o actualizar el usuario en el mapa usando el email como clave
        usuarios.put(usuario.getEmail(), usuario);

        // Convertir el mapa actualizado a JSON y escribir en el archivo
        String json = gson.toJson(usuarios);
        try {
            Files.write(Paths.get(RUTA_ARCHIVO), json.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para cargar todos los usuarios del archivo JSON
    private static Map<String, Usuario> cargarUsuarios() {
        Gson gson = new Gson();
        Map<String, Usuario> usuarios = new HashMap<>();

        try {
            // Leer el archivo si existe
            if (Files.exists(Paths.get(RUTA_ARCHIVO))) {
                String json = new String(Files.readAllBytes(Paths.get(RUTA_ARCHIVO)));
                usuarios = gson.fromJson(json, new TypeToken<Map<String, Usuario>>() {}.getType());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return usuarios;
    }

    // Método para obtener un usuario por su email
    public static Usuario obtenerUsuarioPorEmail(String email) {
        Map<String, Usuario> usuarios = cargarUsuarios();  // Cargar el mapa de usuarios
        return usuarios.get(email);  // Retornar el usuario correspondiente al email o null si no existe
    }
}



