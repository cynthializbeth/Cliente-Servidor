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
    public static void main(String[] args) {
        try {
            ServerSocket servidor = new ServerSocket(4500);
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
        return "Servidor: \nTeclea una opción: \n1. Sumar \n2. Restar \n3. Multiplicar \n4. Salir";
    }

    public static void solicitaOperacion(int op, Socket cn, ServerSocket serv, DataInputStream entrada) {
        try {
           int suma = 0;
           int resta = 0;
           int multiplicacion = 0;
           if (op == 4){
            serv.close();
           }else{
            /* SUMA */
            if (op == 1){
                /* Le pedimos al cliente el primer valor de la suma */
                DataOutputStream pdSuma = new DataOutputStream(cn.getOutputStream());
                pdSuma.writeUTF("Dame el primer digito ");

                /* Leemos el primer valor de la suma */
                int digito1 = entrada.readInt();
                suma += digito1;

                /* Le pedimos al cliente el segundo valor de la suma */
                DataOutputStream sdSuma = new DataOutputStream(cn.getOutputStream());
                sdSuma.writeUTF("Dame el segundo digito ");

                /* Leemos el segundo valor de la suma */
                int digito2 = entrada.readInt();
                suma += digito2;

                /* Mostramos el resultado de la suma */
                DataOutputStream resultadoSuma = new DataOutputStream(cn.getOutputStream());
                resultadoSuma.writeUTF("El resultado de la suma es: " + suma);

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
            }
            /* RESTA */
            if (op == 2){
                /* Le pedimos al cliente el primer valor de la resta */
                DataOutputStream pdResta = new DataOutputStream(cn.getOutputStream());
                pdResta.writeUTF("Dame el primer digito ");

                /* Leemos el primer valor de la resta */
                int digito1 = entrada.readInt();
                resta += digito1;

                /* Le pedimos al cliente el segundo valor de la resta */
                DataOutputStream sdResta = new DataOutputStream(cn.getOutputStream());
                sdResta.writeUTF("Dame el segundo digito ");

                /* Leemos el segundo valor de la suma */
                int digito2 = entrada.readInt();
                resta -= digito2;

                /* Mostramos el resultado de la resta */
                DataOutputStream resultadoResta = new DataOutputStream(cn.getOutputStream());
                resultadoResta.writeUTF("El resultado de la resta es: " + resta);

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
            }
            /* MULTIPLICACIÓN */
            if (op == 3){
                /* Le pedimos al cliente el primer valor de la multiplicación */
                DataOutputStream pdMultiplicacion = new DataOutputStream(cn.getOutputStream());
                pdMultiplicacion.writeUTF("Dame el primer digito ");

                /* Leemos el primer valor de la multiplicación */
                int digito1 = entrada.readInt();
                multiplicacion += digito1;

                /* Le pedimos al cliente el segundo valor de la multiplicación */
                DataOutputStream sdMultiplicacion = new DataOutputStream(cn.getOutputStream());
                sdMultiplicacion.writeUTF("Dame el segundo digito ");

                /* Leemos el segundo valor de la multiplicación */
                int digito2 = entrada.readInt();
                multiplicacion *= digito2;

                /* Mostramos el resultado de la multiplicación */
                DataOutputStream resultadoMultiplicacion = new DataOutputStream(cn.getOutputStream());
                resultadoMultiplicacion.writeUTF("El resultado de la multiplicación es: " + multiplicacion);

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
            }
           }
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}