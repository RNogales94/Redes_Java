/*
 * Alumnos: Lothar Soto Palma y Elena Toro Perez
 * El cliente se encarga de elegir un numero oculto que el servidor debera 
acertar y el servidor otro numero, ahora por turnos se iran preguntando 
mutuamente si el numero dado es el correcto si no lo es se informa al opuesto
de que es menor o mayor que el dado. Una vez uno de los dos acierte se cierra el
socket de comunicacion, ademas el servidor usado es concurrente y permite 
interactuar a varios clientes en el mismo tiempo.
 */

package servidorad;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorAd {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int port=8080;
        byte []buffer=new byte[256];
        int bytesLeidos=0;
        ServerSocket socketServidor = null;
        Socket socketServicio = null;
        
        try {
            socketServidor=new ServerSocket(port);
            do {
                    socketServicio=socketServidor.accept();
                    AdivinaNumeros adivina = new AdivinaNumeros(socketServicio);
                    adivina.start();
                    AdivinaNumeros.yield();
                    
            } while (true);

        } catch (IOException e) {
                System.err.println("Error al escuchar en el puerto "+port);
        }

    }
} 
