import java.net.Socket;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Scanner;

/**
 * Cliente
 */
public class Cliente {
    static Scanner teclado = new Scanner(System.in);
    static Socket cliente;
    static DataOutputStream mensaje;
     // Código de color ANSI para amarillo
    static String colorAmarillo = "\u001B[33m";
     // Código de color ANSI para rojo
    static String colorRojo = "\u001B[31m";
     // Código de color ANSI para azul
    static String colorAzul = "\u001B[34m";
     // Restablecer el color a su estado original
    static String resetColor = "\u001B[0m";

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
            mensaje.writeInt(opcion);
            despliegaJuego(opcion);
     * @param opcion,  la opción que el usuario desea realizar
     * @param cliente, el socket del cliente
     * @param mensaje, el mensaje que se le enviará al servidor
     */
    public static void despliegaJuego(int opcion) {
        int numero = 0;
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
                    while (intentos <=6 && !acertado) {
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
                        if (respSuma.equals(colorAmarillo + "¡Felicidades, has adivinado el número!"+ resetColor + "\uD83D\uDE0A")) {
                            acertado = true;
                        }
                    }
                    break;
                case 2:
                    System.out.println("Juguemos ahorcado");
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
