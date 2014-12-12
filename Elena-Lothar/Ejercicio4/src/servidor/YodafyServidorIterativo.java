/*
 * Alumnos: Lothar Soto Palma y Elena Toro Perez
 * Modificamos los socket por DatagramSocket y usamos la clase DatagramPacket 
para enviar y recibir el bufer que contiene la frase a yodaficar. Todo esto 
para utilizar UDP y ademas muestra de ello es que la respuesta del servidor 
puede verse alterada.
 */

package servidor;

import java.io.IOException;
import java.net.DatagramSocket;


//
// YodafyServidorIterativo
// (CC) jjramos, 2012
//
public class YodafyServidorIterativo {

	public static void main(String[] args) {
	
		// Puerto de escucha
		int port=8080;
		// array de bytes auxiliar para recibir o enviar datos.
		byte []buffer=new byte[256];
		// Número de bytes leídos
		int bytesLeidos=0;
		
                DatagramSocket socketServidor = null;
                
		try {
			// Abrimos el socket en modo pasivo, escuchando el en puerto indicado por "port"
			//////////////////////////////////////////////////
			socketServidor=new DatagramSocket(port);
			//////////////////////////////////////////////////
			
			// Mientras ... siempre!
			do {
				// Creamos un objeto de la clase ProcesadorYodafy, pasándole como 
				// argumento el nuevo socket, para que realice el procesamiento
				// Este esquema permite que se puedan usar hebras más fácilmente.
				ProcesadorYodafy procesador=new ProcesadorYodafy(socketServidor);
				procesador.procesa();
			} while (true);
			
		} catch (IOException e) {
			System.err.println("Error al escuchar en el puerto "+port);
		}

	}

}
