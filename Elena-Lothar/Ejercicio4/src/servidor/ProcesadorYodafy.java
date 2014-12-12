/*
 * Alumnos: Lothar Soto Palma y Elena Toro Perez
 * Modificamos los socket por DatagramSocket y usamos la clase DatagramPacket 
para enviar y recibir el bufer que contiene la frase a yodaficar. Todo esto 
para utilizar UDP y ademas muestra de ello es que la respuesta del servidor 
puede verse alterada.
 */

package servidor;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Random;


//
// Nota: si esta clase extendiera la clase Thread, y el procesamiento lo hiciera el método "run()",
// ¡Podríamos realizar un procesado concurrente! 
//
public class ProcesadorYodafy {
	// Referencia a un socket para enviar/recibir las peticiones/respuestas
	private DatagramSocket socketServicio;
        private DatagramPacket paquete;
	
	// Para que la respuesta sea siempre diferente, usamos un generador de números aleatorios.
	private Random random;
	
	// Constructor que tiene como parámetro una referencia al socket abierto en por otra clase
	public ProcesadorYodafy(DatagramSocket socketServicio) {
		this.socketServicio=socketServicio;
		random=new Random();
	}
	
	
	// Aquí es donde se realiza el procesamiento realmente:
	void procesa(){
		
		// Como máximo leeremos un bloque de 1024 bytes. Esto se puede modificar.
		byte [] bufer=new byte[1024];
		InetAddress direccion;
                int port;
		// Array de bytes para enviar la respuesta. Podemos reservar memoria cuando vayamos a enviarka:
                
		try {
			// Lee la frase a Yodaficar:
                        paquete = new DatagramPacket(bufer, bufer.length);
                        socketServicio.receive(paquete);
                        
                        direccion = paquete.getAddress();
                        port = paquete.getPort();
                        bufer = paquete.getData();
			
			// Yoda hace su magia:
			String peticion=new String(bufer,0,bufer.length);
			// Yoda reinterpreta el mensaje:
			String respuesta=yodaDo(peticion);
			// Convertimos el String de respuesta en una array de bytes:
			bufer=respuesta.getBytes();
			
			// Enviamos la traducción de Yoda:
                        paquete = new DatagramPacket(bufer, bufer.length, direccion, port);
                        socketServicio.send(paquete);			
			
		} catch (IOException e) {
			System.err.println("Error al obtener los flujso de entrada/salida.");
		}

	}

	// Yoda interpreta una frase y la devuelve en su "dialecto":
	private String yodaDo(String peticion) {
		// Desordenamos las palabras:
		String[] s = peticion.split(" ");
		String resultado="";
		
		for(int i=0;i<s.length;i++){
			int j=random.nextInt(s.length);
			int k=random.nextInt(s.length);
			String tmp=s[j];
			
			s[j]=s[k];
			s[k]=tmp;
		}
		
		resultado=s[0];
		for(int i=1;i<s.length;i++){
		  resultado+=" "+s[i];
		}
		
		return resultado;
	}
}
