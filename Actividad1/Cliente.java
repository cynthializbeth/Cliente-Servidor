import java.net.Socket;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Scanner;

/**
 * Cliente, el cliente que se conecta al servidor
 * para jugar a adivinar un número o ahorcado
 * 
 * @author cynthializbeth
 * @version 1.0
 */
public class Cliente {
    /* Scanner para leer datos del teclado */
    static Scanner teclado = new Scanner(System.in);
    /* Socket del cliente */
    static Socket cliente;
    /* Mensaje que se le enviará al servidor */
    static DataOutputStream mensaje;
    /* Código de color ANSI para amarillo */
    static String colorAmarillo = "\u001B[33m";
    /* Código de color ANSI para rojo */
    static String colorRojo = "\u001B[31m";
    /* Código de color ANSI para azul */
    static String colorAzul = "\u001B[34m";
    /* Restablecer el color a su estado original */
    static String resetColor = "\u001B[0m";

    /**
     * Método principal, el cliente se conecta al servidor y comunica
     * si desea jugar a adivinar un número o ahorcado.
     */
    public static void main(String[] args) {
        // Scanner teclado = new Scanner(System.in);
        int opcion = 0;
        try {
            cliente = new Socket("localhost", 4500);
            /* MANDAMOS SALUDO AL SERVIDOR */
            mensaje = new DataOutputStream(cliente.getOutputStream());
            mensaje.writeUTF("Hi \uD83D\uDE0A");
            /* LEEMOS LO QUE NOS MANDA EL SERVIDOR */
            // El menú de operaciones
            DataInputStream entrada = new DataInputStream(cliente.getInputStream());
            String menu = entrada.readUTF();
            System.out.println(menu);
            /* ESCRIBIMOS LA OPCIÓN DEL USUARIO */
            // Que operación se desea realizar
            opcion = teclado.nextInt();
            mensaje.writeInt(opcion);
            despliegaJuego(opcion);
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Método que realiza la operación que el usuario desea
     * 1-. El usuario tiene que adivinar el numero del servidor
     * tiene 7 intentos.
     * 2-. El usuario juega ahorcado con el servidor
     * opcion = teclado.nextInt();
     * mensaje.writeInt(opcion);
     * despliegaJuego(opcion);
     * 
     * @param opcion,  la opción que el usuario desea realizar
     * @param cliente, el socket del cliente
     * @param mensaje, el mensaje que se le enviará al servidor
     */
    public static void despliegaJuego(int opcion) {
        int numero = 0;
        String letra = "";
        int intentos = 0;
        boolean acertado = false;
        try {
            switch (opcion) {
                case 1:
                    // Permite obtener mensajes del servidor
                    DataInputStream entradaPDS = new DataInputStream(cliente.getInputStream());
                    String elServidorSolicita = (String) entradaPDS.readUTF();
                    System.out.println(elServidorSolicita);
                    // Mientras el usuario no haya acertado y no se hayan agotado los intentos
                    while (intentos <= 6 && !acertado) {
                        /* El cliente escribe el primer digito */
                        numero = teclado.nextInt();
                        // mandamos el numero al servidor
                        mensaje.writeInt(numero);
                        intentos++;
                        /* El cliente lee si el usuario acertó o no */
                        DataInputStream entrada = new DataInputStream(cliente.getInputStream());
                        String respSuma = (String) entrada.readUTF();
                        System.out.println(respSuma);
                        // Revisamos las respuestas del servidor para saber si el usuario acertó
                        // Si ha acertado, se termina el juego
                        if (respSuma.equals(colorAmarillo + "¡Felicidades, has adivinado el número!" + resetColor
                                + "\uD83D\uDE0A")) {
                            acertado = true;
                        }
                    }
                    despliegoJuegoIntento();
                    break;
                case 2:
                    DataInputStream entradaPDS2 = new DataInputStream(cliente.getInputStream());
                    String elServidorSolicita2 = (String) entradaPDS2.readUTF();
                    System.out.println(elServidorSolicita2);
                    while (intentos <= 5 && !acertado) {
                        /* El cliente escribe el primer digito */
                        letra = teclado.next();
                        mensaje.writeUTF(letra);
                        DataInputStream entrada2 = new DataInputStream(cliente.getInputStream());
                        String respAhorcado = entrada2.readUTF();
                        System.out.println(respAhorcado);
                        // Revisamos las respuestas del servidor para saber si el usuario acertó
                        // Si ha acertado, se termina el juego
                        if (respAhorcado.contains("\uD83D\uDE0A")) {
                            acertado = true;
                        } else if (respAhorcado.contains("no está en la palabra")) {
                            intentos++;
                        }
                    }
                    despliegoJuegoIntento();
                    break;
                case 3:
                    cliente.close();
                    System.out.println("Se ha cerrado la conexión");
                    break;
                default:
                    System.out.println("Opción no válida");
                    despliegoJuegoIntento();
                    break;
            }
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Método que establece de nuevo la conexión con el servidor
     * para que el usuario pueda jugar nuevamente.
     */
    public static void despliegoJuegoIntento() {
        try {
            /* SE VUELVEN A CARGAR DATOS */
            DataOutputStream mensajeNM = new DataOutputStream(cliente.getOutputStream());
            mensajeNM.writeUTF("Hola servidor");
            /* LEEMOS LO QUE NOS MANDA EL SERVIDOR */
            // El menú de operaciones
            DataInputStream entradaNM = new DataInputStream(cliente.getInputStream());
            String mensaje2NM = entradaNM.readUTF();
            System.out.println(mensaje2NM);
            /* ESCRIBIMOS LA OPCIÓN DEL USUARIO */
            int op = teclado.nextInt();
            mensaje.writeInt(op);
            despliegaJuego(op);
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
