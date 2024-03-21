import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Scanner;

/**
 * Clase que descarga un archivo de un servidor
 *
 * @author cynthializbeth
 * @version 1.0
 */
public class DownloadFile {
  /* Scanner para leer datos del teclado */
  private static Scanner scanner = new Scanner(System.in);
  /* Variable que indica si se puede descargar un archivo */
  private static boolean puedeDescargar = false;
  /* Código de color ANSI para amarillo */
  static String colorAmarillo = "\u001B[33m";
  /* Código de color ANSI para rojo */
  static String colorRojo = "\u001B[31m";
  /* Código de color ANSI para azul */
  static String colorAzul = "\u001B[34m";
  /* Restablecer el color a su estado original */
  static String resetColor = "\u001B[0m";

  /**
   * Método principal, despliega el menú del programa
   */
  public static void main(String[] args) throws IOException {
    despliegaMenu();
  }

  /**
   * Método que despliega el menú principal del programa
   * @throws IOException, si hay un error al descargar el archivo
   */
  public static void despliegaMenu() throws IOException {
    String option = "";
    System.out.println(colorAzul+"Menú del programa:"+resetColor);
    System.out.println("1. Establecer conexión");
    System.out.println("2. Cerrar conexión");
    System.out.println("3. Descargar un archivo del servidor");
    System.out.println("4. Salir");
    System.out.print("Elige una opción: ");
    option = scanner.nextLine();
    switch (option) {
      case "1":
        puedeDescargar = true;
        System.out.println(colorAmarillo+"Conexión establecida ... [POST 201]"+resetColor);
        despliegaMenu();
        break;
      case "2":
        puedeDescargar = false;
        System.out.println(colorAmarillo+"Conexión cerrada ... [POST 202]" + resetColor);
        despliegaMenu();
        break;
      case "3":
        menuDescarga();
        break;
      case "4":
        System.out.println("Adiós");
        break;
      default:
        System.out.println(colorAmarillo+"Solicitud erronea ... [POST 400]" + resetColor);
        break;
    }
  }

  public static void menuDescarga() throws IOException {
    if (puedeDescargar) {
      System.out.println(colorAzul+"Elige archivo a descargar:"+resetColor);
      System.out.println("1. fabula");
      System.out.println("2. recetario");
      System.out.println("3. historia");
      System.out.print("Archivo elegido: ");
      String archivo = scanner.nextLine();
      switch (archivo) {
        case "1":
          descargaArchivo("fabula", "archivo1");
          break;
        case "2":
          descargaArchivo("recetas", "archivo2");
          break;
        case "3":
          descargaArchivo("flores_para_algernon", "archivo3");
          break;
        default:
          System.err.println(colorAmarillo+"Solicitud erronea ... [POST 400, POST 404]" + resetColor);
          break;
      }
    } else {
      System.out.println(colorAmarillo+"No autorizado ... [POST 401]" + resetColor);
      despliegaMenu();
    }
  }

  public static void descargaArchivo(String nombreArchivo, String nuevo) throws IOException {
    URL fetchWebsite = new URL(
        "https://orienta.mx/redes/" + nombreArchivo + ".pdf");

    ReadableByteChannel readableByteChannel = Channels.newChannel(fetchWebsite.openStream());

    try (FileOutputStream fos = new FileOutputStream(nuevo + ".pdf")) {

      fos.getChannel().transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
    }
    System.out.println(colorAmarillo+"Archivo descargado ... [POST 200]" + resetColor);
  }

}