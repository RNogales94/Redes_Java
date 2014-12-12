/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fr.practica2.pkg4;

import java.net.DatagramSocket;
import java.net.SocketException;

//
// YodafyServidorIterativo
// (CC) jjramos, 2012
//
public class YodafyServidorIterativo {
    public static void main(String[] args) {

        int port=8989;        // Puerto de escucha
        DatagramSocket socketServidor;

        try {
            socketServidor = new DatagramSocket(port);
            // Mientras ... siempre!
            while(true){
                ProcesadorYodafy procesador=new ProcesadorYodafy(socketServidor);
                procesador.procesa();
            }

        } catch (SocketException e) {
                System.err.println("Error al escuchar en el puerto "+port);
        }
    }
}
