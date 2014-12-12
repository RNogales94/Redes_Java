/*
 * Alumnos: Lothar Soto Palma y Elena Toro Perez
 * Modificamos los socket por DatagramSocket y usamos la clase DatagramPacket 
para enviar y recibir el bufer que contiene la frase a yodaficar. Todo esto 
para utilizar UDP y ademas muestra de ello es que la respuesta del servidor 
puede verse alterada.
 */

package cliente;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class YodafyClienteUDP {

	public static void main(String[] args) {
		
                InetAddress direccion;
		
		// Nombre del host donde se ejecuta el servidor:
		String host="localhost";
		// Puerto en el que espera el servidor:
		int port=8080;
                byte []bufer = "Al monte del volcán debes ir sin demora".getBytes();
                
		// Socket para la conexión UDP
		DatagramSocket socketServicio=null;
                DatagramPacket paquete = null;
                                
		try {
                        socketServicio = new DatagramSocket();
                        direccion = InetAddress.getByName(host);
						
                        paquete = new DatagramPacket(bufer, bufer.length, direccion, port);
                        socketServicio.send(paquete);
			
                        paquete = new DatagramPacket(bufer, bufer.length);
                        socketServicio.receive(paquete);
                        
                        bufer = paquete.getData();
			
                        
			// MOstremos la cadena de caracteres recibidos:
			System.out.println("Recibido: ");
			for(int i=0;i<bufer.length;i++){
				System.out.print((char)bufer[i]);
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
