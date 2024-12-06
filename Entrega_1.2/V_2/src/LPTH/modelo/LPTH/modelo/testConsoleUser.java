package LPTH.modelo;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Scanner;

import LPTH.exceptions.ExceptionNoPersistencia;
import LPTH.exceptions.ExceptionUsuarioNoEncontrado;
import LPTH.usuarios.Usuario;

public class testConsoleUser {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserFactory userFactory = null;
        try {
            userFactory = new UserFactory();
        } catch (ExceptionNoPersistencia | IOException e) {
            System.out.println("Error initializing UserFactory: " + e.getMessage());
            return;
        }

        while (true) {
            System.out.println("\nUser Management Console");
            System.out.println("1. Create Profesor");
            System.out.println("2. Create Estudiante");
            System.out.println("3. Save Users");
            System.out.println("4. Load Users");
            System.out.println("5. Login");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter name: ");
                    String profesorName = scanner.nextLine();
                    System.out.print("Enter email: ");
                    String profesorEmail = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String profesorPassword = scanner.nextLine();
                    String profesorFecha = LocalDate.now().toString();
                    System.out.print("Enter subject: ");
                    String profesorMateria = scanner.nextLine();
                    userFactory.crearUsuario("profesor", profesorName, profesorEmail, profesorPassword, profesorFecha, profesorMateria);
                    System.out.println("Profesor created successfully.");
                    break;
                case 2:
                    System.out.print("Enter name: ");
                    String studentName = scanner.nextLine();
                    System.out.print("Enter email: ");
                    String studentEmail = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String studentPassword = scanner.nextLine();
                    String studentFecha = LocalDate.now().toString();
                    userFactory.crearUsuario("estudiante", studentName, studentEmail, studentPassword, studentFecha, null);
                    System.out.println("Estudiante created successfully.");
                    break;
                case 3:
                    try {
                        userFactory.saveUsuarios();
                        System.out.println("Users saved successfully.");
                    } catch (ExceptionNoPersistencia e) {
                        System.out.println("Error saving users: " + e.getMessage());
                    }
                    break;
                case 4:
                    try {
                        userFactory = userFactory.loadUsuarios();
                        System.out.println("Users loaded successfully.");
                    } catch (ExceptionNoPersistencia e) {
                        System.out.println("Error loading users: " + e.getMessage());
                    }
                    break;
                case 5:
                    System.out.print("Enter email: ");
                    String loginEmail = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String loginPassword = scanner.nextLine();
                    try {
                        Usuario loggedInUser = userFactory.autenticarUsuario(loginEmail, loginPassword);
                        if (loggedInUser != null) {
                            System.out.println("Login successful. Welcome, " + loggedInUser.getNombre() + "!");
                        } else {
                            System.out.println("Invalid email or password.");
                        }
                    } catch (ExceptionUsuarioNoEncontrado e) {
                        System.out.println("User not found: " + e.getMessage());
                    }
                    break;
                case 6:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
