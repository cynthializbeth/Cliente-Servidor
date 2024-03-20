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
public class Cliente{
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        int opcion = 0;
        try {
            Socket cliente = new Socket("localhost", 4500);
            /* MANDAMOS SALUDO AL SERVIDOR */
            DataOutputStream mensaje = new DataOutputStream(cliente.getOutputStream());
            mensaje.writeUTF("Hola servidor");
            /* LEEMOS LO QUE NOS MANDA EL SERVIDOR */
            // El menú de operaciones
            DataInputStream entrada = new DataInputStream(cliente.getInputStream());
            String mensaje2 = entrada.readUTF();
            System.out.println(mensaje2);
            /* ESCRIBIMOS LA OPCIÓN DEL USUARIO */
            // Que operación se desea realizar
            opcion = teclado.nextInt();
            mensaje.writeInt(opcion);
            hazOp(opcion, cliente, mensaje);
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void hazOp(int opcion, Socket cliente, DataOutputStream mensaje){
        Scanner teclado = new Scanner(System.in);
        int digito1 = 0;
        int digito2 = 0;
        try {
            System.out.println("La opción actual es: " + opcion);
            if (opcion == 4){
                cliente.close();
                System.out.println("Se ha cerrado la conexión");
            }else{
                if (opcion == 1){
                    /* El cliente lee en su terminal que se le pide el primer digito */
                    DataInputStream entradaPDS = new DataInputStream(cliente.getInputStream());
                    String mensajeDeSolicitarPDS = (String) entradaPDS.readUTF();
                    System.out.println(mensajeDeSolicitarPDS);
                    /* El cliente escribe el primer digito */
                    digito1 = teclado.nextInt();
                    mensaje.writeInt(digito1);
                    /* El cliente lee en su terminal que se le pide el segundo digito */
                    DataInputStream entradaSDS = new DataInputStream(cliente.getInputStream());
                    String mensajeDeSolicitarSDS = (String) entradaSDS.readUTF();
                    System.out.println(mensajeDeSolicitarSDS);
                    /* El cliente escribe el segundo digito */
                    digito2 = teclado.nextInt();
                    mensaje.writeInt(digito2);
                    /* El cliente lee el resultado de la suma */
                    DataInputStream entradaSuma = new DataInputStream(cliente.getInputStream());
                    String respSuma = (String) entradaSuma.readUTF();
                    System.out.println(respSuma);

                    /* SE VUELVEN A CARGAR DATOS */
                    DataOutputStream mensajeNS = new DataOutputStream(cliente.getOutputStream());
                    mensajeNS.writeUTF("Hola servidor");
                    /* LEEMOS LO QUE NOS MANDA EL SERVIDOR */
                    // El menú de operaciones
                    DataInputStream entradaNS = new DataInputStream(cliente.getInputStream());
                    String mensaje2NS = entradaNS.readUTF();
                    System.out.println(mensaje2NS);
                    /* ESCRIBIMOS LA OPCIÓN DEL USUARIO */
                    int op = teclado.nextInt();
                    mensaje.writeInt(op);
                    hazOp(op, cliente, mensajeNS);
                }
                /* RESTA */
                if (opcion == 2){
                    /* El cliente lee en su terminal que se le pide el primer digito */
                    DataInputStream entradaPDR = new DataInputStream(cliente.getInputStream());
                    String mensajeDeSolicitarPDR = (String) entradaPDR.readUTF();
                    System.out.println(mensajeDeSolicitarPDR);
                    /* El cliente escribe el primer digito */
                    digito1 = teclado.nextInt();
                    mensaje.writeInt(digito1);
                    /* El cliente lee en su terminal que se le pide el segundo digito */
                    DataInputStream entradaSDR = new DataInputStream(cliente.getInputStream());
                    String mensajeDeSolicitarSDR = (String) entradaSDR.readUTF();
                    System.out.println(mensajeDeSolicitarSDR);
                    /* El cliente escribe el segundo digito */
                    digito2 = teclado.nextInt();
                    mensaje.writeInt(digito2);
                    /* El cliente lee el resultado de la resta */
                    DataInputStream entradaResta = new DataInputStream(cliente.getInputStream());
                    String respResta = (String) entradaResta.readUTF();
                    System.out.println(respResta);

                    /* SE VUELVEN A CARGAR DATOS */
                    DataOutputStream mensajeNR = new DataOutputStream(cliente.getOutputStream());
                    mensajeNR.writeUTF("Hola servidor");
                    /* LEEMOS LO QUE NOS MANDA EL SERVIDOR */
                    // El menú de operaciones
                    DataInputStream entradaNR = new DataInputStream(cliente.getInputStream());
                    String mensaje2NR = entradaNR.readUTF();
                    System.out.println(mensaje2NR);
                    /* ESCRIBIMOS LA OPCIÓN DEL USUARIO */
                    int op = teclado.nextInt();
                    mensaje.writeInt(op);
                    hazOp(op, cliente, mensajeNR);
                }
                /* MULTIPLICACIÓN */
                if (opcion == 3){
                    /* El cliente lee en su terminal que se le pide el primer digito */
                    DataInputStream entradaPDM = new DataInputStream(cliente.getInputStream());
                    String mensajeDeSolicitarPDM = (String) entradaPDM.readUTF();
                    System.out.println(mensajeDeSolicitarPDM);
                    /* El cliente escribe el primer digito */
                    digito1 = teclado.nextInt();
                    mensaje.writeInt(digito1);
                    /* El cliente lee en su terminal que se le pide el segundo digito */
                    DataInputStream entradaSDM = new DataInputStream(cliente.getInputStream());
                    String mensajeDeSolicitarSDM = (String) entradaSDM.readUTF();
                    System.out.println(mensajeDeSolicitarSDM);
                    /* El cliente escribe el segundo digito */
                    digito2 = teclado.nextInt();
                    mensaje.writeInt(digito2);
                    /* El cliente lee el resultado de la multiplicación */
                    DataInputStream entradaMultiplicacion = new DataInputStream(cliente.getInputStream());
                    String respMultiplicacion = (String) entradaMultiplicacion.readUTF();
                    System.out.println(respMultiplicacion);

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
                    hazOp(op, cliente, mensajeNM);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
