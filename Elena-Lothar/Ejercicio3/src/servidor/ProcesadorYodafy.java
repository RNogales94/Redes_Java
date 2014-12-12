/*
 * Alumnos: Lothar Soto Palma y Elena Toro Perez
 * Añadimos a la clase ProcesadorYodafy "extends Thread" es decir hacemos que
con herencia sea una clase hija de Thread para aprovecharnos del multihebra
y asi hacer nuestro servidor concurrente eso si es necesario cambiar en el main
del servidor procesador.procesa() por procesador.start().
 */

package servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;


//
// Nota: si esta clase extendiera la clase Thread, y el procesamiento lo hiciera el método "run()",
// ¡Podríamos realizar un procesado concurrente! 
//
public class ProcesadorYodafy extends Thread{
	// Referencia a un socket para enviar/recibir las peticiones/respuestas
	private Socket socketServicio;
	
	// Para que la respuesta sea siempre diferente, usamos un generador de números aleatorios.
	private Random random;
	
	// Constructor que tiene como parámetro una referencia al socket abierto en por otra clase
	public ProcesadorYodafy(Socket socketServicio) {
		this.socketServicio=socketServicio;
		random=new Random();
	}
	
	
	// Aquí es donde se realiza el procesamiento realmente:
	public void run(){
		
		// Como máximo leeremos un bloque de 1024 bytes. Esto se puede modificar.
		char [] datosRecibidos=new char[1024];
		int bytesRecibidos=0;
		
		// Array de bytes para enviar la respuesta. Podemos reservar memoria cuando vayamos a enviarka:
		char [] datosEnviar;
		
                PrintWriter outPrinter;   
                BufferedReader inReader;
                
		try {
			// Obtiene los flujos de escritura/lectura
			//inputStream=socketServicio.getInputStream();
			//outputStream=socketServicio.getOutputStream();
			
			// Lee la frase a Yodaficar:
			////////////////////////////////////////////////////////
                        inReader = new BufferedReader(new InputStreamReader(socketServicio.getInputStream()));
                        bytesRecibidos = inReader.read(datosRecibidos);
			////////////////////////////////////////////////////////
			
			// Yoda hace su magia:
			// Creamos un String a partir de un array de bytes de tamaño "bytesRecibidos":
			String peticion=new String(datosRecibidos,0,bytesRecibidos);
			// Yoda reinterpreta el mensaje:
			String respuesta=yodaDo(peticion);
			// Convertimos el String de respuesta en una array de bytes:
			datosEnviar=respuesta.toCharArray();
			
			// Enviamos la traducción de Yoda:
			////////////////////////////////////////////////////////
                        outPrinter = new PrintWriter(socketServicio.getOutputStream(),true);
                        outPrinter.print(datosEnviar);
                        outPrinter.flush();
			////////////////////////////////////////////////////////
			
			
			
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
