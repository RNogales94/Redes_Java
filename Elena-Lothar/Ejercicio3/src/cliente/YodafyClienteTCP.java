/*
 * Alumnos: Lothar Soto Palma y Elena Toro Perez
 * Añadimos a la clase ProcesadorYodafy "extends Thread" es decir hacemos que
con herencia sea una clase hija de Thread para aprovecharnos del multihebra
y asi hacer nuestro servidor concurrente eso si es necesario cambiar en el main
del servidor procesador.procesa() por procesador.start().
 */

package cliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class YodafyClienteTCP {

	public static void main(String[] args) {
		
		char []buferEnvio;
		char []buferRecepcion=new char[256];
		int bytesLeidos=0;
		
		// Nombre del host donde se ejecuta el servidor:
		String host="localhost";
		// Puerto en el que espera el servidor:
		int port=8080;
		
		// Socket para la conexión TCP
		Socket socketServicio=null;
		PrintWriter outPrinter;   
                BufferedReader inReader;
                
		try {
			// Creamos un socket que se conecte a "hist" y "port":
			//////////////////////////////////////////////////////
			socketServicio=new Socket(host,port);
			//////////////////////////////////////////////////////			
			
		
			// Si queremos enviar una cadena de caracteres por un OutputStream, hay que pasarla primero
			// a un array de bytes:
			buferEnvio="Al monte del volcán debes ir sin demora".toCharArray();
			// Enviamos el array por el outputStream;
			//////////////////////////////////////////////////////
                        outPrinter = new PrintWriter(socketServicio.getOutputStream(),true);
                        outPrinter.print(buferEnvio);
			//////////////////////////////////////////////////////
			
			// Aunque le indiquemos a TCP que queremos enviar varios arrays de bytes, sólo
			// los enviará efectivamente cuando considere que tiene suficientes datos que enviar...
			// Podemos usar "flush()" para obligar a TCP a que no espere para hacer el envío:
			//////////////////////////////////////////////////////
                        outPrinter.flush();
			//////////////////////////////////////////////////////
			
			// Leemos la respuesta del servidor. Para ello le pasamos un array de bytes, que intentará
			// rellenar. El método "read(...)" devolverá el número de bytes leídos.
			//////////////////////////////////////////////////////
                        inReader = new BufferedReader(new InputStreamReader(socketServicio.getInputStream()));
                        bytesLeidos = inReader.read(buferRecepcion);
			//////////////////////////////////////////////////////
			
			// MOstremos la cadena de caracteres recibidos:
			System.out.println("Recibido: ");
			for(int i=0;i<bytesLeidos;i++){
				System.out.print((char)buferRecepcion[i]);
			}
			
			// Una vez terminado el servicio, cerramos el socket 
			//////////////////////////////////////////////////////
			socketServicio.close();
			//////////////////////////////////////////////////////
			
			// Excepciones:
		} catch (UnknownHostException e) {
			System.err.println("Error: Nombre de host no encontrado.");
		} catch (IOException e) {
			System.err.println("Error de entrada/salida al abrir el socket.");
		}
	}
}
