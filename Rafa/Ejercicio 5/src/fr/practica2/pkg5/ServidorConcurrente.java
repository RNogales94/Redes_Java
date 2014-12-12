package fr.practica2.pkg5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class ServidorConcurrente {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /*
        Debug Gaussian Random:
        
        Random random = new Random();
        List<Integer> l = new ArrayList<>();
        int numero;
        for(int i=0; i<1000000; i++ ){
            numero = (int)Math.round(random.nextGaussian()+5);
            l.add(numero);
        }
        int [] contadores = new int[15];
        for(int i=0; i<1000000; i++){
            contadores[l.get(i)]++;
        }
        for(int i=0; i<15; i++)
            System.out.print(contadores[i]+" ");
        */
        int port=8080;
        ServerSocket socketServidor;
        Socket socketServicio ;
        
        try {
            socketServidor=new ServerSocket(port);
            do {
                socketServicio=socketServidor.accept();
                Adivinador adivina = new Adivinador(socketServicio);
                adivina.start();
                //Adivinador.yield();
            } while (true);

        } catch (IOException e) {
                System.err.println("Error al escuchar en el puerto "+port);
        }

    }
} 