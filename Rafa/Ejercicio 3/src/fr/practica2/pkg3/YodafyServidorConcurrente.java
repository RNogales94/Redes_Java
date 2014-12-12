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
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Rafa Nogales
 */
//
// YodafyServidorConcurrente
// (CC) jjramos, 2012
//
public class YodafyServidorConcurrente {

    public static void main(String[] args) {

        // Puerto de escucha
        int port=8989;		
        //Debug// int peticiones_atendidas=0;
        String mensaje_del_cliente;

        ServerSocket socketServidor;
        Socket socketServicio;
        try {
            // Abrimos el socket en modo pasivo, escuchando el en puerto indicado por "port"
            //////////////////////////////////////////////////
            socketServidor = new ServerSocket(port);
            //////////////////////////////////////////////////
            
            // Mientras ... siempre!
            do {
                //Debug// System.out.println("Hemos atendido "+peticiones_atendidas+" peticiones.");
                // Aceptamos una nueva conexión con accept()
                /////////////////////////////////////////////////
                socketServicio = socketServidor.accept();
                //////////////////////////////////////////////////
            
                //Debug
                //PrintWriter outPrinter = new PrintWriter(socketServicio.getOutputStream(),true);
                //BufferedReader inReader = new BufferedReader(new InputStreamReader(socketServicio.getInputStream()));
                //mensaje_del_cliente = inReader.readLine();
                //System.out.println(mensaje_del_cliente);
                //End Debug

                // Creamos un objeto de la clase ProcesadorYodafy, pasándole como 
                // argumento el nuevo socket, para que realice el procesamiento
                // Este esquema permite que se puedan usar hebras más fácilmente.
                ProcesadorYodafy procesador=new ProcesadorYodafy(socketServicio);
                procesador.start();
                //Debug // peticiones_atendidas++;
            } while (true);

        } catch (IOException e) {
                System.err.println("Error al escuchar en el puerto "+port);
        }
    }
}
