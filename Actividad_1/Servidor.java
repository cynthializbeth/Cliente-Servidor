import java.net.ServerSocket;
import java.net.Socket;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Servidor
 */
public interface Servidor {
     // Código de color ANSI para amarillo
     String colorAmarillo = "\u001B[33m";
     // Código de color ANSI para rojo
     String colorRojo = "\u001B[31m";
     // Código de color ANSI para azul
     String colorAzul = "\u001B[34m";
     // Restablecer el color a su estado original
     String resetColor = "\u001B[0m";
    public static void main(String[] args) {
        try {
            ServerSocket servidor = new ServerSocket(4500);
            System.out.println("Estoy vivo muajaja.\uD83D\uDE08 Esperando conexión...");
            Socket clienteNuevo = servidor.accept();
            System.out.println("Se ha establecido una conexión con el cliente: " + clienteNuevo.getInetAddress());

            /* LEEMOS DESDE EL SERVIDOR */
            DataInputStream entrada = new DataInputStream(clienteNuevo.getInputStream());
            String mensaje = (String) entrada.readUTF();
            System.out.println("Mensaje del cliente: " + mensaje);

            /* ESCRIBIMOS DESDE EL SERVIDOR */
            DataOutputStream respuesta = new DataOutputStream(clienteNuevo.getOutputStream());
            String menu = mostrarMenu();
            respuesta.writeUTF(menu);

            /* LEEMOS LO QUE QUIERE EL USUARIO */
            int operacionDeseada = entrada.readInt();
            System.out.println("Operación deseada: " + operacionDeseada);
            solicitaOperacion(operacionDeseada, clienteNuevo, servidor, entrada);

        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static String mostrarMenu() {
        return colorAmarillo + "\n-----[ Bienvenido ]-----" + resetColor + "\nTeclea una opción: \n1. Jugar a adivinar un número \n2. Jugar Ahorcado \n3. salir";
    }

    public static void solicitaOperacion(int op, Socket cn, ServerSocket serv, DataInputStream entrada) {
        try {
            // Permite enviar mensajes al cliente
            DataOutputStream mensaje = new DataOutputStream(cn.getOutputStream());
            switch (op) {
                case 1:
                    mensaje.writeUTF(colorRojo+"\nElegiste jugar Adivina el número: "+resetColor +"Elige un número del 1 al 100, tienes 7 intentos para adivinarlo");
                    int numero = (int) (Math.random() * 100 + 1);
                    System.out.println("Número a adivinar: " + numero);
                    int iteraciones = 6;
                    int respuesta = 0;
                    int intentos = iteraciones;
                    // El usuario tiene 7 intentos para adivinar el número
                    while (iteraciones >= 0) {
                        // mensaje.writeUTF("Te quedan " + intentos + " intentos");
                        respuesta = entrada.readInt();

                        if (respuesta == numero) {
                            mensaje.writeUTF(colorAmarillo + "¡Felicidades, has adivinado el número!"+ resetColor + "\uD83D\uDE0A");
                            break;
                        } else if (respuesta < numero && intentos > 0) {
                            mensaje.writeUTF("El número que pensé es más grande, te quedan "+ colorRojo + intentos + resetColor +" intentos");
                        } else if (respuesta > numero && intentos > 0) {
                            mensaje.writeUTF("El número que pensé es menor, te quedan "+ colorRojo + intentos + resetColor +" intentos");
                        }
                        iteraciones--;
                        intentos--;
                    }
                    if (iteraciones < 0) {
                        mensaje.writeUTF("Lo siento, has perdido \uD83D\uDE2D");
                    }
                    //despliegaNuevamente(cn, serv, entrada);
                    break;
                case 2:
                    mensaje.writeUTF("Juguemos ahorcado");
                    despliegaNuevamente(cn, serv, entrada);
                    break;
                case 3:
                    mensaje.writeUTF("Adios");
                    // cn.close();
                    serv.close();
                    break;
                default:
                    mensaje.writeUTF("Opción no válida");
                    despliegaNuevamente(cn, serv, entrada);
                    break;
            }
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void despliegaNuevamente(Socket cn, ServerSocket serv, DataInputStream entrada) {
        try {
            /* VOLVER A CARGAR DATOS */
            DataInputStream entradaNS = new DataInputStream(cn.getInputStream());
            String mensajeNS = (String) entradaNS.readUTF();
            System.out.println("Mensaje del cliente: " + mensajeNS);

            /* ESCRIBIMOS DESDE EL SERVIDOR */
            DataOutputStream respuestaNS = new DataOutputStream(cn.getOutputStream());
            String menuNS = mostrarMenu();
            respuestaNS.writeUTF(menuNS);

            /* LEEMOS LO QUE QUIERE EL USUARIO */
            int operacionDeseadaNS = entradaNS.readInt();
            System.out.println("Operación deseada: " + operacionDeseadaNS);
            solicitaOperacion(operacionDeseadaNS, cn, serv, entradaNS);
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}