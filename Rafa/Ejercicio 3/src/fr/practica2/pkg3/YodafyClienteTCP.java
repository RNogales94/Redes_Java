/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fr.practica2.pkg3;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *
 * @author Rafa Nogales
 */
//
// YodafyServidorIterativo
// (CC) jjramos, 2012
//

public class YodafyClienteTCP {
    
    public static void main(String[] args) {
    
        String buferEnvio;
        String buferRecepcion;
        int bytesLeidos=0;

        // Nombre del host donde se ejecuta el servidor:
        String host="localhost";
        // Puerto en el que espera el servidor:
        int port=8989;

        // Socket para la conexión TCP
        Socket socketServicio=null;		
        try {
            // Creamos un socket que se conecte a "host" y "port":
            //////////////////////////////////////////////////////
            socketServicio = new Socket(host, port);
            //////////////////////////////////////////////////////			
            
            //Cambio las instrucciones:
                //InputStream inputStream = socketServicio.getInputStream();
                //OutputStream outputStream = socketServicio.getOutputStream();
            //Por estas otras:
            PrintWriter outPrinter = new PrintWriter(socketServicio.getOutputStream(),true);
            BufferedReader inReader = new BufferedReader(new InputStreamReader(socketServicio.getInputStream()));

            buferEnvio = "Al monte del volcan debes ir sin demora\n";

            // Enviamos el string por el outPrinter;
            //////////////////////////////////////////////////////
            outPrinter.print(buferEnvio);
            //////////////////////////////////////////////////////

            // Aunque le indiquemos a TCP que queremos enviar varios arrays de bytes, sólo
            // los enviará efectivamente cuando considere que tiene suficientes datos que enviar...
            // Podemos usar "flush()" para obligar a TCP a que no espere para hacer el envío:
            //////////////////////////////////////////////////////
            outPrinter.flush();
            //////////////////////////////////////////////////////
            
            // Leemos la respuesta del servidor.
            //////////////////////////////////////////////////////
            buferRecepcion = inReader.readLine();
            //////////////////////////////////////////////////////
            
            // Mostremos la cadena de caracteres recibidos:
            System.out.println("Recibido: ");
            System.out.print(buferRecepcion);
            

            // Una vez terminado el servicio, cerramos el socket (automáticamente se cierran
            // el inReader  y el outPrinter)
            //////////////////////////////////////////////////////
            outPrinter.close();
            inReader.close();
            //////////////////////////////////////////////////////

            // Excepciones:
        } catch (UnknownHostException e) {
                System.err.println("Error: Nombre de host no encontrado.");
        } catch (IOException e) {
                System.err.println("Error de entrada/salida al abrir el socket.");
        }
    }
}
