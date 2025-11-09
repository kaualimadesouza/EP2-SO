package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Main {
    public static final int TAMANHO_ARRANJO_THREADS = 100;

    public static List<Thread> inicializarThreads(BaseDados baseDados) {
        List<Thread> threads = new ArrayList<Thread>();

        // Inicializar posicoes vazias
        HashMap<Integer, Boolean> posicoesValidas = new HashMap<Integer, Boolean>();
        for (int i = 0; i < TAMANHO_ARRANJO_THREADS; i++) {
            threads.add(null);
            posicoesValidas.put(i, true);
        }
        
        for (int i = 0; i < TAMANHO_ARRANJO_THREADS; i++) {
            Random numeroAleatorio = new Random();

            Thread threadAleatoria;
            if (numeroAleatorio.nextBoolean()) {
                threadAleatoria = new ReaderThread(baseDados);
            } else {
                threadAleatoria = new WriterThread(baseDados);
            }

            int posicaoAleatoria;
            while(true) {
                posicaoAleatoria = numeroAleatorio.nextInt(TAMANHO_ARRANJO_THREADS);
                
                if (posicoesValidas.get(posicaoAleatoria)) {
                    // Adiciona Thread no arranjo
                    threads.set(posicaoAleatoria, threadAleatoria);

                    // Torna a posicao invalida
                    posicoesValidas.put(posicaoAleatoria, false);
                    break;
                }
            }
        }
        return threads;
    }

    public static void main(String[] args) throws InterruptedException {
        LeitorBD leitorBD = new LeitorBD("arquivos/bd.txt");
        BaseDados baseDados = leitorBD.carregarArranjos();

        // Inicializar threads de leitura e escrita no arranjo de tamanho 100
        List<Thread> arranjoThreads = inicializarThreads(baseDados);

        if (arranjoThreads.size() != TAMANHO_ARRANJO_THREADS) {
            throw new RuntimeException("Erro ao inicializar o arranjo de threads.");
        }

        // Iniciar todas as threads
        for (Thread thread : arranjoThreads) {
            thread.start();
        }

        // Esperar todas terminarem
        for (Thread thread : arranjoThreads) {
            thread.join();
        }
    }
}