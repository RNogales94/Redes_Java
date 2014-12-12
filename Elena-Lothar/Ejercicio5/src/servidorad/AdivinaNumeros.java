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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AdivinaNumeros extends Thread{
    	// Referencia a un socket para enviar/recibir las peticiones/respuestas
	private Socket socketServicio;
        PrintWriter outPrinter;   
        BufferedReader inReader;
        private Random random;
        private int numeroOculto,numeroAnterior;
        private boolean acierto = false;
        private int estado;
        
        public AdivinaNumeros(Socket socketServicio) {
            random = new Random();
            numeroOculto = random.nextInt(10)+1;
            this.socketServicio=socketServicio; 
            try{
                inReader = new BufferedReader(new InputStreamReader(socketServicio.getInputStream()));
                outPrinter = new PrintWriter(socketServicio.getOutputStream(),true);
            } catch(IOException e) {
                System.err.println("Error al obtener los flujso de entrada/salida.");
            }

        }
        
        public void preguntaCliente(){
            char [] buferEnvio=new char[1024], buferRecepcion=new char[1024];
            int bytesLeidos = 0;
            String respuesta = null;
            int numeroPensado = random.nextInt(10)+1;
            InputStreamReader leer = new InputStreamReader(System.in);
            BufferedReader buff = new BufferedReader(leer);
            if(estado == 1){
                while(numeroPensado <= numeroAnterior){
                    numeroPensado = random.nextInt(10)+1;
                }
            }
            else if(estado == 2){
                while(numeroPensado >= numeroAnterior){
                    numeroPensado = random.nextInt(10)+1;
                }
            }
            
            String numero = String.valueOf(numeroPensado);
            buferEnvio=numero.toCharArray();
            outPrinter.print(buferEnvio);
            outPrinter.flush();
            
            try {
                bytesLeidos = inReader.read(buferRecepcion);
                estado = Integer.parseInt(new String(buferRecepcion,0,bytesLeidos));
                if(estado == 1){
                    System.out.println("mayor");
                    numeroAnterior = numeroPensado;
                }
                else if(estado == 2){
                    System.out.println("menor");
                    numeroAnterior = numeroPensado;
                }
                else if(estado == 3){
                    System.out.println("GANA EL SERVIDOR");
                    acierto = true;
                    socketServicio.close();
                }
                    } catch (IOException ex) {
                Logger.getLogger(AdivinaNumeros.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        public void run(){
            char [] datosRecibidos=new char[1024];
            int bytesRecibidos=0;
            char [] datosEnviar;
            String respuesta = "";
            
            try{
                while(acierto == false && !respuesta.equals("GANASTE\n")){
                    bytesRecibidos = inReader.read(datosRecibidos);
                    int peticion=Integer.parseInt(new String(datosRecibidos,0,bytesRecibidos));

                    if(peticion < numeroOculto)
                        respuesta = "Fallo. El numero es mayor que " + peticion + "\n";
                    else if (peticion > numeroOculto)
                        respuesta = "Fallo. El numero es menor que " + peticion + "\n";
                    else if (peticion == numeroOculto){
                        respuesta = "GANASTE\n";
                    }

                    datosEnviar=respuesta.toCharArray();

                    outPrinter.print(datosEnviar);
                    outPrinter.flush();
                    
                    if(!respuesta.equals("GANASTE\n")){
                        inReader.read(datosRecibidos);
                        preguntaCliente();
                    }
                }
            
            } catch(IOException e) {
                System.err.println("Error al obtener los flujso de entrada/salida.");
            }
        }
}
