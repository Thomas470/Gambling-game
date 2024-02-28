//Thomas Frentzel

import java.util.concurrent.Semaphore;

public class Main {
    public static void main(String[] args){
        Semaphore barreira1 = new Semaphore(0);
        Semaphore barreira2 = new Semaphore(1); 
        Semaphore mutex = new Semaphore(1);// Cria o mutex
        int[] counter = new int[1];// Cria o counter
        int[] numeros = new
         int[1];// Cria o numeros de threads
        int[] rolls = new int[4];
        boolean[] done = new boolean[1];// Cria o booleano de termino 

        //cria as threads
        Jogador j1 = new Jogador(barreira1, barreira2, mutex, counter, numeros, done, rolls, 0);
        Jogador j2 = new Jogador(barreira1, barreira2, mutex, counter, numeros, done, rolls, 1); 
        Jogador j3 = new Jogador(barreira1, barreira2, mutex, counter, numeros, done, rolls, 2);
        Jogador j4 = new Jogador(barreira1, barreira2, mutex, counter, numeros, done, rolls, 3); 

        counter[0] = 0; // Inicializa o counter
        numeros[0] = 4;// Inicializa o numeros de threads
        done[0] = false;// Inicializa o booleano de termino   
        
        j1.start(); j2.start();j3.start();j4.start();// Inicia as threads

        try
        {
            j1.join();j2.join();j3.join();j4.join();// Espera as threads terminarem
        } 
        
        catch(Exception e)   
        {
            System.out.println("Erro: ");
            e.printStackTrace();

        }
    }
}