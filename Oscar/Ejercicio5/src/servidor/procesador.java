package servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class procesador extends Thread{
    	// Referencia a un socket para enviar/recibir las peticiones/respuestas
	private final Socket socketServicio;
        PrintWriter outPrinter;   
        BufferedReader inReader;
        private final Random random;
        private int numeroOculto,numeroAnterior;
        private boolean acierto = false;
        private int estado;
        private final int rango = 10;
       
        private int mid(int a, int b){
            return (int)((a+b)/2);
        }
        
        public procesador(Socket socketServicio) {
            random = new Random();
            numeroOculto = random.nextInt(rango)+1;
            this.socketServicio=socketServicio; 
            try{
                inReader = new BufferedReader(new InputStreamReader(socketServicio.getInputStream()));
                outPrinter = new PrintWriter(socketServicio.getOutputStream(),true);
            } catch(IOException e) {
                System.err.println("Error al obtener los flujos de entrada/salida.");
            }
        }
        
        @Override
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
                    
                    outPrinter.print(respuesta);
                    outPrinter.flush();
                    
                    if(!respuesta.equals("GANASTE\n")){
                        inReader.read(datosRecibidos);
                        preguntaCliente();
                    }
                }
            
            } catch(IOException e) {
                System.err.println("Error al obtener los flujos de entrada/salida.");
            }
        }
        
        
        public void preguntaCliente(){
            char [] buferEnvio=new char[1024], buferRecepcion=new char[1024];
            int bytesLeidos;
            /*
            He mejorado la táctica de juego del servidor haciendo que
            el numeroPensado se eligiese en base a una distribucion normal
            cuya mediana se encuentre en el punto medio del intervalo donde
            se encuentra la solucion.
            
            Tomando como desviacion estandar = 2 para que no sea tan facil 
            anticiparse a las jugada inicial
            Luego disminuimos la desviacion para que juege bien en intervalos 
            pequeños.
            */
            int numeroPensado = (int)Math.round(random.nextGaussian()*2+5);
            
            if(estado == 1){ //Hay que probar un numero mayor que el que he probado
                while(numeroPensado <= numeroAnterior){
                    numeroPensado = (int)Math.round(random.nextGaussian()*1.23+mid(numeroAnterior,rango));
                }
            }
            else if(estado == 2){ //Hay que probar un numero menor que el que he probado
                while(numeroPensado >= numeroAnterior){
                    numeroPensado = (int)Math.round(random.nextGaussian()*1.23+mid(numeroAnterior,0));
                }
            }
            String numero = String.valueOf(numeroPensado);
            outPrinter.print(numero);
            outPrinter.flush();
            
            try {
                bytesLeidos = inReader.read(buferRecepcion);
                estado = Integer.parseInt(new String(buferRecepcion,0,bytesLeidos));
                if(estado != 3)
                    numeroAnterior = numeroPensado;
                else{
                    acierto = true;
                    socketServicio.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(procesador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
}
