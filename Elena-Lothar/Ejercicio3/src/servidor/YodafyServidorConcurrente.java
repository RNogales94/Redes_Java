/*
 * Alumnos: Lothar Soto Palma y Elena Toro Perez
 * Añadimos a la clase ProcesadorYodafy "extends Thread" es decir hacemos que
con herencia sea una clase hija de Thread para aprovecharnos del multihebra
y asi hacer nuestro servidor concurrente eso si es necesario cambiar en el main
del servidor procesador.procesa() por procesador.start().
 */

package servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

//
// YodafyServidorConcurrente
// (CC) jjramos, 2012
//
public class YodafyServidorConcurrente {

	public static void main(String[] args) {
	
		// Puerto de escucha
		int port=8080;
		// array de bytes auxiliar para recibir o enviar datos.
		byte []buffer=new byte[256];
		// Número de bytes leídos
		int bytesLeidos=0;
                ServerSocket socketServidor = null;
                Socket socketServicio = null;
		
		try {
			// Abrimos el socket en modo pasivo, escuchando el en puerto indicado por "port"
			//////////////////////////////////////////////////
			socketServidor=new ServerSocket(port);
			//////////////////////////////////////////////////
			
			// Mientras ... siempre!
			do {
				
				// Aceptamos una nueva conexión con accept()
				/////////////////////////////////////////////////
				socketServicio=socketServidor.accept();
				//////////////////////////////////////////////////
				
				// Creamos un objeto de la clase ProcesadorYodafy, pasándole como 
				// argumento el nuevo socket, para que realice el procesamiento
				// Este esquema permite que se puedan usar hebras más fácilmente.
				ProcesadorYodafy procesador=new ProcesadorYodafy(socketServicio);
                                procesador.start();
                                procesador.yield();
				
			} while (true);
			
		} catch (IOException e) {
			System.err.println("Error al escuchar en el puerto "+port);
		}

	}

}
