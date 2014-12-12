package servidor;

//
// YodafyServidorIterativo
// (CC) jjramos, 2012
//
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Random;


//
// Nota: si esta clase extendiera la clase Thread, y el procesamiento lo hiciera el método "run()",
// ¡Podríamos realizar un procesado concurrente! 
//
public class Procesador {
	// Referencia a un socket para enviar/recibir las peticiones/respuestas
	private final DatagramSocket socketServicio;
	
	// Para que la respuesta sea siempre diferente, usamos un generador de números aleatorios.
	private final Random random;
	
	// Constructor que tiene como parámetro una referencia al socket abierto en por otra clase
	public Procesador(DatagramSocket socketServicio) {
		this.socketServicio=socketServicio;
		random=new Random();
	}
	
	
	// Aquí es donde se realiza el procesamiento realmente:
	void procesa(){
		
		// Nombre del host donde se ejecuta el cliente:
		String host;
		// Puerto en el que espera al cliente:
		int port;
		
                InetAddress direccion;
                
		// Socket para la conexión UDP
		DatagramPacket paquete=null;
                
                byte[] bufer = new byte[1024];
                
		try {
			paquete = new DatagramPacket(bufer, bufer.length);
                        
                        socketServicio.receive(paquete);
                
                        direccion = paquete.getAddress();
                        port = paquete.getPort();
                        
                        bufer = paquete.getData();
                
                        // Yoda hace su magia:
			// Creamos un String a partir de un array de bytes de tamaño "bytesRecibidos":
			String peticion=new String(bufer,0,bufer.length);
			// Yoda reinterpreta el mensaje:
			String respuesta=yodaDo(peticion);
			// Convertimos el String de respuesta en una array de bytes:
			bufer = respuesta.getBytes();
			
			// Enviamos la traducción de Yoda:
			////////////////////////////////////////////////////////
			// ... write ... datosEnviar... datosEnviar.length ... (Completar)
			////////////////////////////////////////////////////////
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
            for (String item : s) {
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
