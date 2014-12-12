package cliente;

//
// YodafyServidorIterativo
// (CC) jjramos, 2012
//
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Cliente {

	public static void main(String[] args) {
		
		// Nombre del host donde se ejecuta el servidor:
		String host="localhost";
		// Puerto en el que espera el servidor:
		int port=8989;
		
                InetAddress direccion;
                
		// Socket para la conexión UDP
		DatagramPacket paquete=null;
                DatagramSocket socket=null;
		
                byte[] bufer = "Al monte del volcán debes ir sin demora".getBytes();;
                
		try {
			// Creamos un socket que se conecte a "hist" y "port":
			//////////////////////////////////////////////////////
			// socketServicio= ... (Completar)
			//////////////////////////////////////////////////////			
			socket=new DatagramSocket();
                        
                        direccion=InetAddress.getByName(host);
                        
                        paquete = new DatagramPacket(bufer, bufer.length, direccion, port);
                        
                        socket.send(paquete);
                        
                        paquete = new DatagramPacket(bufer, bufer.length);
                        
                        socket.receive(paquete);
                        
                        bufer = paquete.getData();
                        
			// MOstremos la cadena de caracteres recibidos:
			System.out.println("Recibido: ");
                        for(int i=0; i < bufer.length; i++){
				System.out.print((char)bufer[i]);
			}
			
			// Una vez terminado el servicio, cerramos el socket (automáticamente se cierran
			// el inpuStream  y el outputStream)
			//////////////////////////////////////////////////////
			// ... close(); (Completar)
			//////////////////////////////////////////////////////
			socket.close();
                        
			// Excepciones:
		} catch (UnknownHostException e) {
			System.err.println("Error: Nombre de host no encontrado.");
		} catch (IOException e) {
			System.err.println("Error de entrada/salida al abrir el socket.");
		}
	}
}