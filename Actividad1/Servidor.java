import java.net.ServerSocket;
import java.net.Socket;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Servidor, el servidor que se conecta al cliente
 * 
 * @author cynthializbeth
 * @version 1.0
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

    /**
     * Método principal, el servidor se conecta al cliente y comunica
    */
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

    /**
     * Método que muestra el menú de opciones al usuario
     * 
     * @return el menú de opciones
     */
    public static String mostrarMenu() {
        return colorAmarillo + "\n-----[ Bienvenido ]-----" + resetColor
                + "\nTeclea una opción: \n1. Jugar a adivinar un número \n2. Jugar Ahorcado \n3. salir";
    }

    /**
     * Método que realiza la operación que el usuario desea
     * 
     * @param op,      la opción que el usuario desea realizar
     * @param cn,      el socket del cliente
     * @param serv,    el servidor
     * @param entrada, el mensaje que se le enviará al servidor
     */
    public static void solicitaOperacion(int op, Socket cn, ServerSocket serv, DataInputStream entrada) {
        try {
            // Permite enviar mensajes al cliente
            DataOutputStream mensaje = new DataOutputStream(cn.getOutputStream());
            switch (op) {
                case 1:
                    mensaje.writeUTF(colorRojo + "\nAdivina el número: " + resetColor
                            + "Elige un número del 1 al 100, tienes 7 intentos para adivinarlo");
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
                            mensaje.writeUTF(colorAmarillo + "¡Felicidades, has adivinado el número!" + resetColor
                                    + "\uD83D\uDE0A");
                            break;
                        } else if (respuesta < numero && intentos > 0) {
                            mensaje.writeUTF("El número que pensé es más grande, te quedan " + colorRojo + intentos
                                    + resetColor + " intentos");
                        } else if (respuesta > numero && intentos > 0) {
                            mensaje.writeUTF("El número que pensé es menor, te quedan " + colorRojo + intentos
                                    + resetColor + " intentos");
                        }
                        iteraciones--;
                        intentos--;
                    }
                    if (iteraciones < 0) {
                        mensaje.writeUTF("Lo siento, has perdido \uD83D\uDE2D");
                    }
                    despliegaNuevamente(cn, serv);
                    break;
                case 2:
                    // Lista con 15 paises
                    String[] paises = { "Mexico", "Canada", "Japon", "Guatemala", "Belice", "Honduras", "Chile",
                            "Nicaragua", "Haiti", "Panama", "Colombia", "Venezuela", "Brasil", "Argentina", "Uruguay" };
                    int indice = (int) (Math.random() * 15);
                    // El país a adivinar
                    String pais = paises[indice];
                    char guion = '_';
                    String palabraGuiones = String.valueOf(guion).repeat(pais.length());
                    mensaje.writeUTF(colorRojo + "\nAhorcado: " + resetColor + "Pensé en un país de " + pais.length()
                            + " letras trata de adivinar que letras lo componen \uD83D\uDE0A\n" + palabraGuiones + "\n");
                    System.out.println("País a adivinar: " + pais);
                    int iteraciones2 = 5;
                    String letra = "";
                    int intentos2 = iteraciones2;
                    String salidaServidor = "";
                    // El usuario tiene hasta que se equivoque de letra 6 veces
                    while (iteraciones2 >= 0) {
                        letra = entrada.readUTF();
                        if (!letra.isEmpty() && (pais.contains(letra) || pais.contains(letra.toUpperCase()))) {
                            for (int i = 0; i < pais.length(); i++) {
                                if (pais.charAt(i) == letra.charAt(0) || pais.charAt(i) == letra.toUpperCase().charAt(0)){
                                    StringBuilder sb = new StringBuilder(palabraGuiones);
                                    if (i == 0) {
                                        sb.setCharAt(i, letra.toUpperCase().charAt(0));
                                        palabraGuiones = sb.toString();
                                    } else {
                                    sb.setCharAt(i, letra.charAt(0));
                                    palabraGuiones = sb.toString();
                                    }
                                }
                            }
                            salidaServidor = "La letra " + letra + " está en la palabra " + palabraGuiones;
                            if (palabraGuiones.equals(pais) || palabraGuiones.equals(pais.toUpperCase())) {
                                salidaServidor += colorAmarillo + "\n¡Felicidades, has adivinado la palabra!" + resetColor
                                        + "\uD83D\uDE0A";
                                mensaje.writeUTF(salidaServidor);
                                break;
                            } else {
                                mensaje.writeUTF(salidaServidor);
                            }
                        } else {
                            salidaServidor = "La letra " + letra + " no está en la palabra" + dameDibujo(intentos2);
                            iteraciones2--;
                            intentos2--;
                            if (iteraciones2 < 0) {
                                salidaServidor += "Lo siento, has perdido \uD83D\uDE2D";
                            }
                            mensaje.writeUTF(salidaServidor);
                        }
                    }
                    despliegaNuevamente(cn, serv);
                    break;
                case 3:
                    mensaje.writeUTF("Adios");
                    serv.close();
                    break;
                default:
                    mensaje.writeUTF("Opción no válida");
                    despliegaNuevamente(cn, serv);
                    break;
            }
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Método que despliega nuevamente el menú de opciones al usuario
     * 
     * @param cn,   el socket del cliente
     * @param serv, el servidor
     */
    public static void despliegaNuevamente(Socket cn, ServerSocket serv) {
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

    /**
     * Método que despliega el dibujo del ahorcado
     * 
     * @param intentos, los intentos que le quedan al usuario
     * @return el dibujo del ahorcado
     */
    public static String dameDibujo(int intentos) {
        String dibujo = "";
        switch (intentos) {
            case 5:
                dibujo = "\n O \n";
                break;
            case 4:
                dibujo = "\n O \n | \n";
                break;
            case 3:
                dibujo = "\n O \n/| \n";
                break;
            case 2:
                dibujo = "\n O \n/|\\ \n";
                break;
            case 1:
                dibujo = "\n O \n/|\\ \n/ \n";
                break;   
            default:
                dibujo = "\n O \n/|\\ \n/ \\ \n";
                break;
        }
        return dibujo;
    }
}