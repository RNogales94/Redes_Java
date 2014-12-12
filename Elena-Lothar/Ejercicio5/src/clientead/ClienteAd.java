/*
 * Alumnos: Lothar Soto Palma y Elena Toro Perez
 * El cliente se encarga de elegir un numero oculto que el servidor debera 
acertar y el servidor otro numero, ahora por turnos se iran preguntando 
mutuamente si el numero dado es el correcto si no lo es se informa al opuesto
de que es menor o mayor que el dado. Una vez uno de los dos acierte se cierra el
socket de comunicacion, ademas el servidor usado es concurrente y permite 
interactuar a varios clientes en el mismo tiempo.
 */

package clientead;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClienteAd {
    	public static void main(String[] args) {
		
		char []buferEnvio;
		char []buferRecepcion=new char[256];
		int bytesLeidos=0,n=0,numeroPensado;
                String resultado = "";
                int estado =0;
		
		// Nombre del host donde se ejecuta el servidor:
		String host="localhost";
		// Puerto en el que espera el servidor:
		int port=8080;
		
		// Socket para la conexión TCP
		Socket socketServicio=null;
		PrintWriter outPrinter;   
                BufferedReader inReader;
                
                
		try {
                        socketServicio=new Socket(host,port);
                        inReader = new BufferedReader(new InputStreamReader(socketServicio.getInputStream()));
                        outPrinter = new PrintWriter(socketServicio.getOutputStream(),true);   
                        InputStreamReader leer = new InputStreamReader(System.in);
                        BufferedReader buff = new BufferedReader(leer);
                        
                        System.out.println("Introduce un numero secreto: ");
                        numeroPensado = Integer.parseInt(buff.readLine());
                        
                        while(!(resultado.equals("GANASTE\n")) && estado != 3){
                            System.out.println("Adivina el numero!! \nIntroduce el número: ");
                            String numero = buff.readLine();
                            buferEnvio=numero.toCharArray();

                            outPrinter.print(buferEnvio);

                            outPrinter.flush();

                            bytesLeidos = inReader.read(buferRecepcion);

                            System.out.println("Recibido: ");
                            for(int i=0;i<bytesLeidos;i++){
                                    System.out.print((char)buferRecepcion[i]);
                            }
                            
                            outPrinter.print("OK");
                            outPrinter.flush();
                            
                            resultado = new String(buferRecepcion,0,bytesLeidos);
                            
                            
                            if(!resultado.equals("GANASTE\n")){
                                //------------------Recibe-------------------------
                                bytesLeidos = inReader.read(buferRecepcion);
                                n = Integer.parseInt(new String(buferRecepcion,0,bytesLeidos));
                                if(n < numeroPensado)
                                    buferEnvio = "1".toCharArray();
                                else if (n > numeroPensado)
                                    buferEnvio = "2".toCharArray();
                                else if (n == numeroPensado){
                                    buferEnvio = "3".toCharArray();
                                }
                                outPrinter.print(buferEnvio);
                                outPrinter.flush();
                                
                                estado = Integer.parseInt(new String(buferEnvio,0,bytesLeidos));
                                //-------------------------------------------------
                            }
                        }
                        
			socketServicio.close();
			
			// Excepciones:
		} catch (UnknownHostException e) {
			System.err.println("Error: Nombre de host no encontrado.");
		} catch (IOException e) {
			System.err.println("Error de entrada/salida al abrir el socket.");
		}
	}
}
