import java.util.concurrent.Semaphore;
import java.util.Random;

public class Jogador extends Thread {
    private Semaphore barreira1;
    private Semaphore barreira2;
    private Semaphore mutex;
    private int[] contador;
    private int[] numero;
    private int pontos;
    private int order;
    private int[] rolls;
    private int gamble;private boolean[] done;

    public Jogador(Semaphore entrada, Semaphore saida, Semaphore mutex, int[] contador, int[] n, boolean[] done, int[] rolls, int order){// Construtor
    this.barreira1 = entrada; this.barreira2 = saida;this.mutex = mutex;this.contador = contador;this.numero = n;this.pontos = 10;this.done = done;this.rolls = rolls;this.order = order;// Inicializa os atributos
    }

    // Metodo de Apostar
    public void Apostar(){
        Random gen = new Random();// Gera um numero aleatorio
        try {
            this.gamble = gen.nextInt(this.pontos) + 1; // Gera um numero aleatorio entre 1 e o numero de pontos
            this.rolls[this.order] = gen.nextInt(10) + 1;// Gera um numero aleatorio entre 1 e 10
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    // Metodo de calcular o resultado
    public void Calculoderesultado(){
        try {
            if(this.rolls[this.order] == this.getBestpontos()){// Se o numero aleatorio for igual ao maior numero de pontos
                this.pontos += this.gamble;// Ganha a aposta
            }
            else{
                this.pontos -= this.gamble;
                if(this.pontos < 1){// Se o pontos do jogador for menor que 1
                    done[0] = true;// Finaliza o jogo
                    
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void run(){// Metodo run
        try {
            while(!done[0]){// Enquanto o jogo nao terminar
                this.Apostar(); // Chama o metodo de Apostar
                mutex.acquire(); // Pede acesso ao mutex
                this.contador[0]++;// Incrementa o contador
                if(this.contador[0] == this.numero[0]){ // Se o contador for igual ao numero de threads
                    System.out.println();// Imprime uma linha em branco
                    this.barreira2.acquire();  // Pede acesso a barreira de saida
                    this.barreira1.release(); //    Libera a barreira de entrada
                }
                this.mutex.release();
                this.barreira1.acquire();
                this.barreira1.release();
                this.Calculoderesultado();
                this.mutex.acquire();
                this.contador[0]--; 
                if(this.contador[0] == 0){
                    this.barreira1.acquire();
                    this.barreira2.release();
                    System.out.println();// Imprime uma linha em branco
                } 
                this.mutex.release(); 
                this.barreira2.acquire(); 
                this.barreira2.release();
                System.out.println("Jogador " + this.order + " apostou " + this.gamble + " pontos e tirou " + this.rolls[this.order] + " no dado, e agora tem "+ this.pontos + " pontos agora.");// Imprime o resultado da aposta
            }
        }
        catch(Exception e){
            System.out.println("Erro: ");
            e.printStackTrace();
        }
    }

    public int getBestpontos(){// Metodo para retornar o maior numero de pontos
        int best = -1;
        for (int i = 0; i < this.numero[0]; i++){
            if (this.rolls[i] > best){
                best = this.rolls[i];
            }
        }
        return best;// Retorna o melhor pontos
    }

}