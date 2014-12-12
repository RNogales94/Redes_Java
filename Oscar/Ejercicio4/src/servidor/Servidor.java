package servidor;

import java.net.DatagramSocket;
import java.net.SocketException;

//
// YodafyServidorIterativo
// (CC) jjramos, 2012
//
public class Servidor {

	public static void main(String[] args) {
	
		// Puerto de escucha
		int port=8989;
                DatagramSocket socketServidor = null;
		
		try {
			// Abrimos el socket en modo pasivo, escuchando el en puerto indicado por "port"
			//////////////////////////////////////////////////
			// ...serverSocket=... (completar)
			//////////////////////////////////////////////////
                        socketServidor = new DatagramSocket(port);
                    
                    
			// Mientras ... siempre!
			do {
				
				// Creamos un objeto de la clase ProcesadorYodafy, pasándole como 
				// argumento el nuevo socket, para que realice el procesamiento
				// Este esquema permite que se puedan usar hebras más fácilmente.
				Procesador procesador=new Procesador(socketServidor);
				procesador.procesa();
				
			} while (true);
			
		} catch (SocketException e) {
			System.err.println("Error al escuchar en el puerto "+port);
		}

	}

}