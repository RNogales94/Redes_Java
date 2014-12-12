package fr.practica2.pkg5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClienteAdivinador {
    public static void main(String[] args) {

        char []buferEnvio;
        char []buferRecepcion=new char[256];
        int bytesLeidos=0,preguntaDelServidor=0,miNumero;
        String resultado = "";
        int estado =0;

        // Nombre del host donde se ejecuta el servidor:
        String host="localhost";
        // Puerto en el que espera el servidor:
        int port=8080;

        // Socket para la conexión TCP
        Socket socketServicio;
        PrintWriter outPrinter;   
        BufferedReader inReader;
        Scanner capt;

        try {
            //Nos conectamos al servidor
            socketServicio=new Socket(host,port);
            //I/O con el socket
            inReader = new BufferedReader(new InputStreamReader(socketServicio.getInputStream()));
            outPrinter = new PrintWriter(socketServicio.getOutputStream(),true);
            
            // I/O con el teclado
            capt = new Scanner(System.in);
            
            System.out.print("Introduce un numero secreto: ");
            miNumero = capt.nextInt();
            while(!(resultado.equals("GANASTE\n")) && estado != 3){
                System.out.print("Adivina el numero!! \nIntroduce el número: ");
                String numero = Integer.toString(capt.nextInt());

                outPrinter.print(numero);
                outPrinter.flush();

                resultado = inReader.readLine();
                System.out.println("Recibido: "+resultado);

                outPrinter.print("OK");
                outPrinter.flush();
                
                if(!resultado.equals("GANASTE\n")){
                    // Ahora el servidor nos pregunta a nosotros
                    // Y respondermos con:
                    //      "El numero que he pensado es mayor (1)"
                    //      "El numero que he pensado es menor (2)"
                    //      "Has acertado                      (3)"
                    bytesLeidos = inReader.read(buferRecepcion);
                    preguntaDelServidor = Integer.parseInt(new String(buferRecepcion,0,bytesLeidos));
                    String estado_string = null;
                    if(preguntaDelServidor < miNumero)
                        estado_string = "1";
                    else if (preguntaDelServidor > miNumero)
                        estado_string = "2";
                    else if (preguntaDelServidor == miNumero){
                        estado_string = "3";
                    }
                    outPrinter.print(estado_string);
                    outPrinter.flush();    
                    estado = Integer.parseInt(estado_string);
                    if(estado == 3){
                        System.out.println("Has perdido.");
                    }
                    //-------------------------------------------------
                }
                else
                    System.out.println(resultado);
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